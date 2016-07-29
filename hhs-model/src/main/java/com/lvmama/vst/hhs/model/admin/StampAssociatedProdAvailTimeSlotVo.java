/**
 * 
 */
package com.lvmama.vst.hhs.model.admin;

import java.io.Serializable;
import java.util.List;

/**
 * @author baolm
 *
 */
public class StampAssociatedProdAvailTimeSlotVo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8375222614136585369L;

	private String dateType;
	private List<String> selectDates;
	private String startDate;
	private String endDate;
	private List<String> weekDays;

	public String getDateType() {
		return dateType;
	}

	public void setDateType(String dateType) {
		this.dateType = dateType;
	}

	public List<String> getSelectDates() {
		return selectDates;
	}

	public void setSelectDates(List<String> selectDates) {
		this.selectDates = selectDates;
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

	public List<String> getWeekDays() {
		return weekDays;
	}

	public void setWeekDays(List<String> weekDays) {
		this.weekDays = weekDays;
	}

}
