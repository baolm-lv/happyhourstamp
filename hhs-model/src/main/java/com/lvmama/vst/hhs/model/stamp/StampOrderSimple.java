package com.lvmama.vst.hhs.model.stamp;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 
 * 券订单，可用券信息
 * @author baolm
 *
 */
public class StampOrderSimple implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 订单ID
	 */
	private String orderId;
	/**
	 * 单价
	 */
	private Long price;
	/**
	 * 券ID
	 */
	private String stampId;
	/**
	 * 券名称
	 */
	private String stampName;
	/**
	 * 券有效期（兑换时间）
	 */
	private Date stampExpireDate;
	/**
	 * 未使用（可退款）的券
	 */
	private List<StampCode> unuseStampCodes;

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public Long getPrice() {
		return price;
	}

	public void setPrice(Long price) {
		this.price = price;
	}

	public String getStampId() {
		return stampId;
	}

	public void setStampId(String stampId) {
		this.stampId = stampId;
	}

	public String getStampName() {
		return stampName;
	}

	public void setStampName(String stampName) {
		this.stampName = stampName;
	}

	public Date getStampExpireDate() {
		return stampExpireDate;
	}

	public void setStampExpireDate(Date stampExpireDate) {
		this.stampExpireDate = stampExpireDate;
	}

	public List<StampCode> getUnuseStampCodes() {
		return unuseStampCodes;
	}

	public void setUnuseStampCodes(List<StampCode> unuseStampCodes) {
		this.unuseStampCodes = unuseStampCodes;
	}

}
