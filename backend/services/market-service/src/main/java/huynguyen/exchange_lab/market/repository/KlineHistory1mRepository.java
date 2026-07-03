package huynguyen.exchange_lab.market.repository;

import huynguyen.exchange_lab.market.common.KlineId;
import huynguyen.exchange_lab.market.entities.KLineData_1m;
import org.springframework.data.jpa.repository.JpaRepository;

public interface KlineHistory1mRepository extends JpaRepository<KLineData_1m, KlineId>,
        KlineHistoryRepository<KLineData_1m> {
}
