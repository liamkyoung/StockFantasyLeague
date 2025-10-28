package Stock.Fantasy.League.user;

import java.util.Optional;
import java.util.UUID;

public interface UserService {
    Optional<User> getUserByJwtToken(String jwtToken);
    Optional<String> getUsernameByJwtToken(String jwtToken);
}
