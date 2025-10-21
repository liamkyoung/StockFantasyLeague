package Stock.Fantasy.League.league.domain;

import jakarta.persistence.*;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(
        name="league_users",
        uniqueConstraints=@UniqueConstraint(columnNames={"league_id","user_id"})
)
public class LeagueUser {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID leagueUserId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="league_id")
    private League league;

    private UUID userId;
    private Instant joinedAt = Instant.now();
}
