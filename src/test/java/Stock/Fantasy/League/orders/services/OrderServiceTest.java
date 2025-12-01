package Stock.Fantasy.League.orders.services;

import Stock.Fantasy.League.orders.domain.Order;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class OrderServiceTest {
    // Tests
        // 1. Create order
            // 1a. Happy path
            // 1b. Errors
                // User not in league
                // symbol not found
                // BUYING when not enough cash
                // SELLING stock you dont have
        // 2. Get orders ready to execute

}
