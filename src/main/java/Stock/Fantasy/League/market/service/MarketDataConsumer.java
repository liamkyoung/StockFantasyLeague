package Stock.Fantasy.League.market.service;

import Stock.Fantasy.League.market.domain.Quote;
import Stock.Fantasy.League.market.domain.Trade;

import java.util.List;

public interface MarketDataConsumer {
    void onBatchQuote(List<Quote> batch);
    void onQuote(Quote quote);
    void onTrade(Trade trade); // TODO
}
