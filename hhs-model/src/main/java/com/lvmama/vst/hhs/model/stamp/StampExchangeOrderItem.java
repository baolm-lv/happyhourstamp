/**
 * 
 */
package com.lvmama.vst.hhs.model.stamp;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 
 * 券兑换子订单-券信息
 * 
 * @author baolm
 *
 */
public class StampExchangeOrderItem implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2763960891346183605L;
	/**
	 * 券兑换子订单ID
	 */
	private String useOrderItemId;
	/**
	 * 券单价
	 */
	private Long stampAmount;
	/**
	 * 券（可兑换）有效期
	 */
	private Date expiredTime;
	/**
	 * 已使用的券信息
	 */
	private List<StampCode> usedStampCodes;

	public String getUseOrderItemId() {
		return useOrderItemId;
	}

	public void setUseOrderItemId(String useOrderItemId) {
		this.useOrderItemId = useOrderItemId;
	}

	public Long getStampAmount() {
		return stampAmount;
	}

	public void setStampAmount(Long stampAmount) {
		this.stampAmount = stampAmount;
	}

	public Date getExpiredTime() {
		return expiredTime;
	}

	public void setExpiredTime(Date expiredTime) {
		this.expiredTime = expiredTime;
	}

	public List<StampCode> getUsedStampCodes() {
		return usedStampCodes;
	}

	public void setUsedStampCodes(List<StampCode> usedStampCodes) {
		this.usedStampCodes = usedStampCodes;
	}

}
