package com.lvmama.vst.hhs.product.web;

import static com.lvmama.vst.hhs.common.web.ResponseEntityBuilder.ok;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import java.util.List;

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
import com.lvmama.vst.hhs.model.product.ProdProductModel;
import com.lvmama.vst.hhs.product.service.ProductService;


@RestController
@Api(value = HhsWebConstants.APP_ROOT_PATH)
@RequestMapping(HhsApiUriTemplates.V1 + "/admin/product/")
public class BackProductController {
	
	@Autowired
	private ProductService productService;
	
	@ApiOperation("后台查询产品")
    @RequestMapping(
            method = RequestMethod.GET,
            value = {"/query" },
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<ProdProductModel>> getPreTag(
            @RequestParam(required = false, value="productId") final Integer productId,
            @RequestParam(required = false, value = "productName")final String productName) {

        try {
            
            List<ProdProductModel> models = this.productService.queryProdProductList(productId, productName);
            return ok(models);
        } catch (Exception e) {
            throw new RestApiException(e);
        }
    }
}
