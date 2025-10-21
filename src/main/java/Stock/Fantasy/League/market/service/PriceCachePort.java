package Stock.Fantasy.League.market.service;

import Stock.Fantasy.League.market.domain.Quote;

import java.math.BigInteger;
import java.util.List;
import java.util.Map;

public interface PriceCachePort {
    void upsert(String symbol, Long price) throws IllegalArgumentException; // insert + update
    Long get(String symbol) throws IllegalArgumentException;
    List<Quote> getAllQuotes();

}