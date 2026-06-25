package huynguyen.exchange_lab.market.projections;

import huynguyen.exchange_lab.market.entities.Asset;

public interface SymbolProjection {
    Integer getId();
    String getSymbol();
    Asset getBaseAsset();
    Asset getQuoteAsset();
}
