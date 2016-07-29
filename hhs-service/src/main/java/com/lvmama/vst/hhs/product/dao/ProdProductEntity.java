package com.lvmama.vst.hhs.product.dao;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


/**
 * The persistent class for the PROD_PRODUCT database table.
 * 
 */
@Entity
@Table(name="PROD_PRODUCT")
@NamedQuery(name="ProdProductEntity.findAll", query="SELECT p FROM ProdProductEntity p")
public class ProdProductEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="PRODUCT_ID")
	private long productId;

	@Column(name="ABANDON_FLAG")
	private String abandonFlag;

	@Column(name="ATTRIBUTION_ID")
	private BigDecimal attributionId;

	@Column(name="AUDIT_STATUS")
	private String auditStatus;

	private String bu;

	@Column(name="CANCEL_FLAG")
	private String cancelFlag;

	@Column(name="CATEGORY_ID")
	private BigDecimal categoryId;

	@Column(name="COMPANY_TYPE")
	private String companyType;

	@Temporal(TemporalType.DATE)
	@Column(name="CREATE_TIME")
	private Date createTime;

	@Column(name="CREATE_USER")
	private String createUser;

	@Column(name="DISTRICT_ID")
	private BigDecimal districtId;

	private String filiale;

	@Column(name="MANAGER_ID")
	private BigDecimal managerId;

	@Column(name="MANAGER_ID_PERM")
	private String managerIdPerm;

	@Column(name="MODEL_VERSION")
	private BigDecimal modelVersion;

	@Column(name="MUILT_DEPARTURE_FLAG")
	private String muiltDepartureFlag;

	@Column(name="PACKAGE_TYPE")
	private String packageType;

	@Column(name="PRODUCT_NAME")
	private String productName;

	@Column(name="PRODUCT_TYPE")
	private String productType;

	@Column(name="RECOMMEND_LEVEL")
	private BigDecimal recommendLevel;

	@Column(name="SALE_FLAG")
	private String saleFlag;

	@Column(name="SENSITIVE_FLAG")
	private String sensitiveFlag;

	private String source;

	@Column(name="SUB_CATEGORY_ID")
	private BigDecimal subCategoryId;

	@Column(name="SUPP_PRODUCT_NAME")
	private String suppProductName;

	@Temporal(TemporalType.DATE)
	@Column(name="UPDATE_TIME")
	private Date updateTime;

	@Column(name="UPDATE_USER")
	private String updateUser;

	@Column(name="URL_ID")
	private String urlId;
	
	public ProdProductEntity() {
	}

	public long getProductId() {
		return this.productId;
	}

	public void setProductId(long productId) {
		this.productId = productId;
	}

	public String getAbandonFlag() {
		return this.abandonFlag;
	}

	public void setAbandonFlag(String abandonFlag) {
		this.abandonFlag = abandonFlag;
	}

	public BigDecimal getAttributionId() {
		return this.attributionId;
	}

	public void setAttributionId(BigDecimal attributionId) {
		this.attributionId = attributionId;
	}

	public String getAuditStatus() {
		return this.auditStatus;
	}

	public void setAuditStatus(String auditStatus) {
		this.auditStatus = auditStatus;
	}

	public String getBu() {
		return this.bu;
	}

	public void setBu(String bu) {
		this.bu = bu;
	}

	public String getCancelFlag() {
		return this.cancelFlag;
	}

	public void setCancelFlag(String cancelFlag) {
		this.cancelFlag = cancelFlag;
	}

	public BigDecimal getCategoryId() {
		return this.categoryId;
	}

	public void setCategoryId(BigDecimal categoryId) {
		this.categoryId = categoryId;
	}

	public String getCompanyType() {
		return this.companyType;
	}

	public void setCompanyType(String companyType) {
		this.companyType = companyType;
	}

	public Date getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getCreateUser() {
		return this.createUser;
	}

	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}

	public BigDecimal getDistrictId() {
		return this.districtId;
	}

	public void setDistrictId(BigDecimal districtId) {
		this.districtId = districtId;
	}

	public String getFiliale() {
		return this.filiale;
	}

	public void setFiliale(String filiale) {
		this.filiale = filiale;
	}

	public BigDecimal getManagerId() {
		return this.managerId;
	}

	public void setManagerId(BigDecimal managerId) {
		this.managerId = managerId;
	}

	public String getManagerIdPerm() {
		return this.managerIdPerm;
	}

	public void setManagerIdPerm(String managerIdPerm) {
		this.managerIdPerm = managerIdPerm;
	}

	public BigDecimal getModelVersion() {
		return this.modelVersion;
	}

	public void setModelVersion(BigDecimal modelVersion) {
		this.modelVersion = modelVersion;
	}

	public String getMuiltDepartureFlag() {
		return this.muiltDepartureFlag;
	}

	public void setMuiltDepartureFlag(String muiltDepartureFlag) {
		this.muiltDepartureFlag = muiltDepartureFlag;
	}

	public String getPackageType() {
		return this.packageType;
	}

	public void setPackageType(String packageType) {
		this.packageType = packageType;
	}

	public String getProductName() {
		return this.productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getProductType() {
		return this.productType;
	}

	public void setProductType(String productType) {
		this.productType = productType;
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

	public String getSource() {
		return this.source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public BigDecimal getSubCategoryId() {
		return this.subCategoryId;
	}

	public void setSubCategoryId(BigDecimal subCategoryId) {
		this.subCategoryId = subCategoryId;
	}

	public String getSuppProductName() {
		return this.suppProductName;
	}

	public void setSuppProductName(String suppProductName) {
		this.suppProductName = suppProductName;
	}

	public Date getUpdateTime() {
		return this.updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public String getUpdateUser() {
		return this.updateUser;
	}

	public void setUpdateUser(String updateUser) {
		this.updateUser = updateUser;
	}

	public String getUrlId() {
		return this.urlId;
	}

	public void setUrlId(String urlId) {
		this.urlId = urlId;
	}
	
}