/*
 * The copyright to the computer software herein is the property of
 * Lvmama. The software may be used and/or copied only
 * with the written permission of Lvmama or in accordance
 * with the terms and conditions stipulated in the agreement/contract
 * under which the software has been supplied.
 */

package com.lvmama.vst.hhs.product.repository;


import java.sql.Timestamp;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.lvmama.vst.hhs.product.dao.OrdStampOrderEntity;

/**
 *
 * @author Luochang Tang
 */
@Repository
public interface OrdStampOrderRepository extends JpaRepository<OrdStampOrderEntity, Long> {
    
    @Modifying
    @Query("update OrdStampOrderEntity o set o.balanceDueWaitDate= ?2 where o.orderId= ?1 ")
    void  updateOrdStampOrderLastPaytime(Long orderId,Timestamp date);

	@Modifying
	@Query("update OrdStampOrderEntity o set o.downDate = ?2 where o.orderId = ?1")
	int updateDownDateByOrderId(Long orderId, Timestamp downDate);
	
	@Modifying
    @Query("update OrdStampOrderEntity o set o.payType = ?2 ,o.balanceDueWaitDate = ?3 ,o.downPayment = ?4 where o.orderId = ?1")
    int updatePayTypeByOrderId(Long orderId, String payType,Timestamp balanceDueWaitDate,Long downPayment);

}
