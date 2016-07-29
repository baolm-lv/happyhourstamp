package com.lvmama.vst.hhs.model.admin;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import com.lvmama.vst.hhs.model.common.StampDuration;

public class StampDetailsVo implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private String id;
	private String activityStatus;					//可售状态
	private String stampNo;		
	private int balanceDueInHour;					//尾款最后支付时间
	private Timestamp createDate;				
	private String description;						//描述
	private Double downPayment;						//首付金额
	private String name;							
	private Double printPrice;						//票面价
	private String productManagerId;				//产品经理ID
	private String productManagerName;				//产品经理名称
	private String remarks;							//费用包含
	private Date remindCustomerDate;			//崔兑换提醒时间
	private String ruleRestrict;					//退改规则
	private Double salePrice;						//建议销售价
	private Double setPrice;						//结算价
	private StampDuration stampOnsaleDuration;		//售卖时间
	private StampDuration stampRedeemableDuration;	//兑换时间
	private Timestamp updateDate;
	private int inventoryLevel;			 			//库存
	private int initInventoryLevel;                 //初始化库存
	private BoundMerchantVo boundMerchant; 			//绑定产品
	private List<StampGoodsVo> stampGoods; 			//绑定商品
	private int buyMax;					 			//最大购买数量	
//	private List<StampDurationVo> associatedProdAvailTimeSlot;//可兑换的产品时间
	
	private StampAssociatedProdAvailTimeSlotVo prodAvailTimeSlot;
	private boolean canSale;
	private String goodsName;
	private String goodsId;	
	private StampGoodsRelationVo  stampGoodsRelation;
	private Double operationsAmount ;
	private StampAssociatedProdAvailTimeSlotVo stampRedeemablelTimeslotVo; //兑换
    private StampAssociatedProdAvailTimeSlotVo remindCustomerTimeslotVo;//提醒
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
	public BoundMerchantVo getBoundMerchant() {
		return boundMerchant;
	}
	public void setBoundMerchant(BoundMerchantVo boundMerchant) {
		this.boundMerchant = boundMerchant;
	}
	public List<StampGoodsVo> getStampGoods() {
		return stampGoods;
	}
	public void setStampGoods(List<StampGoodsVo> stampGoods) {
		this.stampGoods = stampGoods;
	}
	public int getBuyMax() {
		return buyMax;
	}
	public void setBuyMax(int buyMax) {
		this.buyMax = buyMax;
	}
//	public List<StampDurationVo> getAssociatedProdAvailTimeSlot() {
//		return associatedProdAvailTimeSlot;
//	}
//	public void setAssociatedProdAvailTimeSlot(List<StampDurationVo> associatedProdAvailTimeSlot) {
//		this.associatedProdAvailTimeSlot = associatedProdAvailTimeSlot;
//	}
	public boolean isCanSale() {
		return canSale;
	}
	public void setCanSale(boolean canSale) {
		this.canSale = canSale;
	}
    public String getGoodsName() {
        return goodsName;
    }
    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }
    public String getGoodsId() {
        return goodsId;
    }
    public void setGoodsId(String goodsId) {
        this.goodsId = goodsId;
    }
    public int getInitInventoryLevel() {
        return initInventoryLevel;
    }
    public void setInitInventoryLevel(int initInventoryLevel) {
        this.initInventoryLevel = initInventoryLevel;
    }
    public StampGoodsRelationVo getStampGoodsRelation() {
        return stampGoodsRelation;
    }
    public void setStampGoodsRelation(StampGoodsRelationVo stampGoodsRelation) {
        this.stampGoodsRelation = stampGoodsRelation;
    }
    public Double getDownPayment() {
        return downPayment;
    }
    public void setDownPayment(Double downPayment) {
        this.downPayment = downPayment;
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
    
    
    
	
	
    

}
