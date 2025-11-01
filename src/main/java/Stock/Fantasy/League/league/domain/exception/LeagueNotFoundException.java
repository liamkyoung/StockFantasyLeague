package Stock.Fantasy.League.league.domain.exception;

public class LeagueNotFoundException extends RuntimeException {
    public LeagueNotFoundException(String id) {
        super("League not found with ID: " + id);
    }
}