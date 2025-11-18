package Stock.Fantasy.League.portfolio.infra.persistence;

import Stock.Fantasy.League.league.domain.LeagueUser;
import Stock.Fantasy.League.portfolio.domain.Portfolio;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface PortfolioRepository extends JpaRepository<Portfolio, UUID> {
    Object getPortfolioById(UUID id);
    Optional<Portfolio> findByLeagueUser(LeagueUser leagueUser);
}
