package Stock.Fantasy.League.portfolio.services;

import Stock.Fantasy.League.league.domain.LeagueUser;
import Stock.Fantasy.League.orders.domain.Execution;
import Stock.Fantasy.League.orders.domain.ExecutionDto;
import Stock.Fantasy.League.orders.domain.Order;
import Stock.Fantasy.League.orders.infra.web.CreateOrderRequest;
import Stock.Fantasy.League.user.domain.User;
import jakarta.transaction.NotSupportedException;

import java.util.UUID;

public interface PortfolioService {
    void updatePortfolio(LeagueUser user, ExecutionDto execution);
    boolean canCreateOrder(LeagueUser leagueUser, CreateOrderRequest orderRequest);
}
