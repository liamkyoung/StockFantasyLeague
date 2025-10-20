package Stock.Fantasy.League.market.service;

import Stock.Fantasy.League.market.domain.Quote;
import Stock.Fantasy.League.market.domain.Trade;

public interface PricePubSubPort {
    void publishQuote(Quote quote);
    void publishTrade(Trade trade);
}