package Stock.Fantasy.League.league.infra.persistence;

import Stock.Fantasy.League.league.domain.LeagueUser;
import Stock.Fantasy.League.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface LeagueUserRepository extends JpaRepository<LeagueUser, UUID>  {
    boolean existsByLeague_IdAndUser_Id(UUID leagueId, UUID userId);

    Object getLeagueUserByUser(User user);

    Object getLeagueUserByLeagueUserId(UUID leagueUserId);
}
