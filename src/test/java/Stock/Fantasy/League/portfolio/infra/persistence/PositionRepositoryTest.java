package Stock.Fantasy.League.portfolio.infra.persistence;

import Stock.Fantasy.League.portfolio.domain.Position;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class PositionRepositoryTest {
    // Tests
        // 1. Check position of user and symbol
        // 2. Incorrect leagueUser / invalid
        // 3. Incorrect symbol
}
