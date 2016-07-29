package com.lvmama.vst.hhs.model.stamp;

import java.util.Date;
import java.util.List;

import com.lvmama.vst.hhs.model.common.StampDuration;

public class DateList {

	private StampDuration duration;
	private List<Date> dates;
	
	public StampDuration getDuration() {
		return duration;
	}
	public void setDuration(StampDuration duration) {
		this.duration = duration;
	}
	public List<Date> getDates() {
		return dates;
	}
	public void setDates(List<Date> dates) {
		this.dates = dates;
	}
	
}
