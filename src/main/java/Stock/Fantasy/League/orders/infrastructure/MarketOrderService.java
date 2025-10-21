package Stock.Fantasy.League.orders.infrastructure;

import Stock.Fantasy.League.orders.domain.Order;
import org.springframework.stereotype.Component;

@Component
public class MarketOrderService implements OrderService {
    @Override
    public void createOrder(Order order) {
        // Add order to pub/sub queue
        // Will become an execution
    }
}
