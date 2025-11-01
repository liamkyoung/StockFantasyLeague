package Stock.Fantasy.League.orders.services;

import Stock.Fantasy.League.orders.infra.web.CreateOrderRequest;
import Stock.Fantasy.League.orders.domain.Order;

import java.util.List;

public interface OrderService {
    Order tryCreateOrder(CreateOrderRequest order, String username);
    List<Order> getOrdersReadyToExecute(); // Get orders that are stored in redis/db that are now valid
}
