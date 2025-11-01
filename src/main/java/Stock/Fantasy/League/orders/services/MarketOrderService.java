package Stock.Fantasy.League.orders.services;

import Stock.Fantasy.League.league.domain.exception.UserNotInLeagueException;
import Stock.Fantasy.League.league.infra.persistence.LeagueUserRepository;
import Stock.Fantasy.League.market.services.MarketDataConsumer;
import Stock.Fantasy.League.market.services.PriceCachePort;
import Stock.Fantasy.League.orders.infra.web.CreateOrderRequest;
import Stock.Fantasy.League.orders.domain.OrderState;
import Stock.Fantasy.League.orders.domain.OrderType;
import Stock.Fantasy.League.orders.domain.Order;
import Stock.Fantasy.League.orders.domain.exceptions.SymbolNotFoundException;
import Stock.Fantasy.League.orders.infra.persistence.OrderRepository;
import Stock.Fantasy.League.user.domain.User;
import Stock.Fantasy.League.user.infra.persistence.UserRepository;
import Stock.Fantasy.League.util.StockDirectory;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@Service
@Slf4j
public class MarketOrderService implements OrderService {
    private final UserRepository userRepository;
    private final OrderRepository orderRepository;
    private final LeagueUserRepository leagueUserRepository;
    private final PriceCachePort priceCache;
    private final MarketDataConsumer market;

    @Override
    public Order tryCreateOrder(CreateOrderRequest request, String username) {
        // 1. Validate that user belongs in league
        // 2. Validate Request Params
            // 2a. Symbol, Side, Quantity, Price
        // 3. Order Execution Options
            // 3a. BUY/SELL - MARKET / LIMIT
            // 3b. BUY/SELL + LIMIT => EXECUTE ORDER NOW
            // 3c. BUY + MARKET => CHECK PRICE EVERY TICK, PURCHASE WHEN PRICE <= LIMIT
            // 3d. SELL + MARKET => CHECK PRICE EVERY TICK, SELL WHEN PRICE >= LIMIT
        // 4. Send to Kafka/Redis Stream

        try {
            User user = userRepository.findByEmail(username)
                    .orElseThrow(() -> new UsernameNotFoundException("Username not found"));

            var leagueId = UUID.fromString(request.leagueId());
            var userId = user.getId();

            if (!leagueUserRepository.existsByLeague_IdAndUser_Id(leagueId, userId)) {
                throw new UserNotInLeagueException(username, request.leagueId());
            }

            if (!StockDirectory.isValidSymbol(request.symbol())) {
                throw new SymbolNotFoundException(request.symbol());
            }

            var order = Order.builder()
                    .createdAt(Instant.now())
                    .symbol(request.symbol())
                    .state(OrderState.PENDING)
                    .leagueId(leagueId)
                    .userId(userId)
                    .type(request.type())
                    .side(request.side())
                    .quantity(request.qty())
                    .limitPriceCents(request.price())
                    .build();

            var savedOrder = orderRepository.save(order);

            if (savedOrder.getType() == OrderType.MARKET) {
                market.executeOrders(List.of(savedOrder));
            } else {
                market.queueOrder(savedOrder);
            }

            return savedOrder;
        } catch (RuntimeException e) {
            log.error("Unable to create order for user: {}", username);
            throw new IllegalArgumentException(e);
        }
    }

    @Override
    public List<Order> getOrdersReadyToExecute() {
        var validOrderIds = priceCache.getExecutableOrderIds();

        if (!validOrderIds.isEmpty()) {
            log.info("Orders Ready to Execute: {}", validOrderIds.size());
        }

        return orderRepository.findAllById(validOrderIds);
    }
}