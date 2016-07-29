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
public class StampRefundResponse implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8411049489869353192L;
	/**
	 * 退券总金额
	 */
	private long refundAmount;
	/**
	 * 券码集合
	 */
	private List<String> stampCodes;

	public long getRefundAmount() {
		return refundAmount;
	}

	public void setRefundAmount(long refundAmount) {
		this.refundAmount = refundAmount;
	}

	public List<String> getStampCodes() {
		return stampCodes;
	}

	public void setStampCodes(List<String> stampCodes) {
		this.stampCodes = stampCodes;
	}

}
