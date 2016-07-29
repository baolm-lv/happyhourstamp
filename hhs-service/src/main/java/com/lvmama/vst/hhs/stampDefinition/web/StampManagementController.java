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
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.lvmama.vst.back.client.pub.service.ComLogClientService;
import com.lvmama.vst.back.pub.po.ComLog;
import com.lvmama.vst.comm.vo.ResultHandleT;
import com.lvmama.vst.hhs.common.utils.HhsUtils;
import com.lvmama.vst.hhs.common.web.BaseResponse;
import com.lvmama.vst.hhs.common.web.BaseRestApi;
import com.lvmama.vst.hhs.common.web.HhsApiUriTemplates;
import com.lvmama.vst.hhs.common.web.HhsWebConstants;
import com.lvmama.vst.hhs.common.web.RestApiException;
import com.lvmama.vst.hhs.model.admin.AddStampGoodsRelationRequest;
import com.lvmama.vst.hhs.model.admin.ComLogs;
import com.lvmama.vst.hhs.model.admin.StampActiveRequest;
import com.lvmama.vst.hhs.model.admin.StampCreationRequest;
import com.lvmama.vst.hhs.model.admin.StampDetailsVo;
import com.lvmama.vst.hhs.model.admin.StampGoodsVo;
import com.lvmama.vst.hhs.model.admin.StampRequest;
import com.lvmama.vst.hhs.product.service.ProductService;
import com.lvmama.vst.hhs.stampDefinition.dts.IStampManagementDts;
import com.lvmama.vst.hhs.stampDefinition.service.StampGoodsService;
import com.lvmama.vst.hhs.stampDefinition.service.StampManagementService;

/**
 *
 * @author Luocheng Tang
 */

@RestController
@Api(value = HhsWebConstants.APP_ROOT_PATH)
@RequestMapping(value = {HhsApiUriTemplates.V1 + "/admin/stamp/definition/"})
public class StampManagementController extends BaseRestApi {
	
	private static final Logger logger = LoggerFactory.getLogger(StampManagementController.class);
	
    @Autowired 
    private StampManagementService service; 
    @Autowired
    private StampGoodsService stampGoodsService;    
    @Autowired
    private ComLogClientService  comLogClientService;    
    @Autowired
    private IStampManagementDts stampManagementDts;
    @Autowired
    private ProductService productService;

