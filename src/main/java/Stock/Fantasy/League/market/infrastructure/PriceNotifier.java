package Stock.Fantasy.League.market.infrastructure;

import Stock.Fantasy.League.market.domain.Quote;
import Stock.Fantasy.League.market.domain.Trade;
import Stock.Fantasy.League.market.service.PricePubSubPort;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class PriceNotifier implements PricePubSubPort {

    @Override
    public void publishQuote(Quote quote) {
        log.info("[NOTIFIED USER]: {}:{}", quote.symbol(), quote.priceInCents());
    }

    @Override
    public void publishTrade(Trade trade) {

    }
}
