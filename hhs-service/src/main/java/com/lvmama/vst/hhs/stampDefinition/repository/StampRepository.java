/*
 * The copyright to the computer software herein is the property of
 * Lvmama. The software may be used and/or copied only
 * with the written permission of Lvmama or in accordance
 * with the terms and conditions stipulated in the agreement/contract
 * under which the software has been supplied.
 */

package com.lvmama.vst.hhs.stampDefinition.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.lvmama.vst.hhs.stampDefinition.dao.StampEntity;

/**
 *
 * @author Luochang Tang
 */
@Repository
public interface StampRepository extends JpaRepository<StampEntity, String> {
    
    List<StampEntity> getByOrderId(String orderId);
    
    List<StampEntity> getByOrderIdAndStampStatus(String orderId, String stampStatus);

	List<StampEntity> getByCustomerIdAndStampStatus(String customerId, String stampStatus);
	
	List<StampEntity> getByCustomerIdAndStampDefinitionId(String customerId, String stampDefinitionId);
	
	List<StampEntity> getByCustomerIdAndStampDefinitionIdAndStampStatus(String customerId, String stampDefinitionId, String stampStatus);
	
	@Query("select s from StampEntity s where s.customerId = ?1 and s.stampDefinitionId in ?2 and s.stampStatus = ?3 ")
	List<StampEntity> getByCustomerIdAndStampDefinitionIdInAndStampStatus(String customerId, List<String> stampDefinitionIds, String stampStatus);
	
	List<StampEntity> getByUseOrderId(String userOrderId);
	
	List<StampEntity> getByRefundOrderId(String refundOrderId);

	@Modifying
	@Query("update StampEntity s set s.stampStatus = ?3, s.refundOrderId=?4 where s.id = ?1 and s.stampStatus=?2")
	int compareAndSetStatusById(String id, String oldStatus, String newStatus, String objectId);

	@Modifying
	@Query("update StampEntity s set s.stampStatus = ?3 where s.refundOrderId = ?1 and s.stampStatus=?2")
	int compareAndSetStatusByObjectId(String objectId, String oldStatus, String newStatus);

	@Query("select s from StampEntity s where s.serialNumber in ?1")
	List<StampEntity> getByStampCodes(List<String> stampCodes);

	@Modifying
	@Query("update StampEntity s set s.stampStatus = ?3 where s.id = ?1 and s.stampStatus=?2")
    int unbindStamp(String id, String oldStatus, String newStatus);

	@Modifying
	@Query("update StampEntity s set s.stampStatus = ?3 where s.orderId = ?1 and s.stampStatus=?2")
	int compareAndSetStatusByOrderId(String orderId, String oldStatus, String newStatus);
	
	@Modifying
	@Query("update StampEntity s set s.stampStatus = ?4, s.useOrderId = ?2 where s.id in ?1 and s.stampStatus = ?3 ")
	int updateForOrder(List<String> ids, String orderId, String oldStatus, String newStatus);

	@Modifying
	@Query("update StampEntity s set s.stampStatus = ?5, s.useOrderId = ?2, s.useOrderItemId = ?3 where s.id in ?1 and s.stampStatus = ?4 ")
	int updateForOrder(List<String> ids, String orderId, String orderItemId, String oldStatus, String newStatus);
	
	StampEntity getBySerialNumber(String serialNumber);
	
	@Modifying
	@Query("update StampEntity s set s.stampStatus = ?3 where s.serialNumber = ?1 and s.stampStatus=?2 ")
	int compareAndSetStatusBySerialNumber(String serialNumber, String oldStatus, String newStatus);
}
