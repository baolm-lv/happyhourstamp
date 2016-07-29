package com.lvmama.vst.hhs.model.stamp;

import java.io.Serializable;

public class NotifyRefundIdRequest implements Serializable {

	private static final long serialVersionUID = 1L;

	/** 退款申请ID */
	private String refundApplyId;
	/** 退款ID */
	private String refundId;

	public String getRefundApplyId() {
		return refundApplyId;
	}

	public void setRefundApplyId(String refundApplyId) {
		this.refundApplyId = refundApplyId;
	}

	public String getRefundId() {
		return refundId;
	}

	public void setRefundId(String refundId) {
		this.refundId = refundId;
	}

}
