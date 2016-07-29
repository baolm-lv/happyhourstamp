package com.lvmama.vst.hhs.product.dao;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;


/**
 * The persistent class for the PROD_PRODUCT_BRANCH_PROP database table.
 * 
 */
@Entity
@Table(name="PROD_PRODUCT_BRANCH_PROP")
@NamedQuery(name="ProdProductBranchPropEntity.findAll", query="SELECT p FROM ProdProductBranchPropEntity p")
public class ProdProductBranchPropEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="PRODUCT_BRANCH_PROP_ID")
	private long productBranchPropId;

	@Column(name="ADD_VALUE")
	private String addValue;

	@Column(name="PROD_VALUE")
	private String prodValue;

	@Column(name="PRODUCT_BRANCH_ID")
	private BigDecimal productBranchId;

	@Column(name="PROP_ID")
	private BigDecimal propId;

	public ProdProductBranchPropEntity() {
	}

	public long getProductBranchPropId() {
		return this.productBranchPropId;
	}

	public void setProductBranchPropId(long productBranchPropId) {
		this.productBranchPropId = productBranchPropId;
	}

	public String getAddValue() {
		return this.addValue;
	}

	public void setAddValue(String addValue) {
		this.addValue = addValue;
	}

	public String getProdValue() {
		return this.prodValue;
	}

	public void setProdValue(String prodValue) {
		this.prodValue = prodValue;
	}

	public BigDecimal getProductBranchId() {
		return this.productBranchId;
	}

	public void setProductBranchId(BigDecimal productBranchId) {
		this.productBranchId = productBranchId;
	}

	public BigDecimal getPropId() {
		return this.propId;
	}

	public void setPropId(BigDecimal propId) {
		this.propId = propId;
	}

}