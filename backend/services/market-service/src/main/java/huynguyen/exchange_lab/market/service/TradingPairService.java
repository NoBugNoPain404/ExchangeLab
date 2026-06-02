package huynguyen.exchange_lab.market.service;

import huynguyen.exchange_lab.market.repository.TradingPairRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TradingPairService {
    private final TradingPairRepository tradingPairRepository;

    private final List<String> symbols;

    @PostConstruct
    public void loadSymbolList() {

    }
}
