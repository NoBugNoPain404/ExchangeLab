package huynguyen.exchange_lab.market.controller;

import huynguyen.exchange_lab.market.dto.AssetDTO;
import huynguyen.exchange_lab.market.service.AssetService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/market-data")
@RequiredArgsConstructor
public class    AssetController {

    private final AssetService assetService;

    @GetMapping("/asset")
    public ResponseEntity<List<AssetDTO>> findAll() {
        List<AssetDTO> result = assetService.findAll();
        return ResponseEntity.ok(result);
    }

    @GetMapping("/asset/{ticker}")
    public ResponseEntity<AssetDTO> findByTicker(@PathVariable String ticker) {
        AssetDTO assetDTO = assetService.findByTicker(ticker);
        return  ResponseEntity.ok(assetDTO);
    }
}
