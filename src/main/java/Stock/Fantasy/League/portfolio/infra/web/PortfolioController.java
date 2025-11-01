package Stock.Fantasy.League.portfolio.infra.web;

import Stock.Fantasy.League.auth.services.JwtService;
import Stock.Fantasy.League.league.domain.LeagueUser;
import Stock.Fantasy.League.league.infra.persistence.LeagueUserRepository;
import Stock.Fantasy.League.portfolio.domain.Portfolio;
import Stock.Fantasy.League.portfolio.domain.PositionDto;
import Stock.Fantasy.League.portfolio.infra.persistence.CashEntryRepository;
import Stock.Fantasy.League.portfolio.infra.persistence.PortfolioRepository;
import Stock.Fantasy.League.user.domain.User;
import Stock.Fantasy.League.user.infra.persistence.UserRepository;
import io.lettuce.core.dynamic.annotation.Param;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;
import java.util.ArrayList;
import java.util.UUID;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/portfolio")
public class PortfolioController {
    private final LeagueUserRepository leagueUserRepository;
    private final CashEntryRepository cashRepository;
    private final JwtService jwtService;
    private final PortfolioRepository portfolioRepository;
    private final UserRepository userRepository;

    @GetMapping
    public ResponseEntity<PortfolioResponse> getPlayerPortfolio(HttpServletRequest httpRequest) {
        String token = jwtService.getJwtFromRequest(httpRequest);
        String username = jwtService.extractUsername(token);
        var user = userRepository.findByEmail(username);

        if (user.isEmpty()) {
            throw new UsernameNotFoundException("User not found");
        }

        var leagueUser = (LeagueUser)leagueUserRepository.getLeagueUserByUser(user.get());

        // TODO: Change to get list<position> and turn into List<PositionDto>
        var portfolio = (Portfolio)portfolioRepository.getPortfolioById(leagueUser.getPortfolio().getId());
        long cash = cashRepository.currentBalance(portfolio.getId(), Instant.now());

        var res = PortfolioResponse.builder()
                .positions(new ArrayList<>())
                .cashBalance(cash)
                .build();

        return ResponseEntity.ok().body(res);
    }
}
