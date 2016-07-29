/*
 * The copyright to the computer software herein is the property of
 * Lvmama. The software may be used and/or copied only
 * with the written permission of Lvmama or in accordance
 * with the terms and conditions stipulated in the agreement/contract
 * under which the software has been supplied.
 */

package com.lvmama.vst.hhs.stampDefinition.repositorySlave;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.lvmama.vst.hhs.stampDefinition.dao.StampRefundOrderEntity;


/**
 *
 * @author baolm
 */
@Repository
public interface StampRefundOrderRepositorySlave extends JpaRepository<StampRefundOrderEntity, String> {

	@Modifying
	@Query("update StampRefundOrderEntity t set t.refundId = ?2 where t.refundApplyId=?1")
	int updateRefundId(String refundApplyId, String refundId);
	
	StampRefundOrderEntity getByRefundId(String refundId);
	
	StampRefundOrderEntity getByRefundApplyId(String refundApplyId);
}
