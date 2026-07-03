package huynguyen.exchange_lab.market.repository;

import huynguyen.exchange_lab.market.entities.KlineData;
import huynguyen.exchange_lab.market.enums.KlineIntervalEnum;

import java.time.Instant;
import java.util.List;

public interface KlineHistoryRepository <T extends KlineData> {

    KlineIntervalEnum interval();

    List<T> findWithPagination(Integer tradingPairId, Instant endTime, Integer limit);

}
