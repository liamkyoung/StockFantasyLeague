package Stock.Fantasy.League.market.services;

import Stock.Fantasy.League.market.domain.Quote;
import Stock.Fantasy.League.orders.domain.Order;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class PriceNotifier implements PricePubSubPort {

    @Override
    public void publishQuote(Quote quote) {
        // log.info("[NOTIFIED USER]: {}:{}", quote.symbol(), quote.priceInCents());
    }

    @Override
    public void publishOrder(Order order) {
        // Push order to redis pub/sub
        // Notify user that order is submitted

    }
}
