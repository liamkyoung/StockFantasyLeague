package Stock.Fantasy.League.orders.domain;

import Stock.Fantasy.League.orders.infrastructure.OrderService;
import org.springframework.stereotype.Component;

public enum LedgerType {
    DEPOSIT,
    WITHDRAWAL,
    FEE,
}