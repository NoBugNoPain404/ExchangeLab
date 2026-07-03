package huynguyen.exchange_lab.market.common;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.JoinColumn;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class KlineId {


    @Column(name = "trading_pair_id")
    private Integer tradingPairId;

    @Column(name = "start_time")
    private Instant startTime;
}
