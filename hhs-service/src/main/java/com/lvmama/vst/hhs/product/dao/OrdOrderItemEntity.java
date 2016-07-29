package com.lvmama.vst.hhs.product.dao;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;


/**
 * The persistent class for the ORD_ORDER_ITEM database table.
 * 
 */
@Entity
@Table(name="ORD_ORDER_ITEM")
@NamedQuery(name="OrdOrderItemEntity.findAll", query="SELECT o FROM OrdOrderItemEntity o")
public class OrdOrderItemEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="ORDER_ITEM_ID")
	private long orderItemId;

	@Column(name="ACTUAL_SETTLEMENT_PRICE")
	private BigDecimal actualSettlementPrice;

	@Column(name="ATTRIBUTION_ID")
	private BigDecimal attributionId;

	@Column(name="BOOK_LIMIT_TYPE")
	private String bookLimitType;

	@Column(name="BRANCH_ID")
	private BigDecimal branchId;

	@Column(name="BU_CODE")
	private String buCode;

	@Column(name="BUYOUT_FLAG")
	private String buyoutFlag;

	@Column(name="BUYOUT_PRICE")
	private BigDecimal buyoutPrice;

	@Column(name="BUYOUT_QUANTITY")
	private BigDecimal buyoutQuantity;

	@Column(name="BUYOUT_TOTAL_PRICE")
	private BigDecimal buyoutTotalPrice;

	@Column(name="CANCEL_CERT_CONFIRM_STATUS")
	private String cancelCertConfirmStatus;

	@Column(name="CANCEL_STRATEGY")
	private String cancelStrategy;

	@Column(name="CATEGORY_ID")
	private BigDecimal categoryId;

	@Column(name="CERT_CONFIRM_STATUS")
	private String certConfirmStatus;

	@Column(name="COMPANY_TYPE")
	private String companyType;

	private String content;

	@Column(name="CONTRACT_ID")
	private BigDecimal contractId;

	@Column(name="COURIER_STATUS")
	private String courierStatus;

	@Temporal(TemporalType.DATE)
	@Column(name="CREATE_TIME")
	private Date createTime;

	@Column(name="DEDUCT_AMOUNT")
	private BigDecimal deductAmount;

	@Column(name="DEDUCT_TYPE")
	private String deductType;

	@Temporal(TemporalType.DATE)
	@Column(name="INFO_PASS_TIME")
	private Date infoPassTime;

	@Column(name="INFO_STATUS")
	private String infoStatus;

	@Column(name="INVOKE_INTERFACE_PF_STATUS")
	private String invokeInterfacePfStatus;

	@Temporal(TemporalType.DATE)
	@Column(name="LAST_CANCEL_TIME")
	private Date lastCancelTime;

	@Column(name="MAIN_ITEM")
	private String mainItem;

	@Column(name="MANAGER_ID")
	private BigDecimal managerId;

	@Column(name="MARKET_PRICE")
	private BigDecimal marketPrice;

	@Column(name="NEED_RESOURCE_CONFIRM")
	private String needResourceConfirm;

	@Column(name="NOTINTIME_FLAG")
	private String notintimeFlag;

	@Column(name="ORDER_ID")
	private BigDecimal orderId;

	@Column(name="ORDER_MEMO")
	private String orderMemo;

	@Column(name="ORDER_PACK_ID")
	private BigDecimal orderPackId;

	@Column(name="ORDER_STATUS")
	private String orderStatus;

	@Column(name="ORDER_SUBTYPE")
	private String orderSubtype;

	@Temporal(TemporalType.DATE)
	@Column(name="ORDER_UPDATE_TIME")
	private Date orderUpdateTime;

	@Column(name="PAYMENT_STATUS")
	private String paymentStatus;

	@Column(name="PERFORM_STATUS")
	private String performStatus;

	@Column(name="PERFORM_STATUS_CODE")
	private String performStatusCode;

	private BigDecimal price;

	@Column(name="PRODUCT_ID")
	private BigDecimal productId;

	@Column(name="PRODUCT_NAME")
	private String productName;

	private BigDecimal quantity;

	@Column(name="REAL_BU_TYPE")
	private String realBuType;

	@Column(name="REFUND_RULES")
	private String refundRules;

	@Temporal(TemporalType.DATE)
	@Column(name="RESOURCE_AMPLE_TIME")
	private Date resourceAmpleTime;

	@Column(name="RESOURCE_STATUS")
	private String resourceStatus;

	@Column(name="SETTLEMENT_PRICE")
	private BigDecimal settlementPrice;

	@Column(name="SETTLEMENT_STATUS")
	private String settlementStatus;

	@Column(name="STOCK_FLAG")
	private String stockFlag;

	@Column(name="SUPP_GOODS_ID")
	private BigDecimal suppGoodsId;

	@Column(name="SUPP_GOODS_NAME")
	private String suppGoodsName;

	@Column(name="SUPP_PRODUCT_NAME")
	private String suppProductName;

	@Column(name="SUPPLIER_ID")
	private BigDecimal supplierId;

	@Column(name="TICKET_TYPE")
	private String ticketType;

	@Column(name="TOTAL_AMOUNT")
	private BigDecimal totalAmount;

	@Column(name="TOTAL_SETTLEMENT_PRICE")
	private BigDecimal totalSettlementPrice;

	@Temporal(TemporalType.DATE)
	@Column(name="VISIT_TIME")
	private Date visitTime;

	public OrdOrderItemEntity() {
	}

	public long getOrderItemId() {
		return this.orderItemId;
	}

	public void setOrderItemId(long orderItemId) {
		this.orderItemId = orderItemId;
	}

	public BigDecimal getActualSettlementPrice() {
		return this.actualSettlementPrice;
	}

	public void setActualSettlementPrice(BigDecimal actualSettlementPrice) {
		this.actualSettlementPrice = actualSettlementPrice;
	}

	public BigDecimal getAttributionId() {
		return this.attributionId;
	}

	public void setAttributionId(BigDecimal attributionId) {
		this.attributionId = attributionId;
	}

	public String getBookLimitType() {
		return this.bookLimitType;
	}

	public void setBookLimitType(String bookLimitType) {
		this.bookLimitType = bookLimitType;
	}

	public BigDecimal getBranchId() {
		return this.branchId;
	}

	public void setBranchId(BigDecimal branchId) {
		this.branchId = branchId;
	}

	public String getBuCode() {
		return this.buCode;
	}

	public void setBuCode(String buCode) {
		this.buCode = buCode;
	}

	public String getBuyoutFlag() {
		return this.buyoutFlag;
	}

	public void setBuyoutFlag(String buyoutFlag) {
		this.buyoutFlag = buyoutFlag;
	}

	public BigDecimal getBuyoutPrice() {
		return this.buyoutPrice;
	}

	public void setBuyoutPrice(BigDecimal buyoutPrice) {
		this.buyoutPrice = buyoutPrice;
	}

	public BigDecimal getBuyoutQuantity() {
		return this.buyoutQuantity;
	}

	public void setBuyoutQuantity(BigDecimal buyoutQuantity) {
		this.buyoutQuantity = buyoutQuantity;
	}

	public BigDecimal getBuyoutTotalPrice() {
		return this.buyoutTotalPrice;
	}

	public void setBuyoutTotalPrice(BigDecimal buyoutTotalPrice) {
		this.buyoutTotalPrice = buyoutTotalPrice;
	}

	public String getCancelCertConfirmStatus() {
		return this.cancelCertConfirmStatus;
	}

	public void setCancelCertConfirmStatus(String cancelCertConfirmStatus) {
		this.cancelCertConfirmStatus = cancelCertConfirmStatus;
	}

	public String getCancelStrategy() {
		return this.cancelStrategy;
	}

	public void setCancelStrategy(String cancelStrategy) {
		this.cancelStrategy = cancelStrategy;
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

	public String getCompanyType() {
		return this.companyType;
	}

	public void setCompanyType(String companyType) {
		this.companyType = companyType;
	}

	public String getContent() {
		return this.content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public BigDecimal getContractId() {
		return this.contractId;
	}

	public void setContractId(BigDecimal contractId) {
		this.contractId = contractId;
	}

	public String getCourierStatus() {
		return this.courierStatus;
	}

	public void setCourierStatus(String courierStatus) {
		this.courierStatus = courierStatus;
	}

	public Date getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public BigDecimal getDeductAmount() {
		return this.deductAmount;
	}

	public void setDeductAmount(BigDecimal deductAmount) {
		this.deductAmount = deductAmount;
	}

	public String getDeductType() {
		return this.deductType;
	}

	public void setDeductType(String deductType) {
		this.deductType = deductType;
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

	public String getInvokeInterfacePfStatus() {
		return this.invokeInterfacePfStatus;
	}

	public void setInvokeInterfacePfStatus(String invokeInterfacePfStatus) {
		this.invokeInterfacePfStatus = invokeInterfacePfStatus;
	}

	public Date getLastCancelTime() {
		return this.lastCancelTime;
	}

	public void setLastCancelTime(Date lastCancelTime) {
		this.lastCancelTime = lastCancelTime;
	}

	public String getMainItem() {
		return this.mainItem;
	}

	public void setMainItem(String mainItem) {
		this.mainItem = mainItem;
	}

	public BigDecimal getManagerId() {
		return this.managerId;
	}

	public void setManagerId(BigDecimal managerId) {
		this.managerId = managerId;
	}

	public BigDecimal getMarketPrice() {
		return this.marketPrice;
	}

	public void setMarketPrice(BigDecimal marketPrice) {
		this.marketPrice = marketPrice;
	}

	public String getNeedResourceConfirm() {
		return this.needResourceConfirm;
	}

	public void setNeedResourceConfirm(String needResourceConfirm) {
		this.needResourceConfirm = needResourceConfirm;
	}

	public String getNotintimeFlag() {
		return this.notintimeFlag;
	}

	public void setNotintimeFlag(String notintimeFlag) {
		this.notintimeFlag = notintimeFlag;
	}

	public BigDecimal getOrderId() {
		return this.orderId;
	}

	public void setOrderId(BigDecimal orderId) {
		this.orderId = orderId;
	}

	public String getOrderMemo() {
		return this.orderMemo;
	}

	public void setOrderMemo(String orderMemo) {
		this.orderMemo = orderMemo;
	}

	public BigDecimal getOrderPackId() {
		return this.orderPackId;
	}

	public void setOrderPackId(BigDecimal orderPackId) {
		this.orderPackId = orderPackId;
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

	public String getPaymentStatus() {
		return this.paymentStatus;
	}

	public void setPaymentStatus(String paymentStatus) {
		this.paymentStatus = paymentStatus;
	}

	public String getPerformStatus() {
		return this.performStatus;
	}

	public void setPerformStatus(String performStatus) {
		this.performStatus = performStatus;
	}

	public String getPerformStatusCode() {
		return this.performStatusCode;
	}

	public void setPerformStatusCode(String performStatusCode) {
		this.performStatusCode = performStatusCode;
	}

	public BigDecimal getPrice() {
		return this.price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public BigDecimal getProductId() {
		return this.productId;
	}

	public void setProductId(BigDecimal productId) {
		this.productId = productId;
	}

	public String getProductName() {
		return this.productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public BigDecimal getQuantity() {
		return this.quantity;
	}

	public void setQuantity(BigDecimal quantity) {
		this.quantity = quantity;
	}

	public String getRealBuType() {
		return this.realBuType;
	}

	public void setRealBuType(String realBuType) {
		this.realBuType = realBuType;
	}

	public String getRefundRules() {
		return this.refundRules;
	}

	public void setRefundRules(String refundRules) {
		this.refundRules = refundRules;
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

	public BigDecimal getSettlementPrice() {
		return this.settlementPrice;
	}

	public void setSettlementPrice(BigDecimal settlementPrice) {
		this.settlementPrice = settlementPrice;
	}

	public String getSettlementStatus() {
		return this.settlementStatus;
	}

	public void setSettlementStatus(String settlementStatus) {
		this.settlementStatus = settlementStatus;
	}

	public String getStockFlag() {
		return this.stockFlag;
	}

	public void setStockFlag(String stockFlag) {
		this.stockFlag = stockFlag;
	}

	public BigDecimal getSuppGoodsId() {
		return this.suppGoodsId;
	}

	public void setSuppGoodsId(BigDecimal suppGoodsId) {
		this.suppGoodsId = suppGoodsId;
	}

	public String getSuppGoodsName() {
		return this.suppGoodsName;
	}

	public void setSuppGoodsName(String suppGoodsName) {
		this.suppGoodsName = suppGoodsName;
	}

	public String getSuppProductName() {
		return this.suppProductName;
	}

	public void setSuppProductName(String suppProductName) {
		this.suppProductName = suppProductName;
	}

	public BigDecimal getSupplierId() {
		return this.supplierId;
	}

	public void setSupplierId(BigDecimal supplierId) {
		this.supplierId = supplierId;
	}

	public String getTicketType() {
		return this.ticketType;
	}

	public void setTicketType(String ticketType) {
		this.ticketType = ticketType;
	}

	public BigDecimal getTotalAmount() {
		return this.totalAmount;
	}

	public void setTotalAmount(BigDecimal totalAmount) {
		this.totalAmount = totalAmount;
	}

	public BigDecimal getTotalSettlementPrice() {
		return this.totalSettlementPrice;
	}

	public void setTotalSettlementPrice(BigDecimal totalSettlementPrice) {
		this.totalSettlementPrice = totalSettlementPrice;
	}

	public Date getVisitTime() {
		return this.visitTime;
	}

	public void setVisitTime(Date visitTime) {
		this.visitTime = visitTime;
	}

}