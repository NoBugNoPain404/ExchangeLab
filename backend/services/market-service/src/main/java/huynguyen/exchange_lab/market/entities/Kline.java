package huynguyen.exchange_lab.market.entities;

import huynguyen.exchange_lab.market.common.KlineId;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;

@MappedSuperclass
@Getter
@Setter
public abstract class Kline {

    @EmbeddedId
    private KlineId id;


}
