package huynguyen.exchange_lab.market.dto;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class TradingPairDTO {

    private String symbol;

    private String base;

    private String quote;
}
