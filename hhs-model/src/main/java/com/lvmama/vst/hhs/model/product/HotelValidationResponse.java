/**
 * 
 */
package com.lvmama.vst.hhs.model.product;

import java.io.Serializable;

/**
 * @author fengyonggang
 *
 */
public class HotelValidationResponse implements Serializable {

	private static final long serialVersionUID = 1L;
	private String status;//valid, invalid
	private String message;
	private String earliestArriveTime;
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getEarliestArriveTime() {
		return earliestArriveTime;
	}
	public void setEarliestArriveTime(String earliestArriveTime) {
		this.earliestArriveTime = earliestArriveTime;
	}
}
