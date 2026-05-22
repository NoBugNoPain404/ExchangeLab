package huynguyen.exchange_lab.market.repository;

import huynguyen.exchange_lab.market.entities.Asset;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AssetRepository extends JpaRepository<Asset, Integer> {

    Optional<Asset> findByTickerIgnoreCase(String ticker);
}
