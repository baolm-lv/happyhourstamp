package com.lvmama.vst.hhs.stamp.service;

import java.sql.Timestamp;

import com.lvmama.vst.hhs.stampDefinition.dao.PresaleInventoryUnitEntity;

public interface PreSaleInventoryUnitService {
    
    PresaleInventoryUnitEntity getByStampDefinitionId(String stampDefinitionId);
    
    @Deprecated
    void updateresaleInventoryUnit(String stampId,int inventory,Timestamp now);

    /**
     * 消费库存
     * @param stampDefinitionId
     * @param inventory
     * @return
     */
    int consumeInventory(String stampDefinitionId, int inventory);

    /**
     * 回滚库存
     * @param stampDefinitionId
     * @param inventory
     * @return
     */
    int rollbackConsumeInventory(String stampDefinitionId, int inventory);

}
