package com.lvmama.vst.hhs.model.stamp;

import java.io.Serializable;

import com.lvmama.vst.hhs.model.product.StampBuyInfo;


public class BuyInfoRequest implements Serializable  {
	
	private static final long serialVersionUID = 7023738168976232403L;

	private StampBuyInfo buyInfo;
	private  Long StampId;
    private Integer nums;//使用的券的数量
	

	public Long getStampId() {
		return StampId;
	}
	public void setStampId(Long stampId) {
		StampId = stampId;
	}
	public Integer getNums() {
		return nums;
	}
	public void setNums(Integer nums) {
		this.nums = nums;
	}
	public StampBuyInfo getBuyInfo() {
		return buyInfo;
	}
	public void setBuyInfo(StampBuyInfo buyInfo) {
		this.buyInfo = buyInfo;
	}
	

}
