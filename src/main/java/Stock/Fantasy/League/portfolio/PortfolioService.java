package Stock.Fantasy.League.portfolio;

import Stock.Fantasy.League.orders.domain.Order;
import Stock.Fantasy.League.user.User;
import jakarta.transaction.NotSupportedException;

import java.util.UUID;

public interface PortfolioService {
    void updatePortfolio(User user, Order order) throws NotSupportedException;
    long getCashBalance(UUID userId);
    void subtractCashBalance(UUID userId, long amountInCents);
    void addCashBalance(UUID userId, long amountInCents);
}
