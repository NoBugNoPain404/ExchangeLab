package huynguyen.exchange_lab.market.service;

import huynguyen.exchange_lab.market.common.KlineCache;
import huynguyen.exchange_lab.market.components.*;
import huynguyen.exchange_lab.market.entities.KlineData;
import huynguyen.exchange_lab.market.common.PriceEngine;
import huynguyen.exchange_lab.market.enums.KlineIntervalEnum;
import jakarta.annotation.PostConstruct;
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

    private final KlineBuffer klineBuffer;

    private final KlineCache klineCache;
    private final MarketRegistry marketRegistry;

    @PostConstruct
    public void init() {
        priceCache.update(priceEngine.getAllPrices());
        pairPriceCalc.calcSymbolPrice();
        pairPriceCache.getAll().forEach(
                (key, value) -> {
                    for (KlineIntervalEnum intervalEnum : KlineIntervalEnum.values()) {
                        KlineData klineData = new KlineData(marketRegistry.get(key).getId());
                        klineData.setAll(value);
                        klineCache.put(key, intervalEnum.getMillis(), klineData);
                    }
                }
        );
    }

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
                                kline.getId().getStartTime()
                                        .toEpochMilli() / intervalEnum.getMillis();

                        if (currentBucket != cachedBucket) {
                            KlineData klineData = new KlineData(kline.getId().getTradingPairId());
                            klineData.setAll(kline.getClose());
                            klineCache.put(key, intervalEnum.getMillis(), klineData);

                            klineBuffer.putIntoBuffer(intervalEnum.getCode(), kline);
                        }
                        klineCache.update(key, intervalEnum.getMillis(), value);
                    }
                }
        );
    }
}
