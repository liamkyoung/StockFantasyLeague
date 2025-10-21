package Stock.Fantasy.League.orders.infrastructure;

import Stock.Fantasy.League.orders.domain.Order;

public interface OrderService {
    void createOrder(Order order);
}
