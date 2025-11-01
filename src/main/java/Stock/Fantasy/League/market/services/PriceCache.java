package Stock.Fantasy.League.market.services;

import Stock.Fantasy.League.market.domain.Quote;
import Stock.Fantasy.League.market.domain.Stock;
import Stock.Fantasy.League.orders.domain.OrderSide;
import Stock.Fantasy.League.util.StockDirectory;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.*;

import static java.lang.Long.parseLong;

@AllArgsConstructor
@Component
@Slf4j
public class PriceCache implements PriceCachePort {
    private final StringRedisTemplate redis;

    @Override
    public void upsert(String symbol, Long priceInCents) throws IllegalArgumentException {
        if (!StockDirectory.isValidSymbol(symbol)) {
            throw new IllegalArgumentException("Invalid symbol: " + symbol);
        }

        redis.opsForValue().set("md:px:" + symbol, priceInCents.toString());
        redis.convertAndSend("quotes:" + symbol, priceInCents.toString());
    }

    @Override
    public Long get(String symbol) {
        var v = redis.opsForValue().get("md:px:" + symbol);
        return v == null ? null : parseLong(v);
    }

    @Override
    public List<Quote> getAllQuotes() {
        List<String> symbols = StockDirectory.all().stream()
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

    @Override
    public List<UUID> getExecutableOrderIds() {
        List<UUID> validOrderIds = new ArrayList<>();

        for (String symbol : StockDirectory.allSymbols()) {
            var currPrice = get(symbol);

            String key = "order:" + symbol + ":";
            var buyLimitOrders = getOrderIdsAbovePrice(key + OrderSide.BUY.name(), currPrice)
                    .stream().map(UUID::fromString).toList();

            var sellLimitOrders = getOrderIdsBelowPrice(key + OrderSide.SELL.name(), currPrice)
                    .stream().map(UUID::fromString).toList();

            validOrderIds.addAll(buyLimitOrders);
            validOrderIds.addAll(sellLimitOrders);
        }

        return validOrderIds;
    }

    // BUY LIMIT ORDER (If order is > price, bottom to buy)
    private Set<String> getOrderIdsAbovePrice(String key, Long minPrice) {
        var res = redis.opsForZSet()
                .rangeByScore(key, minPrice, Double.POSITIVE_INFINITY);

        redis.opsForZSet().removeRangeByScore(key, minPrice, Double.POSITIVE_INFINITY);

        return res;
    }

    // SELL LIMIT ORDER (If order < price, peak to sell)
    private Set<String> getOrderIdsBelowPrice(String key, Long maxPrice) {
        var res = redis.opsForZSet()
                .rangeByScore(key, Double.NEGATIVE_INFINITY, maxPrice);

        redis.opsForZSet().removeRangeByScore(key, Double.NEGATIVE_INFINITY, maxPrice);

        return res;
    }

}
