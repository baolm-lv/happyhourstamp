package com.lvmama.vst.hhs.product.dao;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;


/**
 * The persistent class for the PROD_PRODUCT_BRANCH database table.
 * 
 */
@Entity
@Table(name="PROD_PRODUCT_BRANCH")
@NamedQuery(name="ProdProductBranchEntity.findAll", query="SELECT p FROM ProdProductBranchEntity p")
public class ProdProductBranchEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="PRODUCT_BRANCH_ID")
	private long productBranchId;

	@Column(name="BRANCH_ID")
	private BigDecimal branchId;

	@Column(name="BRANCH_NAME")
	private String branchName;

	@Column(name="CANCEL_FLAG")
	private String cancelFlag;

	@Column(name="MAX_VISITOR")
	private BigDecimal maxVisitor;

	@Column(name="PRODUCT_ID")
	private BigDecimal productId;

	@Column(name="RECOMMEND_LEVEL")
	private BigDecimal recommendLevel;

	@Column(name="SALE_FLAG")
	private String saleFlag;

	@Column(name="SENSITIVE_FLAG")
	private String sensitiveFlag;

	public ProdProductBranchEntity() {
	}

	public long getProductBranchId() {
		return this.productBranchId;
	}

	public void setProductBranchId(long productBranchId) {
		this.productBranchId = productBranchId;
	}

	public BigDecimal getBranchId() {
		return this.branchId;
	}

	public void setBranchId(BigDecimal branchId) {
		this.branchId = branchId;
	}

	public String getBranchName() {
		return this.branchName;
	}

	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}

	public String getCancelFlag() {
		return this.cancelFlag;
	}

	public void setCancelFlag(String cancelFlag) {
		this.cancelFlag = cancelFlag;
	}

	public BigDecimal getMaxVisitor() {
		return this.maxVisitor;
	}

	public void setMaxVisitor(BigDecimal maxVisitor) {
		this.maxVisitor = maxVisitor;
	}

	public BigDecimal getProductId() {
		return this.productId;
	}

	public void setProductId(BigDecimal productId) {
		this.productId = productId;
	}

	public BigDecimal getRecommendLevel() {
		return this.recommendLevel;
	}

	public void setRecommendLevel(BigDecimal recommendLevel) {
		this.recommendLevel = recommendLevel;
	}

	public String getSaleFlag() {
		return this.saleFlag;
	}

	public void setSaleFlag(String saleFlag) {
		this.saleFlag = saleFlag;
	}

	public String getSensitiveFlag() {
		return this.sensitiveFlag;
	}

	public void setSensitiveFlag(String sensitiveFlag) {
		this.sensitiveFlag = sensitiveFlag;
	}

}