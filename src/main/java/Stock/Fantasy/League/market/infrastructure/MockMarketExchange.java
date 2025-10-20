package Stock.Fantasy.League.market.infrastructure;

import Stock.Fantasy.League.market.domain.Quote;
import Stock.Fantasy.League.market.domain.Stock;
import Stock.Fantasy.League.market.service.MarketDataConsumer;
import Stock.Fantasy.League.market.service.MarketExchange;
import Stock.Fantasy.League.util.MockPriceManipulator;
import Stock.Fantasy.League.util.PriceManipulator;
import Stock.Fantasy.League.util.StockDirectory;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import org.springframework.scheduling.annotation.Scheduled;

@AllArgsConstructor
@Service
public class MockMarketExchange implements MarketExchange {
   private final StockDirectory stockDirectory;
   private final MarketDataConsumer consumer;
   private final PriceManipulator priceManipulator;
   private final PriceCache priceCache;

    @Scheduled(fixedRate = 100)
    @Override
    public void start() {
        for (Stock s : stockDirectory.all()) {
            Long currentPrice = priceCache.get(s.symbol());
            if (currentPrice == null) {
                currentPrice = s.initPrice();
            }

            Quote q = priceManipulator.getQuote(s, currentPrice);
            consumer.onQuote(q);
        }
    }

    @Override
    public void stop() {

    }
}
