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
import org.springframework.stereotype.Repository;

import com.lvmama.vst.hhs.stampDefinition.dao.PresaleStampDefinitionGoodsBindingEntity;
import com.lvmama.vst.hhs.stampDefinition.dao.StampDefinitionEntity;

@Repository
public interface PresaleStampDefinitionGoodsBindingRepository extends 
					JpaRepository<PresaleStampDefinitionGoodsBindingEntity, String> {
	
	@Modifying
	void deleteByStampDefinition(StampDefinitionEntity stampDefinition);
	
	PresaleStampDefinitionGoodsBindingEntity getById(String goodsId);
	
	List<PresaleStampDefinitionGoodsBindingEntity> getByStampDefinition(StampDefinitionEntity stampDefinition);
	
	List<PresaleStampDefinitionGoodsBindingEntity> getByStampDefinitionIdOrderByGoodsIdAsc(String stampDefinitionId);
	
}
