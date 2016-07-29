/**
 * 
 */
package com.lvmama.vst.hhs.stamp.dts;

import com.lvmama.vst.hhs.model.stamp.OrderStampCodesResponse;
import com.lvmama.vst.hhs.model.stamp.UseStampCodeResponse;

/**
 * @author "baolm"
 *
 */
public interface IStampOrderDts {
    OrderStampCodesResponse getOrderStampCodesOnly(String orderId);

    UseStampCodeResponse getUseStampCodes(String useOrderId);
}
