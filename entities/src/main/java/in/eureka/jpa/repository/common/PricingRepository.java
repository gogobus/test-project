package in.eureka.jpa.repository.common;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import in.eureka.jpa.entity.common.PricingEntity;

@Repository
public interface PricingRepository extends JpaRepository<PricingEntity, Integer> {

	public PricingEntity findBySkuId(int skuId);
}
