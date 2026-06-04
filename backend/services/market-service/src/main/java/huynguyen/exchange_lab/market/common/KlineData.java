package huynguyen.exchange_lab.market.common;

import lombok.Data;

import java.math.BigDecimal;
import java.time.Instant;

@Data
public class KlineData{
    private Instant openTime;
    private BigDecimal low;
    private BigDecimal high;
    private BigDecimal open;
    private BigDecimal close;

    public KlineData() {
        this.openTime = Instant.now();
        this.low = BigDecimal.valueOf(0.0);
        this.high = BigDecimal.valueOf(0.0);
        this.open = BigDecimal.valueOf(0.0);
        this.close = BigDecimal.valueOf(0.0);
    }

    public void setAll(BigDecimal value) {
        this.open = value;
        this.close = value;
        this.high = value;
        this.low = value;
    }
}
