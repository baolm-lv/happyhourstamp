/**
 * 
 */
package com.lvmama.vst.hhs.model.stamp;

import java.util.List;

import com.lvmama.vst.hhs.common.web.BaseResponse;

/**
 * @author baolm
 *
 */
public class StampExchangeOrderRefundResponse extends BaseResponse {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3726173398180981318L;

	private List<StampExchangeOrderRefund> stamps;

	public List<StampExchangeOrderRefund> getStamps() {
		return stamps;
	}

	public void setStamps(List<StampExchangeOrderRefund> stamps) {
		this.stamps = stamps;
	}

	@Override
	public String toString() {
		return new StringBuffer("StampExchangeOrderRefundResponse: ").append("[code=").append(getCode()).append(",msg=")
				.append(getMsg()).append(",stamp size=").append(stamps == null ? 0 : stamps.size()).append("]")
				.toString();
	}

}
