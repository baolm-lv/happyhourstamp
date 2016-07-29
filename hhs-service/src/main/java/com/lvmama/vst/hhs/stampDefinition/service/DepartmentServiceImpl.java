package com.lvmama.vst.hhs.stampDefinition.service;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;



@Service
public class DepartmentServiceImpl implements DepartmentService {

    public static final String HHS_STAMP_DEFINITION_KEY = "hhs.model.stamp.StampDetails_";  
	public static final String UNDER_SCORE = "_";
	public static final String HHS__STAMP_PRODUCT_IS_EXCHANGE = "hhs.model.stamp.productIsExchange_";
	public static final String DEPT_KEY2 = "com.lvmama.vst.hhs.model.Department2_";	
		
	public static final String HHS_STAMP_DEFINITION_PRODUCT_KEY = "hhs.model.stamp.ProductDetailsStamps_";
	public static final String HHS_IS_STAMP_DEFINITION_KEY = "hhs.model.stamp.getStampProductByProductIds_"; 
	
	@Override
	@CacheEvict(value = "Department", key = "#root.target.HHS_STAMP_DEFINITION_KEY + #id")
	public void deleteStampDefinitionById(String id) {
		return ;
	}

    @Override
    @CacheEvict(value = "Department", key = "#root.target.HHS_IS_STAMP_DEFINITION_KEY + #ids")
    public void deleteGetStampProductByProductIds(String ids) {
        return ;
    }

    @Override
    @CacheEvict(value = "Department", key = "#root.target.HHS__STAMP_PRODUCT_IS_EXCHANGE + #productId +#root.target.UNDER_SCORE + #startDistrictId")
    public void deleteProductIsExchange(String productId, String startDistrictId) {
        return ;
    }

    @Override
    @CacheEvict(value = "Department", key = "#root.target.HHS_STAMP_DEFINITION_PRODUCT_KEY + #productId +#root.target.UNDER_SCORE + #startDistrictId")
    public void deleteProductStampsDetails(String productId, String startDistrictId) {
       return ;        
    }

}
