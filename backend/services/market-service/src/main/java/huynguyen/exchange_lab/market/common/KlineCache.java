package huynguyen.exchange_lab.market.common;

import huynguyen.exchange_lab.market.components.MarketRegistry;
import huynguyen.exchange_lab.market.entities.KlineData;
import huynguyen.exchange_lab.market.enums.KlineIntervalEnum;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
@RequiredArgsConstructor
public class KlineCache {

    private final MarketRegistry marketRegistry;

    private final Map<String, Map<Long, KlineData>> data = new ConcurrentHashMap<>();

    private final PriceEngine priceEngine;

    public void init() {
        marketRegistry.getPairs()
                .keySet()
                .forEach(
                        symbol -> {
                            Map<Long, KlineData> intervals = new ConcurrentHashMap<>();

                            for (KlineIntervalEnum e : KlineIntervalEnum.values()) {
                                KlineData klineData = new KlineData(marketRegistry.get(symbol)
                                        .getId());
                                klineData.setAll(priceEngine.getPrice(symbol));
                                intervals.put(
                                        e.getMillis(),
                                        klineData
                                );
                            }
                            data.put(symbol, intervals);
                        }
                );

    }

    public KlineData get(
            String symbol,
            Long interval
    ) {
        return data.get(symbol).get(interval);
    }

    public void put(
            String symbol,
            Long interval,
            KlineData kline
    ) {
        data.get(symbol).put(interval, kline);
    }

    public void update(String symbol, Long interval, BigDecimal price) {
        this.data.get(symbol)
                .get(interval)
                .update(price);
    }
}
