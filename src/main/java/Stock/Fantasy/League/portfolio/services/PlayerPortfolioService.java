package Stock.Fantasy.League.portfolio.services;

import Stock.Fantasy.League.league.domain.LeagueUser;
import Stock.Fantasy.League.market.services.PriceCachePort;
import Stock.Fantasy.League.orders.domain.*;
import Stock.Fantasy.League.orders.domain.exceptions.InvalidOrderException;
import Stock.Fantasy.League.orders.infra.web.CreateOrderRequest;
import Stock.Fantasy.League.portfolio.domain.CashEntry;
import Stock.Fantasy.League.portfolio.domain.Portfolio;
import Stock.Fantasy.League.portfolio.domain.Position;
import Stock.Fantasy.League.portfolio.domain.TransactionType;
import Stock.Fantasy.League.portfolio.domain.exceptions.PortfolioNotFoundException;
import Stock.Fantasy.League.portfolio.infra.persistence.CashEntryRepository;
import Stock.Fantasy.League.portfolio.infra.persistence.PortfolioRepository;
import Stock.Fantasy.League.portfolio.infra.persistence.PositionRepository;
import Stock.Fantasy.League.user.domain.User;
import jakarta.transaction.NotSupportedException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@AllArgsConstructor
@Service
public class PlayerPortfolioService implements PortfolioService {
    private final PortfolioRepository portfolioRepository;
    private final PriceCachePort priceCache;
    private final PositionRepository positionRepository;
    private final CashEntryRepository cashEntryRepository;

    // positions, cash ledger entry, portfolio update
    @Override
    public void updatePortfolio(LeagueUser leagueUser, ExecutionDto execution) {

        var portfolio = getPortfolio(leagueUser);
        var totalPrice = execution.priceCents() * execution.quantity();
        var cashEntry = CashEntry.builder()
                        .portfolio(portfolio)
                        .amountInCents(totalPrice)
                        .type(execution.orderSide().equals(OrderSide.BUY) ?
                                TransactionType.WITHDRAWAL : TransactionType.DEPOSIT)
                        .timestamp(execution.executedAt())
                        .build();

        var position = positionRepository.findByPortfolio_IdAndSymbolIgnoreCase(portfolio.getId(), execution.symbol());

        if (position.isPresent()) {
            position.get().updatePosition(execution);

            positionRepository.save(position.get());
        } else if (execution.orderSide().equals(OrderSide.BUY)) {
            var newPosition = Position.builder()
                    .symbol(execution.symbol())
                    .avgPriceCents(execution.priceCents())
                    .quantity(execution.quantity())
                    .portfolio(portfolio)
                    .realizedPnlCents(0)
                    .build();

            positionRepository.save(newPosition);
        }

        if (execution.orderSide().equals(OrderSide.BUY)) {
            portfolio.updateCashBalance(-1 * totalPrice);
        } else {
            portfolio.updateCashBalance(totalPrice);
        }


        portfolioRepository.save(portfolio);
        cashEntryRepository.save(cashEntry);
    }

    @Override
    public long getCashBalance(LeagueUser leagueUser) throws PortfolioNotFoundException {
        return getPortfolio(leagueUser).getCashBalanceCents();
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

    @Override
    public boolean canCreateOrder(LeagueUser leagueUser, CreateOrderRequest request) {
        var portfolio = getPortfolio(leagueUser);

        var balance = portfolio.getCashBalanceCents();
        var orderType = request.type();
        var qty = request.qty();
        Long price;

        if (orderType.equals(OrderType.MARKET)) {
            price = priceCache.get(request.symbol());
        } else {
            price = request.price();
        }

        return switch (request.side()) {
            case OrderSide.BUY -> balance >= price * qty;
            case OrderSide.SELL -> {
                var position = positionRepository.findByPortfolio_IdAndSymbolIgnoreCase(portfolio.getId(), request.symbol());
                yield position.isPresent() && position.get().getQuantity() >= request.qty();
            }
        };
    }

    private Portfolio getPortfolio(LeagueUser leagueUser) {
        return portfolioRepository.findByLeagueUser(leagueUser)
                .orElseThrow(() -> new PortfolioNotFoundException(leagueUser.toString()));
    }
}
