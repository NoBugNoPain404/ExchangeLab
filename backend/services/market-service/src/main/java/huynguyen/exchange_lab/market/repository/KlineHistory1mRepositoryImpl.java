package huynguyen.exchange_lab.market.repository;

import huynguyen.exchange_lab.market.entities.KLineData_1m;
import huynguyen.exchange_lab.market.enums.KlineIntervalEnum;
import org.springframework.stereotype.Repository;


@Repository
public class KlineHistory1mRepositoryImpl
        extends KlineHistoryRepositoryImpl<KLineData_1m>{

    public KlineHistory1mRepositoryImpl() {
        super(KLineData_1m.class);
    }

    @Override
    public KlineIntervalEnum interval() {
        return KlineIntervalEnum.m1;
    }
}
