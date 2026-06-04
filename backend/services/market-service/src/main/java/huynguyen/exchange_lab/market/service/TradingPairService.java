package huynguyen.exchange_lab.market.service;

import huynguyen.exchange_lab.market.common.KlineCache;
import huynguyen.exchange_lab.market.common.PairInfo;
import huynguyen.exchange_lab.market.common.SymbolProjection;
import huynguyen.exchange_lab.market.components.MarketRegistry;
import huynguyen.exchange_lab.market.repository.TradingPairRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class TradingPairService {

    private final TradingPairRepository tradingPairRepository;

    private final MarketRegistry marketRegistry;

    private final KlineCache klineCache;

    @PostConstruct
    public void loadSymbolList() {
        Map<String, PairInfo> cachedData = tradingPairRepository.getAllBy()
                .stream()
                .collect(Collectors.toMap(
                        SymbolProjection::getSymbol,
                        projection ->
                            new PairInfo(
                                    projection.getBaseAsset().getTicker(),
                                    projection.getQuoteAsset().getTicker()
                            )
                ));
        marketRegistry.putAll(cachedData);

        klineCache.init();
    }
}
