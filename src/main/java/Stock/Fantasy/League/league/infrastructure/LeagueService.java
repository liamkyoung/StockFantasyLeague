package Stock.Fantasy.League.league.infrastructure;

import Stock.Fantasy.League.league.domain.CreateLeagueRequest;
import Stock.Fantasy.League.league.domain.League;
import Stock.Fantasy.League.user.User;

import java.util.Optional;
import java.util.UUID;

public interface LeagueService {
    League tryCreateLeague(CreateLeagueRequest request);
    void getLeaderboard(String leagueId);
    Optional<League> tryGetLeague(String leagueId);
    void getAllLeagues();
    boolean tryJoinLeague(String leagueId, String username);
    void leaveLeague(String userId, String leagueId);
    void deleteLeague(String leagueId);
}
