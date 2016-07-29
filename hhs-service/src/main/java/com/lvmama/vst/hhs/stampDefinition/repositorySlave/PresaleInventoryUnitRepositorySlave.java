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

import com.lvmama.vst.hhs.stampDefinition.dao.PresaleInventoryUnitEntity;

/**
 *
 * @author Luochang Tang
 */
@Repository
public interface PresaleInventoryUnitRepositorySlave extends JpaRepository<PresaleInventoryUnitEntity, String> {
	PresaleInventoryUnitEntity getByStampDefinitionId(String stampDefinitionId);

	@Query("select s from PresaleInventoryUnitEntity s where s.stampDefinitionId in ?1 ")
	List<PresaleInventoryUnitEntity> getByStampDefinitionIdIn(String[] stampDefinitionId);

	@Modifying
	@Query("update PresaleInventoryUnitEntity p set p.inventoryLevel = ?2 ,p.updateDate = ?3  where p.stampDefinitionId = ?1")
	int updateresaleInventoryUnit(String stampDefinitionId, int inventoryLevel, Timestamp time);

	@Modifying
	@Query("update PresaleInventoryUnitEntity p set p.inventoryLevel = p.inventoryLevel - ?2 where p.stampDefinitionId = ?1 and p.inventoryLevel >= ?2")
	int consumeInventory(String stampDefinitionId, int inventory);

	@Modifying
	@Query("update PresaleInventoryUnitEntity p set p.inventoryLevel = p.inventoryLevel + ?2 where p.stampDefinitionId = ?1")
	int rollbackConsumeInventory(String stampDefinitionId, int inventory);
}
