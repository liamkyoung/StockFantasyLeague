package Stock.Fantasy.League.league.services;

import Stock.Fantasy.League.league.domain.League;
import Stock.Fantasy.League.league.infra.web.CreateLeagueRequest;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

@SpringBootTest
public class LeagueServiceTest {
    // Tests
        // 1. Create League
            // 2a. Cannot Create League
                // 1. User already participating in league
                // 2. Illegal arguments (duration,time,available,totalSpots)
            // 2b. Can Create League
        // 2. Get league leaderboard
        // 3. Get league by id
        // 4. Join league
            // 5. User Already in other league
            // 6. User does not exist
            // 7. Available Spots
        // 5. Leaving league
        // 6. Deleting league
            // 6a. If league admin, can delete
            // 6b. If not, cannot delete
}
