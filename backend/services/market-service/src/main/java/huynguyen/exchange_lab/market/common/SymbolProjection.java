package huynguyen.exchange_lab.market.common;

import huynguyen.exchange_lab.market.entities.Asset;

public interface SymbolProjection {
    String getSymbol();
    Asset getBaseAsset();
    Asset getQuoteAsset();
}
