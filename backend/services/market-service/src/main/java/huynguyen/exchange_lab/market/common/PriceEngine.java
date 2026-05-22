package huynguyen.exchange_lab.market.common;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicLong;

public class PriceEngine {

    public record AssetConfig(String symbol, BigDecimal initialPrice, double mu, double baseSigma, double beta) {
    }

    public static final Map<String, AssetConfig> PRESETS = Map.of(
            "BTC",  new AssetConfig("BTC",  BigDecimal.valueOf(65000.00), 0.001,  0.03,  1.00),
            "ETH",  new AssetConfig("ETH",  BigDecimal.valueOf(3500.00),  0.002,  0.04,  0.95),
            "BNB",  new AssetConfig("BNB",  BigDecimal.valueOf(600.00),   0.002,  0.05,  0.85),
            "SOL",  new AssetConfig("SOL",  BigDecimal.valueOf(150.00),   0.004,  0.08,  1.20),
            "LINK", new AssetConfig("LINK", BigDecimal.valueOf(20.00),    0.002,  0.06,  0.75),
            "DOGE", new AssetConfig("DOGE", BigDecimal.valueOf(0.15),     0.000,  0.15,  1.50),
            "USDT", new AssetConfig("USDT", BigDecimal.valueOf(1.00),     0.000,  0.001, 0.00)
    );

    private final AtomicLong globalMarketShockBits = new AtomicLong(Double.doubleToLongBits(0.0));
    private final Map<String, BigDecimal> prices = new ConcurrentHashMap<>();
    private final Map<String, Double> sigmas = new ConcurrentHashMap<>();
    private final Map<String, Double> lastReturns = new ConcurrentHashMap<>();

    private static final double DT = 1.0 / 86400.0;
    private static final double SQRT_DT = Math.sqrt(DT);
    private static final double MAX_TOTAL_RETURN = 0.20;
    private static final double PANIC_PROB = 0.001;
    private static final double DECAY_RATE = 0.995;
    private static final double JUMP_PROB = 0.00025;
    private static final double JUMP_MIN = -0.10;
    private static final double JUMP_MAX = 0.15;
    private static final double MOMENTUM_FACTOR = 0.12;
    private static final double MAX_MOMENTUM = 0.02;
    private static final double SHOCK_PROB = 0.00010;
    private static final double SHOCK_DECAY = 0.96;
    private static final BigDecimal STABLE_MIN = BigDecimal.valueOf(0.97);
    private static final BigDecimal STABLE_MAX = BigDecimal.valueOf(1.03);

    public PriceEngine() {
        PRESETS.forEach((symbol, cfg) -> {
            prices.put(symbol, cfg.initialPrice);
            sigmas.put(symbol, cfg.baseSigma);
            lastReturns.put(symbol, 0.0);
        });
    }

    public Map<String, BigDecimal> tick() {
        updateGlobalShock();

        PRESETS.forEach((symbol, cfg) -> {
            BigDecimal newPrice;
            if ("MUSD".equals(symbol)) {
                newPrice = tickStablecoin(symbol, cfg);
            } else {
                newPrice = tickAsset(symbol, cfg);
            }
            prices.replace(symbol, newPrice);
        });

        return Map.copyOf(prices);
    }

    private BigDecimal tickAsset(String symbol, AssetConfig cfg) {
        BigDecimal currentPrice = prices.get(symbol);
        double sigma = sigmas.get(symbol);
        double previousReturn = lastReturns.get(symbol);

        if (ThreadLocalRandom.current().nextDouble() < PANIC_PROB) {
            double multiplier = 2.0 + ThreadLocalRandom.current().nextDouble() * 4.0;
            sigma = cfg.baseSigma * multiplier;
        } else {
            sigma = sigma * DECAY_RATE + cfg.baseSigma * (1.0 - DECAY_RATE);
        }
        sigmas.put(symbol, sigma);

        double momentum = Math.clamp(previousReturn * MOMENTUM_FACTOR, -MAX_MOMENTUM, MAX_MOMENTUM);
        double Z = ThreadLocalRandom.current().nextGaussian();
        double sigma2 = sigma * sigma;

        double globalMarketShock = Double.longBitsToDouble(globalMarketShockBits.get());
        double shockContribution = globalMarketShock * cfg.beta;

        double logReturn = (cfg.mu + momentum + shockContribution - sigma2 / 2.0) * DT
                + sigma * SQRT_DT * Z;

        double jumpReturn = 0.0;
        if (ThreadLocalRandom.current().nextDouble() < JUMP_PROB) {
            jumpReturn = ThreadLocalRandom.current().nextDouble(JUMP_MIN, JUMP_MAX);
        }

        double totalReturn = Math.clamp(logReturn + jumpReturn, -MAX_TOTAL_RETURN, MAX_TOTAL_RETURN);

        BigDecimal multiplier = BigDecimal.valueOf(Math.exp(totalReturn));
        BigDecimal newPrice = currentPrice.multiply(multiplier);

        BigDecimal floorPrice = cfg.initialPrice.multiply(BigDecimal.valueOf(0.0001));
        if (newPrice.compareTo(floorPrice) < 0) {
            newPrice = floorPrice;
        }

        lastReturns.put(symbol, Math.log(newPrice.doubleValue() / currentPrice.doubleValue()));

        return newPrice.setScale(8, RoundingMode.HALF_UP);
    }

    private BigDecimal tickStablecoin(String symbol, AssetConfig cfg) {
        BigDecimal price = prices.get(symbol);
        double theta = 2.5;
        double mean = 1.0;
        double Z = ThreadLocalRandom.current().nextGaussian();

        double drift = theta * (mean - price.doubleValue()) * DT;
        double diffusion = cfg.baseSigma * SQRT_DT * Z;

        BigDecimal change = BigDecimal.valueOf(drift + diffusion);
        BigDecimal newPrice = price.add(change);

        if (newPrice.compareTo(STABLE_MIN) < 0) return STABLE_MIN;
        if (newPrice.compareTo(STABLE_MAX) > 0) return STABLE_MAX;

        return newPrice.setScale(8, RoundingMode.HALF_UP);
    }

    private void updateGlobalShock() {
        if (ThreadLocalRandom.current().nextDouble() < SHOCK_PROB) {
            double newShock = ThreadLocalRandom.current().nextDouble(-0.08, 0.05);
            globalMarketShockBits.set(Double.doubleToLongBits(newShock));
        }

        globalMarketShockBits.updateAndGet(bits -> {
            double current = Double.longBitsToDouble(bits);
            return Double.doubleToLongBits(current * SHOCK_DECAY);
        });
    }

    public BigDecimal getPrice(String symbol) {
        return prices.getOrDefault(symbol, BigDecimal.ZERO);
    }

    public void setAllPrices(Map<String, BigDecimal> data) {
        prices.putAll(data);
    }

    public Map<String, BigDecimal> getAllPrices() {
        return Map.copyOf(prices);
    }
}