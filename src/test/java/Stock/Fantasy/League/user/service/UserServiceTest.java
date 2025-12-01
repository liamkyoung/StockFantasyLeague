package Stock.Fantasy.League.user.service;

import Stock.Fantasy.League.user.domain.User;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class UserServiceTest {
    //    Optional<User> getUserByJwtToken(String jwtToken);
    //    Optional<String> getUsernameByJwtToken(String jwtToken);
}
