package huynguyen.exchange_lab.market.entities;

import jakarta.persistence.MappedSuperclass;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Instant;

@Data
@MappedSuperclass
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class KlineData{
    private Integer tradingPairId;
    private Instant openTime;
    private BigDecimal low;
    private BigDecimal high;
    private BigDecimal open;
    private BigDecimal close;
    private BigDecimal change;
    private BigDecimal changePercent;
    private BigDecimal volume;
    private BigDecimal quoteVolume;
    private Long tradeCount;
    private BigDecimal takerBuyVolume;
    private BigDecimal takerBuyQuoteVolume;

    public KlineData(Integer id) {
        this.tradingPairId = id;
        this.openTime = Instant.now();

        this.low = BigDecimal.ZERO;
        this.high = BigDecimal.ZERO;
        this.open = BigDecimal.ZERO;
        this.close = BigDecimal.ZERO;

        this.change = BigDecimal.ZERO;
        this.changePercent = BigDecimal.ZERO;

        this.volume = BigDecimal.ZERO;
        this.quoteVolume = BigDecimal.ZERO;

        this.tradeCount = 0L;

        this.takerBuyVolume = BigDecimal.ZERO;
        this.takerBuyQuoteVolume = BigDecimal.ZERO;
    }

    public void setAll(BigDecimal value) {
        this.open = value;
        this.close = value;
        this.high = value;
        this.low = value;
    }

    public void update(BigDecimal price) {
        if (price.compareTo(this.high) > 0) {
            this.high = price;
        }

        if (price.compareTo(this.low) < 0) {
            this.low = price;
        }

        this.close = price;

        this.change = this.close.subtract(this.open);

        this.changePercent = this.change
                .multiply(BigDecimal.valueOf(100))
                .divide(this.open, 2, RoundingMode.HALF_UP);
    }
}
