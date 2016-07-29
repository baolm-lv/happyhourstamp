/**
 * 
 */
package com.lvmama.vst.hhs.product.repository;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lvmama.vst.hhs.product.dao.ProdPackageDetailEntity;

/**
 * @author fengyonggang
 *
 */
public interface ProdPackageDetailRepository extends JpaRepository<ProdPackageDetailEntity, Long>{

	List<ProdPackageDetailEntity> getByGroupId(BigDecimal groupId);
	
	ProdPackageDetailEntity getByGroupIdAndObjectIdAndObjectType(BigDecimal groupId, BigDecimal objectId, String objectType);
}
