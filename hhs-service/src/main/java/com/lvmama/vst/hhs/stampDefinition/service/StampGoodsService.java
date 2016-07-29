package com.lvmama.vst.hhs.stampDefinition.service;

import java.util.List;

import com.lvmama.vst.hhs.model.admin.AddStampGoodsRelationRequest;
import com.lvmama.vst.hhs.model.admin.StampDetailsVo;
import com.lvmama.vst.hhs.model.admin.StampGoodsVo;
import com.lvmama.vst.hhs.stampDefinition.bo.StampGoodsBindingBo;
import com.lvmama.vst.hhs.stampDefinition.dao.PresaleStampDefinitionGoodsBindingEntity;
import com.lvmama.vst.hhs.stampDefinition.dao.StampDefinitionEntity;

public interface StampGoodsService {

	List<PresaleStampDefinitionGoodsBindingEntity> getStampGoodsBindingByStamp(StampDefinitionEntity stamp);
	
	List<PresaleStampDefinitionGoodsBindingEntity> getStampGoodsBindingByStampId(String stampId);
	
	StampGoodsBindingBo getStampGoodsByStampId(String stampId);
	
	StampDetailsVo addStampGoodsRelation(AddStampGoodsRelationRequest request);
    
    List<StampGoodsVo> findStampGoods(String stampId);
    
    boolean isModifyActivityStatus(String stampDefinitionId);

}
