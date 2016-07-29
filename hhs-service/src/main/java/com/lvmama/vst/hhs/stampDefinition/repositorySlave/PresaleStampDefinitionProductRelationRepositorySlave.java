/*
 * The copyright to the computer software herein is the property of
 * Lvmama. The software may be used and/or copied only
 * with the written permission of Lvmama or in accordance
 * with the terms and conditions stipulated in the agreement/contract
 * under which the software has been supplied.
 */

package com.lvmama.vst.hhs.stampDefinition.repositorySlave;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.lvmama.vst.hhs.stampDefinition.dao.PresaleStampDefinitionProductRelationEntity;


/**
 *
 * @author Luochang Tang
 */
@Repository
public interface PresaleStampDefinitionProductRelationRepositorySlave extends 
					JpaRepository<PresaleStampDefinitionProductRelationEntity, String> {
	
	PresaleStampDefinitionProductRelationEntity getByStampDefinitionId(String stampDefinitionId);
	
	PresaleStampDefinitionProductRelationEntity getByGoodsId(String goodsId);
	
	

}
