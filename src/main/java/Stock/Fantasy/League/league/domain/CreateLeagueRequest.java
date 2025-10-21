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
        @NotBlank @Size(min=2, max=30) String leagueName,
        @Min(2) @Max(10) int playerLimit,
        Instant startTime
) {
    public CreateLeagueRequest {
        if (startTime == null) startTime = Instant.now();
    }

}
