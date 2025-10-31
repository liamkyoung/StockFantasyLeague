package Stock.Fantasy.League.orders.infrastructure;

import Stock.Fantasy.League.orders.domain.Execution;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ExecutionRepository extends JpaRepository<Execution, UUID> {
}
