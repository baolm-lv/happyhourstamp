/**
 * 
 */
package com.lvmama.vst.hhs.stamp.service;

import java.util.List;

import com.lvmama.vst.hhs.model.stamp.StampRefundApplyRequest;
import com.lvmama.vst.hhs.model.stamp.StampRefundOrder;
import com.lvmama.vst.hhs.model.stamp.StampRefundResponse;

/**
 * @author baolm
 *
 */
public interface StampRefundService {

    StampRefundResponse refundStampApply(StampRefundApplyRequest request);

    StampRefundResponse refundStampApplyBack(String refundId, String orderId, Integer refundNum);

    void notifyRefundId(String refundApplyId, String refundId);

    void notifyRefundResult(String refundId, String refundStatus);

    StampRefundOrder getByRefundApplyId(String refundApplyId);

    void unbindStamp(String refundId, List<String> stampCodes);

    void unbindAndRefundStamp(String refundId, List<String> stampCodes);

    void refundStampApplyBackByCode(String refundId, String orderId, List<String> stampCodes);

    void cancelStampRefund(String refundId);

    void cancelStampUnbind(String refundId);

    void cancelOrderAfterRefund(String orderId);
}
