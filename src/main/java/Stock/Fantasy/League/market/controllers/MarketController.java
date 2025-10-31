package Stock.Fantasy.League.market.controllers;

import Stock.Fantasy.League.market.domain.Quote;
import Stock.Fantasy.League.market.domain.QuoteResponse;
import Stock.Fantasy.League.market.service.PriceCachePort;
import Stock.Fantasy.League.orders.exception.SymbolNotFoundException;
import Stock.Fantasy.League.util.StockDirectory;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.math.RoundingMode;

@RestController
@RequestMapping("/api/v1/market")
@AllArgsConstructor
public class MarketController {

    private final PriceCachePort priceCache;

    @GetMapping("/{symbol}")
    public ResponseEntity<QuoteResponse> getCurrentPrice(@PathVariable("symbol") @NotNull String symbol) {
        if (!StockDirectory.isValidSymbol(symbol)) {
            throw new SymbolNotFoundException("symbol");
        }

        var priceInCents = priceCache.get(symbol);
        var response = QuoteResponse.builder()
                .price(new BigDecimal(priceInCents)
                        .divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP)
                        .floatValue())
                .symbol(symbol)
                .build();


        return ResponseEntity.ok(response);
    }
}
