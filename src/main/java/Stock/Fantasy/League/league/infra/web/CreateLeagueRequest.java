package Stock.Fantasy.League.league.infra.web;

import jakarta.validation.constraints.*;

import java.time.Duration;
import java.time.Instant;

public record CreateLeagueRequest (
        @NotBlank
        @Size(min = 3, max = 50, message = "League name must be between 3 and 50 characters")
        String leagueName,

        @Min(value = 2, message = "A league must have at least 2 available spots")
        @Max(value = 10, message = "A league can have at most 10 available spots")
        int availableSpots,

        Instant startTime
) {
    public CreateLeagueRequest {
        if (startTime == null) {
            startTime = Instant.now().plus(Duration.ofHours(1));
        } else if (startTime.isBefore(Instant.now())) {
            throw new IllegalArgumentException("Start time must be in the present or future");
        }
    }

}
