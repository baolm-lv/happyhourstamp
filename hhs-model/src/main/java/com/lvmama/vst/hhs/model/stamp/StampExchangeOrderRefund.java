/**
 * 
 */
package com.lvmama.vst.hhs.model.stamp;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @author baolm
 *
 */
public class StampExchangeOrderRefund implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3986354088386410288L;

	private String id;
	/**
	 * 券号
	 */
	private String stampNo;
	/**
	 * 券名称
	 */
	private String name;
	/**
	 * 产品
	 */
	private String productId;
	private String productName;
	/**
	 * 券单价
	 */
	private Long stampAmount;
	/**
	 * 券（可兑换）有效期
	 */
	private Date expiredTime;
	/**
	 * 可退券券码集合
	 */
	private List<StampCode> canRefundStampCodes;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getStampNo() {
		return stampNo;
	}

	public void setStampNo(String stampNo) {
		this.stampNo = stampNo;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
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

	public List<StampCode> getCanRefundStampCodes() {
		return canRefundStampCodes;
	}

	public void setCanRefundStampCodes(List<StampCode> canRefundStampCodes) {
		this.canRefundStampCodes = canRefundStampCodes;
	}
}
