package huynguyen.exchange_lab.market.repository;

import huynguyen.exchange_lab.market.common.SymbolProjection;
import huynguyen.exchange_lab.market.entities.TradingPair;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TradingPairRepository extends JpaRepository<TradingPair, Integer> {
    List<SymbolProjection> getAllBy();
}
