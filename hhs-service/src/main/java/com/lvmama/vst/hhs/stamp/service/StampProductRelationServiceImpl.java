package com.lvmama.vst.hhs.stamp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lvmama.vst.hhs.stampDefinition.dao.PresaleStampDefinitionProductRelationEntity;
import com.lvmama.vst.hhs.stampDefinition.repositorySlave.PresaleStampDefinitionProductRelationRepositorySlave;

@Service
public class StampProductRelationServiceImpl implements StampProductRelationService {
    
    @Autowired
    private PresaleStampDefinitionProductRelationRepositorySlave relationRepositorySlave;

    @Override
    public PresaleStampDefinitionProductRelationEntity getStampProductRalationByGoodsId(String goodsId) {
       
        return this.relationRepositorySlave.getByGoodsId(goodsId);
    }

}
