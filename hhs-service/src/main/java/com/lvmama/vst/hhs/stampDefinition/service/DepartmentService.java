package com.lvmama.vst.hhs.stampDefinition.service;



public interface DepartmentService {

	void deleteStampDefinitionById(String id);
	
	void deleteGetStampProductByProductIds(String ids);
	
	void deleteProductIsExchange(String productId,String startDistrictId);
	
	void deleteProductStampsDetails(String productId,String startDistrictId);
}
