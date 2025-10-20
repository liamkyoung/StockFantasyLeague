package Stock.Fantasy.League.market.domain;

public record Order(
        String id, String userId, String symbol, OrderSide side, OrderType type, long qty,
        Integer limitPxCents, long tsMillis
) { }
