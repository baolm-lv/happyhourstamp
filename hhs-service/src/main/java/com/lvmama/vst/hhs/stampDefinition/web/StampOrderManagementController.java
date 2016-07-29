/*
 *
 * The copyright to the computer software herein is the property of
 * Lvmama Company. The software may be used and/or copied only
 * with the written permission of Lvmama or in accordance
 * with the terms and conditions stipulated in the agreement/contract
 * under which the software has been supplied.
 */

package com.lvmama.vst.hhs.stampDefinition.web;

import static com.lvmama.vst.hhs.common.web.ResponseEntityBuilder.notFound;
import static com.lvmama.vst.hhs.common.web.ResponseEntityBuilder.ok;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.lvmama.vst.comm.utils.DateUtil;
import com.lvmama.vst.hhs.common.web.BaseRestApi;
import com.lvmama.vst.hhs.common.web.HhsApiUriTemplates;
import com.lvmama.vst.hhs.common.web.HhsWebConstants;
import com.lvmama.vst.hhs.common.web.RestApiException;
import com.lvmama.vst.hhs.model.admin.StampOrderVo;
import com.lvmama.vst.hhs.product.service.ProductOrderService;
import com.lvmama.vst.hhs.stampDefinition.service.StampOrderManagementService;

/**
 * 
 * @author baolm
 */
@RestController
@Api(value = HhsWebConstants.APP_ROOT_PATH)
@RequestMapping(value = { HhsApiUriTemplates.V1 + "/admin/stamp" })
public class StampOrderManagementController extends BaseRestApi {

	@Autowired
	private StampOrderManagementService service;
	@Autowired
	private ProductOrderService productOrderService;

	@ApiOperation(value = "count for stamp order.")
	@RequestMapping(method = RequestMethod.GET, value = "/orders/{orderId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<StampOrderVo> get(@PathVariable String orderId) {

		if (null == orderId) {
			throw new RestApiException(HttpStatus.UNPROCESSABLE_ENTITY, "Bad request parameter on the request");
		}

		try {
			StampOrderVo order = service.getById(orderId);
			return ok(order);
		} catch (Exception e) {
			throw new RestApiException(e);
		}
	}

	@ApiOperation(value = "count for stamp order.")
	@RequestMapping(method = RequestMethod.GET, value = "/orders/count", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Long> countStampOrders(@RequestParam(value = "stampId", required = true) String stampId,
			@RequestParam(value = "orderId", required = false) String orderId,
			@RequestParam(value = "contactName", required = false) String contactName,
			@RequestParam(value = "contactMobile", required = false) String contactMobile) {

		try {
			long count = productOrderService.countStampOrder(stampId, orderId, contactName, contactMobile);
			return ok(count);
		} catch (Exception e) {
			throw new RestApiException(e);
		}
	}

	@ApiOperation(value = "findPage<StampOrderVo>")
	@RequestMapping(method = RequestMethod.GET, value = "/orders/page", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<StampOrderVo>> searchStampOrders(
			@RequestParam(value = "stampId", required = true) String stampId,
			@RequestParam(value = "orderId", required = false) String orderId,
			@RequestParam(value = "contactName", required = false) String contactName,
			@RequestParam(value = "contactMobile", required = false) String contactMobile,
			@RequestParam(value = "startRow", required = true) Integer startRow,
			@RequestParam(value = "pageSize", required = true) Integer pageSize) {

		try {
			List<StampOrderVo> order = productOrderService.queryStampOrder(stampId, orderId, contactName, contactMobile,
					startRow, pageSize);
			if (order == null) {
				return notFound();
			}
			return ok(order);
		} catch (Exception e) {
			throw new RestApiException(e);
		}
	}

	@ApiOperation(value = "查询stampDefinitionId是否有订单")
	@RequestMapping(method = RequestMethod.GET, value = "/{stampId}/orders/exists", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Boolean> countStampOrderByStampDefinitionId(@PathVariable String stampId) {

		try {
			long count = productOrderService.countOrderStampOrderItemByStampDefinitionId(stampId);
			if (count > 0)
				return ok(true);
			return ok(false);
		} catch (Exception e) {
			throw new RestApiException(e);
		}
	}
	    
	@ApiOperation(value = "后台更新预售券订单最后支付时间")
	@RequestMapping(method = RequestMethod.POST, value = "updateOrdOrderLastPayTime", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Boolean> updateOrdOrderLastPayTime(
			@RequestParam(value = "orderId", required = true) Long orderId,
			@RequestParam(value = "lastPayTime", required = true) String lastPayTime) {

		try {
			service.updateStampOrderLastPayTime(orderId, DateUtil.toDate(lastPayTime, DateUtil.HHMMSS_DATE_FORMAT));
			return ok(true);
		} catch (Exception e) {
			throw new RestApiException(e);
		}
	}
	 
}
