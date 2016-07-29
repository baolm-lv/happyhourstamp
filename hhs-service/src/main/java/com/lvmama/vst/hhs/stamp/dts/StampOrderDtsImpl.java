/**
 * 
 */
package com.lvmama.vst.hhs.stamp.dts;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lvmama.vst.hhs.common.web.HhsRespHandler;
import com.lvmama.vst.hhs.model.stamp.OrderStampCodesResponse;
import com.lvmama.vst.hhs.model.stamp.StampCode;
import com.lvmama.vst.hhs.model.stamp.UseStampCodeInfo;
import com.lvmama.vst.hhs.model.stamp.UseStampCodeResponse;
import com.lvmama.vst.hhs.stamp.service.StampOrderService;

/**
 * @author "baolm"
 *
 */
@Service
public class StampOrderDtsImpl implements IStampOrderDts {

    @Autowired
    private StampOrderService stampOrderService;
    /* (non-Javadoc)
     * @see com.lvmama.vst.hhs.stamp.dts.IStampOrderDts#getOrderStampCodesOnly(long)
     */
    @Override
    @HhsRespHandler
    public OrderStampCodesResponse getOrderStampCodesOnly(String orderId) {
        List<StampCode> list = stampOrderService.getOrderStampCodesOnly(orderId);
        OrderStampCodesResponse resp = new OrderStampCodesResponse();
        resp.setStampCodes(list);
        return resp;
    }
    
    @Override
    @HhsRespHandler
    public UseStampCodeResponse getUseStampCodes(String useOrderId) {
        UseStampCodeResponse resp = new UseStampCodeResponse();
        List<UseStampCodeInfo> useStampCodes = stampOrderService.getUseStampCodes(useOrderId);
        resp.setUseStampCodes(useStampCodes);
        return resp;
    }

}
