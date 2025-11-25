package Stock.Fantasy.League.portfolio.services;

import Stock.Fantasy.League.league.domain.LeagueUser;
import Stock.Fantasy.League.league.infra.persistence.LeagueUserRepository;
import Stock.Fantasy.League.market.services.PriceCachePort;
import Stock.Fantasy.League.orders.domain.*;
import Stock.Fantasy.League.orders.infra.web.CreateOrderRequest;
import Stock.Fantasy.League.portfolio.domain.CashEntry;
import Stock.Fantasy.League.portfolio.domain.Position;
import Stock.Fantasy.League.portfolio.domain.TransactionType;
import Stock.Fantasy.League.portfolio.domain.exceptions.PortfolioNotFoundException;
import Stock.Fantasy.League.portfolio.infra.persistence.CashEntryRepository;
import Stock.Fantasy.League.portfolio.infra.persistence.PositionRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@AllArgsConstructor
@Service
public class PlayerPortfolioService implements PortfolioService {
    private final PriceCachePort priceCache;
    private final PositionRepository positionRepository;
    private final CashEntryRepository cashEntryRepository;
    private final LeagueUserRepository leagueUserRepository;

    // positions, cash ledger entry, portfolio update
    @Override
    public void updatePortfolio(LeagueUser leagueUser, ExecutionDto execution) {
        var totalPrice = execution.priceCents() * execution.quantity();
        var cashEntry = CashEntry.builder()
                    .leagueUser(leagueUser)
                        .amountInCents(totalPrice)
                        .type(execution.orderSide().equals(OrderSide.BUY) ?
                                TransactionType.WITHDRAWAL : TransactionType.DEPOSIT)
                        .timestamp(execution.executedAt())
                        .build();

        var position = positionRepository.findByLeagueUserAndSymbolIgnoreCase(leagueUser, execution.symbol());

        if (position.isPresent()) {
            position.get().updatePosition(execution);

            positionRepository.save(position.get());
        } else if (execution.orderSide().equals(OrderSide.BUY)) {
            var newPosition = Position.builder()
                    .leagueUser(leagueUser)
                    .symbol(execution.symbol())
                    .avgPriceCents(execution.priceCents())
                    .quantity(execution.quantity())
                    .realizedPnlCents(0)
                    .build();

            positionRepository.save(newPosition);
        }

        if (execution.orderSide().equals(OrderSide.BUY)) {
            leagueUser.updateCashBalance(-1 * totalPrice);
        } else {
            leagueUser.updateCashBalance(totalPrice);
        }


        leagueUserRepository.save(leagueUser);
        cashEntryRepository.save(cashEntry);
    }


    @Override
    public boolean canCreateOrder(LeagueUser leagueUser, CreateOrderRequest request) {

        var balance = leagueUser.getCashBalanceCents();
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
                var position = positionRepository.findByLeagueUserAndSymbolIgnoreCase(leagueUser, request.symbol());
                yield position.isPresent() && position.get().getQuantity() >= request.qty();
            }
        };
    }
}
