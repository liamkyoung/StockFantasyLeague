package Stock.Fantasy.League.market.infrastructure;

import Stock.Fantasy.League.market.domain.Quote;
import Stock.Fantasy.League.orders.domain.Execution;
import Stock.Fantasy.League.orders.domain.Order;
import Stock.Fantasy.League.market.service.MarketDataConsumer;
import Stock.Fantasy.League.market.service.PriceCachePort;
import Stock.Fantasy.League.market.service.PricePubSubPort;
import Stock.Fantasy.League.orders.domain.OrderState;
import Stock.Fantasy.League.orders.domain.OrderType;
import Stock.Fantasy.League.orders.infrastructure.ExecutionRepository;
import Stock.Fantasy.League.orders.infrastructure.OrderRepository;
import Stock.Fantasy.League.portfolio.PortfolioService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import static org.yaml.snakeyaml.nodes.Tag.STR;


@Slf4j
@Service
@AllArgsConstructor
public class MockMarketDataConsumer implements MarketDataConsumer {
    private final PriceCachePort cache;
    private final PricePubSubPort notifier;
    private final QuoteBatchRepository quoteRepo;
    private final StringRedisTemplate redis;
    private final PortfolioService portfolioService;
    private final ExecutionRepository executionRepository;
    private final OrderRepository orderRepository;

    @Override
    public void onQuote(Quote quote) {
        cache.upsert(quote.symbol(), quote.priceInCents());
        notifier.publishQuote(quote);
    }

    @Scheduled(cron = "0 * * * * *")
    public void storeCurrentQuotes() {
        List<Quote> quotes = cache.getAllQuotes();
        int[] res = quoteRepo.saveAll(quotes);

        if (res.length == 0) {
            log.warn("[quotes.batchInsert] FAILED STORING QUOTES");
            return;
        }

        log.info("[quotes.batchInsert] STORED QUOTE SNAPSHOT IN DB");
    }


    public void onBatchQuote(List<Quote> quotes) {
        quotes.forEach(this::onQuote);
    }

    @Override
    public void onTrade(Order order) {
        // TODO: Store in Redis Sorted Set
    }

    @Transactional
    @Override
    public void executeOrders(List<Order> orders) {
        List<Order> savedOrders = new ArrayList<>(orders.size());
        List<Execution> savedExecutions = new ArrayList<>(orders.size());

        for (Order order : orders) {
            // TODO: Can optimize calls to portfolio service
            var balance = portfolioService.getCashBalance(order.getUserId());
            var symbol = order.getSymbol();
            var now = Instant.now();
            var maxQty = order.getQuantity();

            order.setUpdatedAt(now);

            var price = cache.get(symbol);

            // Get most that can be purchased
            // If user can afford, will buy max quantity of order
            while (maxQty != 0 && balance < price * maxQty) {
                maxQty--;
            }

            // TODO: Notify user was rejected with how much balance needed to buy
            if (maxQty == 0) {
                order.setState(OrderState.REJECTED);
                savedOrders.add(order);
                return;
            } else if (maxQty.equals(order.getQuantity())) {
                order.setState(OrderState.FILLED);
            } else {
                order.setState(OrderState.PARTIALLY_FILLED);
            }

            var execution = Execution.builder()
                    .executedAt(now)
                    .order(order)
                    .priceCents(price)
                    .symbol(symbol)
                    .quantity(maxQty)
                    .build();

            savedExecutions.add(execution);
            savedOrders.add(order);
        }

        executionRepository.saveAll(savedExecutions);
        orderRepository.saveAll(savedOrders);
    }

    @Override
    public void queueOrder(Order order) {
        String key = "order:" + order.getSymbol() + ":" + order.getSide().name();
        redis.opsForZSet().add(key, order.getId().toString(), order.getLimitPriceCents());
        notifier.publishOrder(order);
    }
}
