package Stock.Fantasy.League.portfolio.domain;

import Stock.Fantasy.League.league.domain.LeagueUser;
import Stock.Fantasy.League.orders.domain.Execution;
import Stock.Fantasy.League.orders.domain.ExecutionDto;
import Stock.Fantasy.League.orders.domain.OrderSide;
import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;
import java.util.UUID;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(
        name="positions",
        uniqueConstraints=@UniqueConstraint(columnNames={"league_user_id","symbol"})
)
public class Position {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "league_user_id", nullable = false, unique = true)
    private LeagueUser leagueUser;

    @Column(nullable = false, length = 24)
    private String symbol;

    @Column(nullable = false)
    private int quantity;

    @Column(nullable = false)
    private long avgPriceCents = 0;

    @Column(nullable = false)
    private long realizedPnlCents = 0;

    @Column(nullable = false)
    private Instant updatedAt = Instant.now();

    @PrePersist
    public void prePersist() {
        if (updatedAt == null) updatedAt = Instant.now();
        if (symbol != null) symbol = symbol.toUpperCase();
    }

    @PreUpdate
    public void preUpdate() {
        updatedAt = Instant.now();
        if (symbol != null) symbol = symbol.toUpperCase();
    }

    public void updatePosition(ExecutionDto execution) {
        var orderSide = execution.orderSide();

        var positionTotalCents = (avgPriceCents * quantity);
        var executionPriceCents = execution.priceCents() * execution.quantity();

        updatedAt = execution.executedAt();
        realizedPnlCents += orderSide.equals(OrderSide.SELL) ? (execution.priceCents() - avgPriceCents) * execution.quantity(): 0;

        var newTotalCents = positionTotalCents + (orderSide.equals(OrderSide.SELL) ? -1 * executionPriceCents : executionPriceCents);
        var newQty = quantity + (orderSide.equals(OrderSide.SELL) ? -1 * execution.quantity() : execution.quantity());

        avgPriceCents = newQty != 0 ? newTotalCents / newQty : 0;
        quantity = newQty;
    }
}
