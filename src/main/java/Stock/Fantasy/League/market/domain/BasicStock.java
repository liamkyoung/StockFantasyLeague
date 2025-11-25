package Stock.Fantasy.League.market.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.Getter;
import lombok.Setter;

import java.math.BigInteger;
import java.util.Locale;
import java.util.Optional;

public record BasicStock(
        String id,
        String symbol,
        String name,
        Sector sector,
        Industry industry,
        long initPrice
) implements Stock {

    public BasicStock {
        // Normalize & basic validation
        if (id == null || id.isBlank())
            throw new IllegalArgumentException("id is required");

        if (symbol == null || symbol.isBlank())
            throw new IllegalArgumentException("symbol is required");
        symbol = symbol.toUpperCase(Locale.ROOT);

        if (name == null || name.isBlank())
            throw new IllegalArgumentException("name is required");
        if (sector == null) throw new IllegalArgumentException("sector is required");

        if (initPrice < 0) throw new IllegalArgumentException("priceInCents must be greater than 0");
    }
}