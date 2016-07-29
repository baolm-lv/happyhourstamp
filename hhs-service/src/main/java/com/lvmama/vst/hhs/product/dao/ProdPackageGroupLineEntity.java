package com.lvmama.vst.hhs.product.dao;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;


/**
 * The persistent class for the PROD_PACKAGE_GROUP_LINE database table.
 * 
 */
@Entity
@Table(name="PROD_PACKAGE_GROUP_LINE")
@NamedQuery(name="ProdPackageGroupLineEntity.findAll", query="SELECT p FROM ProdPackageGroupLineEntity p")
public class ProdPackageGroupLineEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="GROUP_LINE_ID")
	private long groupLineId;

	private BigDecimal adult;

	private BigDecimal child;

	@Column(name="GROUP_ID")
	private BigDecimal groupId;

	private String remark;

	@Column(name="START_DAY")
	private String startDay;

	@Column(name="STAY_DAYS")
	private BigDecimal stayDays;

	@Column(name="TRAVEL_DAYS")
	private BigDecimal travelDays;

	public ProdPackageGroupLineEntity() {
	}

	public long getGroupLineId() {
		return this.groupLineId;
	}

	public void setGroupLineId(long groupLineId) {
		this.groupLineId = groupLineId;
	}

	public BigDecimal getAdult() {
		return this.adult;
	}

	public void setAdult(BigDecimal adult) {
		this.adult = adult;
	}

	public BigDecimal getChild() {
		return this.child;
	}

	public void setChild(BigDecimal child) {
		this.child = child;
	}

	public BigDecimal getGroupId() {
		return this.groupId;
	}

	public void setGroupId(BigDecimal groupId) {
		this.groupId = groupId;
	}

	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getStartDay() {
		return this.startDay;
	}

	public void setStartDay(String startDay) {
		this.startDay = startDay;
	}

	public BigDecimal getStayDays() {
		return this.stayDays;
	}

	public void setStayDays(BigDecimal stayDays) {
		this.stayDays = stayDays;
	}

	public BigDecimal getTravelDays() {
		return this.travelDays;
	}

	public void setTravelDays(BigDecimal travelDays) {
		this.travelDays = travelDays;
	}

}