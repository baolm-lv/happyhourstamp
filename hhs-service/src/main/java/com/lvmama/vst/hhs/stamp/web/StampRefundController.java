package com.lvmama.vst.hhs.stamp.web;

import static com.lvmama.vst.hhs.common.web.ResponseEntityBuilder.ok;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.lvmama.vst.hhs.common.exception.StampBizException;
import com.lvmama.vst.hhs.common.web.BaseResponse;
import com.lvmama.vst.hhs.common.web.HhsApiUriTemplates;
import com.lvmama.vst.hhs.common.web.HhsWebConstants;
import com.lvmama.vst.hhs.common.web.RestApiException;
import com.lvmama.vst.hhs.model.common.Constant.StampStatus;
import com.lvmama.vst.hhs.model.stamp.NotifyRefundIdRequest;
import com.lvmama.vst.hhs.model.stamp.StampCode;
import com.lvmama.vst.hhs.model.stamp.StampExchangeOrderItem;
import com.lvmama.vst.hhs.model.stamp.StampExchangeOrderRefundResponse;
import com.lvmama.vst.hhs.model.stamp.StampOrderDetails;
import com.lvmama.vst.hhs.model.stamp.StampOrderSimple;
import com.lvmama.vst.hhs.model.stamp.StampRefundApplyRequest;
import com.lvmama.vst.hhs.model.stamp.StampRefundOrder;
import com.lvmama.vst.hhs.model.stamp.StampRefundResponse;
import com.lvmama.vst.hhs.stamp.dts.IStampRefundDts;
import com.lvmama.vst.hhs.stamp.service.StampOrderService;
import com.lvmama.vst.hhs.stamp.service.StampRefundService;
import com.lvmama.vst.hhs.stamp.service.StampService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@Api(value = HhsWebConstants.APP_ROOT_PATH)
@RequestMapping(HhsApiUriTemplates.V1)
public class StampRefundController {

    private static final Logger logger = LoggerFactory.getLogger(StampRefundController.class);

    @Autowired
    private StampOrderService stampOrderService;
    @Autowired
    private StampRefundService stampRefundService;
    @Autowired
    private StampService stampService;
    @Autowired
    private IStampRefundDts stampRefundDts;

    @ApiOperation("退券详情")
    @RequestMapping(
            method = RequestMethod.GET,
            value = "/customer/stamp/refund/{refundApplyId}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<StampRefundOrder> getStampRefundOrder(
            @PathVariable("refundApplyId") final String refundApplyId) {

        logger.info("getStampRefundOrder: refundApplyId={}", refundApplyId);
        try {
            StampRefundOrder refundOrder = this.stampRefundService.getByRefundApplyId(refundApplyId);
            return ok(refundOrder);
        } catch (Exception e) {
            logger.error("getStampRefundOrder error: " + refundApplyId, e);
            throw new RestApiException(e);
        }
    }

