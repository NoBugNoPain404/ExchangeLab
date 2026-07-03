package huynguyen.exchange_lab.market.components;

import huynguyen.exchange_lab.market.entities.KlineData;
import jakarta.annotation.PreDestroy;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicBoolean;

@Slf4j
@Component
@RequiredArgsConstructor
public class KlineBuffer {

    private final Map<String, ConcurrentLinkedQueue<KlineData>> buffer = new ConcurrentHashMap<>();

    private final JdbcTemplate jdbcTemplate;

    private final AtomicBoolean isShuttingDown = new AtomicBoolean(false);

    public void putIntoBuffer(String key, KlineData data) {
        buffer.computeIfAbsent(key, k -> new ConcurrentLinkedQueue<>()).add(data);
    }

    public void writeIntoDb() {
        buffer.forEach((key, queue) -> {
            if (queue.isEmpty()) {
                return;
            }

            List<KlineData> batch = new ArrayList<>();
            KlineData item;
            while ((item = queue.poll()) != null) {
                batch.add(item);
            }

            String sql = String.format(
                    """
                    INSERT INTO market_data.trading_pair_kline_%s
                    (
                        trading_pair_id, start_time, open, close, low, high, change, change_percent, 
                        volume, quote_volume, trade_count, taker_buy_volume, taker_buy_quote_volume
                    )
                    VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
                    """, key
            );

            try {
                jdbcTemplate.batchUpdate(
                        sql,
                        batch,
                        1000,
                        (ps, kline) -> {
                            ps.setInt(1, kline.getId().getTradingPairId());
                            ps.setTimestamp(2, Timestamp.from(kline.getId().getStartTime()));
                            ps.setBigDecimal(3, kline.getOpen());
                            ps.setBigDecimal(4, kline.getClose());
                            ps.setBigDecimal(5, kline.getLow());
                            ps.setBigDecimal(6, kline.getHigh());
                            ps.setBigDecimal(7, kline.getChange());
                            ps.setBigDecimal(8, kline.getChangePercent());
                            ps.setBigDecimal(9, kline.getVolume());
                            ps.setBigDecimal(10, kline.getQuoteVolume());
                            ps.setLong(11, kline.getTradeCount());
                            ps.setBigDecimal(12, kline.getTakerBuyVolume());
                            ps.setBigDecimal(13, kline.getTakerBuyQuoteVolume());
                        }
                );
            } catch (Exception ex) {
                log.error("Error writing batch to DB for key {}: ", key, ex);
            }
        });
    }

    @Scheduled(fixedRate = 60 * 1000)
    @Transactional
    public void scheduledWrite() {
        if (isShuttingDown.get()) {
            return;
        }
        writeIntoDb();
    }

    @PreDestroy
    public void onShuttingDown() {
        log.info("Writing into db before shutting down!");
        isShuttingDown.set(true);
        writeIntoDb();
    }
}