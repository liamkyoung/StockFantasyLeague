package Stock.Fantasy.League.league.exception;

public class LeagueNotFoundException extends RuntimeException {
    public LeagueNotFoundException(String id) {
        super("League not found with ID: " + id);
    }
}