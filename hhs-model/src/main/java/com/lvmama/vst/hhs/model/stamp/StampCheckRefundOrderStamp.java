package com.lvmama.vst.hhs.model.stamp;

import java.io.Serializable;

public class StampCheckRefundOrderStamp implements Serializable {

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
	 * 已支付金额
	 */
	private Long paidPrice;
	// UNPAY("待支付"), TRANSFERRED("已转移"), PART_PAY("部分支付"), PAYED("已支付 ")
	// CANCEL("已取消") ;
	/**
	 * 订单状态 支付状态 和取消
	 * <p>
	 * UNPAY("待支付"), TRANSFERRED("已转移"), PART_PAY("部分支付"), PAYED("已支付 "), CANCEL("已取消")
	 */
	private String orderStatus;
	/**
	 * 券ID
	 */
	private String stampId;
	/**
	 * 券名称
	 */
	private String stampName;
	/**
	 * 产品名称
	 */
	private String productName;
	/**
	 * 是否有退款中的订单
	 */
	private Boolean hasRefundOrder;
	/**
	 * 未使用券数量
	 */
	private int unuseNum;

	public Boolean getHasRefundOrder() {
		return hasRefundOrder;
	}

	public void setHasRefundOrder(Boolean hasRefundOrder) {
		this.hasRefundOrder = hasRefundOrder;
	}

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

	public Long getPaidPrice() {
		return paidPrice;
	}

	public void setPaidPrice(Long paidPrice) {
		this.paidPrice = paidPrice;
	}

	public String getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
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

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public int getUnuseNum() {
		return unuseNum;
	}

	public void setUnuseNum(int unuseNum) {
		this.unuseNum = unuseNum;
	}

}
