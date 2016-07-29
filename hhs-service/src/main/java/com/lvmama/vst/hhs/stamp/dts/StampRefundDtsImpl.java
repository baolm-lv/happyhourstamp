/**
 * 
 */
package com.lvmama.vst.hhs.stamp.dts;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lvmama.vst.hhs.common.exception.StampBizException;
import com.lvmama.vst.hhs.common.exception.StampErrorCodes;
import com.lvmama.vst.hhs.common.web.BaseResponse;
import com.lvmama.vst.hhs.common.web.HhsRespHandler;
import com.lvmama.vst.hhs.model.stamp.StampExchangeOrderRefund;
import com.lvmama.vst.hhs.model.stamp.StampExchangeOrderRefundResponse;
import com.lvmama.vst.hhs.stamp.service.StampRefundService;
import com.lvmama.vst.hhs.stamp.service.StampService;

/**
 * @author baolm
 *
 */
@Service
public class StampRefundDtsImpl implements IStampRefundDts {

    @Autowired
    private StampService stampService;
    @Autowired
    private StampRefundService stampRefundService;

    @Override
    @HhsRespHandler
    public StampExchangeOrderRefundResponse getExchangeOrderStampsForRefund(String orderId) {

        if (StringUtils.isBlank(orderId))
            throw new StampBizException(StampErrorCodes.E_REQ_1001, "orderId is empty");

        List<StampExchangeOrderRefund> stamps = this.stampService.getExchangeOrderStamps(orderId);

        StampExchangeOrderRefundResponse resp = new StampExchangeOrderRefundResponse();
        if (stamps != null)
            resp.setStamps(stamps);

        return resp;
    }

    @Override
    @HhsRespHandler
    public BaseResponse cancelStampRefund(String refundId) {

        if (StringUtils.isBlank(refundId))
            throw new StampBizException(StampErrorCodes.E_REQ_1001, "refundId is empty");

        this.stampRefundService.cancelStampRefund(refundId);

        return new BaseResponse();
    }

    @Override
    @HhsRespHandler
    public BaseResponse cancelStampUnbind(String refundId) {

        if (StringUtils.isBlank(refundId))
            throw new StampBizException(StampErrorCodes.E_REQ_1001, "refundId is empty");

        this.stampRefundService.cancelStampUnbind(refundId);

        return new BaseResponse();
    }

}
