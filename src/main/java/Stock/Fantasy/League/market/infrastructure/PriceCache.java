package Stock.Fantasy.League.market.infrastructure;

import Stock.Fantasy.League.market.domain.Quote;
import Stock.Fantasy.League.market.domain.Stock;
import Stock.Fantasy.League.market.service.PriceCachePort;
import Stock.Fantasy.League.util.StockDirectory;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.*;

import static java.lang.Long.parseLong;

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
            double dollarAmt = (double)priceInCents/100;
            // log.info("[price.update] {}:${}",  symbol, String.format("%.2f", dollarAmt));
        }

    }

    @Override
    public Long get(String symbol) {
        var v = redis.opsForValue().get("md:px:" + symbol);
        return v == null ? null : parseLong(v);
    }

    @Override
    public List<Quote> getAllQuotes() {
        List<String> symbols = stockDirectory.all().stream()
                .map(Stock::symbol)
                .toList();

        List<String> keys = symbols.stream().map(s -> "md:px:" + s).toList();
        List<String> vals = redis.opsForValue().multiGet(keys);
        List<Quote> quotes = new ArrayList<>(symbols.size());

        if (vals == null || vals.isEmpty()) return quotes;

        for (int i = 0; i < symbols.size(); i++) {
            String v = vals.get(i);
            if (v != null) {
                quotes.add(
                        new Quote(
                                symbols.get(i),
                                Long.parseLong(v),
                                new Date().toInstant()
                        )
                );
            }
        }

        return quotes;
    }


}
