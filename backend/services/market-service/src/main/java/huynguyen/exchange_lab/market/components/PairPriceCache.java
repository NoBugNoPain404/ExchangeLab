package huynguyen.exchange_lab.market.components;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
@RequiredArgsConstructor
public class PairPriceCache {

    private final Map<String, BigDecimal> pairsCache = new ConcurrentHashMap<>();

    public void put(String symbol, BigDecimal price) {
        pairsCache.put(symbol, price);
    }

    public Map<String, BigDecimal> getAll() {
        return pairsCache;
    }
}
