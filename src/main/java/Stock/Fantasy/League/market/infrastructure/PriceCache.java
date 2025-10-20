package Stock.Fantasy.League.market.infrastructure;

import Stock.Fantasy.League.market.service.PriceCachePort;
import Stock.Fantasy.League.util.StockDirectory;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component
@Slf4j
public class PriceCache implements PriceCachePort {
    private final StringRedisTemplate redis;
    private final StockDirectory stockDirectory;

    @Override
    public void upsert(String symbol, Long priceInCents) throws IllegalArgumentException {
        try {
            if (!stockDirectory.isValidSymbol(symbol)) {
                throw new IllegalArgumentException("Invalid symbol: " + symbol);
            }

            redis.opsForValue().set("md:px:" + symbol, priceInCents.toString());
            redis.convertAndSend("quotes:" + symbol, priceInCents.toString());
        } finally {
            log.info("[UPDATED] {}:{}",  symbol, priceInCents);
        }

    }

    @Override
    public Long get(String symbol) {
        var v = redis.opsForValue().get("md:px:" + symbol);
        return v == null ? null : Long.parseLong(v);
    }
}
