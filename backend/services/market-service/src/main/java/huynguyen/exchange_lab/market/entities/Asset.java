package huynguyen.exchange_lab.market.entities;


import huynguyen.common.common_jpa.BaseEntity;
import huynguyen.exchange_lab.market.enums.AssetStatusEnum;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;

@Entity
@Table(name = "asset", schema = "market_data")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Asset extends BaseEntity {

    @Id
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "ticker", nullable = false)
    private String ticker;

    @Column(name = "icon_url", nullable = false)
    private String iconUrl;

    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    private AssetStatusEnum assetStatus;

    @Column(name = "listed_time")
    private Instant listedTime;

    @Column(name = "delisted_time")
    private Instant delistedTime;
}
