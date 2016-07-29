package com.lvmama.vst.hhs.product.web;

import static com.lvmama.vst.hhs.common.web.ResponseEntityBuilder.ok;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.lvmama.vst.hhs.common.web.HhsApiUriTemplates;
import com.lvmama.vst.hhs.common.web.HhsWebConstants;
import com.lvmama.vst.hhs.common.web.RestApiException;
import com.lvmama.vst.hhs.model.product.HotelTimePriceDate;
import com.lvmama.vst.hhs.model.product.HotelValidationRequest;
import com.lvmama.vst.hhs.model.product.HotelValidationResponse;
import com.lvmama.vst.hhs.model.product.ProdGroupDateVO;
import com.lvmama.vst.hhs.model.product.ProductAddition;
import com.lvmama.vst.hhs.model.product.ProductAdditionRequest;
import com.lvmama.vst.hhs.model.product.ProductDetails;
import com.lvmama.vst.hhs.model.product.StampBuyInfo;
import com.lvmama.vst.hhs.model.product.VisitCalendarRequest;
import com.lvmama.vst.hhs.model.stamp.ProductDetailsStamps;
import com.lvmama.vst.hhs.model.stamp.SaveOrderResponse;
import com.lvmama.vst.hhs.model.stamp.StampRedeem;
import com.lvmama.vst.hhs.product.service.ProductDetailService;
import com.lvmama.vst.hhs.product.service.ProductOrderService;
import com.lvmama.vst.hhs.product.service.ProductService;
import com.lvmama.vst.hhs.stamp.service.StampProductService;
import com.lvmama.vst.hhs.stamp.service.StampService;

@RestController
@Api(value = HhsWebConstants.APP_ROOT_PATH)
@RequestMapping(HhsApiUriTemplates.V1 + "/customer/product/")
public class ProductController {

	@Autowired
	private ProductService productService;
	@Autowired
	private StampProductService  stampProdcutService;
	@Autowired
	private StampService stampService;
	@Autowired
	private ProductOrderService productOrderService;
	@Autowired
	@Qualifier("productDetailServiceImpl")
	private ProductDetailService productDetailService;

