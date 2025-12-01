package Stock.Fantasy.League.orders.infra.persistence;

import Stock.Fantasy.League.orders.domain.Order;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class OrderRepositoryTest {
    // Test
        // 1. Find Order By id
        // 2. Create Order From Data
            // 2a. Invalid orders:
                // 2a. Symbol, qty, not enough cash in account?

        // 3. Update order state
}
