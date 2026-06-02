package huynguyen.exchange_lab.market.service;

import huynguyen.exchange_lab.market.common.KlineCache;
import huynguyen.exchange_lab.market.common.PriceEngine;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Instant;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class MarketDataPublisher {
    private final SimpMessagingTemplate template;
    private final PriceEngine priceEngine;
    private final KlineCache data;

    @Scheduled(fixedRate = 1000,
    initialDelay = 5000)
    public void publish() {
        Map<String, BigDecimal> newPrices = priceEngine.tick();
        newPrices.forEach((symbol, price) -> {
            price = price.setScale(4, RoundingMode.HALF_UP);
            template.convertAndSend("/topic/market-data." + symbol, price);
        });
    }
}
