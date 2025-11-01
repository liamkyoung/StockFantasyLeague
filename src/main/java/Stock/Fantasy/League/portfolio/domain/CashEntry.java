package Stock.Fantasy.League.portfolio.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.UUID;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name="cash_ledger", indexes = {
        @Index(name = "idx_cash_portfolio_time", columnList = "portfolio_id, timestamp"),
})
public class CashEntry {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "portfolio_id", nullable = false, unique = true)
    private Portfolio portfolio;

    @Column(nullable = false)
    private Instant timestamp = Instant.now();

    @Column(nullable = false)
    private long amountInCents;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TransactionType type;

    @JoinColumn(name="execution_id", nullable = false)
    private UUID executionId;
}
