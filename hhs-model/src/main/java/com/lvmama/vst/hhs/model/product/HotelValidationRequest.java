/**
 * 
 */
package com.lvmama.vst.hhs.model.product;

import java.io.Serializable;

/**
 * @author fengyonggang
 *
 */
public class HotelValidationRequest implements Serializable {

	private static final long serialVersionUID = 1L;

	private String stampId;
	private String startDate;
	private String endDate;
	private int room;
	
	public String getStampId() {
		return stampId;
	}
	public void setStampId(String stampId) {
		this.stampId = stampId;
	}
	public String getStartDate() {
		return startDate;
	}
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	public int getRoom() {
		return room;
	}
	public void setRoom(int room) {
		this.room = room;
	}
}
