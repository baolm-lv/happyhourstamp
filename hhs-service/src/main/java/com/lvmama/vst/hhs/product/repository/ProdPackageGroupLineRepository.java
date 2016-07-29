/**
 * 
 */
package com.lvmama.vst.hhs.product.repository;

import java.math.BigDecimal;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.lvmama.vst.hhs.product.dao.ProdPackageGroupLineEntity;

/**
 * @author fengyonggang
 *
 */
@Repository
public interface ProdPackageGroupLineRepository extends JpaRepository<ProdPackageGroupLineEntity, Long>{

	ProdPackageGroupLineEntity getFirstByGroupId(BigDecimal groupId);
}
