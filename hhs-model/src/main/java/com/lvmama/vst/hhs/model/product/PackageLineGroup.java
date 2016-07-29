/**
 * 
 */
package com.lvmama.vst.hhs.model.product;

import java.util.List;

/**
 * @author fengyonggang
 *
 */
public class PackageLineGroup {

	private Long groupId;
	private int travelDay;
	private int stayDay;
	private String remark;
	private SuppGoods goods;
	private String saleType; // PEOPLE:按人卖, COPIES:按份卖
	private List<String> dates;
	
	public Long getGroupId() {
		return groupId;
	}
	public void setGroupId(Long groupId) {
		this.groupId = groupId;
	}
	public int getTravelDay() {
		return travelDay;
	}
	public void setTravelDay(int travelDay) {
		this.travelDay = travelDay;
	}
	public int getStayDay() {
		return stayDay;
	}
	public void setStayDay(int stayDay) {
		this.stayDay = stayDay;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public SuppGoods getGoods() {
		return goods;
	}
	public void setGoods(SuppGoods goods) {
		this.goods = goods;
	}
	public String getSaleType() {
		return saleType;
	}
	public void setSaleType(String saleType) {
		this.saleType = saleType;
	}
	public List<String> getDates() {
		return dates;
	}
	public void setDates(List<String> dates) {
		this.dates = dates;
	}
}
