package huynguyen.exchange_lab.market.service;

import huynguyen.exchange_lab.market.dto.AssetDTO;
import huynguyen.exchange_lab.market.entities.Asset;
import huynguyen.exchange_lab.market.mapper.AssetResponseMapper;
import huynguyen.exchange_lab.market.repository.AssetRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AssetService {
    private final AssetRepository assetRepository;
    private final AssetResponseMapper mapper;

    public List<AssetDTO> findAll() {
        return assetRepository.findAll()
                .stream()
                .map(mapper::toDto)
                .toList();
    }

    public AssetDTO findByTicker(String ticker) {
       Asset asset = assetRepository.findByTickerIgnoreCase(ticker)
               .orElseThrow(IllegalArgumentException::new);
       return mapper.toDto(asset);
    }
}
