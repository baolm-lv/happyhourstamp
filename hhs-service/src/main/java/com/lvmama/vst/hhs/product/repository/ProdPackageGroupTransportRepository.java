/**
 * 
 */
package com.lvmama.vst.hhs.product.repository;

import java.math.BigDecimal;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.lvmama.vst.hhs.product.dao.ProdPackageGroupTransportEntity;

/**
 * @author fengyonggang
 *
 */
@Repository
public interface ProdPackageGroupTransportRepository extends JpaRepository<ProdPackageGroupTransportEntity, Long>{

	ProdPackageGroupTransportEntity getFirstByGroupId(BigDecimal bigDecimal);

}
