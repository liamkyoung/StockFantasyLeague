package Stock.Fantasy.League.market.infra.web;

import Stock.Fantasy.League.league.infra.web.LeagueController;
import Stock.Fantasy.League.market.domain.QuoteResponse;
import Stock.Fantasy.League.market.services.PriceCachePortTest;
import Stock.Fantasy.League.orders.domain.exceptions.SymbolNotFoundException;
import Stock.Fantasy.League.util.StockDirectoryTest;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.math.RoundingMode;

@WebMvcTest(controllers = MarketController.class)
public class MarketControllerTest { }
