package Stock.Fantasy.League.league.infra.persistence;

import Stock.Fantasy.League.league.domain.League;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface LeagueRepository extends JpaRepository<League, UUID> {
    Optional<League> findLeagueById(UUID id);
}
