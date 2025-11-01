package Stock.Fantasy.League.league.domain.exception;

public class LeagueFullException extends RuntimeException {
    public LeagueFullException(String leagueName) {
        super("League " + leagueName + " is full");
    }
}