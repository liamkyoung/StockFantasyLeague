package Stock.Fantasy.League.portfolio.infra.persistence;

import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class CashEntryRepositoryTest {
    // Tests
        // 1. Check current balance
        // 2. Insert deposit
        // 3. Insert withdrawl
            // 4. Deny if <= 0
}