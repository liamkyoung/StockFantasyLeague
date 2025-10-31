package Stock.Fantasy.League.orders.controllers;

import Stock.Fantasy.League.config.JwtService;
import Stock.Fantasy.League.league.exception.LeagueNotFoundException;
import Stock.Fantasy.League.league.infrastructure.LeagueService;
import Stock.Fantasy.League.orders.domain.CreateOrderRequest;
import Stock.Fantasy.League.orders.exception.SymbolNotFoundException;
import Stock.Fantasy.League.orders.infrastructure.OrderService;
import Stock.Fantasy.League.util.ObjectResponse;
import Stock.Fantasy.League.util.StockDirectory;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping("/api/v1/orders")
public class OrderController {
    private final OrderService orderService;
    private final LeagueService leagueService;
    private final JwtService jwtService;
    // Create an order
    @PostMapping
    public ResponseEntity<ObjectResponse> createOrder(
            @Valid @RequestBody CreateOrderRequest body,
            HttpServletRequest httpRequest
    ) {
        if (leagueService.tryGetLeague(body.leagueId()).isEmpty()) {
            throw new LeagueNotFoundException(body.leagueId());
        }

        String token = jwtService.getJwtFromRequest(httpRequest);
        String username = jwtService.extractUsername(token);

        var order = orderService.tryCreateOrder(body, username);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new ObjectResponse(order.getId()));
    }

    // Get an existing order

    // Market executes an order ()

    //
}
