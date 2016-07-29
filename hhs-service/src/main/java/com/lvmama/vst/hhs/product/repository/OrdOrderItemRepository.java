/*
 * The copyright to the computer software herein is the property of
 * Lvmama. The software may be used and/or copied only
 * with the written permission of Lvmama or in accordance
 * with the terms and conditions stipulated in the agreement/contract
 * under which the software has been supplied.
 */

package com.lvmama.vst.hhs.product.repository;

import java.math.BigDecimal;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.lvmama.vst.hhs.product.dao.OrdOrderItemEntity;


/**
 *
 * @author Luochang Tang
 */
@Repository
public interface OrdOrderItemRepository extends JpaRepository<OrdOrderItemEntity, Long> {

	@Modifying
	@Query("update OrdOrderItemEntity o set o.orderSubtype = ?1 where o.orderId = ?2")
	void updateSubTypeByOrderId(String subType, BigDecimal orderId);
	
	
}
