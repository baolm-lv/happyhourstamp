package com.lvmama.vst.hhs.stamp.service;

import com.lvmama.vst.hhs.stampDefinition.dao.PresaleStampDefinitionProductRelationEntity;

public interface StampProductRelationService {
    
    PresaleStampDefinitionProductRelationEntity getStampProductRalationByGoodsId(String goodsId);

}
