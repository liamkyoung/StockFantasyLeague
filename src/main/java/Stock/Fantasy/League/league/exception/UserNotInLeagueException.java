package Stock.Fantasy.League.league.exception;


public class UserNotInLeagueException extends RuntimeException {
    public UserNotInLeagueException(String username, String leagueName) {
        super(username + " is already a member of league " + leagueName);
    }
}
