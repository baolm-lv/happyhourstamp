package com.lvmama.vst.hhs.product.web;

import static com.lvmama.vst.hhs.common.web.ResponseEntityBuilder.notFound;
import static com.lvmama.vst.hhs.common.web.ResponseEntityBuilder.ok;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.lvmama.vst.hhs.common.web.HhsApiUriTemplates;
import com.lvmama.vst.hhs.common.web.HhsWebConstants;
import com.lvmama.vst.hhs.common.web.RestApiException;
import com.lvmama.vst.hhs.model.admin.ProductGoodsVo;
import com.lvmama.vst.hhs.product.service.ProductDetailAdminService;

@RestController
@Api(value = HhsWebConstants.APP_ROOT_PATH)
@RequestMapping(HhsApiUriTemplates.V1 + "/admin/goods/")
public class GoodsController {

	@Autowired
	private ProductDetailAdminService productDetailAdminService;
	
	@ApiOperation("REtrieve goods list based on product id.")
	@RequestMapping(method = RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ProductGoodsVo> getGoodsByProductId(@RequestParam("productId") final long productId) {
		try {
			ProductGoodsVo goodsVo = productDetailAdminService.getProductGoods(productId);
			if(goodsVo == null) {
				return notFound();
			}
			return ok(goodsVo);
		} catch (Exception e) {
			throw new RestApiException(e);
		}
	}
	
}
