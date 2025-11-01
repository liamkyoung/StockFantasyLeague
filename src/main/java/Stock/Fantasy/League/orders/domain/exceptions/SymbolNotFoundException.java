package Stock.Fantasy.League.orders.domain.exceptions;

public class SymbolNotFoundException extends IllegalArgumentException {
    public SymbolNotFoundException(String message) {
        super(message);
    }
}
