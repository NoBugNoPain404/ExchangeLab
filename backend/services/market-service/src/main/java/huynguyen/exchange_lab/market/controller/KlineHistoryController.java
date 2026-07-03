package huynguyen.exchange_lab.market.controller;

import huynguyen.common.common_jpa.dto.ApiResponse;
import huynguyen.exchange_lab.market.entities.KlineData;
import huynguyen.exchange_lab.market.repository.TradingPairRepository;
import huynguyen.exchange_lab.market.service.KlineHistoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;

@RestController
@RequestMapping("/kline-history")
@RequiredArgsConstructor
public class KlineHistoryController {

    private final KlineHistoryService historyService;


    @GetMapping("/")
    public ResponseEntity<ApiResponse<Object>> getHistory(
            @RequestParam String symbol,
            @RequestParam String interval,
            @RequestParam(required = false) Instant endTime,
            @RequestParam(required = false) Integer limit
    ) {
        if (limit == null) {
            limit = 200;
        }


        return ResponseEntity.ok(
                historyService.getHistory(
                        symbol,
                        interval,
                        endTime,
                        limit
                )
        );
    }
}
