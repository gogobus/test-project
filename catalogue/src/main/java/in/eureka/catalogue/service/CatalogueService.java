package in.eureka.catalogue.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import in.eureka.catalogue.dto.CatalogueDto;
import in.eureka.catalogue.mapper.CatalogueMapper;
import in.eureka.jpa.entity.common.CatalogueEntity;
import in.eureka.jpa.entity.common.PricingEntity;
import in.eureka.jpa.repository.common.CatalogueRepository;
import in.eureka.jpa.repository.common.PricingRepository;

@Service
public class CatalogueService {
	
	@Autowired
	private PricingRepository priceRepo;
	
	@Autowired
	private CatalogueRepository catRepo;
	
	@Autowired
	private CatalogueMapper catalogueMapper;
	
	
	private PricingEntity findPricingBySkuId(int skuId) {
		return priceRepo.findBySkuId(skuId);
	}
	
	private CatalogueEntity findCatalogueBySkuId(int skuId) {
		return catRepo.findById(skuId).get();
	}
	
	public CatalogueDto findCatalogueDetailsBySkuId(int skuId) {
		PricingEntity pe = findPricingBySkuId(skuId);
		CatalogueEntity ce = findCatalogueBySkuId(skuId);
		
		return catalogueMapper.mapCatalogueEntityToDto(ce, pe);
	}
}
