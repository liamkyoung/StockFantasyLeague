package Stock.Fantasy.League.league.infra.web;

import jakarta.validation.constraints.NotBlank;

public record JoinLeagueRequest(
        @NotBlank String leagueId
) {
}
