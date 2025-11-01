package Stock.Fantasy.League.portfolio.domain;

import Stock.Fantasy.League.league.domain.League;
import Stock.Fantasy.League.league.domain.LeagueUser;
import Stock.Fantasy.League.user.domain.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;


@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "portfolios")
public class Portfolio {
    @Id @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "league_user_id", nullable = false, unique = true)
    private LeagueUser leagueUser;

    // convenience accessors (optional) if you want direct league/user:
    public League getLeague() { return leagueUser.getLeague(); }
    public User getUser() { return leagueUser.getUser(); }

    @Column(nullable = false)
    private long cashBalanceCents = 1000L * 100; // Default: players start with $1000.00
}