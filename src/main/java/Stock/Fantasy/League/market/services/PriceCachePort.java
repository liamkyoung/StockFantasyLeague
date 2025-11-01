package Stock.Fantasy.League.market.services;

import Stock.Fantasy.League.market.domain.Quote;

import java.util.List;
import java.util.UUID;

public interface PriceCachePort {
    void upsert(String symbol, Long price) throws IllegalArgumentException; // insert + update
    Long get(String symbol) throws IllegalArgumentException;
    List<Quote> getAllQuotes();
    List<UUID> getExecutableOrderIds();
}