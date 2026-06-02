package huynguyen.exchange_lab.market.entities;

import huynguyen.exchange_lab.market.enums.TradingPairStatusEnum;
import jakarta.persistence.*;
import lombok.Data;

import java.time.Instant;

@Entity
@Data
@Table(name = "spot_trading_pair", schema = "market_data")
public class TradingPair {

    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "trading_pair_generator"
    )
    @SequenceGenerator(
            name = "trading_pair_generator",
            sequenceName = "market_data.spot_trading_pair_id_seq",
            allocationSize = 1
    )
    private Integer id;

    @Column(name = "symbol", unique = true)
    private String symbol;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "base_asset_id", nullable = false)
    private Asset baseAsset;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "quote_asset_id", nullable = false)
    private Asset quoteAsset;

    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    private TradingPairStatusEnum status;

    @Column(name = "listed_time")
    private Instant listedTime;

    @Column(name = "delisted_time")
    private Instant delistedTime;

    @Column(name = "updated_at")
    private Instant updatedAt;

    @Column(name = "created_at")
    private Instant createdAt;
}
