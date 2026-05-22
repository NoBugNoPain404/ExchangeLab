package huynguyen.exchange_lab.market.mapper;

import huynguyen.exchange_lab.market.dto.AssetDTO;
import huynguyen.exchange_lab.market.entities.Asset;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AssetResponseMapper {

    AssetDTO toDto(Asset asset);
}
