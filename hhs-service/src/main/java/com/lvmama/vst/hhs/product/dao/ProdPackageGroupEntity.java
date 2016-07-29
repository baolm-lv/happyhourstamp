package com.lvmama.vst.hhs.product.dao;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;


/**
 * The persistent class for the PROD_PACKAGE_GROUP database table.
 * 
 */
@Entity
@Table(name="PROD_PACKAGE_GROUP")
@NamedQuery(name="ProdPackageGroupEntity.findAll", query="SELECT p FROM ProdPackageGroupEntity p")
public class ProdPackageGroupEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="GROUP_ID")
	private long groupId;

	@Column(name="CATEGORY_ID")
	private BigDecimal categoryId;

	@Column(name="DATE_TYPE")
	private String dateType;

	@Column(name="GROUP_NAME")
	private String groupName;

	@Column(name="GROUP_TYPE")
	private String groupType;

	@Column(name="PRODUCT_ID")
	private BigDecimal productId;

	@Column(name="SELECT_TYPE")
	private String selectType;

	public ProdPackageGroupEntity() {
	}

	public long getGroupId() {
		return this.groupId;
	}

	public void setGroupId(long groupId) {
		this.groupId = groupId;
	}

	public BigDecimal getCategoryId() {
		return this.categoryId;
	}

	public void setCategoryId(BigDecimal categoryId) {
		this.categoryId = categoryId;
	}

	public String getDateType() {
		return this.dateType;
	}

	public void setDateType(String dateType) {
		this.dateType = dateType;
	}

	public String getGroupName() {
		return this.groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public String getGroupType() {
		return this.groupType;
	}

	public void setGroupType(String groupType) {
		this.groupType = groupType;
	}

	public BigDecimal getProductId() {
		return this.productId;
	}

	public void setProductId(BigDecimal productId) {
		this.productId = productId;
	}

	public String getSelectType() {
		return this.selectType;
	}

	public void setSelectType(String selectType) {
		this.selectType = selectType;
	}

}