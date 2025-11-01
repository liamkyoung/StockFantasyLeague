package Stock.Fantasy.League.portfolio.infra.web;

import Stock.Fantasy.League.portfolio.domain.PositionDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PortfolioResponse {
    public List<PositionDto> positions;
    public long cashBalance;
}
