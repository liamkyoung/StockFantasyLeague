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
        var qty = execution.quantity();
        var isSelling = orderSide.equals(OrderSide.SELL);

        var totalCents = (avgPriceCents * quantity);
        var executionPriceCents = execution.priceCents() * qty;

        updatedAt = execution.executedAt();
        realizedPnlCents += isSelling ? (execution.priceCents() - avgPriceCents) * qty : 0;

        var newTotalCents = totalCents + ((isSelling ? -1 : 1) * executionPriceCents);
        quantity += ((isSelling ? -1 : 1) * qty);

        avgPriceCents = quantity != 0 ? newTotalCents / quantity : 0;
    }
}
