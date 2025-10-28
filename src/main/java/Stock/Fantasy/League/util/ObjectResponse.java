package Stock.Fantasy.League.util;

import jakarta.validation.constraints.NotBlank;

import java.util.UUID;

public record ObjectResponse(@NotBlank UUID id) {
}
