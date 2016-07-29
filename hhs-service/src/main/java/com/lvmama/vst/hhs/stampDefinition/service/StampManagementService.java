package com.lvmama.vst.hhs.stampDefinition.service;

import java.sql.Timestamp;
import java.util.List;

import com.lvmama.vst.hhs.model.admin.StampCreationRequest;
import com.lvmama.vst.hhs.model.admin.StampDetailsVo;
import com.lvmama.vst.hhs.model.admin.StampRequest;
import com.lvmama.vst.hhs.stampDefinition.dao.StampDefinitionEntity;

public interface StampManagementService {

	String createStampDefinition(StampCreationRequest createRequest) throws RuntimeException;

	StampDetailsVo getStampById(String id);

	List<StampDetailsVo> getStampDefinitionByName(String name);

	List<StampDetailsVo> queryStampList(StampRequest stamp,int startRow,int rowNum);
	
	long countQueryStampList(StampRequest stamp);

	void updateStampActivityStatus(String id, String activityStatus,String operationName);
	
	void updateStampDefinition(StampCreationRequest createRequest);
	
	StampDetailsVo getStampDetails(StampDefinitionEntity stampDefinition);
	
	List<StampDetailsVo> getStampDetails(List<StampDefinitionEntity> stampDefinitions);
	
    Boolean goodsIsPreSaleByTime(String productId,String goodsId,Timestamp date);
	
    void deleteStampDefinition(String id);
	
//	StampDefinitionEntity  getStampDefintionById(String id);
	
}
