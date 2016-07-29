/**
 * 
 */
package com.lvmama.vst.hhs.model.stamp;

import java.io.Serializable;
import java.util.List;

/**
 * @author baolm
 *
 */
public class StampRefundOrder implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7131230823376384512L;
	/**
	 * 退券申请ID
	 */
	private String refundApplyId;
	/**
	 * 订单ID
	 */
	private String orderId;
	/**
	 * 退款券数
	 */
	private Integer refundNum;
	/**
	 * 券单价
	 */
	private Long stampAmount;
	/**
	 * 退款总额
	 */
	private Long refundAmount;
	/**
	 * 退券状态（可忽略）
	 */
	private String refundStatus;
	/**
	 * 券ID
	 */
	private String stampId;
	/**
	 * 券名称
	 */
	private String stampName;
	/**
	 * 产品ID
	 */
	private String productId;
	/**
	 * 产品名称
	 */
	private String productName;
	/**
	 * 退款券号
	 */
	private List<String> stampCodes;

	public String getRefundApplyId() {
		return refundApplyId;
	}

	public void setRefundApplyId(String refundApplyId) {
		this.refundApplyId = refundApplyId;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public Integer getRefundNum() {
		return refundNum;
	}

	public void setRefundNum(Integer refundNum) {
		this.refundNum = refundNum;
	}

	public Long getStampAmount() {
		return stampAmount;
	}

	public void setStampAmount(Long stampAmount) {
		this.stampAmount = stampAmount;
	}

	public Long getRefundAmount() {
		return refundAmount;
	}

	public void setRefundAmount(Long refundAmount) {
		this.refundAmount = refundAmount;
	}

	public String getRefundStatus() {
		return refundStatus;
	}

	public void setRefundStatus(String refundStatus) {
		this.refundStatus = refundStatus;
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

	public List<String> getStampCodes() {
		return stampCodes;
	}

	public void setStampCodes(List<String> stampCodes) {
		this.stampCodes = stampCodes;
	}
	
}
