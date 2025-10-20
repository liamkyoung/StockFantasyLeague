package Stock.Fantasy.League.market.domain;

import java.math.BigDecimal;
import java.time.Instant;

public record Trade(
        String symbol, OrderSide side, long qty, BigDecimal price, Instant ts
) {}