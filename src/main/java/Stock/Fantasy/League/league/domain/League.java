package Stock.Fantasy.League.league.domain;

import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="leagues")
public class League {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID leagueId;

    private String leagueName;
    private Instant createdAt = Instant.now();
    private Instant startTime;
    private Instant endTime;

    @Enumerated(EnumType.STRING)
    private LeagueStatus status;

    private int userLimit = 10;
}

// league
// leagueId, createdAt, startTime, endTime, status, userLimit

// portfolio
// (userId, leagueId), cashValue, stockValue, totalValue, timestamp
// Index with leagueId with (timestamp, totalValue desc) (get rank/leaderboard)
// Updates every 1 minutes

// leagueToUser
// userId -> leagueId

// orders
// orderId -> (userId, leagueId), symbol, orderSide, orderType, shares, price, executedAt, state
// Note:
// - orderSide (BUY/SELL)
// - orderType: MARKET, BUY_LIMIT, SELL_LIMIT
// - state: cancelled, pending, executed

