package Stock.Fantasy.League.market.infrastructure;

import Stock.Fantasy.League.market.domain.Quote;
import Stock.Fantasy.League.market.domain.Stock;
import Stock.Fantasy.League.market.service.MarketDataConsumer;
import Stock.Fantasy.League.market.service.MarketExchange;
import Stock.Fantasy.League.orders.domain.Order;
import Stock.Fantasy.League.orders.infrastructure.OrderService;
import Stock.Fantasy.League.util.PriceManipulator;
import Stock.Fantasy.League.util.StockDirectory;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import org.springframework.scheduling.annotation.Scheduled;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Service
public class MockMarketExchange implements MarketExchange {
   private final MarketDataConsumer consumer;
   private final PriceManipulator priceManipulator;
   private final PriceCache priceCache;
   private final OrderService orderService;

   private final int POLL_RATE = 1;
    @Scheduled(fixedRate = 1000 * POLL_RATE)
    @Override
    public void start() {
        updateStockPrices();
        updateReadyOrders();
    }

    private void updateStockPrices() {
        List<Quote> quotes = new ArrayList<>();
        for (Stock s : StockDirectory.all()) {
            Long currentPrice = priceCache.get(s.symbol());
            if (currentPrice == null) {
                currentPrice = s.initPrice();
            }

            Quote q = priceManipulator.getQuote(s, currentPrice, POLL_RATE);
            quotes.add(q);
        }
        consumer.onBatchQuote(quotes);
    }

    private void updateReadyOrders() {
        List<Order> readyOrders = orderService.getOrdersReadyToExecute();
        consumer.executeOrders(readyOrders);
    }

    @Override
    public void stop() {

    }
}
