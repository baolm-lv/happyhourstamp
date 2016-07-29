/**
 * 
 */
package com.lvmama.vst.hhs.stamp.dts;

import com.lvmama.vst.hhs.common.web.BaseResponse;
import com.lvmama.vst.hhs.model.stamp.StampExchangeOrderRefundResponse;

/**
 * @author baolm
 *
 */
public interface IStampRefundDts {

    StampExchangeOrderRefundResponse getExchangeOrderStampsForRefund(String orderId);

    BaseResponse cancelStampRefund(String refundId);

    BaseResponse cancelStampUnbind(String refundId);

}
