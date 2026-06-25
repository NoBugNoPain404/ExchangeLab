package huynguyen.exchange_lab.market.common;

import huynguyen.exchange_lab.market.entities.KlineData;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class HotCache {

    private final RedisTemplate<String, Object> redisTemplate;

    private static int MAX_SIZE = 200;

    public void updateHotCache(String symbol, Long interval, KlineData data) {
        redisTemplate.opsForZSet()
                .add(String.format("%s:%d", symbol, interval), data, Double.parseDouble(data.getOpenTime().toString()));

        redisTemplate.opsForZSet()
                .removeRange(String.format("%s:%d", symbol, interval), 0, -(MAX_SIZE + 1));
    }
}
