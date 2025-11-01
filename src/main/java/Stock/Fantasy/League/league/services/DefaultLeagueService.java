package Stock.Fantasy.League.league.services;

import Stock.Fantasy.League.league.infra.persistence.LeagueRepository;
import Stock.Fantasy.League.league.infra.persistence.LeagueUserRepository;
import Stock.Fantasy.League.league.infra.web.CreateLeagueRequest;
import Stock.Fantasy.League.league.domain.League;
import Stock.Fantasy.League.league.domain.LeagueStatus;
import Stock.Fantasy.League.league.domain.LeagueUser;
import Stock.Fantasy.League.league.domain.exception.LeagueFullException;
import Stock.Fantasy.League.league.domain.exception.UserAlreadyInLeagueException;
import Stock.Fantasy.League.user.domain.User;
import Stock.Fantasy.League.user.infra.persistence.UserRepository;
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
        try {
            Instant now = Instant.now();

            if (now.isAfter(request.startTime()))
                throw new IllegalArgumentException("Time is invalid");

            League league = League.builder()
                    .leagueName(request.leagueName())
                    .createdAt(Instant.now())
                    .startTime(now) // TODO: Push to start 1 day from start, end 1 week after.
                    .endTime(now.plus(Duration.ofDays(7)))
                    .availableSpots(request.availableSpots())
                    .totalSpots(request.availableSpots())
                    .status(LeagueStatus.SCHEDULED)
                    .build();

            return leagueRepository.save(league);
        } catch (IllegalArgumentException e) {
            log.error("Unable to create league: {}", e.getMessage());
            throw new IllegalArgumentException();
        }
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
    public void tryJoinLeague(String leagueId, String username) {
        League league = leagueRepository.findLeagueById(UUID.fromString(leagueId))
                .orElseThrow(() -> new IllegalArgumentException("League not found"));

        User user = userRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("Username not found"));

        if (leagueUserRepository.existsByLeague_IdAndUser_Id(league.getId(), user.getId())) {
            throw new UserAlreadyInLeagueException(username, league.getLeagueName());
        }

        int availableSpots = league.getAvailableSpots();
        if (availableSpots <= 0) {
            throw new LeagueFullException(league.getLeagueName());
        }

        league.setAvailableSpots(availableSpots - 1);

        var leagueUser = LeagueUser.builder()
                .user(user)
                .league(league)
                .joinedAt(Instant.now())
                .build();

        leagueUserRepository.save(leagueUser);
        leagueRepository.save(league);
    }

    @Override
    public void leaveLeague(String userId, String leagueId) {

    }

    @Override
    public void deleteLeague(String leagueId) {

    }
}
