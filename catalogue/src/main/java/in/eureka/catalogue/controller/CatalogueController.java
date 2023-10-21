package in.eureka.catalogue.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import in.eureka.catalogue.dto.CatalogueDto;
import in.eureka.catalogue.service.CatalogueService;

@RestController
public class CatalogueController {

	@Autowired
	private CatalogueService catalogueService;
	
	@GetMapping("catalogue/{skuId}")
	public ResponseEntity<CatalogueDto> fetchCatalogueDetails(@PathVariable Integer skuId) {
		HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");    
		CatalogueDto res = catalogueService.findCatalogueDetailsBySkuId(skuId);
		return new ResponseEntity<>(res, headers, HttpStatus.OK);
	}
}
