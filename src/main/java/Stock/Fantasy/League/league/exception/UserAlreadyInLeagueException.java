package Stock.Fantasy.League.league.exception;

public class UserAlreadyInLeagueException extends RuntimeException {
    public UserAlreadyInLeagueException(String username, String leagueName) {
        super(username + " is already a member of league " + leagueName);
    }
}
