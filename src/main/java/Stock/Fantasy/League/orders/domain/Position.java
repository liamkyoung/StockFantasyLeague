package Stock.Fantasy.League.orders.domain;

import Stock.Fantasy.League.league.domain.LeagueUser;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(
        name="positions",
        uniqueConstraints=@UniqueConstraint(columnNames={"league_id","user_id","symbol"})
)
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Position {
    @Id @GeneratedValue(strategy = GenerationType.UUID)
    private UUID positionId;

    @ManyToOne(fetch = FetchType.LAZY) @JoinColumns({
            @JoinColumn(name="league_id", referencedColumnName="league_id"),
            @JoinColumn(name="user_id",   referencedColumnName="user_id")
    })
    @NotFound(action = NotFoundAction.IGNORE) // optional iff composite FK isn’t explicit
    @JsonIgnore
    private LeagueUser membership;            // or just store the two columns

    @Column(name="league_id", insertable=false, updatable=false)
    private UUID leagueId;

    @Column(name="user_id", insertable=false, updatable=false)
    private UUID userId;

    private String symbol;
    private long qty;
    private long avgCostCents;
    private long realizedPnlCents;
    private Instant lastUpdatedAt = Instant.now();
}