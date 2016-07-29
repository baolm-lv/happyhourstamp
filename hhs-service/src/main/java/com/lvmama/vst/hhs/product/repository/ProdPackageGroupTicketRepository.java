/**
 * 
 */
package com.lvmama.vst.hhs.product.repository;

import java.math.BigDecimal;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.lvmama.vst.hhs.product.dao.ProdPackageGroupTicketEntity;

/**
 * @author fengyonggang
 *
 */
@Repository
public interface ProdPackageGroupTicketRepository extends JpaRepository<ProdPackageGroupTicketEntity, Long>{

	ProdPackageGroupTicketEntity getFirstByGroupId(BigDecimal bigDecimal);

}
