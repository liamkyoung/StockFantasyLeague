package Stock.Fantasy.League.portfolio.infra.web;

import Stock.Fantasy.League.auth.services.JwtService;
import Stock.Fantasy.League.league.domain.LeagueUser;
import Stock.Fantasy.League.league.infra.persistence.LeagueUserRepository;
import Stock.Fantasy.League.portfolio.infra.persistence.CashEntryRepository;
import Stock.Fantasy.League.user.infra.persistence.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;
import java.util.ArrayList;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/portfolio")
public class PortfolioController {
    private final LeagueUserRepository leagueUserRepository;
    private final CashEntryRepository cashRepository;
    private final JwtService jwtService;
    private final UserRepository userRepository;

    @GetMapping
    public ResponseEntity<PortfolioResponse> getPlayerPortfolio(HttpServletRequest httpRequest) {
        String token = jwtService.getJwtFromRequest(httpRequest);
        String username = jwtService.extractUsername(token);
        var user = userRepository.findByEmail(username);

        if (user.isEmpty()) {
            throw new UsernameNotFoundException("User not found");
        }

        var leagueUser = leagueUserRepository.getLeagueUserByUser(user.get());

        var res = PortfolioResponse.builder()
                .positions(new ArrayList<>())
                .cashBalance(leagueUser.getCashBalanceCents())
                .build();

        return ResponseEntity.ok().body(res);
    }
}
