package Stock.Fantasy.League.market.infrastructure;

import Stock.Fantasy.League.market.domain.Quote;
import Stock.Fantasy.League.orders.domain.Order;
import Stock.Fantasy.League.market.service.MarketDataConsumer;
import Stock.Fantasy.League.market.service.PriceCachePort;
import Stock.Fantasy.League.market.service.PricePubSubPort;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;


@Slf4j
@Service
@AllArgsConstructor
public class MockMarketDataConsumer implements MarketDataConsumer {
    private final PriceCachePort cache;
    private final PricePubSubPort notifier;
    private final QuoteBatchRepository quoteRepo;

    @Override
    public void onQuote(Quote quote) {
        cache.upsert(quote.symbol(), quote.priceInCents());
        notifier.publishQuote(quote);
    }

    @Scheduled(cron = "0 * * * * *")
    private void storeCurrentQuotes() {
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
        notifier.publishOrder(order);
    }
}
