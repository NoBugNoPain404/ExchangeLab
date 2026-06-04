package huynguyen.exchange_lab.market.components;

import huynguyen.exchange_lab.market.common.PairInfo;
import lombok.Getter;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Getter
@Component
public class MarketRegistry {

    private final Map<String, PairInfo> pairs = new ConcurrentHashMap<>();

    public PairInfo get(String symbol) {
        return pairs.get(symbol);
    }

    public void put(String symbol, PairInfo pairInfo) {
        pairs.put(symbol, pairInfo);
    }

    public void putAll(Map<String, PairInfo> data) {
        pairs.putAll(data);
    }
}
