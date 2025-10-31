package Stock.Fantasy.League.league.domain;

import Stock.Fantasy.League.portfolio.Portfolio;
import Stock.Fantasy.League.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
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
    @JoinColumn(name="league_id", nullable = false)
    private League league;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @OneToOne(mappedBy = "leagueUser", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private Portfolio portfolio;

    private Instant joinedAt = Instant.now();
}
