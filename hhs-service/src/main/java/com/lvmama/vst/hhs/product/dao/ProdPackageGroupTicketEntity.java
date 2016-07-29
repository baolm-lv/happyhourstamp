package com.lvmama.vst.hhs.product.dao;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;


/**
 * The persistent class for the PROD_PACKAGE_GROUP_TICKET database table.
 * 
 */
@Entity
@Table(name="PROD_PACKAGE_GROUP_TICKET")
@NamedQuery(name="ProdPackageGroupTicketEntity.findAll", query="SELECT p FROM ProdPackageGroupTicketEntity p")
public class ProdPackageGroupTicketEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="GROUP_TICKET_ID")
	private long groupTicketId;

	@Column(name="GROUP_ID")
	private BigDecimal groupId;

	private String remark;

	@Column(name="START_DAY")
	private String startDay;

	public ProdPackageGroupTicketEntity() {
	}

	public long getGroupTicketId() {
		return this.groupTicketId;
	}

	public void setGroupTicketId(long groupTicketId) {
		this.groupTicketId = groupTicketId;
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

}