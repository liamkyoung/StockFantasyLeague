package Stock.Fantasy.League.orders.infra.web;

import Stock.Fantasy.League.orders.domain.OrderSide;
import Stock.Fantasy.League.orders.domain.OrderType;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CreateOrderRequest(
        @NotBlank String leagueId,
        @NotBlank String symbol,
        @NotNull OrderSide side,
        @NotNull OrderType type,

        @Min(value=1, message="Quantity must be at least 1")
        Integer qty,

        @Min(value=1, message="Price must be at least 1")
        Long price // IN CENTS
) {
}
