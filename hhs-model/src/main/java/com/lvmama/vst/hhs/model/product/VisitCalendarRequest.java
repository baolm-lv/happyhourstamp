/**
 * 
 */
package com.lvmama.vst.hhs.model.product;

import java.io.Serializable;
import java.util.List;

/**
 * @author fengyonggang
 *
 */
public class VisitCalendarRequest implements Serializable {

	private static final long serialVersionUID = 1L;
	private Long startDistrictId;
	private List<StampUsage> stamps;

	public Long getStartDistrictId() {
		return startDistrictId;
	}

	public void setStartDistrictId(Long startDistrictId) {
		this.startDistrictId = startDistrictId;
	}

	public List<StampUsage> getStamps() {
		return stamps;
	}

	public void setStamps(List<StampUsage> stamps) {
		this.stamps = stamps;
	}

}
