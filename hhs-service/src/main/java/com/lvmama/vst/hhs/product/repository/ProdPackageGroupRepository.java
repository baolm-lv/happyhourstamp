/**
 * 
 */
package com.lvmama.vst.hhs.product.repository;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.lvmama.vst.hhs.product.dao.ProdPackageGroupEntity;

/**
 * @author fengyonggang
 *
 */
@Repository
public interface ProdPackageGroupRepository extends JpaRepository<ProdPackageGroupEntity, Long>{

	List<ProdPackageGroupEntity> getByProductId(BigDecimal productId);
	
	@Query("select s from ProdPackageGroupEntity s where s.productId in ?1 and s.groupType = ?2")
	List<ProdPackageGroupEntity> getByProductIdAndGroupType(List<BigDecimal> productIds, String groupType);
}
