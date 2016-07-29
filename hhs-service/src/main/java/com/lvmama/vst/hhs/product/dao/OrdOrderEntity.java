package com.lvmama.vst.hhs.product.dao;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;


/**
 * The persistent class for the ORD_ORDER database table.
 * 
 */
@Entity
@Table(name="ORD_ORDER")
@NamedQuery(name="OrdOrderEntity.findAll", query="SELECT o FROM OrdOrderEntity o")
public class OrdOrderEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="ORDER_ID")
	private long orderId;

	@Column(name="ACTUAL_AMOUNT")
	private BigDecimal actualAmount;

	@Column(name="ANONYMITY_BOOK_FLAG")
	private String anonymityBookFlag;

	@Temporal(TemporalType.DATE)
	@Column(name="APPROVE_TIME")
	private Date approveTime;

	@Column(name="ATTRIBUTION_ID")
	private BigDecimal attributionId;

	@Column(name="BACK_USER_ID")
	private String backUserId;

	@Column(name="BONUS_AMOUNT")
	private BigDecimal bonusAmount;

	@Column(name="BOOK_LIMIT_TYPE")
	private String bookLimitType;

	@Column(name="BU_CODE")
	private String buCode;

	@Column(name="CANCEL_CERT_CONFIRM_STATUS")
	private String cancelCertConfirmStatus;

	@Column(name="CANCEL_CODE")
	private String cancelCode;

	@Column(name="CANCEL_FAIL_COUNT")
	private BigDecimal cancelFailCount;

	@Column(name="CANCEL_STRATEGY")
	private String cancelStrategy;

	@Temporal(TemporalType.DATE)
	@Column(name="CANCEL_TIME")
	private Date cancelTime;

	@Column(name="CATEGORY_ID")
	private BigDecimal categoryId;

	@Column(name="CERT_CONFIRM_STATUS")
	private String certConfirmStatus;

	@Column(name="CLIENT_IP_ADDRESS")
	private String clientIpAddress;

	@Column(name="COMPANY_TYPE")
	private String companyType;

	@Temporal(TemporalType.DATE)
	@Column(name="CREATE_TIME")
	private Date createTime;

	@Column(name="CURRENCY_CODE")
	private String currencyCode;

	@Column(name="DEPOSITS_AMOUNT")
	private BigDecimal depositsAmount;

	@Column(name="DISTRIBUTION_CHANNEL")
	private BigDecimal distributionChannel;

	@Column(name="DISTRIBUTOR_CODE")
	private String distributorCode;

	@Column(name="DISTRIBUTOR_ID")
	private BigDecimal distributorId;

	@Column(name="DISTRIBUTOR_NAME")
	private String distributorName;

	@Column(name="FILIALE_NAME")
	private String filialeName;

	private String guarantee;

	@Temporal(TemporalType.DATE)
	@Column(name="INFO_PASS_TIME")
	private Date infoPassTime;

	@Column(name="INFO_STATUS")
	private String infoStatus;

	@Column(name="INVOICE_STATUS")
	private String invoiceStatus;

	@Column(name="INVOKE_INTERFACE_PF_STATUS")
	private String invokeInterfacePfStatus;

	@Column(name="IS_TEST_ORDER")
	private String isTestOrder;

	@Temporal(TemporalType.DATE)
	@Column(name="LAST_CANCEL_TIME")
	private Date lastCancelTime;

	@Column(name="LINE_ROUTE_ID")
	private BigDecimal lineRouteId;

	@Column(name="MANAGER_ID")
	private BigDecimal managerId;

	@Column(name="MANAGER_ID_PERM")
	private String managerIdPerm;

	@Column(name="MOBILE_EQUIPMENT_NO")
	private String mobileEquipmentNo;

	@Column(name="MOBILE_MORE_REBATE")
	private BigDecimal mobileMoreRebate;

	@Column(name="NEED_INVOICE")
	private String needInvoice;

	@Column(name="NOTIFY_TYPE")
	private String notifyType;

	@Column(name="ORDER_MEMO")
	private String orderMemo;

	@Column(name="ORDER_STATUS")
	private String orderStatus;

	@Column(name="ORDER_SUBTYPE")
	private String orderSubtype;

	@Temporal(TemporalType.DATE)
	@Column(name="ORDER_UPDATE_TIME")
	private Date orderUpdateTime;

	@Column(name="ORI_ORDER_ID")
	private BigDecimal oriOrderId;

	@Column(name="OUGHT_AMOUNT")
	private BigDecimal oughtAmount;

	@Column(name="PAY_PROC_TRIGGERED")
	private String payProcTriggered;

	@Column(name="PAYMENT_STATUS")
	private String paymentStatus;

	@Column(name="PAYMENT_TARGET")
	private String paymentTarget;

	@Temporal(TemporalType.DATE)
	@Column(name="PAYMENT_TIME")
	private Date paymentTime;

	@Column(name="PAYMENT_TYPE")
	private String paymentType;

	@Column(name="PROCESS_KEY")
	private String processKey;

	@Column(name="PROM_PAYMENT_CHANNEL")
	private String promPaymentChannel;

	private String reason;

	@Column(name="REBATE_AMOUNT")
	private BigDecimal rebateAmount;

	@Column(name="REBATE_FLAG")
	private String rebateFlag;

	@Column(name="REFUNDED_AMOUNT")
	private BigDecimal refundedAmount;

	private String remark;

	@Column(name="REMIND_SMS_SEND_STATUS")
	private String remindSmsSendStatus;

	@Temporal(TemporalType.DATE)
	@Column(name="RESOURCE_AMPLE_TIME")
	private Date resourceAmpleTime;

	@Column(name="RESOURCE_STATUS")
	private String resourceStatus;

	@Column(name="SEND_CONTRACT_FLAG")
	private String sendContractFlag;

	@Column(name="SMS_LVMAMA_FLAG")
	private String smsLvmamaFlag;

	@Column(name="START_DISTRICT_ID")
	private BigDecimal startDistrictId;

	@Column(name="SUB_CATEGORY_ID")
	private BigDecimal subCategoryId;

	@Column(name="SUPPLIER_API_FLAG")
	private String supplierApiFlag;

	private BigDecimal tag;

	@Column(name="TRAVELLER_DELAY_FLAG")
	private String travellerDelayFlag;

	@Column(name="TRAVELLER_LOCK_FLAG")
	private String travellerLockFlag;

	@Column(name="USER_ID")
	private String userId;

	@Column(name="USER_NO")
	private BigDecimal userNo;

	@Column(name="VALID_STATUS")
	private String validStatus;

	@Column(name="VIEW_ORDER_STATUS")
	private String viewOrderStatus;

	@Temporal(TemporalType.DATE)
	@Column(name="VISIT_TIME")
	private Date visitTime;

	@Temporal(TemporalType.DATE)
	@Column(name="WAIT_PAYMENT_TIME")
	private Date waitPaymentTime;

	public OrdOrderEntity() {
	}

	public long getOrderId() {
		return this.orderId;
	}

	public void setOrderId(long orderId) {
		this.orderId = orderId;
	}

	public BigDecimal getActualAmount() {
		return this.actualAmount;
	}

	public void setActualAmount(BigDecimal actualAmount) {
		this.actualAmount = actualAmount;
	}

	public String getAnonymityBookFlag() {
		return this.anonymityBookFlag;
	}

	public void setAnonymityBookFlag(String anonymityBookFlag) {
		this.anonymityBookFlag = anonymityBookFlag;
	}

	public Date getApproveTime() {
		return this.approveTime;
	}

	public void setApproveTime(Date approveTime) {
		this.approveTime = approveTime;
	}

	public BigDecimal getAttributionId() {
		return this.attributionId;
	}

	public void setAttributionId(BigDecimal attributionId) {
		this.attributionId = attributionId;
	}

	public String getBackUserId() {
		return this.backUserId;
	}

	public void setBackUserId(String backUserId) {
		this.backUserId = backUserId;
	}

	public BigDecimal getBonusAmount() {
		return this.bonusAmount;
	}

	public void setBonusAmount(BigDecimal bonusAmount) {
		this.bonusAmount = bonusAmount;
	}

	public String getBookLimitType() {
		return this.bookLimitType;
	}

	public void setBookLimitType(String bookLimitType) {
		this.bookLimitType = bookLimitType;
	}

	public String getBuCode() {
		return this.buCode;
	}

	public void setBuCode(String buCode) {
		this.buCode = buCode;
	}

	public String getCancelCertConfirmStatus() {
		return this.cancelCertConfirmStatus;
	}

	public void setCancelCertConfirmStatus(String cancelCertConfirmStatus) {
		this.cancelCertConfirmStatus = cancelCertConfirmStatus;
	}

	public String getCancelCode() {
		return this.cancelCode;
	}

	public void setCancelCode(String cancelCode) {
		this.cancelCode = cancelCode;
	}

	public BigDecimal getCancelFailCount() {
		return this.cancelFailCount;
	}

	public void setCancelFailCount(BigDecimal cancelFailCount) {
		this.cancelFailCount = cancelFailCount;
	}

	public String getCancelStrategy() {
		return this.cancelStrategy;
	}

	public void setCancelStrategy(String cancelStrategy) {
		this.cancelStrategy = cancelStrategy;
	}

	public Date getCancelTime() {
		return this.cancelTime;
	}

	public void setCancelTime(Date cancelTime) {
		this.cancelTime = cancelTime;
	}

	public BigDecimal getCategoryId() {
		return this.categoryId;
	}

	public void setCategoryId(BigDecimal categoryId) {
		this.categoryId = categoryId;
	}

	public String getCertConfirmStatus() {
		return this.certConfirmStatus;
	}

	public void setCertConfirmStatus(String certConfirmStatus) {
		this.certConfirmStatus = certConfirmStatus;
	}

	public String getClientIpAddress() {
		return this.clientIpAddress;
	}

	public void setClientIpAddress(String clientIpAddress) {
		this.clientIpAddress = clientIpAddress;
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

	public String getCurrencyCode() {
		return this.currencyCode;
	}

	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}

	public BigDecimal getDepositsAmount() {
		return this.depositsAmount;
	}

	public void setDepositsAmount(BigDecimal depositsAmount) {
		this.depositsAmount = depositsAmount;
	}

	public BigDecimal getDistributionChannel() {
		return this.distributionChannel;
	}

	public void setDistributionChannel(BigDecimal distributionChannel) {
		this.distributionChannel = distributionChannel;
	}

	public String getDistributorCode() {
		return this.distributorCode;
	}

	public void setDistributorCode(String distributorCode) {
		this.distributorCode = distributorCode;
	}

	public BigDecimal getDistributorId() {
		return this.distributorId;
	}

	public void setDistributorId(BigDecimal distributorId) {
		this.distributorId = distributorId;
	}

	public String getDistributorName() {
		return this.distributorName;
	}

	public void setDistributorName(String distributorName) {
		this.distributorName = distributorName;
	}

	public String getFilialeName() {
		return this.filialeName;
	}

	public void setFilialeName(String filialeName) {
		this.filialeName = filialeName;
	}

	public String getGuarantee() {
		return this.guarantee;
	}

	public void setGuarantee(String guarantee) {
		this.guarantee = guarantee;
	}

	public Date getInfoPassTime() {
		return this.infoPassTime;
	}

	public void setInfoPassTime(Date infoPassTime) {
		this.infoPassTime = infoPassTime;
	}

	public String getInfoStatus() {
		return this.infoStatus;
	}

	public void setInfoStatus(String infoStatus) {
		this.infoStatus = infoStatus;
	}

	public String getInvoiceStatus() {
		return this.invoiceStatus;
	}

	public void setInvoiceStatus(String invoiceStatus) {
		this.invoiceStatus = invoiceStatus;
	}

	public String getInvokeInterfacePfStatus() {
		return this.invokeInterfacePfStatus;
	}

	public void setInvokeInterfacePfStatus(String invokeInterfacePfStatus) {
		this.invokeInterfacePfStatus = invokeInterfacePfStatus;
	}

	public String getIsTestOrder() {
		return this.isTestOrder;
	}

	public void setIsTestOrder(String isTestOrder) {
		this.isTestOrder = isTestOrder;
	}

	public Date getLastCancelTime() {
		return this.lastCancelTime;
	}

	public void setLastCancelTime(Date lastCancelTime) {
		this.lastCancelTime = lastCancelTime;
	}

	public BigDecimal getLineRouteId() {
		return this.lineRouteId;
	}

	public void setLineRouteId(BigDecimal lineRouteId) {
		this.lineRouteId = lineRouteId;
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

	public String getMobileEquipmentNo() {
		return this.mobileEquipmentNo;
	}

	public void setMobileEquipmentNo(String mobileEquipmentNo) {
		this.mobileEquipmentNo = mobileEquipmentNo;
	}

	public BigDecimal getMobileMoreRebate() {
		return this.mobileMoreRebate;
	}

	public void setMobileMoreRebate(BigDecimal mobileMoreRebate) {
		this.mobileMoreRebate = mobileMoreRebate;
	}

	public String getNeedInvoice() {
		return this.needInvoice;
	}

	public void setNeedInvoice(String needInvoice) {
		this.needInvoice = needInvoice;
	}

	public String getNotifyType() {
		return this.notifyType;
	}

	public void setNotifyType(String notifyType) {
		this.notifyType = notifyType;
	}

	public String getOrderMemo() {
		return this.orderMemo;
	}

	public void setOrderMemo(String orderMemo) {
		this.orderMemo = orderMemo;
	}

	public String getOrderStatus() {
		return this.orderStatus;
	}

	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}

	public String getOrderSubtype() {
		return this.orderSubtype;
	}

	public void setOrderSubtype(String orderSubtype) {
		this.orderSubtype = orderSubtype;
	}

	public Date getOrderUpdateTime() {
		return this.orderUpdateTime;
	}

	public void setOrderUpdateTime(Date orderUpdateTime) {
		this.orderUpdateTime = orderUpdateTime;
	}

	public BigDecimal getOriOrderId() {
		return this.oriOrderId;
	}

	public void setOriOrderId(BigDecimal oriOrderId) {
		this.oriOrderId = oriOrderId;
	}

	public BigDecimal getOughtAmount() {
		return this.oughtAmount;
	}

	public void setOughtAmount(BigDecimal oughtAmount) {
		this.oughtAmount = oughtAmount;
	}

	public String getPayProcTriggered() {
		return this.payProcTriggered;
	}

	public void setPayProcTriggered(String payProcTriggered) {
		this.payProcTriggered = payProcTriggered;
	}

	public String getPaymentStatus() {
		return this.paymentStatus;
	}

	public void setPaymentStatus(String paymentStatus) {
		this.paymentStatus = paymentStatus;
	}

	public String getPaymentTarget() {
		return this.paymentTarget;
	}

	public void setPaymentTarget(String paymentTarget) {
		this.paymentTarget = paymentTarget;
	}

	public Date getPaymentTime() {
		return this.paymentTime;
	}

	public void setPaymentTime(Date paymentTime) {
		this.paymentTime = paymentTime;
	}

	public String getPaymentType() {
		return this.paymentType;
	}

	public void setPaymentType(String paymentType) {
		this.paymentType = paymentType;
	}

	public String getProcessKey() {
		return this.processKey;
	}

	public void setProcessKey(String processKey) {
		this.processKey = processKey;
	}

	public String getPromPaymentChannel() {
		return this.promPaymentChannel;
	}

	public void setPromPaymentChannel(String promPaymentChannel) {
		this.promPaymentChannel = promPaymentChannel;
	}

	public String getReason() {
		return this.reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public BigDecimal getRebateAmount() {
		return this.rebateAmount;
	}

	public void setRebateAmount(BigDecimal rebateAmount) {
		this.rebateAmount = rebateAmount;
	}

	public String getRebateFlag() {
		return this.rebateFlag;
	}

	public void setRebateFlag(String rebateFlag) {
		this.rebateFlag = rebateFlag;
	}

	public BigDecimal getRefundedAmount() {
		return this.refundedAmount;
	}

	public void setRefundedAmount(BigDecimal refundedAmount) {
		this.refundedAmount = refundedAmount;
	}

	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getRemindSmsSendStatus() {
		return this.remindSmsSendStatus;
	}

	public void setRemindSmsSendStatus(String remindSmsSendStatus) {
		this.remindSmsSendStatus = remindSmsSendStatus;
	}

	public Date getResourceAmpleTime() {
		return this.resourceAmpleTime;
	}

	public void setResourceAmpleTime(Date resourceAmpleTime) {
		this.resourceAmpleTime = resourceAmpleTime;
	}

	public String getResourceStatus() {
		return this.resourceStatus;
	}

	public void setResourceStatus(String resourceStatus) {
		this.resourceStatus = resourceStatus;
	}

	public String getSendContractFlag() {
		return this.sendContractFlag;
	}

	public void setSendContractFlag(String sendContractFlag) {
		this.sendContractFlag = sendContractFlag;
	}

	public String getSmsLvmamaFlag() {
		return this.smsLvmamaFlag;
	}

	public void setSmsLvmamaFlag(String smsLvmamaFlag) {
		this.smsLvmamaFlag = smsLvmamaFlag;
	}

	public BigDecimal getStartDistrictId() {
		return this.startDistrictId;
	}

	public void setStartDistrictId(BigDecimal startDistrictId) {
		this.startDistrictId = startDistrictId;
	}

	public BigDecimal getSubCategoryId() {
		return this.subCategoryId;
	}

	public void setSubCategoryId(BigDecimal subCategoryId) {
		this.subCategoryId = subCategoryId;
	}

	public String getSupplierApiFlag() {
		return this.supplierApiFlag;
	}

	public void setSupplierApiFlag(String supplierApiFlag) {
		this.supplierApiFlag = supplierApiFlag;
	}

	public BigDecimal getTag() {
		return this.tag;
	}

	public void setTag(BigDecimal tag) {
		this.tag = tag;
	}

	public String getTravellerDelayFlag() {
		return this.travellerDelayFlag;
	}

	public void setTravellerDelayFlag(String travellerDelayFlag) {
		this.travellerDelayFlag = travellerDelayFlag;
	}

	public String getTravellerLockFlag() {
		return this.travellerLockFlag;
	}

	public void setTravellerLockFlag(String travellerLockFlag) {
		this.travellerLockFlag = travellerLockFlag;
	}

	public String getUserId() {
		return this.userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public BigDecimal getUserNo() {
		return this.userNo;
	}

	public void setUserNo(BigDecimal userNo) {
		this.userNo = userNo;
	}

	public String getValidStatus() {
		return this.validStatus;
	}

	public void setValidStatus(String validStatus) {
		this.validStatus = validStatus;
	}

	public String getViewOrderStatus() {
		return this.viewOrderStatus;
	}

	public void setViewOrderStatus(String viewOrderStatus) {
		this.viewOrderStatus = viewOrderStatus;
	}

	public Date getVisitTime() {
		return this.visitTime;
	}

	public void setVisitTime(Date visitTime) {
		this.visitTime = visitTime;
	}

	public Date getWaitPaymentTime() {
		return this.waitPaymentTime;
	}

	public void setWaitPaymentTime(Date waitPaymentTime) {
		this.waitPaymentTime = waitPaymentTime;
	}

}