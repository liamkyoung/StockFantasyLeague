package Stock.Fantasy.League.util;

import Stock.Fantasy.League.market.domain.Quote;
import Stock.Fantasy.League.market.domain.Stock;

public interface PriceManipulator {
    Quote getQuote(Stock stock, Long priceInCents, int pollRate);
}
