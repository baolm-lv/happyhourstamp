package com.lvmama.vst.hhs.product.dao;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;


/**
 * The persistent class for the PROD_PACKAGE_DETAIL database table.
 * 
 */
@Entity
@Table(name="PROD_PACKAGE_DETAIL")
@NamedQuery(name="ProdPackageDetailEntity.findAll", query="SELECT p FROM ProdPackageDetailEntity p")
public class ProdPackageDetailEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="DETAIL_ID")
	private long detailId;

	@Column(name="GROUP_ID")
	private BigDecimal groupId;

	@Column(name="OBJECT_ID")
	private BigDecimal objectId;

	@Column(name="OBJECT_TYPE")
	private String objectType;

	@Column(name="PACKAGE_COUNT")
	private BigDecimal packageCount;

	private BigDecimal price;

	@Column(name="PRICE_TYPE")
	private String priceType;

	private BigDecimal seq;

	@Column(name="START_DISTRICT_ID")
	private BigDecimal startDistrictId;

	public ProdPackageDetailEntity() {
	}

	public long getDetailId() {
		return this.detailId;
	}

	public void setDetailId(long detailId) {
		this.detailId = detailId;
	}

	public BigDecimal getGroupId() {
		return this.groupId;
	}

	public void setGroupId(BigDecimal groupId) {
		this.groupId = groupId;
	}

	public BigDecimal getObjectId() {
		return this.objectId;
	}

	public void setObjectId(BigDecimal objectId) {
		this.objectId = objectId;
	}

	public String getObjectType() {
		return this.objectType;
	}

	public void setObjectType(String objectType) {
		this.objectType = objectType;
	}

	public BigDecimal getPackageCount() {
		return this.packageCount;
	}

	public void setPackageCount(BigDecimal packageCount) {
		this.packageCount = packageCount;
	}

	public BigDecimal getPrice() {
		return this.price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public String getPriceType() {
		return this.priceType;
	}

	public void setPriceType(String priceType) {
		this.priceType = priceType;
	}

	public BigDecimal getSeq() {
		return this.seq;
	}

	public void setSeq(BigDecimal seq) {
		this.seq = seq;
	}

	public BigDecimal getStartDistrictId() {
		return this.startDistrictId;
	}

	public void setStartDistrictId(BigDecimal startDistrictId) {
		this.startDistrictId = startDistrictId;
	}

}