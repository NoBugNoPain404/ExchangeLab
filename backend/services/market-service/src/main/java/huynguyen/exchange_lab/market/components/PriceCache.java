package huynguyen.exchange_lab.market.components;

import lombok.Getter;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Getter
@Component
public class PriceCache {

    private final Map<String, BigDecimal> prices = new ConcurrentHashMap<>();

    public void update(Map<String, BigDecimal> newPrices) {
        prices.putAll(newPrices);
    }

    public BigDecimal getPrice(String ticker) {
        return prices.get(ticker);
    }
}
