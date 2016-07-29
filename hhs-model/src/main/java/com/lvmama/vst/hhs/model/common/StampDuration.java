package com.lvmama.vst.hhs.model.common;

import java.io.Serializable;
import java.util.Date;

public class StampDuration implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private Date startDate;
	private Date endDate;
	
	public StampDuration() {
		
	}
	
	public StampDuration(Date start, Date end) {
		this.startDate = start;
		this.endDate = end;
	}
	
	public Date getStartDate() {
		return startDate;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	
	@Override
	public String toString() {
		return "TimeSlot [startDate=" + startDate + ", endDate=" + endDate + "]";
	}
	
}
