package Stock.Fantasy.League.league.infrastructure;

import Stock.Fantasy.League.league.domain.CreateLeagueRequest;
import Stock.Fantasy.League.league.domain.League;
import Stock.Fantasy.League.league.domain.LeagueStatus;
import Stock.Fantasy.League.league.domain.LeagueUser;
import Stock.Fantasy.League.user.User;
import Stock.Fantasy.League.user.UserRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
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
    private final LeagueUserRepository leagueUserRepository;
    private final UserRepository userRepository;


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
                    .availableSpots(request.availableSpots())
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
        return leagueRepository.findLeagueById(UUID.fromString(leagueId));
    }

    @Override
    public void getAllLeagues() {

    }

    @Transactional
    @Override
    public boolean tryJoinLeague(String leagueId, String username) {

        League league = leagueRepository.findLeagueById(UUID.fromString(leagueId))
                .orElseThrow(() -> new IllegalArgumentException("League not found"));

        User user = userRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("Username not found"));

        if (leagueUserRepository.existsByLeague_IdAndUser_Id(league.getId(), user.getId())) {
            return false;
        }

        int availableSpots = league.getAvailableSpots();
        if (availableSpots <= 0) {
            return false;
        }

        league.setAvailableSpots(availableSpots - 1);

        var leagueUser = LeagueUser.builder()
                .user(user)
                .league(league)
                .joinedAt(Instant.now())
                .build();

        leagueUserRepository.save(leagueUser);
        leagueRepository.save(league);

        return true;
    }

    @Override
    public void leaveLeague(String userId, String leagueId) {

    }

    @Override
    public void deleteLeague(String leagueId) {

    }
}
