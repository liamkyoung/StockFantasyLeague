package Stock.Fantasy.League.league.domain;

import jakarta.validation.constraints.NotBlank;

public record JoinLeagueRequest(
        @NotBlank String leagueId
) {
}
