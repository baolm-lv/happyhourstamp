/*
 * The copyright to the computer software herein is the property of
 * Lvmama. The software may be used and/or copied only
 * with the written permission of Lvmama or in accordance
 * with the terms and conditions stipulated in the agreement/contract
 * under which the software has been supplied.
 */

package com.lvmama.vst.hhs.product.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.lvmama.vst.hhs.product.dao.OrdStampOrderItemEntity;


/**
 *
 * @author Luochang Tang
 */
@Repository
public interface OrdStampOrderItemRepository extends JpaRepository<OrdStampOrderItemEntity, Long> {
    
    Long countByStampDefinitionId(String stampDefinitionId);
    
    OrdStampOrderItemEntity getByOrderId(Long orderId);
    
}
