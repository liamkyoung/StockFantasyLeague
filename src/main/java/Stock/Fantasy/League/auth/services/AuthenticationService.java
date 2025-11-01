package Stock.Fantasy.League.auth.services;

import Stock.Fantasy.League.auth.domain.exceptions.EmailAlreadyExistsException;
import Stock.Fantasy.League.auth.infra.web.AuthenticationRequest;
import Stock.Fantasy.League.auth.infra.web.AuthenticationResponse;
import Stock.Fantasy.League.auth.infra.web.RegisterRequest;
import Stock.Fantasy.League.user.domain.Role;
import Stock.Fantasy.League.user.domain.User;
import Stock.Fantasy.League.user.infra.persistence.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthenticationService {

    private final UserRepository repository;
    private final PasswordEncoder encoder;
    private final JwtService jwtService;
    private final AuthenticationManager authManager;

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
        );

        var user = repository
                .findByEmail(request.getEmail())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        var jwtToken = jwtService.generateToken(user);

        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }

    @Transactional
    public AuthenticationResponse register(RegisterRequest request) {
        try {
            if (repository.existsByEmailIgnoreCase(request.getEmail())) {
                throw new EmailAlreadyExistsException(request.getEmail());
            }

            User user = User.builder()
                    .firstName(request.getFirstName())
                    .lastName(request.getLastName())
                    .email(request.getEmail())
                    .password(encoder.encode(request.getPassword()))
                    .role(Role.USER)
                    .isEnabled(true)
                    .build();

            repository.save(user);

            var jwtToken = jwtService.generateToken(user);
            log.info("Created user: {}", user.getEmail());

            return AuthenticationResponse.builder()
                    .token(jwtToken)
                    .build();
        } catch (DataIntegrityViolationException e) {
            log.error("Could not create user with email: {}", request.getEmail());
            throw new IllegalArgumentException("Could not create user", e);
        }
    }
}
