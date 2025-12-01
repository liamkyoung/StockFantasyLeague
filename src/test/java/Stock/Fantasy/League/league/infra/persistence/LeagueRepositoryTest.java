package Stock.Fantasy.League.league.infra.persistence;

import Stock.Fantasy.League.league.domain.League;
import Stock.Fantasy.League.league.domain.LeagueStatus;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.Duration;
import java.time.Instant;

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class LeagueRepositoryTest {

    @Autowired
    private LeagueRepository leagueRepository;

    @Test
    public void leagueRepository_saveAll_returnSavedLeague() {
        League league = League.builder()
                .leagueName("Example League")
                .createdAt(Instant.now())
                .availableSpots(10)
                .startTime(Instant.now())
                .endTime(Instant.now().plus(Duration.ofDays(7)))
                .status(LeagueStatus.SCHEDULED)
                .build();

        League l = leagueRepository.save(league);

        Assertions.assertNotNull(l);
    }

}
