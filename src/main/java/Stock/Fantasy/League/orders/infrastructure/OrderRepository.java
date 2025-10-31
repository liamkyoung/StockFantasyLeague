package Stock.Fantasy.League.orders.infrastructure;

import Stock.Fantasy.League.orders.domain.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface OrderRepository extends JpaRepository<Order, UUID> {
    List<Order> findByLeagueId(UUID leagueId);
    List<Order> findByUserId(UUID userId);
    List<Order> findByLeagueIdAndUserIdOrderByCreatedAtDesc(UUID leagueId, UUID userId);
}
