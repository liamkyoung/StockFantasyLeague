package Stock.Fantasy.League.orders.infrastructure;

import Stock.Fantasy.League.orders.domain.CreateOrderRequest;
import Stock.Fantasy.League.orders.domain.Order;

import java.util.List;
import java.util.UUID;

public interface OrderService {
    Order tryCreateOrder(CreateOrderRequest order, String username);
    List<Order> getOrdersReadyToExecute(); // Get orders that are stored in redis/db that are now valid
}