    @ApiOperation("预售券退券申请")
    @RequestMapping(
            method = RequestMethod.POST,
            value = "/customer/stamp/refund/apply",
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<StampRefundResponse> refundStampApplyFront(
            @RequestBody final StampRefundApplyRequest request) {

        logger.info("refundStampApplyFront: param={}", JSON.toJSONString(request));
        try {
            StampRefundResponse response = this.stampRefundService.refundStampApply(request);

            this.stampRefundService.cancelOrderAfterRefund(request.getOrderId());

            return ok(response);
        } catch (StampBizException e) {
            logger.error("refundStampApplyFront biz error: req={}, errorCode={}, errorMsg={}",
                    JSON.toJSONString(request), e.getErrorCode(), e.getErrorMsg());
            throw new RestApiException(e.getErrorCode());
        } catch (Exception e) {
            logger.error("refundStampApplyFront error: " + JSON.toJSONString(request), e);
            throw new RestApiException(e);
        }
    }

    @ApiOperation("预售券退款ID通知")
    @RequestMapping(
            method = RequestMethod.POST,
            value = "/customer/stamp/refund/notifyId",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> notifyRefundId(@RequestBody final NotifyRefundIdRequest request) {

        logger.info("notifyRefundId: param={}", JSON.toJSONString(request));
        try {
            this.stampRefundService.notifyRefundId(request.getRefundApplyId(), request.getRefundId());
            return ok();
        } catch (StampBizException e) {
            logger.error("notifyRefundId biz error: request={}, errorCode={}, errorMsg={}", JSON.toJSONString(request),
                    e.getErrorCode(), e.getErrorMsg());
            throw new RestApiException(e.getErrorCode());
        } catch (Exception e) {
            logger.error("notifyRefundId error: " + JSON.toJSONString(request), e);
            throw new RestApiException(e);
        }
    }

    @ApiOperation("预售券退款结果通知")
    @RequestMapping(
            method = RequestMethod.POST,
            value = "/customer/stamp/refund/notifyResult",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> notifyRefundResult(@RequestParam("refundId") String refundId,
            @RequestParam("refundStatus") String refundStatus) {

        logger.info("notifyRefundResult: refundId={}, refundStatus={}", refundId, refundStatus);
        try {
            this.stampRefundService.notifyRefundResult(refundId, refundStatus);
            return ok();
        } catch (StampBizException e) {
            logger.error("notifyRefundResult biz error: refundId={}, refundStatus={}, errorCode={}, errorMsg={}",
                    refundId, refundStatus, e.getErrorCode(), e.getErrorMsg());
            throw new RestApiException(e.getErrorCode());
        } catch (Exception e) {
            logger.error("notifyRefundResult error: " + refundId, e);
            throw new RestApiException(e);
        }
    }

    @ApiOperation("预售券退券申请(后台)")
    @RequestMapping(
            method = RequestMethod.POST,
            value = "/back/stamp/refund/apply",
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<StampRefundResponse> refundStampApplyBack(@RequestParam("refundId") String refundId,
            @RequestParam("orderId") String orderId, @RequestParam("refundNum") Integer refundNum) {

        logger.info("refundStampApplyBack: refundId={}, orderId={}, refundNum={}", refundId, orderId, refundNum);
        try {
            StampRefundResponse response = this.stampRefundService.refundStampApplyBack(refundId, orderId, refundNum);
            
            this.stampRefundService.cancelOrderAfterRefund(orderId);
            
            return ok(response);
        } catch (StampBizException e) {
            logger.error(
                    "refundStampApplyFront biz error: refundId={}, orderId={}, refundNum={}, errorCode={}, errorMsg={}",
                    refundId, orderId, refundNum, e.getErrorCode(), e.getErrorMsg());
            throw new RestApiException(e.getErrorCode());
        } catch (Exception e) {
            logger.error("refundStampApplyBack error:" + refundId + "," + orderId + "," + refundNum, e);
            throw new RestApiException(e);
        }
    }

    @ApiOperation("预售券退券申请(后台)")
    @RequestMapping(
            method = RequestMethod.POST,
            value = "/back/stamp/refundByCode/apply",
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> refundStampApplyBackByCode(@RequestParam("refundId") String refundId,
            @RequestParam("orderId") String orderId, @RequestParam("stampCodes") List<String> stampCodes) {

        logger.info("refundStampApplyBackByCode: refundId={}, orderId={}, stampCodes={}", refundId, orderId,
                stampCodes);
        try {
            this.stampRefundService.refundStampApplyBackByCode(refundId, orderId, stampCodes);
            return ok();
        } catch (StampBizException e) {
            logger.error(
                    "refundStampApplyBackByCode biz error: refundId={}, orderId={}, stampCodes={}, errorCode={}, errorMsg={}",
                    refundId, orderId, JSON.toJSONString(stampCodes), e.getErrorCode(), e.getErrorMsg());
            throw new RestApiException(e.getErrorCode());
        } catch (Exception e) {
            logger.error("refundStampApplyBackByCode error:" + refundId + "," + orderId + "," + stampCodes, e);
            throw new RestApiException(e);
        }
    }

    @ApiOperation("解绑券(后台)")
    @RequestMapping(
            method = RequestMethod.POST,
            value = "/back/stamp/refund/unbind",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> unbindStamp(@RequestParam("refundId") String refundId,
            @RequestParam("stampCodes") List<String> stampCodes) {

        logger.info("unbindStamp: refundId={}, stampCodes={}", refundId, stampCodes);
        try {
            this.stampRefundService.unbindStamp(refundId, stampCodes);
            return ok();
        } catch (StampBizException e) {
            logger.error("unbindStamp biz error: refundId={}, stampCodes={}, errorCode={}, errorMsg={}", refundId,
                    JSON.toJSONString(stampCodes), e.getErrorCode(), e.getErrorMsg());
            throw new RestApiException(e.getErrorCode());
        } catch (Exception e) {
            logger.error("unbindStamp error:" + refundId + "," + stampCodes, e);
            throw new RestApiException(e);
        }
    }

    @ApiOperation("解绑并退券(后台)")
    @RequestMapping(
            method = RequestMethod.POST,
            value = "/back/stamp/refund/unbindAndRefund",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> unbindAndRefundStamp(@RequestParam("refundId") String refundId,
            @RequestParam("stampCodes") List<String> stampCodes) {

        logger.info("unbindAndRefundStamp: refundId={}, stampCodes={}", refundId, stampCodes);
        try {
            this.stampRefundService.unbindAndRefundStamp(refundId, stampCodes);
            return ok();
        } catch (StampBizException e) {
            logger.error("unbindAndRefundStamp biz error: refundId={}, stampCodes={}, errorCode={}, errorMsg={}",
                    refundId, JSON.toJSONString(stampCodes), e.getErrorCode(), e.getErrorMsg());
            throw new RestApiException(e.getErrorCode());
        } catch (Exception e) {
            logger.error("unbindAndRefundStamp error:" + refundId + "," + stampCodes, e);
            throw new RestApiException(e);
        }
    }

    @ApiOperation("预约券订单（后台）")
    @RequestMapping(
            method = RequestMethod.GET,
            value = "/back/stamp/order/{orderId}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<StampOrderSimple> getStampOrderStampInfo(@PathVariable("orderId") String orderId) {

        logger.info("getStampOrderStampInfo: orderId={}", orderId);
        try {
            StampOrderSimple result = new StampOrderSimple();

            // FIXME by baolm , 是否定制查询
            StampOrderDetails details = this.stampOrderService.getStampOrderDetails(NumberUtils.toLong(orderId));
            if (details == null) {
                return ok(null);
            }
            result.setOrderId(details.getOrderId());
            result.setPrice(details.getPrice());
            result.setStampId(details.getStamp().getId());
            result.setStampName(details.getStamp().getName());
            result.setStampExpireDate(details.getStampCodes().get(0).getExpiredDate());
            result.setUnuseStampCodes(getCanRefundStamp(details));

            return ok(result);
        } catch (Exception e) {
            logger.error("getStampOrderStampInfo error:" + orderId, e);
            throw new RestApiException(e);
        }
    }

    /**
     * 可退的券集合
     * <p>
     * （包括{@code StampStatus.UNUSE}和{@code StampStatus.PAID_INVALID}两种状态的券）
     */
    private List<StampCode> getCanRefundStamp(StampOrderDetails details) {
        List<StampCode> canRefundStamps = Lists.newArrayList();
        if (CollectionUtils.isEmpty(details.getStampCodes())) {
            return canRefundStamps;
        }
        for (StampCode code : details.getStampCodes()) {
            if (StringUtils.equals(code.getStampStatus(), StampStatus.UNUSE.name())
                    || StringUtils.equals(code.getStampStatus(), StampStatus.PAID_INVALID.name())) {
                canRefundStamps.add(code);
            }
        }
        return canRefundStamps;
    }

    @ApiOperation("兑换券产品订单（后台）")
    @RequestMapping(
            method = RequestMethod.GET,
            value = "/back/stamp/order/useStampInfo/{orderId}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<StampExchangeOrderItem>> getStampExchangeOrder(@PathVariable("orderId") String orderId) {

        logger.info("getStampExchangeOrder: orderId={}", orderId);
        try {
            List<StampExchangeOrderItem> list = this.stampService.getStampExchangeOrder(orderId);
            return ok(list);
        } catch (Exception e) {
            logger.error("getStampExchangeOrder error:" + orderId, e);
            throw new RestApiException(e);
        }

    }

    @ApiOperation("券兑换订单退款查询（按券分类）")
    @RequestMapping(
            method = RequestMethod.GET,
            value = "/back/stamp/refund/exchangeOrders/{orderId}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<StampExchangeOrderRefundResponse> getExchangeOrderStamps(
            @PathVariable("orderId") String orderId) {
        try {
            StampExchangeOrderRefundResponse resp = this.stampRefundDts.getExchangeOrderStampsForRefund(orderId);
            return ok(resp);
        } catch (Exception e) {
            logger.error("getExchangeOrderStamps error:" + orderId, e);
            throw new RestApiException(e);
        }
    }

    @ApiOperation("退券废弃")
    @RequestMapping(
            method = RequestMethod.POST,
            value = "/back/stamp/refund/cancel/{refundId}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<BaseResponse> cancelStampRefund(@PathVariable("refundId") String refundId) {

        try {
            BaseResponse resp = this.stampRefundDts.cancelStampRefund(refundId);
            return ok(resp);
        } catch (Exception e) {
            logger.error("cancelStampRefund error: " + refundId, e);
            throw new RestApiException(e);
        }
    }

    @ApiOperation("券解绑废弃")
    @RequestMapping(
            method = RequestMethod.POST,
            value = "/back/stamp/unbind/cancel/{refundId}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<BaseResponse> cancelStampUnbind(@PathVariable("refundId") String refundId) {

        try {
            BaseResponse resp = this.stampRefundDts.cancelStampUnbind(refundId);
            return ok(resp);
        } catch (Exception e) {
            logger.error("cancelStampUnbind error: " + refundId, e);
            throw new RestApiException(e);
        }
    }
}
