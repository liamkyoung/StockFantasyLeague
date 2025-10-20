package Stock.Fantasy.League.market.service;

import java.math.BigInteger;

public interface PriceCachePort {
    void upsert(String symbol, Long price) throws IllegalArgumentException; // insert + update
    Long get(String symbol) throws IllegalArgumentException;
}