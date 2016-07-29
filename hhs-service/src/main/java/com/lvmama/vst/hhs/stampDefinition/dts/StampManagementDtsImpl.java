package com.lvmama.vst.hhs.stampDefinition.dts;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lvmama.vst.hhs.common.exception.StampBizException;
import com.lvmama.vst.hhs.common.exception.StampErrorCodes;
import com.lvmama.vst.hhs.common.web.BaseResponse;
import com.lvmama.vst.hhs.common.web.HhsRespHandler;
import com.lvmama.vst.hhs.model.admin.StampActiveRequest;
import com.lvmama.vst.hhs.model.common.Constant.ActivityStatus;
import com.lvmama.vst.hhs.stampDefinition.dao.PresaleStampDefinitionGoodsBindingEntity;
import com.lvmama.vst.hhs.stampDefinition.service.StampGoodsService;
import com.lvmama.vst.hhs.stampDefinition.service.StampManagementService;

@Service
public class StampManagementDtsImpl implements IStampManagementDts {

	@Autowired
	private StampManagementService stampManagementService;
	@Autowired
	private StampGoodsService stampGoodsService;

	@Override
	@HhsRespHandler
	public BaseResponse updateStampActivityStatus(StampActiveRequest request) {

		if (request == null)
			throw new StampBizException(StampErrorCodes.E_REQ_1001, "request is empty");

		// 未绑定商品，不能设置为有效
		if (ActivityStatus.Y.toString().equals(request.getActivityStatus())) {
			List<PresaleStampDefinitionGoodsBindingEntity> goodsList = this.stampGoodsService
					.getStampGoodsBindingByStampId(request.getId());
			if (CollectionUtils.isEmpty(goodsList)) {
				throw new StampBizException(StampErrorCodes.E_STAMP_3002, "未绑定商品，不能设置为有效");
			}
		}
		if(request.getActivityStatus().equals("Y")){
		    if(!stampGoodsService.isModifyActivityStatus(request.getId())){
	            throw new StampBizException(StampErrorCodes.E_STAMP_3002, "预售券绑定商品和产品上不符合，建议重新创建预售券");
	        }
		}
      
		this.stampManagementService.updateStampActivityStatus(request.getId(), request.getActivityStatus(),request.getOperationName());
		return new BaseResponse();
	}

}
