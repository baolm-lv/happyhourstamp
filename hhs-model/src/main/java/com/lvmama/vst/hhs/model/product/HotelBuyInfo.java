/**
 * 
 */
package com.lvmama.vst.hhs.model.product;

/**
 * @author fengyonggang
 *
 */
public class HotelBuyInfo {

	private String stampId;
	private String startDate;
	private String endDate;
	private String arrivalTime;
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
	public String getArrivalTime() {
		return arrivalTime;
	}
	public void setArrivalTime(String arrivalTime) {
		this.arrivalTime = arrivalTime;
	}
	@Override
	public String toString() {
		return "HotelBuyInfo [stampId=" + stampId + ", startDate=" + startDate + ", endDate=" + endDate
				+ ", arrivalTime=" + arrivalTime + ", room=" + room + "]";
	}
}
