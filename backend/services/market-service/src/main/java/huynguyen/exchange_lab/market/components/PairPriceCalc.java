package huynguyen.exchange_lab.market.components;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Component
@RequiredArgsConstructor
public class PairPriceCalc {

    private final PriceCache priceCache;

    private final MarketRegistry marketRegistry;

    private final PairPriceCache pairPriceCache;

    public void calcSymbolPrice() {
        marketRegistry.getPairs()
                .forEach(
                        (symbol, pairInfo) -> {
                            BigDecimal quotePrice = priceCache.getPrice(
                                    pairInfo.getQuote()
                            );
                            BigDecimal basePrice = priceCache.getPrice(
                                    pairInfo.getBase()
                            );
                            pairPriceCache.put(symbol, basePrice.divide(
                                    quotePrice,
                                    8,
                                    RoundingMode.HALF_UP
                            ));
                        }
                );
    }
}
