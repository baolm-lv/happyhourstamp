/**
 * 
 */
package com.lvmama.vst.hhs.model.product;

import java.io.Serializable;

/**
 * @author fengyonggang
 *
 */
public class PackageHotel implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long groupId;
	private String startDate;
	private String endDate;
	private String remark;
	private SuppGoods goods;
	private Integer maxVisitor; 		//最大入住人数
	
	public Long getGroupId() {
		return groupId;
	}
	public void setGroupId(Long groupId) {
		this.groupId = groupId;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
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
	public SuppGoods getGoods() {
		return goods;
	}
	public void setGoods(SuppGoods goods) {
		this.goods = goods;
	}
	public Integer getMaxVisitor() {
		return maxVisitor;
	}
	public void setMaxVisitor(Integer maxVisitor) {
		this.maxVisitor = maxVisitor;
	}
}
