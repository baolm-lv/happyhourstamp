package com.lvmama.vst.hhs.product.dao;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;


/**
 * The persistent class for the PROD_PACKAGE_GROUP_TRANSPORT database table.
 * 
 */
@Entity
@Table(name="PROD_PACKAGE_GROUP_TRANSPORT")
@NamedQuery(name="ProdPackageGroupTransportEntity.findAll", query="SELECT p FROM ProdPackageGroupTransportEntity p")
public class ProdPackageGroupTransportEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="GROUP_TRANSPORT_ID")
	private long groupTransportId;

	@Column(name="BACK_DESTINATION")
	private BigDecimal backDestination;

	@Column(name="BACK_START_DAYS")
	private BigDecimal backStartDays;

	@Column(name="BACK_START_POINT")
	private BigDecimal backStartPoint;

	@Column(name="GROUP_ID")
	private BigDecimal groupId;

	@Column(name="TO_DESTINATION")
	private BigDecimal toDestination;

	@Column(name="TO_START_DAYS")
	private BigDecimal toStartDays;

	@Column(name="TO_START_POINT")
	private BigDecimal toStartPoint;

	@Column(name="TRANSPORT_TYPE")
	private String transportType;

	public ProdPackageGroupTransportEntity() {
	}

	public long getGroupTransportId() {
		return this.groupTransportId;
	}

	public void setGroupTransportId(long groupTransportId) {
		this.groupTransportId = groupTransportId;
	}

	public BigDecimal getBackDestination() {
		return this.backDestination;
	}

	public void setBackDestination(BigDecimal backDestination) {
		this.backDestination = backDestination;
	}

	public BigDecimal getBackStartDays() {
		return this.backStartDays;
	}

	public void setBackStartDays(BigDecimal backStartDays) {
		this.backStartDays = backStartDays;
	}

	public BigDecimal getBackStartPoint() {
		return this.backStartPoint;
	}

	public void setBackStartPoint(BigDecimal backStartPoint) {
		this.backStartPoint = backStartPoint;
	}

	public BigDecimal getGroupId() {
		return this.groupId;
	}

	public void setGroupId(BigDecimal groupId) {
		this.groupId = groupId;
	}

	public BigDecimal getToDestination() {
		return this.toDestination;
	}

	public void setToDestination(BigDecimal toDestination) {
		this.toDestination = toDestination;
	}

	public BigDecimal getToStartDays() {
		return this.toStartDays;
	}

	public void setToStartDays(BigDecimal toStartDays) {
		this.toStartDays = toStartDays;
	}

	public BigDecimal getToStartPoint() {
		return this.toStartPoint;
	}

	public void setToStartPoint(BigDecimal toStartPoint) {
		this.toStartPoint = toStartPoint;
	}

	public String getTransportType() {
		return this.transportType;
	}

	public void setTransportType(String transportType) {
		this.transportType = transportType;
	}

}