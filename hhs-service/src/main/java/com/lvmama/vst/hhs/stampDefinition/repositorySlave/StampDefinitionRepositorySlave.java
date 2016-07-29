/*
 * The copyright to the computer software herein is the property of
 * Lvmama. The software may be used and/or copied only
 * with the written permission of Lvmama or in accordance
 * with the terms and conditions stipulated in the agreement/contract
 * under which the software has been supplied.
 */

package com.lvmama.vst.hhs.stampDefinition.repositorySlave;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.lvmama.vst.hhs.stampDefinition.dao.StampDefinitionEntity;

/**
 *
 * @author Luochang Tang
 */
@Repository

public interface StampDefinitionRepositorySlave extends JpaRepository<StampDefinitionEntity, String>, StampDefinitionRepositoryCustomSlave {
  
	StampDefinitionEntity getById(String id);

	List<StampDefinitionEntity> getByName(String name);

	@Modifying
	@Query("update StampDefinitionEntity u set u.activityStatus = ?2 where u.id = ?1")
	int updateStampActivityStatus(String id, String activityStatus);
	
	@Query("SELECT s FROM StampDefinitionEntity s WHERE s.productBinding.productId = ?1")
	List<StampDefinitionEntity> getByProductId(String productId);
	
	@Query("SELECT s FROM StampDefinitionEntity s WHERE s.productBinding.productId = ?1 and s.productBinding.departId = ?2")
	List<StampDefinitionEntity> getByProductIdAndDepartId(String productId, String departId);
	
	@Query("SELECT s FROM StampDefinitionEntity s WHERE s.productBinding.productId = ?1 and s.stampRedeemableStartDate <= ?2 and s.stampRedeemableEndDate >= ?2  and s.activityStatus='Y'")
	List<StampDefinitionEntity> getByProductIdAndRedeemDate(String productId, Timestamp redeemDate);
	
	@Query("SELECT s FROM StampDefinitionEntity s WHERE s.productBinding.productId = ?1 and s.productBinding.departId = ?2 and s.stampRedeemableStartDate <= ?3 and s.stampRedeemableEndDate >= ?3  and s.activityStatus='Y'")
	List<StampDefinitionEntity> getByProductIdAndDepartIdAndRedeemDate(String productId, String departId, Timestamp redeemDate);
	  
	
	@Query("SELECT s FROM StampDefinitionEntity s WHERE s.productBinding.productId = ?1 and s.stampOnsaleStartDate <= ?2 and s.stampRedeemableEndDate >= ?3  and s.activityStatus='Y'")
	List<StampDefinitionEntity> getByProductIdAndSaleStartDateAndRedeemEndDate(String productId, Timestamp saleStartDate, Timestamp redeemEndDate);
	
	@Query("SELECT s FROM StampDefinitionEntity s WHERE s.productBinding.productId = ?1 and s.productBinding.departId = ?2 and s.stampOnsaleStartDate <= ?3 and s.stampRedeemableEndDate >= ?4 and s.activityStatus='Y'")
	List<StampDefinitionEntity> getByProductIdAndDepartIdAndSaleStartDateAndRedeemEndDate(String productId, String departId, Timestamp saleStartDate, Timestamp redeemEndDate);
	
	@Query("SELECT s FROM StampDefinitionEntity s WHERE s.productBinding.productId in ?1 and s.stampOnsaleStartDate <= ?2 and s.stampRedeemableEndDate >= ?2 and s.activityStatus='Y' ")
    List<StampDefinitionEntity> getByProductId(List<Long> productIds,Timestamp date);
	
	@Query("SELECT s FROM StampDefinitionEntity s WHERE s.productBinding.productId = ?1 and s.stampOnsaleStartDate <= ?2 and s.stampOnsaleEndDate >= ?2 and s.activityStatus='Y' ")
    List<StampDefinitionEntity> getByProductIdAndStartSaleDateAndEndSaleDate(String productId, Timestamp date);
	
	@Query("SELECT s FROM StampDefinitionEntity s WHERE s.productBinding.productId = ?1 and  s.productBinding.departId = ?2 and s.stampOnsaleStartDate <= ?3 and s.stampOnsaleEndDate >= ?3 and s.activityStatus='Y' ")
    List<StampDefinitionEntity> getByProductIdAndDepartIdAndStartSaleDateAndEndSaleDate(String productId,String departId, Timestamp date);
	
	@Query("SELECT s FROM StampDefinitionEntity s WHERE s.productBinding.productId = ?1 and s.stampRedeemableStartDate <= ?2 and s.stampRedeemableEndDate >= ?2  and s.activityStatus='Y' ")
    List<StampDefinitionEntity> getByProductIdAndRedeemStartDateAndRedeemEndDate(String productId, Timestamp now);
	
	@Query("SELECT s FROM StampDefinitionEntity s WHERE s.productBinding.productId = ?1 and s.productBinding.departId = ?2 and s.stampRedeemableStartDate <= ?3 and s.stampRedeemableEndDate >= ?3 and s.activityStatus='Y'")
    List<StampDefinitionEntity> getByProductIdAndDepartIdAndRedeemStartDateAndRedeemEndDate(String productId, String departId, Timestamp now);
	
	@Query("SELECT count(s.id)  FROM StampDefinitionEntity s WHERE s.productBinding.productId = ?1 and s.activityStatus='Y'")
    Long getByProductIdAndActivityStatus(String productId);
	
	

	
}
