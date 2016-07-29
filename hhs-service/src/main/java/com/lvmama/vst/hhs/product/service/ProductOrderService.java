/**
 * 
 */
package com.lvmama.vst.hhs.product.service;

import java.util.List;

import com.lvmama.vst.hhs.model.admin.StampOrderVo;
import com.lvmama.vst.hhs.model.product.StampBuyInfo;
import com.lvmama.vst.hhs.model.stamp.SaveOrderResponse;
import com.lvmama.vst.hhs.model.stamp.StampOrderRequest;
import com.lvmama.vst.hhs.product.dao.OrdStampOrderEntity;
import com.lvmama.vst.hhs.product.dao.OrdStampOrderItemEntity;
import com.lvmama.vst.hhs.stampDefinition.dao.StampDefinitionEntity;

/**
 * @author fengyonggang
 *
 */
public interface ProductOrderService {

	void saveStampOrder(StampOrderRequest request, StampDefinitionEntity definition, long orderId, long oderItemId);

	Long queryCountStampOrderByUserId(String userId);
	
	List<StampOrderVo> queryStampOrder(String stampId, String orderId, String contactName, String contactMobile, int startRow, int pageSize);
    
    public List<Long> getOrderIdByUserId(String userId, int startRow, int end);
    
    OrdStampOrderItemEntity getStampOrderItemById(long orderItemId);
    
    Long countStampOrder(String stampId, String orderId, String contactName, String contactMobile);
    
    Long countOrderStampOrderItemByStampDefinitionId(String stampDefinitionId);
    
    OrdStampOrderEntity getStampOrderByOrderId(long orderId);
    
    SaveOrderResponse saveProductOrder(StampBuyInfo request);

	void updateDownDate(Long orderId);
}
