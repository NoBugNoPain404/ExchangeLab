package huynguyen.exchange_lab.market.dto;

import lombok.Builder;

@Builder
public record AssetDTO(
        Integer id,
        String name,
        String ticker,
        String iconUrl
) {

}
