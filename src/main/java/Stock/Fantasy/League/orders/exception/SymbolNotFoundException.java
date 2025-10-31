package Stock.Fantasy.League.orders.exception;

public class SymbolNotFoundException extends IllegalArgumentException {
    public SymbolNotFoundException(String message) {
        super(message);
    }
}
