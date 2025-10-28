package Stock.Fantasy.League.league.controllers;

import Stock.Fantasy.League.config.JwtService;
import Stock.Fantasy.League.league.domain.CreateLeagueRequest;
import Stock.Fantasy.League.league.domain.JoinLeagueRequest;
import Stock.Fantasy.League.league.domain.League;
import Stock.Fantasy.League.league.infrastructure.LeagueService;
import Stock.Fantasy.League.user.User;
import Stock.Fantasy.League.user.UserService;
import Stock.Fantasy.League.util.ObjectResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping("/api/v1/leagues")
public class LeagueController {
    private final LeagueService leagueService;
    private final JwtService jwtService;

    @PostMapping
    public ResponseEntity<ObjectResponse> createLeague(@Valid @RequestBody CreateLeagueRequest request) {
        League league = leagueService.tryCreateLeague(request);

        if (league != null) {
            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(new ObjectResponse(league.getId()));
        }

        return ResponseEntity.badRequest().build();
    }

    @PostMapping("/{id}/join")
    public ResponseEntity<String> joinLeague(@PathVariable("id") String leagueId, HttpServletRequest httpRequest) {
        String token = jwtService.getJwtFromRequest(httpRequest);
        String username = jwtService.extractUsername(token);
        leagueService.tryJoinLeague(leagueId, username);
        return ResponseEntity.ok().build();
    }
}
