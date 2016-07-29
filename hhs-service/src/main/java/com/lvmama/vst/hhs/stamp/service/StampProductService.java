package com.lvmama.vst.hhs.stamp.service;

import java.util.List;

import com.lvmama.vst.hhs.model.product.ProductAddition;
import com.lvmama.vst.hhs.model.product.ProductAdditionRequest;

public interface StampProductService {
    
    
    List<Long> getStampProductByProductIds(String ids);

    Boolean productIsExchange(Long productId,Long startDistrictId);

	ProductAddition getStampAddition(Long productId, ProductAdditionRequest request);
	
	List<Long> getStampProduct(String categoryIds,int startRow,int num);
	
	long countStampProduct(String categoryIds);
	
	boolean canProductModify(String productId);

}
