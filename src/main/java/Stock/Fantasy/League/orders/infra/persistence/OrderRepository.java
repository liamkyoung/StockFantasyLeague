package Stock.Fantasy.League.orders.infra.persistence;

import Stock.Fantasy.League.orders.domain.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface OrderRepository extends JpaRepository<Order, UUID> {
}
