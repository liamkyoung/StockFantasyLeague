package Stock.Fantasy.League.market.infrastructure;

import Stock.Fantasy.League.market.domain.Quote;
import Stock.Fantasy.League.market.domain.Trade;
import Stock.Fantasy.League.market.service.MarketDataConsumer;
import Stock.Fantasy.League.market.service.PriceCachePort;
import Stock.Fantasy.League.market.service.PricePubSubPort;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;


@Slf4j
@Service
@AllArgsConstructor
public class MockMarketDataConsumer implements MarketDataConsumer {
    private final PriceCachePort cache;
    private final PricePubSubPort notifier;

    @Override
    public void onQuote(Quote quote) {
        cache.upsert(quote.symbol(), quote.priceInCents());
        notifier.publishQuote(quote); // Sends to users
    }

    @Override
    public void onTrade(Trade trade) {
        notifier.publishTrade(trade);
    }
}
