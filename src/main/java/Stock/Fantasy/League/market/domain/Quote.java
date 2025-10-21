package Stock.Fantasy.League.market.domain;

import java.time.Instant;

// Used to store value of stocks -- shown on graphs and TimeScaleDB
public record Quote(String symbol, long priceInCents, Instant ts) {}