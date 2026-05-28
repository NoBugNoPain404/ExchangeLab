package huynguyen.exchange_lab.market.common;

import lombok.Data;

import java.time.Instant;

@Data
public class KlineData{
    private Instant openTime;
    private Double low;
    private Double high;
    private Double open;
    private Double close;

    public KlineData() {
        this.openTime = Instant.now();
        this.low = 0.0;
        this.high = 0.0;
        this.open = 0.0;
        this.close = 0.0;
    }
}
