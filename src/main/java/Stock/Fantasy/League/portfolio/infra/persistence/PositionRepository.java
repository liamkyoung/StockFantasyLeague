package Stock.Fantasy.League.portfolio.infra.persistence;

import Stock.Fantasy.League.league.domain.LeagueUser;
import Stock.Fantasy.League.portfolio.domain.Position;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface PositionRepository extends JpaRepository<Position, UUID> {
    Optional<Position> findByLeagueUserAndSymbolIgnoreCase(LeagueUser leagueUser, String symbol);
}
