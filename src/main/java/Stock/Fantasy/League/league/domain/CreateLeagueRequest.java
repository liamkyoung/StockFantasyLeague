package Stock.Fantasy.League.league.domain;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.bind.DefaultValue;

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
        if (startTime == null) startTime = Instant.now();
    }

}
