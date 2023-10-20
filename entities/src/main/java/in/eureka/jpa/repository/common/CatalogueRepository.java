package in.eureka.jpa.repository.common;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import in.eureka.jpa.entity.common.CatalogueEntity;

@Repository
public interface CatalogueRepository extends JpaRepository<CatalogueEntity, Integer> {

}
