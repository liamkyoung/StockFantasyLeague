package Stock.Fantasy.League.league.services;

import Stock.Fantasy.League.league.infra.web.CreateLeagueRequest;
import Stock.Fantasy.League.league.domain.League;

import java.util.Optional;

public interface LeagueService {
    League tryCreateLeague(CreateLeagueRequest request);
    void getLeaderboard(String leagueId);
    Optional<League> tryGetLeague(String leagueId);
    void getAllLeagues();
    void tryJoinLeague(String leagueId, String username);
    void leaveLeague(String userId, String leagueId);
    void deleteLeague(String leagueId);
}
