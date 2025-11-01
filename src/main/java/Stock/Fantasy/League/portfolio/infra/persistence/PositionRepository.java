package Stock.Fantasy.League.portfolio.infra.persistence;

import Stock.Fantasy.League.portfolio.domain.Position;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface PositionRepository extends JpaRepository<Position, UUID> {
}
