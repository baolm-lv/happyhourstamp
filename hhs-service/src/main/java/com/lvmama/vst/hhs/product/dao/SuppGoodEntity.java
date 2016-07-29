package com.lvmama.vst.hhs.product.dao;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;


/**
 * The persistent class for the SUPP_GOODS database table.
 * 
 */
@Entity
@Table(name="SUPP_GOODS")
@NamedQuery(name="SuppGoodEntity.findAll", query="SELECT s FROM SuppGoodEntity s")
public class SuppGoodEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="SUPP_GOODS_ID")
	private long suppGoodsId;

	private BigDecimal adult;

	@Column(name="AHEAD_BOOK_TIME")
	private BigDecimal aheadBookTime;

	@Column(name="APERIODIC_FLAG")
	private String aperiodicFlag;

	@Column(name="API_FLAG")
	private String apiFlag;

	@Column(name="ATTRIBUTION_ID")
	private BigDecimal attributionId;

	@Column(name="BRANCH_ID")
	private BigDecimal branchId;

	private String bu;

	@Column(name="BUYOUT_FLAG")
	private String buyoutFlag;

	@Column(name="CANCEL_FLAG")
	private String cancelFlag;

	@Column(name="CATEGORY_ID")
	private BigDecimal categoryId;

	@Column(name="CERT_VALID_DAY")
	private BigDecimal certValidDay;

	private BigDecimal child;

	@Column(name="COMMERCIAL_STAFF_ID")
	private BigDecimal commercialStaffId;

	@Column(name="COMPANY_TYPE")
	private String companyType;

	@Column(name="CONTENT_MANAGER_ID")
	private BigDecimal contentManagerId;

	@Column(name="CONTRACT_ID")
	private BigDecimal contractId;

	@Temporal(TemporalType.DATE)
	@Column(name="CREATE_TIME")
	private Date createTime;

	@Column(name="CREATE_USER")
	private String createUser;

	@Column(name="CURRENCY_TYPE")
	private String currencyType;

	@Column(name="DISTRIBUTE_FLAG")
	private String distributeFlag;

	@Column(name="EBK_FLAG")
	private String ebkFlag;

	@Column(name="EXPRESS_TYPE")
	private String expressType;

	@Column(name="FAX_FLAG")
	private String faxFlag;

	@Column(name="FAX_REMARK")
	private String faxRemark;

	@Column(name="FAX_RULE_ID")
	private BigDecimal faxRuleId;

	private String filiale;

	@Column(name="GOODS_DESC")
	private String goodsDesc;

	@Column(name="GOODS_NAME")
	private String goodsName;

	@Column(name="GOODS_SPEC")
	private String goodsSpec;

	@Column(name="GOODS_TYPE")
	private String goodsType;

	@Column(name="GROUP_ID")
	private BigDecimal groupId;

	@Column(name="LIMIT_BOOK_DAY")
	private BigDecimal limitBookDay;

	@Column(name="LVMAMA_FLAG")
	private String lvmamaFlag;

	@Column(name="MANAGER_ID")
	private BigDecimal managerId;

	@Column(name="MAX_QUANTITY")
	private BigDecimal maxQuantity;

	@Column(name="MAX_STAY_DAY")
	private BigDecimal maxStayDay;

	@Column(name="MIN_QUANTITY")
	private BigDecimal minQuantity;

	@Column(name="MIN_STAY_DAY")
	private BigDecimal minStayDay;

	@Column(name="NOTICE_TYPE")
	private String noticeType;

	@Column(name="NOTINTIME_FLAG")
	private String notintimeFlag;

	@Column(name="ONLINE_FLAG")
	private String onlineFlag;

	@Column(name="ORG_ID")
	private BigDecimal orgId;

	@Column(name="PACKAGE_FLAG")
	private String packageFlag;

	@Column(name="PAY_TARGET")
	private String payTarget;

	@Column(name="POST_FREE_FLAG")
	private String postFreeFlag;

	@Column(name="PRICE_TYPE")
	private String priceType;

	@Column(name="PRODUCT_BRANCH_ID")
	private BigDecimal productBranchId;

	@Column(name="PRODUCT_ID")
	private BigDecimal productId;

	@Column(name="REGIONAL_LEADER_ID")
	private BigDecimal regionalLeaderId;

	@Column(name="SENSITIVE_FLAG")
	private String sensitiveFlag;

	private BigDecimal seq;

	@Column(name="SPECIAL_TICKET_TYPE")
	private String specialTicketType;

	@Column(name="STOCK_API_FLAG")
	private String stockApiFlag;

	@Column(name="SUPPLIER_ID")
	private BigDecimal supplierId;

	@Column(name="TODAY_ONLINE_FLAG")
	private String todayOnlineFlag;

	@Temporal(TemporalType.DATE)
	@Column(name="UPDATE_TIME")
	private Date updateTime;

	@Column(name="UPDATE_USER")
	private String updateUser;

	public SuppGoodEntity() {
	}

	public long getSuppGoodsId() {
		return this.suppGoodsId;
	}

	public void setSuppGoodsId(long suppGoodsId) {
		this.suppGoodsId = suppGoodsId;
	}

	public BigDecimal getAdult() {
		return this.adult;
	}

	public void setAdult(BigDecimal adult) {
		this.adult = adult;
	}

	public BigDecimal getAheadBookTime() {
		return this.aheadBookTime;
	}

	public void setAheadBookTime(BigDecimal aheadBookTime) {
		this.aheadBookTime = aheadBookTime;
	}

	public String getAperiodicFlag() {
		return this.aperiodicFlag;
	}

	public void setAperiodicFlag(String aperiodicFlag) {
		this.aperiodicFlag = aperiodicFlag;
	}

	public String getApiFlag() {
		return this.apiFlag;
	}

	public void setApiFlag(String apiFlag) {
		this.apiFlag = apiFlag;
	}

	public BigDecimal getAttributionId() {
		return this.attributionId;
	}

	public void setAttributionId(BigDecimal attributionId) {
		this.attributionId = attributionId;
	}

	public BigDecimal getBranchId() {
		return this.branchId;
	}

	public void setBranchId(BigDecimal branchId) {
		this.branchId = branchId;
	}

	public String getBu() {
		return this.bu;
	}

	public void setBu(String bu) {
		this.bu = bu;
	}

	public String getBuyoutFlag() {
		return this.buyoutFlag;
	}

	public void setBuyoutFlag(String buyoutFlag) {
		this.buyoutFlag = buyoutFlag;
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

	public BigDecimal getCertValidDay() {
		return this.certValidDay;
	}

	public void setCertValidDay(BigDecimal certValidDay) {
		this.certValidDay = certValidDay;
	}

	public BigDecimal getChild() {
		return this.child;
	}

	public void setChild(BigDecimal child) {
		this.child = child;
	}

	public BigDecimal getCommercialStaffId() {
		return this.commercialStaffId;
	}

	public void setCommercialStaffId(BigDecimal commercialStaffId) {
		this.commercialStaffId = commercialStaffId;
	}

	public String getCompanyType() {
		return this.companyType;
	}

	public void setCompanyType(String companyType) {
		this.companyType = companyType;
	}

	public BigDecimal getContentManagerId() {
		return this.contentManagerId;
	}

	public void setContentManagerId(BigDecimal contentManagerId) {
		this.contentManagerId = contentManagerId;
	}

	public BigDecimal getContractId() {
		return this.contractId;
	}

	public void setContractId(BigDecimal contractId) {
		this.contractId = contractId;
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

	public String getCurrencyType() {
		return this.currencyType;
	}

	public void setCurrencyType(String currencyType) {
		this.currencyType = currencyType;
	}

	public String getDistributeFlag() {
		return this.distributeFlag;
	}

	public void setDistributeFlag(String distributeFlag) {
		this.distributeFlag = distributeFlag;
	}

	public String getEbkFlag() {
		return this.ebkFlag;
	}

	public void setEbkFlag(String ebkFlag) {
		this.ebkFlag = ebkFlag;
	}

	public String getExpressType() {
		return this.expressType;
	}

	public void setExpressType(String expressType) {
		this.expressType = expressType;
	}

	public String getFaxFlag() {
		return this.faxFlag;
	}

	public void setFaxFlag(String faxFlag) {
		this.faxFlag = faxFlag;
	}

	public String getFaxRemark() {
		return this.faxRemark;
	}

	public void setFaxRemark(String faxRemark) {
		this.faxRemark = faxRemark;
	}

	public BigDecimal getFaxRuleId() {
		return this.faxRuleId;
	}

	public void setFaxRuleId(BigDecimal faxRuleId) {
		this.faxRuleId = faxRuleId;
	}

	public String getFiliale() {
		return this.filiale;
	}

	public void setFiliale(String filiale) {
		this.filiale = filiale;
	}

	public String getGoodsDesc() {
		return this.goodsDesc;
	}

	public void setGoodsDesc(String goodsDesc) {
		this.goodsDesc = goodsDesc;
	}

	public String getGoodsName() {
		return this.goodsName;
	}

	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}

	public String getGoodsSpec() {
		return this.goodsSpec;
	}

	public void setGoodsSpec(String goodsSpec) {
		this.goodsSpec = goodsSpec;
	}

	public String getGoodsType() {
		return this.goodsType;
	}

	public void setGoodsType(String goodsType) {
		this.goodsType = goodsType;
	}

	public BigDecimal getGroupId() {
		return this.groupId;
	}

	public void setGroupId(BigDecimal groupId) {
		this.groupId = groupId;
	}

	public BigDecimal getLimitBookDay() {
		return this.limitBookDay;
	}

	public void setLimitBookDay(BigDecimal limitBookDay) {
		this.limitBookDay = limitBookDay;
	}

	public String getLvmamaFlag() {
		return this.lvmamaFlag;
	}

	public void setLvmamaFlag(String lvmamaFlag) {
		this.lvmamaFlag = lvmamaFlag;
	}

	public BigDecimal getManagerId() {
		return this.managerId;
	}

	public void setManagerId(BigDecimal managerId) {
		this.managerId = managerId;
	}

	public BigDecimal getMaxQuantity() {
		return this.maxQuantity;
	}

	public void setMaxQuantity(BigDecimal maxQuantity) {
		this.maxQuantity = maxQuantity;
	}

	public BigDecimal getMaxStayDay() {
		return this.maxStayDay;
	}

	public void setMaxStayDay(BigDecimal maxStayDay) {
		this.maxStayDay = maxStayDay;
	}

	public BigDecimal getMinQuantity() {
		return this.minQuantity;
	}

	public void setMinQuantity(BigDecimal minQuantity) {
		this.minQuantity = minQuantity;
	}

	public BigDecimal getMinStayDay() {
		return this.minStayDay;
	}

	public void setMinStayDay(BigDecimal minStayDay) {
		this.minStayDay = minStayDay;
	}

	public String getNoticeType() {
		return this.noticeType;
	}

	public void setNoticeType(String noticeType) {
		this.noticeType = noticeType;
	}

	public String getNotintimeFlag() {
		return this.notintimeFlag;
	}

	public void setNotintimeFlag(String notintimeFlag) {
		this.notintimeFlag = notintimeFlag;
	}

	public String getOnlineFlag() {
		return this.onlineFlag;
	}

	public void setOnlineFlag(String onlineFlag) {
		this.onlineFlag = onlineFlag;
	}

	public BigDecimal getOrgId() {
		return this.orgId;
	}

	public void setOrgId(BigDecimal orgId) {
		this.orgId = orgId;
	}

	public String getPackageFlag() {
		return this.packageFlag;
	}

	public void setPackageFlag(String packageFlag) {
		this.packageFlag = packageFlag;
	}

	public String getPayTarget() {
		return this.payTarget;
	}

	public void setPayTarget(String payTarget) {
		this.payTarget = payTarget;
	}

	public String getPostFreeFlag() {
		return this.postFreeFlag;
	}

	public void setPostFreeFlag(String postFreeFlag) {
		this.postFreeFlag = postFreeFlag;
	}

	public String getPriceType() {
		return this.priceType;
	}

	public void setPriceType(String priceType) {
		this.priceType = priceType;
	}

	public BigDecimal getProductBranchId() {
		return this.productBranchId;
	}

	public void setProductBranchId(BigDecimal productBranchId) {
		this.productBranchId = productBranchId;
	}

	public BigDecimal getProductId() {
		return this.productId;
	}

	public void setProductId(BigDecimal productId) {
		this.productId = productId;
	}

	public BigDecimal getRegionalLeaderId() {
		return this.regionalLeaderId;
	}

	public void setRegionalLeaderId(BigDecimal regionalLeaderId) {
		this.regionalLeaderId = regionalLeaderId;
	}

	public String getSensitiveFlag() {
		return this.sensitiveFlag;
	}

	public void setSensitiveFlag(String sensitiveFlag) {
		this.sensitiveFlag = sensitiveFlag;
	}

	public BigDecimal getSeq() {
		return this.seq;
	}

	public void setSeq(BigDecimal seq) {
		this.seq = seq;
	}

	public String getSpecialTicketType() {
		return this.specialTicketType;
	}

	public void setSpecialTicketType(String specialTicketType) {
		this.specialTicketType = specialTicketType;
	}

	public String getStockApiFlag() {
		return this.stockApiFlag;
	}

	public void setStockApiFlag(String stockApiFlag) {
		this.stockApiFlag = stockApiFlag;
	}

	public BigDecimal getSupplierId() {
		return this.supplierId;
	}

	public void setSupplierId(BigDecimal supplierId) {
		this.supplierId = supplierId;
	}

	public String getTodayOnlineFlag() {
		return this.todayOnlineFlag;
	}

	public void setTodayOnlineFlag(String todayOnlineFlag) {
		this.todayOnlineFlag = todayOnlineFlag;
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

}