	@ApiOperation("兑换预约券下单")
	@RequestMapping(method = RequestMethod.POST, value = "/order",
	produces = MediaType.APPLICATION_JSON_VALUE,
	consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<SaveOrderResponse> saveOrder(
			@RequestBody final StampBuyInfo request) {

		try {
			SaveOrderResponse response = productOrderService.saveProductOrder(request);
			return ok(response);
		} catch (Exception e) {
			throw new RestApiException(e);
		}
	}

	@ApiOperation("支持商品预售  返回支持预售券的产品ID集合")
	@RequestMapping(method = RequestMethod.GET, value = { "/stamptag" }, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Long>> getPreTag(
			@RequestParam("productIds") final String ids) {

		try {		    
		    List<Long> lists = stampProdcutService.getStampProductByProductIds(ids);			
			return ok(lists);
		} catch (Exception e) {
			throw new RestApiException(e);
		}
	}
	
	
    @ApiOperation("产品详细页面  产品是否在兑换时间段内")
    @RequestMapping(method = RequestMethod.GET, value = { "/isExchange" }, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Boolean> getIsExchange(
            @RequestParam("productId") final Long productId,
            @RequestParam(required = false, name = "startDistrictId") Long startDistrictId) {

        try {           
            Boolean result = stampProdcutService.productIsExchange(productId,startDistrictId);            
            return ok(result);
        } catch (Exception e) {
            throw new RestApiException(e);
        }
    }

	@ApiOperation("查询产品下的用户预约券列表.(券预约页面)")
	@RequestMapping(method = RequestMethod.GET, value = "/{productId}/stamps", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<StampRedeem>> getNotUseStampByUser(
			@PathVariable("productId") Long productId,
			@RequestParam("userId") Long userId,
			@RequestParam(required = false, name = "startDistrictId") Long startDistrictId,
			@RequestParam(required = false, name = "withCode") boolean withCode) {
		try {
			List<StampRedeem> redeems = stampService.getRedeemStamps(productId, userId, startDistrictId, withCode);
			return ok(redeems);
		} catch (Exception e) {
			throw new RestApiException(e);
		}
	}
	
	@ApiOperation("验证用户选择的预约券并查询可选产品.(券预约页面)")
	@RequestMapping(method = RequestMethod.POST, value = "/{productId}/addition", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ProductAddition> getStampAddition(
			@PathVariable("productId") Long productId,
			@RequestBody ProductAdditionRequest request) {
		try {
			ProductAddition addition = stampProdcutService.getStampAddition(productId, request);
			return ok(addition);
		} catch (Exception e) {
			throw new RestApiException(e);
		}
	}
	
	@ApiOperation("游玩日期日历.(券预约页面)")
	@RequestMapping(method = RequestMethod.POST, value = "/{productId}/calendar", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<ProdGroupDateVO>> getVisitCalendar(
			@PathVariable("productId") Long productId,
			@RequestBody VisitCalendarRequest request) {
		try {
			List<ProdGroupDateVO> dateList = productService.getVisitCalendar(productId, request);
			return ok(dateList);
		} catch (Exception e) {
			throw new RestApiException(e);
		}
	}
	
	@ApiOperation("券预约商品明细.(填写订单信息页面)")
	@RequestMapping(method = RequestMethod.POST, value = "/{productId}/confirm", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ProductDetails> stampGoodsConfirm(
			@PathVariable("productId") Long productId,
			@RequestBody ProductAdditionRequest request) {
		try {
			ProductDetails product = productDetailService.getProductDetails(productId, request);
			return ok(product);
		} catch (Exception e) {
			throw new RestApiException(e);
		}
	}

	@ApiOperation("产品详情页面查询预约券信息, 多出发地时需要startDistrictId ")
	@RequestMapping(method = RequestMethod.GET, value = { "/details/{productId}/stamps" }, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ProductDetailsStamps> getProductStamps(
			@PathVariable("productId") final Long productId,
			@RequestParam(required = false, name = "startDistrictId") Long startDistrictId) {
		try {
			ProductDetailsStamps stamp = productService.getProductStampsDetails(productId, startDistrictId);
			return ok(stamp);
		} catch (Exception e) {
			throw new RestApiException(e);
		}
	}

	@ApiOperation("时间价格表(团期表)")
    @RequestMapping(
            method = RequestMethod.GET,  
            value = {"/details/{productId}/timeprice" },  
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<ProdGroupDateVO>> getPriceCalendar(@PathVariable("productId") final Long productId, 
    				@RequestParam(required = false, name = "startDistrictId") Long startDistrictId,
    				@RequestParam(required = true, name = "startDate") String startDate,
    				@RequestParam(required = true, name = "endDate") String endDate) {
        try {
        	List<ProdGroupDateVO> dateList = productService.checkAndGetProdGroupDates(productId, startDistrictId, startDate, endDate);
            return ok(dateList);
        } catch (Exception e) {
            throw new RestApiException(e);
        }
    }
	
	@ApiOperation("酒店兑换日历")
    @RequestMapping(
            method = RequestMethod.GET,  
            value = {"/{productId}/hotel/calendar" },  
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<HotelTimePriceDate>> hotelCalendar(@PathVariable("productId") final Long productId, 
    				@RequestParam String stampId) {
        try {
        	List<HotelTimePriceDate> response = productService.getHotelCalendar(productId, stampId);
            return ok(response);
        } catch (Exception e) {
            throw new RestApiException(e);
        }
    }
	
	@ApiOperation("验证酒店是否可用")
    @RequestMapping(
            method = RequestMethod.POST,  
            value = {"/{productId}/hotel/validation" },  
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<HotelValidationResponse> validHotel(@PathVariable("productId") final Long productId, 
    				@RequestBody HotelValidationRequest request) {
        try {
        	HotelValidationResponse response = productService.validHotel(productId, request);
            return ok(response);
        } catch (Exception e) {
            throw new RestApiException(e);
        }
    }
	
	@ApiOperation("查询所有绑定券的产品ID startRow开始行  rowNum行数")
    @RequestMapping(
            method = RequestMethod.GET,  
            value = {"/search/product" },  
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Long>> searchProduct(
                @RequestParam(required = false, name = "categoryIds")String categoryIds,
                @RequestParam(required = true, name = "startRow")int startRow,
                @RequestParam(required = true, name = "rowNum")int rowNum) {
        try {
            List<Long>  resutl = stampProdcutService.getStampProduct(categoryIds,startRow,rowNum);
            return ok(resutl);
        } catch (Exception e) {
            throw new RestApiException(e);
        }
    }
	
	@ApiOperation("查询所有绑定券的产品ID")
    @RequestMapping(
            method = RequestMethod.GET,  
            value = {"/search/countProduct" },  
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Long> countSearchProduct(
            @RequestParam(required = false, name = "categoryIds")String categoryIds) {
        try {
            long  resutl = stampProdcutService.countStampProduct(categoryIds);
            return ok(resutl);
        } catch (Exception e) {
            throw new RestApiException(e);
        }
    }
	
	@ApiOperation("vstBack产品是否能修改")
    @RequestMapping(
            method = RequestMethod.GET,  
            value = {"/canModify" },  
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Boolean> canModify(
            @RequestParam(required = false, name = "productId")String productId) {
        try {
            boolean  result = stampProdcutService.canProductModify(productId);
            return ok(result);
        } catch (Exception e) {
            throw new RestApiException(e);
        }
    }
}
