package Stock.Fantasy.League.market.domain;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class QuoteResponse {
    private String symbol;
    private float price;
}
