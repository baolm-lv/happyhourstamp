package com.lvmama.vst.hhs.model.stamp;

import java.io.Serializable;

public class StampRefundApplyRequest implements Serializable {

	private static final long serialVersionUID = 1L;

	/** 退款申请ID */
	private String refundApplyId;
	/** 订单ID */
	private String orderId;
	/** 用户ID */
	private String userId;
	/** 退券数量 */
	private Integer refundNum;

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

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public Integer getRefundNum() {
		return refundNum;
	}

	public void setRefundNum(Integer refundNum) {
		this.refundNum = refundNum;
	}

}
