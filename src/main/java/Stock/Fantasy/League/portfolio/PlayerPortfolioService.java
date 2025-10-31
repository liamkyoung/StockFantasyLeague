package Stock.Fantasy.League.portfolio;

import Stock.Fantasy.League.orders.domain.Order;
import Stock.Fantasy.League.user.User;
import jakarta.transaction.NotSupportedException;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class PlayerPortfolioService implements PortfolioService {
    @Override
    public void updatePortfolio(User user, Order order) throws NotSupportedException {
        throw new NotSupportedException();
    }

    @Override
    public long getCashBalance(UUID userId) {
        return 10_000L * 100;
    }

    @Override
    public void subtractCashBalance(UUID userId, long amountInCents) {
        // 1. Get user's cash balance
        // 2. Subtract value
    }

    @Override
    public void addCashBalance(UUID userId, long amountInCents) {
        // 1. Get user's cash balance
        // 2. Add value
    }
}
