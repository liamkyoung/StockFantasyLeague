package Stock.Fantasy.League.orders.domain;

import jakarta.persistence.Column;

import java.time.Instant;
import java.util.UUID;

public record ExecutionDto(UUID id, String symbol, int quantity, long priceCents, Instant executedAt, OrderSide orderSide) {

}
