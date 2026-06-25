package huynguyen.exchange_lab.market.controller;


import huynguyen.common.common_jpa.dto.ApiResponse;
import huynguyen.exchange_lab.market.projections.SymbolNameProjection;
import huynguyen.exchange_lab.market.service.TradingPairService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/trading-pairs")
@RequiredArgsConstructor
public class TradingPairController {

    private final TradingPairService tradingPairService;

    @GetMapping("/get-all")
    public ResponseEntity<ApiResponse<List<SymbolNameProjection>>> getAll() {

        ApiResponse<List<SymbolNameProjection>> tradingPairs = tradingPairService.getListedTradingPairs();

        return ResponseEntity.ok(
                tradingPairs
        );
    }
}
