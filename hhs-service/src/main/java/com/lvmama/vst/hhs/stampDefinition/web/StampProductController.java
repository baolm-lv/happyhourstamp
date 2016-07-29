/*
 *
 * The copyright to the computer software herein is the property of
 * Lvmama Company. The software may be used and/or copied only
 * with the written permission of Lvmama or in accordance
 * with the terms and conditions stipulated in the agreement/contract
 * under which the software has been supplied.
 */

package com.lvmama.vst.hhs.stampDefinition.web;

import io.swagger.annotations.Api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lvmama.vst.hhs.common.web.BaseRestApi;
import com.lvmama.vst.hhs.common.web.HhsApiUriTemplates;
import com.lvmama.vst.hhs.common.web.HhsWebConstants;
import com.lvmama.vst.hhs.stampDefinition.service.StampManagementService;

/**
 *
 * @author Luocheng Tang
 */

@RestController
@Api(value = HhsWebConstants.APP_ROOT_PATH)
@RequestMapping(value = {HhsApiUriTemplates.V1 + "/admin/stamp/product/"})
public class StampProductController extends BaseRestApi {
    @Autowired 
    private StampManagementService service; 
       
  /*  @ApiOperation(value = "查看此产品和商品此时间是是否有预售")
    @RequestMapping(
            method = RequestMethod.GET,
            value = "isPreSale", 
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Boolean> countStampLog(@RequestParam("productId") String productId,
                @RequestParam("goodsId") String goodsId, @RequestParam("date") String date) {
        try {      
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            Boolean  result = service.goodsIsPreSaleByTime(productId, goodsId,new Timestamp(df.parse(date).getTime()));
            return ok(result);
        } catch (Exception e) {
            throw new RestApiException(e);
        }
    }*/
    
  
}
