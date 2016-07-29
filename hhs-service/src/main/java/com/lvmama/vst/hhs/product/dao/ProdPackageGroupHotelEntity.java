package com.lvmama.vst.hhs.product.dao;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;


/**
 * The persistent class for the PROD_PACKAGE_GROUP_HOTEL database table.
 * 
 */
@Entity
@Table(name="PROD_PACKAGE_GROUP_HOTEL")
@NamedQuery(name="ProdPackageGroupHotelEntity.findAll", query="SELECT p FROM ProdPackageGroupHotelEntity p")
public class ProdPackageGroupHotelEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="GROUP_HOTEL_ID")
	private long groupHotelId;

	@Column(name="GROUP_ID")
	private BigDecimal groupId;

	private String remark;

	@Column(name="STAY_DAYS")
	private String stayDays;

	public ProdPackageGroupHotelEntity() {
	}

	public long getGroupHotelId() {
		return this.groupHotelId;
	}

	public void setGroupHotelId(long groupHotelId) {
		this.groupHotelId = groupHotelId;
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

	public String getStayDays() {
		return this.stayDays;
	}

	public void setStayDays(String stayDays) {
		this.stayDays = stayDays;
	}

}