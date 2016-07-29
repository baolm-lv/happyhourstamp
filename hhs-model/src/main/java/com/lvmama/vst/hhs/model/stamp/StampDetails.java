package com.lvmama.vst.hhs.model.stamp;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import com.lvmama.vst.hhs.model.common.StampDuration;
import com.lvmama.vst.hhs.model.product.ProductSaleType;

public class StampDetails implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private String id;
	private String activityStatus;					//可售状态
	private String stampNo;		
	private int balanceDueInHour;					//尾款最后支付时间
	private Timestamp createDate;				
	private String description;						//描述
	private Long downPayment;						//首付金额
	private String name;							
	private Long printPrice;						//票面价
	private String productManagerId;				//产品经理ID
	private String productManagerName;				//产品经理名称
	private String remarks;							//费用包含
	private Date remindCustomerDate;			//崔兑换提醒时间
	private String ruleRestrict;					//退改规则
	private Long salePrice;						//销售价
	private Long setPrice;						//结算价
	private StampDuration stampOnsaleDuration;		//售卖时间
	private StampDuration stampRedeemableDuration;	//兑换时间
	private Timestamp updateDate;
	private int inventoryLevel;			 			//库存
	private StampProduct boundMerchant; 			//绑定产品
	private int buyMax;					 			//最大购买数量	
	private List<StampDuration> associatedProdAvailTimeSlot;//可兑换的产品时间
	private boolean onSale;							//是否可售
	private List<StampGoods> goods; 				//主规格商品
//	private List<ChangeHotel> changeHotels;			//可换酒店
	private ProductSaleType saleType;				//按份卖 or 按人卖
	private List<StampDuration> stampRedeemablelTimeslot;//兑换的产品时间
	
	public Long getDownPayment() {
		return downPayment;
	}
	public void setDownPayment(Long downPayment) {
		this.downPayment = downPayment;
	}
	public Long getPrintPrice() {
		return printPrice;
	}
	public void setPrintPrice(Long printPrice) {
		this.printPrice = printPrice;
	}
	public Long getSalePrice() {
		return salePrice;
	}
	public void setSalePrice(Long salePrice) {
		this.salePrice = salePrice;
	}
	public Long getSetPrice() {
		return setPrice;
	}
	public void setSetPrice(Long setPrice) {
		this.setPrice = setPrice;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getActivityStatus() {
		return activityStatus;
	}
	public void setActivityStatus(String activityStatus) {
		this.activityStatus = activityStatus;
	}
	public String getStampNo() {
		return stampNo;
	}
	public void setStampNo(String stampNo) {
		this.stampNo = stampNo;
	}
	public int getBalanceDueInHour() {
		return balanceDueInHour;
	}
	public void setBalanceDueInHour(int balanceDueInHour) {
		this.balanceDueInHour = balanceDueInHour;
	}
	public Timestamp getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Timestamp createDate) {
		this.createDate = createDate;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getProductManagerId() {
		return productManagerId;
	}
	public void setProductManagerId(String productManagerId) {
		this.productManagerId = productManagerId;
	}
	public String getProductManagerName() {
		return productManagerName;
	}
	public void setProductManagerName(String productManagerName) {
		this.productManagerName = productManagerName;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public Date getRemindCustomerDate() {
		return remindCustomerDate;
	}
	public void setRemindCustomerDate(Date remindCustomerDate) {
		this.remindCustomerDate = remindCustomerDate;
	}
	public String getRuleRestrict() {
		return ruleRestrict;
	}
	public void setRuleRestrict(String ruleRestrict) {
		this.ruleRestrict = ruleRestrict;
	}
	public StampDuration getStampOnsaleDuration() {
		return stampOnsaleDuration;
	}
	public void setStampOnsaleDuration(StampDuration stampOnsaleDuration) {
		this.stampOnsaleDuration = stampOnsaleDuration;
	}
	public StampDuration getStampRedeemableDuration() {
		return stampRedeemableDuration;
	}
	public void setStampRedeemableDuration(StampDuration stampRedeemableDuration) {
		this.stampRedeemableDuration = stampRedeemableDuration;
	}
	public Timestamp getUpdateDate() {
		return updateDate;
	}
	public void setUpdateDate(Timestamp updateDate) {
		this.updateDate = updateDate;
	}
	public int getInventoryLevel() {
		return inventoryLevel;
	}
	public void setInventoryLevel(int inventoryLevel) {
		this.inventoryLevel = inventoryLevel;
	}
	public StampProduct getBoundMerchant() {
		return boundMerchant;
	}
	public void setBoundMerchant(StampProduct boundMerchant) {
		this.boundMerchant = boundMerchant;
	}
	public int getBuyMax() {
		return buyMax;
	}
	public void setBuyMax(int buyMax) {
		this.buyMax = buyMax;
	}
	public List<StampDuration> getAssociatedProdAvailTimeSlot() {
		return associatedProdAvailTimeSlot;
	}
	public void setAssociatedProdAvailTimeSlot(List<StampDuration> associatedProdAvailTimeSlot) {
		this.associatedProdAvailTimeSlot = associatedProdAvailTimeSlot;
	}
	public boolean isOnSale() {
		return onSale;
	}
	public void setOnSale(boolean onSale) {
		this.onSale = onSale;
	}

    public List<StampGoods> getGoods() {
        return goods;
    }
    public void setGoods(List<StampGoods> goods) {
        this.goods = goods;
    }

	public ProductSaleType getSaleType() {
		return saleType;
	}
	public void setSaleType(ProductSaleType saleType) {
		this.saleType = saleType;
	}
    public List<StampDuration> getStampRedeemablelTimeslot() {
        return stampRedeemablelTimeslot;
    }
    public void setStampRedeemablelTimeslot(List<StampDuration> stampRedeemablelTimeslot) {
        this.stampRedeemablelTimeslot = stampRedeemablelTimeslot;
    }
	
	
	
	
}
