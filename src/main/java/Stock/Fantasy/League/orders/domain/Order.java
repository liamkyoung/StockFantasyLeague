package Stock.Fantasy.League.orders.domain;

import jakarta.persistence.*;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(
        name="orders",
        indexes = @Index(name="idx_orders_user_time", columnList="league_id,user_id,created_at DESC")
)
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID orderId;

    private String clientOrderId;

    private UUID leagueId;
    private UUID userId;
    private String symbol;

    @Enumerated(EnumType.STRING)
    private OrderSide side;

    @Enumerated(EnumType.STRING)
    private OrderType type;

    private Long limitPriceCents;

    @Enumerated(EnumType.STRING)
    private OrderState state = OrderState.PENDING;

    private Instant createdAt = Instant.now();
    private Instant updatedAt = Instant.now();
}