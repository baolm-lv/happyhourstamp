package com.lvmama.vst.hhs.stamp.service;

import java.sql.Timestamp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lvmama.vst.hhs.stampDefinition.dao.PresaleInventoryUnitEntity;
import com.lvmama.vst.hhs.stampDefinition.repository.PresaleInventoryUnitRepository;
import com.lvmama.vst.hhs.stampDefinition.repositorySlave.PresaleInventoryUnitRepositorySlave;

@Service
public class PreSaleInventoryUnitServiceImpl implements PreSaleInventoryUnitService {
    
    @Autowired
    private PresaleInventoryUnitRepository presaleInventoryUnitRepository;
    @Autowired
    private PresaleInventoryUnitRepositorySlave presaleInventoryUnitRepositorySlave;

    @Override
    @Transactional("slaveTransactionManager")
    public PresaleInventoryUnitEntity getByStampDefinitionId(String stampDefinitionId) {
       
        return this.presaleInventoryUnitRepositorySlave.getByStampDefinitionId(stampDefinitionId);
    }

    @Deprecated
    @Override   
    @Transactional("transactionManager")
    public void updateresaleInventoryUnit(String stampId, int inventory, Timestamp now) {        
        this.presaleInventoryUnitRepository.updateresaleInventoryUnit(stampId, inventory, now);
    }
    
    @Override
    @Transactional("transactionManager")
    public int consumeInventory(String stampDefinitionId, int inventory) {
    	return this.presaleInventoryUnitRepository.consumeInventory(stampDefinitionId, inventory);
    }

	@Override
	@Transactional("transactionManager")
	public int rollbackConsumeInventory(String stampDefinitionId, int inventory) {
		return this.presaleInventoryUnitRepository.rollbackConsumeInventory(stampDefinitionId, inventory);
	}

}
