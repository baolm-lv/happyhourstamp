package com.lvmama.vst.hhs.model.admin;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.lvmama.vst.hhs.model.common.Constant.ActivityStatus;
import com.lvmama.vst.hhs.model.common.StampDuration;

public class StampCreationRequest implements Serializable {

	/**
	 *  Luocheng Tang
	 */
	private static final long serialVersionUID = 1L;

	private String id;
	private String stampName;
	private Double printPrice;
	private Double downpayment;
	private Double salePrice;
	private Double setPrice;
	private Double operationsAmount;
	private int inventoryLevel;
	private ActivityStatus activityStatus;
	private StampDuration stampOnsaleDuration;
	private StampDuration stampRedeemableDuration;
	private Date remindCustomerDate;
	private int balancesDueInHour;
	private List<StampDuration> associatedProdAvailTimeSlot;
	private String remarks;
	private String productManagerId;
	private String productManagerName;
	private String ruleRestrict;
	private Integer productId;
	// second page of bound merchant
	private BoundMerchantVo boundMerchant;
	private Integer buyMax;//最大购买数量
	private String selectDates;
	private String startDate;
	private String endDate;
	private StampAssociatedProdAvailTimeSlotVo prodAvailTimeSlot;
	private int initInventoryLevel;
	private String  operationName;
	private StampAssociatedProdAvailTimeSlotVo stampRedeemablelTimeslotVo; //提醒
	private StampAssociatedProdAvailTimeSlotVo remindCustomerTimeslotVo;//兑换
	private Integer stampOrder;
	
	
	public StampAssociatedProdAvailTimeSlotVo getProdAvailTimeSlot() {
		return prodAvailTimeSlot;
	}
	public void setProdAvailTimeSlot(StampAssociatedProdAvailTimeSlotVo prodAvailTimeSlot) {
		this.prodAvailTimeSlot = prodAvailTimeSlot;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getStampName() {
		return stampName;
	}
	public void setStampName(String stampName) {
		this.stampName = stampName;
	}
	
	
	public Double getDownpayment() {
        return downpayment;
    }
    public void setDownpayment(Double downpayment) {
        this.downpayment = downpayment;
    }
   
	public int getInventoryLevel() {
		return inventoryLevel;
	}
	public void setInventoryLevel(int inventoryLevel) {
		this.inventoryLevel = inventoryLevel;
	}
	public ActivityStatus getActivityStatus() {
		return activityStatus;
	}
	public void setActivityStatus(ActivityStatus activityStatus) {
		this.activityStatus = activityStatus;
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
	public Date getRemindCustomerDate() {
		return remindCustomerDate;
	}
	public void setRemindCustomerDate(Date remindCustomerDate) {
		this.remindCustomerDate = remindCustomerDate;
	}
	public int getBalancesDueInHour() {
		return balancesDueInHour;
	}
	public void setBalancesDueInHour(int balancesDueInHour) {
		this.balancesDueInHour = balancesDueInHour;
	}
	public List<StampDuration> getAssociatedProdAvailTimeSlot() {
		return associatedProdAvailTimeSlot;
	}
	public void setAssociatedProdAvailTimeSlot(List<StampDuration> associatedProdAvailTimeSlot) {
		this.associatedProdAvailTimeSlot = associatedProdAvailTimeSlot;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public String getProductManagerId() {
		return productManagerId;
	}
	public void setProductManagerId(String productManagerId) {
		this.productManagerId = productManagerId;
	}
	public String getRuleRestrict() {
		return ruleRestrict;
	}
	public void setRuleRestrict(String ruleRestrict) {
		this.ruleRestrict = ruleRestrict;
	}
	public BoundMerchantVo getBoundMerchant() {
		return boundMerchant;
	}
	public void setBoundMerchant(BoundMerchantVo boundMerchant) {
		this.boundMerchant = boundMerchant;
	}
	
	public String getProductManagerName() {
        return productManagerName;
    }
    public void setProductManagerName(String productManagerName) {
        this.productManagerName = productManagerName;
    }
    
    public Integer getProductId() {
        return productId;
    }
    public void setProductId(Integer productId) {
        this.productId = productId;
    }
    
    public Integer getBuyMax() {
        return buyMax;
    }
    public void setBuyMax(Integer buyMax) {
        this.buyMax = buyMax;
    }
    public String getStartDate() {
        return startDate;
    }
    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }
    public String getEndDate() {
        return endDate;
    }
    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }
    public String getSelectDates() {
        return selectDates;
    }
    public void setSelectDates(String selectDates) {
        this.selectDates = selectDates;
    }
    
    public int getInitInventoryLevel() {
        return initInventoryLevel;
    }
    public void setInitInventoryLevel(int initInventoryLevel) {
        this.initInventoryLevel = initInventoryLevel;
    }
    
    
    public Double getPrintPrice() {
        return printPrice;
    }
    public void setPrintPrice(Double printPrice) {
        this.printPrice = printPrice;
    }
    public Double getSalePrice() {
        return salePrice;
    }
    public void setSalePrice(Double salePrice) {
        this.salePrice = salePrice;
    }
    public Double getSetPrice() {
        return setPrice;
    }
    public void setSetPrice(Double setPrice) {
        this.setPrice = setPrice;
    }
    
    public Double getOperationsAmount() {
        return operationsAmount;
    }
    public void setOperationsAmount(Double operationsAmount) {
        this.operationsAmount = operationsAmount;
    }
    
    
    public String getOperationName() {
        return operationName;
    }
    public void setOperationName(String operationName) {
        this.operationName = operationName;
    }   
  
    public StampAssociatedProdAvailTimeSlotVo getStampRedeemablelTimeslotVo() {
        return stampRedeemablelTimeslotVo;
    }
    public void setStampRedeemablelTimeslotVo(StampAssociatedProdAvailTimeSlotVo stampRedeemablelTimeslotVo) {
        this.stampRedeemablelTimeslotVo = stampRedeemablelTimeslotVo;
    }
    public StampAssociatedProdAvailTimeSlotVo getRemindCustomerTimeslotVo() {
        return remindCustomerTimeslotVo;
    }
    public void setRemindCustomerTimeslotVo(StampAssociatedProdAvailTimeSlotVo remindCustomerTimeslotVo) {
        this.remindCustomerTimeslotVo = remindCustomerTimeslotVo;
    }
    public Integer getStampOrder() {
        return stampOrder;
    }
    public void setStampOrder(Integer stampOrder) {
        this.stampOrder = stampOrder;
    }
    @Override
	public String toString() {
		return "StampCreationRequest [id=" + id + "stampName=" + stampName + ", printPrice=" + printPrice + ", downpayment="
				+ downpayment + ", salePrice=" + salePrice + ", setPrice=" + setPrice + ", inventoryLevel="
				+ inventoryLevel + ", activityStatus=" + activityStatus + ", stampOnsaleDuration=" + stampOnsaleDuration
				+ ", stampRedeemableDuration=" + stampRedeemableDuration + ", remindCustomerDate=" + remindCustomerDate
				+ ", balancesDueInHour=" + balancesDueInHour + ", associatedProdAvailTimeSlot="
				+ associatedProdAvailTimeSlot + ", remarks=" + remarks + ", productManagerId=" + productManagerId
				+ ", ruleRestrict=" + ruleRestrict + ", boundMerchant=" + boundMerchant + "]";
	}
	
} 