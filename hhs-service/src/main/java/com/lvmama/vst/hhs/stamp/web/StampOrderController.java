package com.lvmama.vst.hhs.stamp.web;

import static com.lvmama.vst.hhs.common.web.ResponseEntityBuilder.ok;

import java.util.List;

import javax.validation.Valid;

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
import com.lvmama.vst.hhs.common.web.HhsApiUriTemplates;
import com.lvmama.vst.hhs.common.web.HhsWebConstants;
import com.lvmama.vst.hhs.common.web.RestApiException;
import com.lvmama.vst.hhs.model.common.Constant.StampStatus;
import com.lvmama.vst.hhs.model.stamp.OrderCancelResponse;
import com.lvmama.vst.hhs.model.stamp.SaveStampOrderResponse;
import com.lvmama.vst.hhs.model.stamp.StampCheckRefundOrderStamp;
import com.lvmama.vst.hhs.model.stamp.StampCode;
import com.lvmama.vst.hhs.model.stamp.StampDetails;
import com.lvmama.vst.hhs.model.stamp.StampGoods;
import com.lvmama.vst.hhs.model.stamp.StampOrder;
import com.lvmama.vst.hhs.model.stamp.StampOrderCancelRequest;
import com.lvmama.vst.hhs.model.stamp.StampOrderDetails;
import com.lvmama.vst.hhs.model.stamp.StampOrderPerson;
import com.lvmama.vst.hhs.model.stamp.StampOrderRequest;
import com.lvmama.vst.hhs.model.stamp.UseStampCodeResponse;
import com.lvmama.vst.hhs.product.service.ProductOrderService;
import com.lvmama.vst.hhs.stamp.dts.IStampOrderDts;
import com.lvmama.vst.hhs.stamp.service.StampOrderService;
import com.lvmama.vst.hhs.stamp.service.StampService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@Api(value = HhsWebConstants.APP_ROOT_PATH)
@RequestMapping(HhsApiUriTemplates.V1 + "/customer/stamp/")
public class StampOrderController {
	
	private static final Logger logger = LoggerFactory.getLogger(StampOrderController.class);

	@Autowired
	private StampService stampService;
	@Autowired
	private StampOrderService stampOrderService;
	@Autowired
	private ProductOrderService productOrderService;
	@Autowired
    private IStampOrderDts stampOrderDts;
	
