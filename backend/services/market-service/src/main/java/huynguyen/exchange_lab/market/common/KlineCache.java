package huynguyen.exchange_lab.market.common;

import huynguyen.exchange_lab.market.components.MarketRegistry;
import huynguyen.exchange_lab.market.enums.KlineIntervalEnum;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
@RequiredArgsConstructor
public class KlineCache {

    private final MarketRegistry marketRegistry;

    private final Map<String, Map<Long, KlineData>> data = new ConcurrentHashMap<>();

    public void init() {
        marketRegistry.getPairs()
                .keySet()
                .forEach(
                        symbol -> {
                            Map<Long, KlineData> intervals = new ConcurrentHashMap<>();

                            for (KlineIntervalEnum e : KlineIntervalEnum.values()) {
                                intervals.put(
                                        e.getMillis(),
                                        new KlineData()
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
        if (price.compareTo(this.data.get(symbol).get(interval).getHigh()) > 0) {
            this.data.get(symbol).get(interval).setHigh(price);
        }
        if (price.compareTo(this.data.get(symbol).get(interval).getLow()) < 0) {
            this.data.get(symbol).get(interval).setLow(price);
        }
        this.data.get(symbol).get(interval).setClose(price);
    }
}
