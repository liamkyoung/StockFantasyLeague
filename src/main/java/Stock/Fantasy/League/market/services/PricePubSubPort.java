package Stock.Fantasy.League.market.services;

import Stock.Fantasy.League.market.domain.Quote;
import Stock.Fantasy.League.orders.domain.Order;

public interface PricePubSubPort {
    void publishQuote(Quote quote);
    void publishOrder(Order order);
}