    @ApiOperation(
            value = "Creates/update happy hour stamp Definition for the given product on specified date ranges. "
                    + "Existing stamp Definitions will be updated with the provided values.")
    @ApiResponses(value = { @ApiResponse(code = 201, message = "Happy Hour Stamp Definitions are created successfully."), })
    @RequestMapping(
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> createStamp(@RequestBody final StampCreationRequest createRequest) {
        try {           
           
           validStampCreationRequestOrFail(createRequest);
           String uuid = this.service.createStampDefinition(createRequest);
           //创建模拟产品 规格 商品
            return ok(uuid);
        } catch (Exception e) {
            throw new RestApiException(HttpStatus.UNPROCESSABLE_ENTITY, e);
        }
    }

    @ApiOperation(value = "Retrieves one happy hour stamp detail information based on stampId.")
    @RequestMapping(
            method = RequestMethod.GET,
            value = { "{id}" },
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<StampDetailsVo> getStampById(@PathVariable("id") final String id) {
        try {
        	StampDetailsVo stamp = this.service.getStampById(id);
            if (stamp == null) {
                return notFound();
            }
            return ok(stamp);
        } catch (Exception e) {
            throw new RestApiException(e);
        }
    }

    @ApiOperation(value = "Retrieves list of happy hour stamp stampId.")
    @RequestMapping(
            method = RequestMethod.GET,
            value = {"name/{name}" },
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<StampDetailsVo>> getStampByName(@PathVariable("name") final String name) {

        try {
            List<StampDetailsVo> stamps = this.service.getStampDefinitionByName(name);
            if (stamps == null) {
                return notFound();
            }
            return ok(stamps);
        } catch (Exception e) {
            throw new RestApiException(e);
        }

    }
    
    private void validStampCreationRequestOrFail(final StampCreationRequest createRequest) {
        /*
         * Will add detail validation later once the business logical become clear.
         */
        if (null == createRequest || HhsUtils.isNullOrEmpty(createRequest.getStampName())) {
            throw new RestApiException(HttpStatus.UNPROCESSABLE_ENTITY, "Bad request parameter on the request");
        }
//        if (null == createRequest.getBoundMerchant()) {
//            createRequest.setActivityStatus(ActivityStatus.PREMATURED);
//        }
    }
    
    private void validStampUpdate(final StampCreationRequest createRequest) {
        /*
         * Will add detail validation later once the business logical become clear.
         */
        if (null == createRequest || HhsUtils.isNullOrEmpty(createRequest.getStampName())) {
            throw new RestApiException(HttpStatus.UNPROCESSABLE_ENTITY, "Bad request parameter on the request");
        }
    }
    
    @ApiOperation(value = " query StampDefinition")
    @ApiResponses(value = { @ApiResponse(code = 201, message = "quryStamp are created successfully."), })
    @RequestMapping(
            method = RequestMethod.GET,
            value = "/queryStamp", 
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<StampDetailsVo>> querytamp(
                @RequestParam(required = false,value= "productId") String productId ,@RequestParam(required = false,value= "productName") String productName,
                @RequestParam(required = false,value= "goodsId") String goodsId ,@RequestParam(required = false,value= "goodsName") String goodsName,
                @RequestParam(required = false,value= "stampNo") String stampNo ,@RequestParam(required = false,value= "stampName") String stampName,
                @RequestParam(required = false,value= "categroyId") String  categroyId,@RequestParam(required = false,value= "managerName") String managerName,
                @RequestParam(required = false,value= "managerId") String  managerId,
                @RequestParam(required = false,value= "startRow")final Integer startRow,
                @RequestParam(required = false,value= "rowNum")final Integer rowNum) {
        try {           
            StampRequest query = new StampRequest();
            query.setCategroyId(categroyId);
            query.setGoodsId(goodsId);
            query.setGoodsName(goodsName);
            query.setManagerName(managerName);
            query.setProductId(productId);
            query.setProductName(productName);
            query.setStampName(stampName);
            query.setStampNo(stampNo);

           List<StampDetailsVo>  details = this.service.queryStampList(query, startRow, rowNum); 
           //创建模拟产品 规格 商品
            return ok(details);
        } catch (Exception e) {
            throw new RestApiException(HttpStatus.UNPROCESSABLE_ENTITY, e);
        }
    }
    

    @ApiOperation(value = " query StampDefinition")
    @ApiResponses(value = { @ApiResponse(code = 201, message = "countQueryStamp are query successfully."), })
    @RequestMapping(
            method = RequestMethod.GET,
            value = "/countQueryStamp", 
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Long> countQuerytamp(
                @RequestParam(required = false,value= "productId") String productId ,@RequestParam(required = false,value= "productName") String productName,
                @RequestParam(required = false,value= "goodsId") String goodsId ,@RequestParam(required = false,value= "goodsName") String goodsName,
                @RequestParam(required = false,value= "stampNo") String stampNo ,@RequestParam(required = false,value= "stampName") String stampName,
                @RequestParam(required = false,value= "categroyId") String  categroyId,@RequestParam(required = false,value= "managerName") String managerName,
                @RequestParam(required = false,value= "managerId") String  managerId) {
        try {           
            StampRequest query = new StampRequest();
            query.setCategroyId(categroyId);
            query.setGoodsId(goodsId);
            query.setGoodsName(goodsName);
            query.setManagerName(managerName);
            query.setProductId(productId);
            query.setProductName(productName);
            query.setStampName(stampName);
            query.setStampNo(stampNo);
            long  count = this.service.countQueryStampList(query); 
           System.out.println("----------countQueryStamp-------detail="+count);
           //创建模拟产品 规格 商品
            return ok(count);
        } catch (Exception e) {
            throw new RestApiException(HttpStatus.UNPROCESSABLE_ENTITY, e);
        }
    }

    @ApiOperation(value = "保存预约券关联商品")
    @RequestMapping(
            method = RequestMethod.POST,
            value = "/goods", 
            produces = MediaType.APPLICATION_JSON_VALUE, 
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<StampDetailsVo> addStampGoods(@RequestBody AddStampGoodsRelationRequest request) {
    	try {
    		StampDetailsVo details = stampGoodsService.addStampGoodsRelation(request);
    		return ok(details);
    	} catch (Exception e) {
            throw new RestApiException(e);
        }
    }
    
    @ApiOperation(value = "查询预约券已关联商品")
    @RequestMapping(
            method = RequestMethod.GET,
            value = "/goods", 
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<StampGoodsVo>> addStampGoods(@RequestParam("stampId") String stampId) {
    	try {
    		List<StampGoodsVo> goodsList = stampGoodsService.findStampGoods(stampId);
    		return ok(goodsList);
    	} catch (Exception e) {
            throw new RestApiException(e);
    	}
    }
    
    @ApiOperation(value = "update stamp Definition")
    @RequestMapping(
            method = RequestMethod.POST,
            value = "updateStampInfo",
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> updateStampInfo(@RequestBody final StampCreationRequest createRequest) {
    	
    	try {           
    		validStampUpdate(createRequest);
    		StampDetailsVo vo = service.getStampById(createRequest.getId());
    		
    		this.service.updateStampDefinition(createRequest);
    		if(!vo.getName().equals(createRequest.getStampName())){
                productService.updateProductAboutName(vo.getStampGoodsRelation(), createRequest.getStampName());
            }
            return ok();
         } catch (Exception e) {
             throw new RestApiException(HttpStatus.UNPROCESSABLE_ENTITY, e);
         }
    }
    
    @ApiOperation(value = "valid stamp Definition")
    @RequestMapping(
            method = RequestMethod.POST,
            value = "updateActivityStatus",
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<BaseResponse> updateStampActivityStatus(@RequestBody final StampActiveRequest request) {
    	
        try {
        	BaseResponse resp = stampManagementDts.updateStampActivityStatus(request);
        	return ok(resp);
        } catch (Exception e) {
            throw new RestApiException(e);
        }
    }
    
    @ApiOperation(value = "Deletes the happy hour stamp based on specified stamp id.")
    @RequestMapping(
    		method = RequestMethod.DELETE, 
    		value = "{id}")
	public ResponseEntity<Void> deleteStampById(@PathVariable("id") final String id) {
		try {
			this.service.deleteStampDefinition(id);
		} catch (Exception e) {
			logger.error("delete error: " + id, e);
			throw new RestApiException(e);
		}
		return ok();
	}
    
    @ApiOperation(value = "查询预售券操作日志")
    @RequestMapping(
            method = RequestMethod.GET,
            value = "logs", 
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<ComLogs>> queryStampLog(@RequestParam("stampId") String stampId,
            @RequestParam("objectType") String objectType,
            @RequestParam("startRow") int page, @RequestParam("end") int end ) {
        try {        
            
            Map<String, Object> paramMap = new HashMap<String, Object>();
            paramMap.put("objectId",new Long(Math.abs(stampId.hashCode())));
            paramMap.put("objectType", objectType);
              
            paramMap.put("_start", page);
            paramMap.put("_end", end);
            paramMap.put("_orderby", "CREATE_TIME"); 
            paramMap.put("_order", "DESC");      
            ResultHandleT<List<ComLog>> resultHandleTList = comLogClientService.queryComLogListByCondition(paramMap);
            List<ComLogs> logs = new ArrayList<ComLogs>();
            if(resultHandleTList.isSuccess()){
                List<ComLog> comlog = resultHandleTList.getReturnContent();
                if(CollectionUtils.isNotEmpty(comlog)){
                    for(ComLog log:comlog){
                        ComLogs newLog = new ComLogs();
                        BeanUtils.copyProperties(newLog, log);
                        logs.add(newLog);
                    }
                }
            }
          
            
           return ok(logs);
        } catch (Exception e) {
            throw new RestApiException(e);
        }
    }
    
    @ApiOperation(value = "查询预售券操作日志数量")
    @RequestMapping(
            method = RequestMethod.GET,
            value = "countLogs", 
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Long> countStampLog(@RequestParam("stampId") String stampId) {
        try {                 
           
            String objectType = "PRE_SALE";     
            Map<String, Object> paramMap = new HashMap<String, Object>();
            paramMap.put("objectId",new Long(Math.abs(stampId.hashCode())));
            paramMap.put("objectType", objectType);
            ResultHandleT<Long> resultHandleTList = comLogClientService.getTotalCount(paramMap);
            return ok(resultHandleTList.getReturnContent());
        } catch (Exception e) {
            throw new RestApiException(e);
        }
    }
}
