package in.eureka.catalogue.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import in.eureka.catalogue.dto.CatalogueDto;
import in.eureka.catalogue.service.CatalogueService;

@RestController
public class CatalogueController {

	@Autowired
	private CatalogueService catalogueService;
	
	@GetMapping("catalogue/{id}")
	public CatalogueDto fetchCatalogueDetails(@PathVariable int skuId) {
		return catalogueService.findCatalogueDetailsBySkuId(skuId);
	}
}
