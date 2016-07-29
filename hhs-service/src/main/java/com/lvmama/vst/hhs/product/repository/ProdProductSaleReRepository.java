/**
 * 
 */
package com.lvmama.vst.hhs.product.repository;

import java.math.BigDecimal;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.lvmama.vst.hhs.product.dao.ProdProductSaleReEntity;

/**
 * @author fengyonggang
 *
 */
@Repository
public interface ProdProductSaleReRepository extends JpaRepository<ProdProductSaleReEntity, Long>{

	ProdProductSaleReEntity getFirstByProductId(BigDecimal bigDecimal);

}
