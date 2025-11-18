package Stock.Fantasy.League.portfolio.domain.exceptions;

public class PortfolioNotFoundException extends IllegalArgumentException {
    public PortfolioNotFoundException(String message) {
        super(message);
    }
}
