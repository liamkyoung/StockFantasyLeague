package Stock.Fantasy.League.portfolio.domain;

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
        uniqueConstraints=@UniqueConstraint(columnNames={"portfolio_id","symbol"})
)
public class Position {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name="portfolio_id", nullable = false)
    private Portfolio portfolio;

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
}