	@ApiOperation("预约券下单下单页，预售券信息")
	@RequestMapping(method = RequestMethod.GET,
			value = "/{stampId}",
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<StampDetails> getStampDetails(@PathVariable("stampId") final String stampId) {
		try {
			StampDetails details = stampService.getSaledStamp(stampId);
			return ok(details);
		} catch (Exception e) {
			throw new RestApiException(e);
		}
	}
	
	@ApiOperation("预约券下单")
	@RequestMapping(method = RequestMethod.POST,
			value = "/order",
			produces = MediaType.APPLICATION_JSON_VALUE,
			consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<SaveStampOrderResponse> saveStampOrder(@Valid @RequestBody final StampOrderRequest request) {
		try {
			SaveStampOrderResponse response = stampOrderService.addStampOrder(request);
			return ok(response);
		} catch (Exception e) {
			throw new RestApiException(e);
		}
	}
	
	@ApiOperation("预约券订单列表")
	@RequestMapping(method = RequestMethod.GET,
			value = "/order",
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<StampOrder>> getStampOrderByUser(@RequestParam("userId") String userId, @RequestParam("start") int start,
	        @RequestParam("end") int end) {
		try {
			List<StampOrder> stampOrderes = stampOrderService.getStampOrderList(userId, start, end);
			return ok(stampOrderes);
		} catch (Exception e) {
			throw new RestApiException(e);
		}
	}
	
	@ApiOperation("预约券订单列表数")
	@RequestMapping(method = RequestMethod.GET,
	            value = "/countOrder",
	            produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Long> getStampOrderByUser(@RequestParam("userId") String userId) {
		Long count = this.productOrderService.queryCountStampOrderByUserId(userId);
	    return ok(count);
	}
	
	@ApiOperation("预约券订单详情")
	@RequestMapping(method = RequestMethod.GET,
			value = "/order/{orderId}",
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<StampOrderDetails> getStampOrderById(@PathVariable("orderId") String orderId) {
		try {
			StampOrderDetails details = stampOrderService.getStampOrderDetails(NumberUtils.toLong(orderId));
			return ok(details);
		} catch (Exception e) {
			throw new RestApiException(e);
		}
	}
	
	   @ApiOperation("预约券订单详情vstback")
	    @RequestMapping(method = RequestMethod.GET,
	            value = "/back/order/{orderId}",
	            produces = MediaType.APPLICATION_JSON_VALUE)
	    public ResponseEntity<StampOrderDetails> getStampBackOrderById(@PathVariable("orderId") String orderId) {
	        try {
	            StampOrderDetails details = stampOrderService.getStampOrderDetails(NumberUtils.toLong(orderId));
	            return ok(details);
	        } catch (Exception e) {
	            throw new RestApiException(e);
	        }
	    }
	
	@ApiOperation("预约券订单详情")
	@RequestMapping(method = RequestMethod.GET,
			value = "/order/{orderId}/checkRefund",
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<StampCheckRefundOrderStamp> checkRefundOrderStamp(@PathVariable("orderId") String orderId) {
		try {
			StampCheckRefundOrderStamp result = new StampCheckRefundOrderStamp();

			// FIXME by baolm , 是否定制查询
			StampOrderDetails details = stampOrderService.getStampOrderDetails(NumberUtils.toLong(orderId));
			if(details == null) {
				return ok(null);
			}
			result.setOrderId(details.getOrderId());
			result.setOrderStatus(details.getOrderStatus());
			result.setPaidPrice(details.getPaidPrice());
			result.setPrice(details.getPrice());
			result.setStampId(details.getStamp().getId());
			result.setStampName(details.getStamp().getName());
			result.setProductName(details.getStamp().getBoundMerchant().getProductName());
			int unuseNum = 0;
			boolean hasRefundOrder = false;
			if (CollectionUtils.isNotEmpty(details.getStampCodes())) {
				for (StampCode code : details.getStampCodes()) {
					// 有退款中的券
					if (StampStatus.REFUNDING.name().equals(code.getStampStatus())
							|| StampStatus.REFUND_FAIL.name().equals(code.getStampStatus())
							|| StampStatus.UNUSE_LOCK.name().equals(code.getStampStatus())) {
						hasRefundOrder = true;
						break;
					}
					// 可退券数量
					if (StringUtils.equals(code.getStampStatus(), StampStatus.UNUSE.name())
							|| StringUtils.equals(code.getStampStatus(), StampStatus.PAID_INVALID.name())) {
						unuseNum++;
					}
				}
			}
			result.setHasRefundOrder(hasRefundOrder);
			result.setUnuseNum(unuseNum);

			return ok(result);
		} catch (Exception e) {
			throw new RestApiException(e);
		}
	}	
	@ApiOperation("券订单的取消")
	@RequestMapping(method = RequestMethod.PUT, value = "/order/cancel",
	produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<OrderCancelResponse> cancelStampOrderById(@Valid @RequestBody final StampOrderCancelRequest request) {
		logger.info("stamp order cancel, param:{}", JSON.toJSONString(request));
		try {
			OrderCancelResponse rl=stampOrderService.cancelOrder(request);
			return ok(rl);
		} catch (Exception e) {
			logger.error("stamp order cancel, param:" + JSON.toJSONString(request), e);
			throw new RestApiException(e);
		}
	}
	
	@ApiOperation("券订单定金支付完成")
	@RequestMapping(
			method = RequestMethod.POST, 
			value = "/order/payment/deposit",
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Boolean> paymentDeposit(@RequestParam("orderId") final String orderId,
			@RequestParam("actualAmount") final Long actualAmount) {
		logger.info("paymentDeposit, orderId: {}, actualAmount:{}", orderId, actualAmount);
		try {
			boolean flag = stampOrderService.paymentDeposit(orderId, actualAmount);
			return ok(flag);
		} catch (Exception e) {
			logger.error("paymentDeposit error: " + orderId + "," + actualAmount, e);
			throw new RestApiException(e);
		}
	}
	
	@ApiOperation("券订单支付完成")
	@RequestMapping(method = RequestMethod.POST,
			value = "/order/payment/complete",
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Void> paymentComplete(@RequestParam("orderId") final String orderId) {
		
		logger.info("paymentComplete, orderId: {}", orderId);
		try {
			stampService.paymentComplete(orderId);
			return ok();
		} catch (Exception e) {
			logger.error("paymentComplete error: " + orderId, e);
			throw new RestApiException(e);
		}
	}
	@ApiOperation("通过订单id查询对应的产品id")
	@RequestMapping(method = RequestMethod.GET,
			value = "/prodouct/{orderId}",
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Long> getProductByOrderId(@PathVariable("orderId") final Long orderId) {
		try {
			Long  prodcutId = stampOrderService.getProductByOrderId(orderId);
			return ok(prodcutId);
		} catch (Exception e) {
			throw new RestApiException(e);
		}
	}
	
	 @ApiOperation(value = "预售券兑换订单 预售券来源")
     @RequestMapping(
             method = RequestMethod.GET, 
             value = "getStampCodeOrder", 
             produces = MediaType.APPLICATION_JSON_VALUE)
     public ResponseEntity<List<StampOrderDetails>> getStampCodeOrder(
             @RequestParam(value = "orderId", required = true) Long orderId) {     

         try {
             List<StampOrderDetails> details = stampOrderService.getStampCodeList(String.valueOf(orderId), "");
             return ok(details);
         } catch (Exception e) {
             throw new RestApiException(e);
         }
     }
	 
	@ApiOperation("vstback请求预售券绑定商品信息")
	@RequestMapping(method = RequestMethod.GET, value = "/bindGoods/{stampId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<StampGoods>> getVstStampDetails(@PathVariable("stampId") final String stampId) {
		try {
		    List<StampGoods> goods = stampService.vstGetStampBindGoods(stampId);
			return ok(goods);
		} catch (Exception e) {
			throw new RestApiException(e);
		}
	}	

	
	@ApiOperation("查询预售券下单人信息")
	@RequestMapping(method = RequestMethod.GET, value = "/order/person", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<StampOrderPerson> getOrderPerson(@RequestParam("stampId") final String stampId, @RequestParam("userId") final Long userId) {
		try {
			StampOrderPerson person = stampService.getStampOrderPerson(stampId, userId);
			return ok(person);
		} catch (Exception e) {
			throw new RestApiException(e);
		}
	}
	
	@ApiOperation("无线修改订单支付方式 定金支付 OR全额支付 payType=PART or FULL")
    @RequestMapping(method = RequestMethod.POST,
            value = "/update/orderPayType",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Boolean> updateOrder(@RequestParam("orderId") Long orderId, @RequestParam("payType") String payType) {
        try {
            boolean result = stampOrderService.updateOrderStampPayType(orderId, payType);
            return ok(true);
        } catch (Exception e) {
            throw new RestApiException(e);
        }
    }
	
//	@ApiOperation("订单预售券信息")
//    @RequestMapping(method = RequestMethod.GET,
//            value = "/order/{orderId}/stampCodes",
//            produces = MediaType.APPLICATION_JSON_VALUE)
//    public ResponseEntity<OrderStampCodesResponse> getOrderStampCodesOnly(@PathVariable("orderId") String orderId) {
//        try {
//            OrderStampCodesResponse result = stampOrderDts.getOrderStampCodesOnly(orderId);
//            return ok(result);
//        } catch (Exception e) {
//            throw new RestApiException(e);
//        }
//    }
	
    @ApiOperation("使用预售券扣款记录")
    @RequestMapping(
            method = RequestMethod.GET,
            value = "/useStamp/{useOrderId}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UseStampCodeResponse> getUseStampCodes(@PathVariable("useOrderId") final String useOrderId) {
        try {
            UseStampCodeResponse resp = stampOrderDts.getUseStampCodes(useOrderId);
            return ok(resp);
        } catch (Exception e) {
            throw new RestApiException(e);
        }
    }

}
