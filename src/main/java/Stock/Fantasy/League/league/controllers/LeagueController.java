package Stock.Fantasy.League.league.controllers;

import Stock.Fantasy.League.league.domain.CreateLeagueRequest;
import Stock.Fantasy.League.league.domain.CreateLeagueResponse;
import Stock.Fantasy.League.league.domain.League;
import Stock.Fantasy.League.league.infrastructure.LeagueService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@AllArgsConstructor
@RestController
@RequestMapping("/api/v1/leagues")
public class LeagueController {
    private final LeagueService leagueService;
    @PostMapping
    public ResponseEntity<CreateLeagueResponse> createLeague(@Valid @RequestBody CreateLeagueRequest request) {
        League league = leagueService.tryCreateLeague(request);
        if (league != null) return ResponseEntity.ok(
                new CreateLeagueResponse(league.getLeagueId().toString())
        );

        return ResponseEntity.badRequest().body(new CreateLeagueResponse(""));
    }
}
