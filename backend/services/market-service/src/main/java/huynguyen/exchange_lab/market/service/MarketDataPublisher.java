package huynguyen.exchange_lab.market.service;

import huynguyen.exchange_lab.market.common.KlineCache;
import huynguyen.exchange_lab.market.common.KlineData;
import huynguyen.exchange_lab.market.common.PriceEngine;
import huynguyen.exchange_lab.market.components.PairPriceCache;
import huynguyen.exchange_lab.market.components.PairPriceCalc;
import huynguyen.exchange_lab.market.components.PriceCache;
import huynguyen.exchange_lab.market.enums.KlineIntervalEnum;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class MarketDataPublisher {
    private final SimpMessagingTemplate template;
    private final PriceEngine priceEngine;
    private final PriceCache priceCache;
    private final PairPriceCalc pairPriceCalc;
    private final PairPriceCache pairPriceCache;

    private final KlineCache klineCache;

    @Scheduled(fixedRate = 1000,
    initialDelay = 5000)
    public void publish() {
        priceCache.update(priceEngine.tick());
        pairPriceCalc.calcSymbolPrice();
        Map<String, BigDecimal> pairPrices = pairPriceCache.getAll();
        pairPrices.forEach(
                (key, value) -> {
                    template.convertAndSend("/topic/market-data." + key, value);
                    Instant thisInstant = Instant.now();
                    for (KlineIntervalEnum intervalEnum : KlineIntervalEnum.values()) {
                        KlineData kline = klineCache.get(key, intervalEnum.getMillis());
                        long currentBucket =
                                thisInstant.toEpochMilli() / intervalEnum.getMillis();

                        long cachedBucket =
                                kline.getOpenTime()
                                        .toEpochMilli() / intervalEnum.getMillis();

                        if (currentBucket != cachedBucket) {
                            KlineData klineData = new KlineData();
                            klineData.setAll(kline.getClose());
                            klineCache.put(key, intervalEnum.getMillis(), klineData);
                        }
                        klineCache.update(key, intervalEnum.getMillis(), value);
                    }
                }
        );
    }
}
