package Stock.Fantasy.League.util;

import Stock.Fantasy.League.market.domain.Quote;
import Stock.Fantasy.League.market.domain.Sector;
import Stock.Fantasy.League.market.domain.Stock;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.*;

@AllArgsConstructor
@Component
public class MockPriceManipulator implements PriceManipulator {
    private final Random rng;

    // Annualized drift (mu) and volatility (sigma) by sector (rough ballparks)
    private static final double DEFAULT_MU = 0.08;   // 8%/year drift
    private static final double DEFAULT_SIGMA = 0.30; // 30%/year vol

    private static final Map<Sector, Double> SECTOR_SIGMA = new EnumMap<>(Sector.class);
    static {
        SECTOR_SIGMA.put(Sector.INFORMATION_TECHNOLOGY, 0.45);
        SECTOR_SIGMA.put(Sector.COMMUNICATION_SERVICES, 0.40);
        SECTOR_SIGMA.put(Sector.CONSUMER_DISCRETIONARY, 0.35);
        SECTOR_SIGMA.put(Sector.INDUSTRIALS, 0.28);
        SECTOR_SIGMA.put(Sector.FINANCIALS, 0.27);
        SECTOR_SIGMA.put(Sector.ENERGY, 0.32);
        SECTOR_SIGMA.put(Sector.HEALTH_CARE, 0.25);
        SECTOR_SIGMA.put(Sector.CONSUMER_STAPLES, 0.18);
        SECTOR_SIGMA.put(Sector.MATERIALS, 0.26);
        SECTOR_SIGMA.put(Sector.REAL_ESTATE, 0.24);
        SECTOR_SIGMA.put(Sector.UTILITIES, 0.16);
        SECTOR_SIGMA.put(Sector.OTHER, 0.25);
    }

    // Trading time constants (approx): 252 trading days/year, 6.5 hours/day
    private static final double TRADING_SECONDS_PER_DAY = 6.5 * 60 * 60;
    private static final double TRADING_DAYS_PER_YEAR = 252.0;

    // Based off of Geometric Brownian Motion
    private long calculatePrice(Stock stock, Long lastPriceCents, double dtSeconds) {
        final double sigma = SECTOR_SIGMA.getOrDefault(stock.sector(), DEFAULT_SIGMA);

        // Convert small dt to "years" of trading time
        double dtYears = (dtSeconds / TRADING_SECONDS_PER_DAY) / TRADING_DAYS_PER_YEAR;

        // GBM step: S_{t+dt} = S_t * exp((mu - 0.5*sigma^2) dt + sigma sqrt(dt) Z)
        double s = lastPriceCents / 100.0;
        double z = rng.nextGaussian();
        double drift = (DEFAULT_MU - 0.5 * sigma * sigma) * dtYears;
        double diffusion = sigma * Math.sqrt(dtYears) * z;
        double sNext = s * Math.exp(drift + diffusion);

        // Optional: rare jump (Merton-style), tiny probability each tick
        // Tune lambda (per day), kappa (mean log jump), delta (std of log jump)
        double lambdaPerDay = 0.03;     // ~3% chance of at least one jump per day
        double jumpProb = lambdaPerDay * (dtSeconds / TRADING_SECONDS_PER_DAY);
        if (rng.nextDouble() < jumpProb) {
            double kappa = -0.005;      // small downward bias
            double delta = 0.03;        // jump volatility
            double j = Math.exp(kappa + delta * rng.nextGaussian());
            sNext *= j;
        }

        // Floor and round to cents
        long cents = Math.max(1, Math.round(sNext * 100.0));

        // (Optional) clamp extreme single-tick moves to keep it tame in UIs, e.g. ±3% per tick
        long minCents = (long) Math.max(1, Math.floor(lastPriceCents * 0.97));
        long maxCents = (long) Math.ceil(lastPriceCents * 1.03);
        if (cents < minCents) cents = minCents;
        if (cents > maxCents) cents = maxCents;

        return cents;
    }

    public Quote getQuote(Stock stock, Long priceInCents) {
        long newPrice = calculatePrice(stock, priceInCents, 0.1);
        return new Quote(stock.symbol(), newPrice, new Date().toInstant());
    }
}
