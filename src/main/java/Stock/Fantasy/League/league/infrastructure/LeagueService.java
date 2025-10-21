package Stock.Fantasy.League.league.infrastructure;

import Stock.Fantasy.League.league.domain.CreateLeagueRequest;
import Stock.Fantasy.League.league.domain.League;

import java.util.Optional;

public interface LeagueService {
    League tryCreateLeague(CreateLeagueRequest request);
    void getLeaderboard(String leagueId);
    Optional<League> tryGetLeague(String leagueId);
    void getAllLeagues();
    void joinLeague(String userId, String leagueId);
    void leaveLeague(String userId, String leagueId);
    void deleteLeague(String leagueId);
}
