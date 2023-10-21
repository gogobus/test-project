package in.eureka.catalogue.mapper;

import org.springframework.stereotype.Component;

import in.eureka.catalogue.dto.CatalogueDto;
import in.eureka.jpa.entity.common.CatalogueEntity;
import in.eureka.jpa.entity.common.PricingEntity;

@Component
public class CatalogueMapper {

	public CatalogueDto mapCatalogueEntityToDto(CatalogueEntity catalogueEntity, PricingEntity priceEntity) {
		
		if(catalogueEntity == null) {
			return null;
		}
		
		CatalogueDto dto = new CatalogueDto();
		
		dto.setCategory(catalogueEntity.getCategory());
		dto.setDescription(catalogueEntity.getDescription());
		dto.setId(catalogueEntity.getId());
		dto.setName(catalogueEntity.getName());
		
		if(priceEntity!= null) {
			dto.setSellingPrice(priceEntity.getSellingPrice());
			dto.setStrikePrice(priceEntity.getStrikePrice());
		}
		
		return dto;
		
	}
}
