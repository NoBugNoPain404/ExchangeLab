package huynguyen.exchange_lab.market.service;

import huynguyen.common.common_jpa.dto.ApiResponse;
import huynguyen.exchange_lab.market.entities.KlineData;
import huynguyen.exchange_lab.market.repository.KlineHistoryRepository;
import huynguyen.exchange_lab.market.repository.TradingPairRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
@RequiredArgsConstructor
public class KlineHistoryService {

    private final TradingPairRepository tradingPairRepository;

    private final List<KlineHistoryRepository<?>> repositories;

    private final Map<String, KlineHistoryRepository<?>> map = new ConcurrentHashMap<>();

    @PostConstruct
    public void loadMap() {
        for (KlineHistoryRepository<?> repository : repositories) {
            map.put(
                    repository.interval().getCode(),
                    repository
            );
        }
    }

    public ApiResponse<Object> getHistory(
            String symbol,
            String interval,
            Instant endTime,
            Integer limit
    ) {
        if (map.containsKey(interval)) {
            KlineHistoryRepository<?> repository = map.get(interval);

            Integer tradingPairId = tradingPairRepository.findBySymbol(symbol)
                    .getId();

            List<? extends KlineData> list = repository.findWithPagination(tradingPairId, endTime, limit);

            return ApiResponse.builder()
                    .code(200)
                    .data(list)
                    .build();
        }

        return ApiResponse.builder()
                .code(404)
                .message("Something went wrong!")
                .build();
    }
}
