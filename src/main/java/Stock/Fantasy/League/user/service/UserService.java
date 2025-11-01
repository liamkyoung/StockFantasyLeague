package Stock.Fantasy.League.user.service;

import Stock.Fantasy.League.user.domain.User;

import java.util.Optional;

public interface UserService {
    Optional<User> getUserByJwtToken(String jwtToken);
    Optional<String> getUsernameByJwtToken(String jwtToken);
}
