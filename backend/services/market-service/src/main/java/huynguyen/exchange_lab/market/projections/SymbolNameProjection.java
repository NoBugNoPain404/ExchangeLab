package huynguyen.exchange_lab.market.projections;

public interface SymbolNameProjection {
    String getSymbol();
    AssetNameProjection getBaseAsset();
    AssetNameProjection getQuoteAsset();
}
