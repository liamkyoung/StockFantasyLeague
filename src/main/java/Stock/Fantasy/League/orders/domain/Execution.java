//package Stock.Fantasy.League.orders.domain;
//
//import jakarta.persistence.*;
//
//import java.time.Instant;
//import java.util.UUID;
//
//@Entity
//@Table(name="executions")
//public class Execution {
//    @Id
//    @GeneratedValue(strategy = GenerationType.UUID)
//    private UUID executionId;
//
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name="order_id")
//    private Order order;
//
//    private String symbol;
//    private long qty;
//    private long priceCents;
//    private Instant executedAt;
//}