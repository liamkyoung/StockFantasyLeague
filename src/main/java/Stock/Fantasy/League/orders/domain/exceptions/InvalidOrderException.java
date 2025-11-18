package Stock.Fantasy.League.orders.domain.exceptions;

public class InvalidOrderException extends IllegalArgumentException {
    public InvalidOrderException(String message) {
        super(message);
    }
}
