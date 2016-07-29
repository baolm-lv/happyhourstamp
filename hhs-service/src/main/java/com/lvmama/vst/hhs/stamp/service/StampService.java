/**
 * 
 */
package com.lvmama.vst.hhs.stamp.service;

import java.util.List;
import java.util.Map;

import com.lvmama.vst.comm.vo.order.BuyInfo;
import com.lvmama.vst.hhs.model.common.Constant.StampPhase;
import com.lvmama.vst.hhs.model.stamp.StampDetails;
import com.lvmama.vst.hhs.model.stamp.StampExchangeOrderItem;
import com.lvmama.vst.hhs.model.stamp.StampExchangeOrderRefund;
import com.lvmama.vst.hhs.model.stamp.StampGoods;
import com.lvmama.vst.hhs.model.stamp.StampOrderPerson;
import com.lvmama.vst.hhs.model.stamp.StampRedeem;
import com.lvmama.vst.hhs.stampDefinition.dao.StampDefinitionEntity;
import com.lvmama.vst.hhs.stampDefinition.dao.StampEntity;

/**
 * @author fengyonggang
 *
 */
public interface StampService {

	StampDefinitionEntity getStampDefinitionById(String id);
	
	StampDetails getSaledStamp(String stampId);
	
	StampDetails getStampById(String id);
	
	StampDetails getStampDetails(StampDefinitionEntity stampDefinition);
	
	List<StampDetails> getStampDetails(List<StampDefinitionEntity> stampDefinitions);
	
	List<StampDefinitionEntity> getStampByProductId(Long productId);
	
	List<StampDefinitionEntity> getStampByIds(List<String> stampIds);
	
	List<StampDefinitionEntity> getStampByProductIdAndDepartId(Long productId, Long departId);
	
	List<StampDefinitionEntity> getAvailableStampByProductIdAndDepartId(Long productId, Long departId, StampPhase phase);
	
	List<StampDetails> getAvailableStampDetailsByProductIdAndDepartId(Long productId, Long departId, StampPhase phase);
	
	List<StampRedeem> getRedeemStamps(Long productId, Long userId, Long departId, boolean withCode);

	List<StampEntity> getStampCodeByOrderId(String orderId);

	String saveStamp(BuyInfo request, StampDefinitionEntity definition, long orderId);
	
	List<StampEntity> getUnusedStampCodeByStampIdAndCustomerId(String stampId, String customerId);
	
	Map<String, List<StampEntity>> getUnusedStampCodeByStampIdAndCustomerId(List<String> stampIds, String customerId);

	List<StampExchangeOrderItem> getStampExchangeOrder(String useOrderId);

	void paymentComplete(String orderId);
	
	void updateStampsForOrder(List<String> ids, String orderId);
	
	int unbindStamp(String id, String oldStatus, String newStatus);

	List<StampEntity> getByOrderIdAndStampStatus(String orderId, String stampStatus);

	void invalidStampCode(String orderId, boolean hasPaid);

	void rollbackInvalidStampCode(String orderId, boolean hasPaid);

	List<StampEntity> getStampCodeByIds(List<String> ids);

	List<StampEntity> updateStampsForOrder(List<StampEntity> stamps);

	void updateStampsForOrder(List<String> extractStampEntityIds, String valueOf, String valueOf2);
	
	List<StampGoods> vstGetStampBindGoods(String stampId);

	StampOrderPerson getStampOrderPerson(String stampId, Long userId);

	List<StampExchangeOrderRefund> getExchangeOrderStamps(String useOrderId);
}
