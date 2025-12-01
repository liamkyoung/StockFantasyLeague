package Stock.Fantasy.League.orders.infra.web;

import Stock.Fantasy.League.auth.services.JwtService;
import Stock.Fantasy.League.league.services.LeagueService;
import Stock.Fantasy.League.orders.services.OrderService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(controllers = OrderController.class)
public class OrderControllerTest {
    // Tests
        // 1. Create order
        // 2. Get order status
        // 3. Get orders by user id
        // 4. Get past orders
            // 4a. By ticker
            // 4b. All (pagination)


    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private OrderService orderService;

    @MockitoBean
    private LeagueService leagueService;

    @MockitoBean
    private JwtService jwtService;

    @Autowired
    private ObjectMapper objectMapper; // to serialize request body

}
