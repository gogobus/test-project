package in.eureka.catalogue.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Random;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import in.eureka.catalogue.dto.CatalogueDto;
import in.eureka.catalogue.mapper.CatalogueMapper;
import in.eureka.jpa.entity.common.CatalogueEntity;
import in.eureka.jpa.entity.common.PricingEntity;
import in.eureka.jpa.enums.Category;
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

	private static final Logger LOGGER = LoggerFactory.getLogger(CatalogueService.class);

	private PricingEntity findPricingBySkuId(int skuId) {
		return priceRepo.findBySkuId(skuId);
	}

	private CatalogueEntity findCatalogueBySkuId(int skuId) {
		return catRepo.findById(skuId).get();
	}

	/**
	 * @param skuId is the id i.e. primary key of Catalogue Entity
	 * @return CatalogueDto
	 */
	public CatalogueDto findCatalogueDetailsBySkuId(int skuId) {
		try {
			// This only to fill table for some results;
			createCatalogue();
			PricingEntity pe = findPricingBySkuId(skuId);
			CatalogueEntity ce = findCatalogueBySkuId(skuId);

			return catalogueMapper.mapCatalogueEntityToDto(ce, pe);
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error(e.getLocalizedMessage());
			return null;
		}
	}

	private PricingEntity savePricing(PricingEntity entity) {
		return priceRepo.save(entity);
	}

	private CatalogueEntity saveCatalogue(CatalogueEntity entity) {
		return catRepo.save(entity);
	}

	@Transactional
	private CatalogueEntity createCatalogue() throws Exception {

		Random r = new Random();
		int low = 10;
		int high = 100;
		int result = r.nextInt(high - low) + low;

		CatalogueEntity cat = new CatalogueEntity();

		PricingEntity price = new PricingEntity();

		cat.setCategory(Category.HOME_APPLIANCES);
		cat.setDescription("Description " + result);
		cat.setName("Test Product " + result);

		cat = saveCatalogue(cat);

		price.setStrikePrice((result * 100) - 200);
		price.setSellingPrice(result * 100);
		price.setSkuId(cat.getId());

		price = savePricing(price);

		return cat;
	}
}
