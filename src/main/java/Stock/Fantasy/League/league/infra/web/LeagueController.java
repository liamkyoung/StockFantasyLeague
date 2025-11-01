package Stock.Fantasy.League.league.infra.web;

import Stock.Fantasy.League.auth.services.JwtService;
import Stock.Fantasy.League.league.domain.League;
import Stock.Fantasy.League.league.services.LeagueService;
import Stock.Fantasy.League.util.ObjectResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
