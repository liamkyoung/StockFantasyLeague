package Stock.Fantasy.League.league.infrastructure;

import Stock.Fantasy.League.league.domain.CreateLeagueRequest;
import Stock.Fantasy.League.league.domain.League;
import Stock.Fantasy.League.league.domain.LeagueStatus;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@AllArgsConstructor
@Service
public class DefaultLeagueService implements LeagueService {
    private final LeagueRepository leagueRepository;


    @Override
    public League tryCreateLeague(CreateLeagueRequest request) {
        Instant now = Instant.now();

        if (now.isBefore(request.startTime()) ||
            now.isAfter(now.plus(Duration.ofDays(14)))
        ) return null;

        try {
            League league = League.builder()
                    .leagueName(request.leagueName())
                    .createdAt(Instant.now())
                    .startTime(now) // TODO: Push to start 1 day from start, end 1 week after.
                    .endTime(now.plus(Duration.ofDays(7)))
                    .status(LeagueStatus.SCHEDULED)
                    .build();


            return leagueRepository.save(league);
        } catch (Exception e) {
            log.error("Unable to create league: {}", e.getMessage());
        }

        return null;
    }

    @Override
    public void getLeaderboard(String leagueId) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Optional<League> tryGetLeague(String leagueId) {
        return leagueRepository.findLeagueByLeagueId(UUID.fromString(leagueId));
    }

    @Override
    public void getAllLeagues() {

    }

    @Override
    public void joinLeague(String userId, String leagueId) {

    }

    @Override
    public void leaveLeague(String userId, String leagueId) {

    }

    @Override
    public void deleteLeague(String leagueId) {

    }
}
