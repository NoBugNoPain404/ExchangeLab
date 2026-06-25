package huynguyen.exchange_lab.market.repository;

import huynguyen.exchange_lab.market.projections.SymbolNameProjection;
import huynguyen.exchange_lab.market.projections.SymbolProjection;
import huynguyen.exchange_lab.market.entities.TradingPair;
import huynguyen.exchange_lab.market.enums.TradingPairStatusEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TradingPairRepository extends JpaRepository<TradingPair, Integer> {

    List<SymbolNameProjection> getAllByStatus(TradingPairStatusEnum status);

    List<SymbolProjection> getAllBy();
}
