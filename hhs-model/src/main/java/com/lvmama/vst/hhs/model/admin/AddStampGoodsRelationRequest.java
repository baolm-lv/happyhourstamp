package com.lvmama.vst.hhs.model.admin;

import java.io.Serializable;
import java.util.List;

public class AddStampGoodsRelationRequest implements Serializable {

	private static final long serialVersionUID = 1L;

	private String stampId;
	
	private List<StampGoodsVo> goodsList;
	private String operationName;

	public String getStampId() {
		return stampId;
	}

	public void setStampId(String stampId) {
		this.stampId = stampId;
	}

	public List<StampGoodsVo> getGoodsList() {
		return goodsList;
	}

	public void setGoodsList(List<StampGoodsVo> goodsList) {
		this.goodsList = goodsList;
	}

    public String getOperationName() {
        return operationName;
    }

    public void setOperationName(String operationName) {
        this.operationName = operationName;
    }
	
	
}
