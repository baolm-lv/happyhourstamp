/**
 * 
 */
package com.lvmama.vst.hhs.stamp.service;

import java.util.List;

import com.lvmama.vst.hhs.model.stamp.OrderCancelResponse;
import com.lvmama.vst.hhs.model.stamp.SaveStampOrderResponse;
import com.lvmama.vst.hhs.model.stamp.StampCode;
import com.lvmama.vst.hhs.model.stamp.StampOrder;
import com.lvmama.vst.hhs.model.stamp.StampOrderCancelRequest;
import com.lvmama.vst.hhs.model.stamp.StampOrderDetails;
import com.lvmama.vst.hhs.model.stamp.StampOrderRequest;
import com.lvmama.vst.hhs.model.stamp.UseStampCodeInfo;

/**
 * @author fengyonggang
 *
 */
public interface StampOrderService {

	/**
	 * 预约券下单
	 * @param request
	 * @return
	 */
	SaveStampOrderResponse addStampOrder(StampOrderRequest request);
	
	StampOrderDetails getStampOrderDetails(long orderId);
	
	List<StampOrder> getStampOrderList(String userId, int start, int end);
	
	OrderCancelResponse cancelOrder(StampOrderCancelRequest stampOrderCancelRequest);

	boolean paymentDeposit(String orderId, Long actualAmount);
	
	Long getProductByOrderId(Long orderId);
	
	List<StampOrderDetails> getStampCodeList(String useOrderId,String stampId);
	
	boolean updateOrderStampPayType(Long orderId,String payType);

    List<StampCode> getOrderStampCodesOnly(String orderId);

    List<UseStampCodeInfo> getUseStampCodes(String useOrderId);
	
}
