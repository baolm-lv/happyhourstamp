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
public class StampRemindCustomerTimeSlot implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8375222614136585369L;

	private String startDate;
	private String endDate;
	private List<String> weekDays;

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

	public List<String> getWeekDays() {
		return weekDays;
	}

	public void setWeekDays(List<String> weekDays) {
		this.weekDays = weekDays;
	}

}
