package huynguyen.exchange_lab.market.common;

import huynguyen.exchange_lab.market.enums.KlineIntervalEnum;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class KlineCache {
    private final Map<Long, KlineData> data = new ConcurrentHashMap<>();

    public KlineCache() {
        for (KlineIntervalEnum intervalEnum : KlineIntervalEnum.values()) {
            data.put(intervalEnum.getMillis(), new KlineData());
        }
    }

    public KlineData getData(Long interval) {
        return data.get(interval);
    }

    public void putData(Long interval, KlineData newData) {
        data.put(interval, newData);
    }

    public Map<Long, KlineData> getAll() {
        return this.data;
    }
}
