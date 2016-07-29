package com.lvmama.vst.hhs.stampDefinition.dao;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;


/**
 * The persistent class for the stamp_definition database table.
 * 
 */
@Entity
@Table(name="stamp_definition")
@NamedQuery(name="StampDefinition.findAll", query="SELECT s FROM StampDefinitionEntity s")
public class StampDefinitionEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private String id;

	@Column(name="ACTIVITY_STATUS")
	private String activityStatus;

	@Column(name="ASSOCIATED_PROD_AVAIL_TIMESLOT")
	private String associatedProdAvailTimeslot;

	@Column(name="BALANCE_DUE_IN_HOUR")
	private int balanceDueInHour;

	@Column(name="CREATE_DATE")
	private Timestamp createDate;

	private String description;

	@Column(name="DOWN_PAYMENT")
	private BigDecimal downPayment;

	private String name;

	@Column(name="PRINT_PRICE")
	private BigDecimal printPrice;

	@Column(name="PRODUCT_MANAGER_ID")
	private String productManagerId;

	private String remarks;

	@Column(name="REMIND_CUSTOMER_DATE")
	private Timestamp remindCustomerDate;

	@Column(name="RULE_RESTRICT")
	private String ruleRestrict;

	@Column(name="SALE_PRICE")
	private BigDecimal salePrice;

	@Column(name="SET_PRICE")
	private BigDecimal setPrice;

	@Column(name="STAMP_ONSALE_END_DATE")
	private Timestamp stampOnsaleEndDate;

	@Column(name="STAMP_ONSALE_START_DATE")
	private Timestamp stampOnsaleStartDate;

	@Column(name="STAMP_REDEEMABLE_END_DATE")
	private Timestamp stampRedeemableEndDate;

	@Column(name="STAMP_REDEEMABLE_START_DATE")
	private Timestamp stampRedeemableStartDate;

	@Column(name="UPDATE_DATE")
	private Timestamp updateDate;
	   
	@Column(name="STAMP_NO")
	private String stampNo;
	
	@Column(name="BUY_MAX")
	private Integer buyMax;

	@OneToOne(fetch = FetchType.EAGER, mappedBy = "stampDefinition")
	private PresaleStampDefinitionProductBindingEntity productBinding;
	
//	@OneToMany(fetch = FetchType.LAZY, mappedBy = "stampDefinition")
//	private List<PresaleStampDefinitionGoodsBindingEntity> goodsBindings;
	
	@Transient
	private String goodsId;
	@Transient
	private String goodsName;
	
	@Column(name="OPERATIONS_AMOUNT")
    private BigDecimal operationsAmount;	
	
	@Column(name="STAMP_REDEEMABLEL_TIMESLOT")
    private String stampRedeemablelTimeslot;
    
    @Column(name="REMIND_CUSTOMER_TIMESLOT")
    private String remindCustomerTimeslot;
    
    @Column(name="STAMP_ORDER")
    private BigDecimal stampOrder ;

	public StampDefinitionEntity() {
	}

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getActivityStatus() {
		return this.activityStatus;
	}

	public void setActivityStatus(String activityStatus) {
		this.activityStatus = activityStatus;
	}

	public String getAssociatedProdAvailTimeslot() {
		return this.associatedProdAvailTimeslot;
	}

	public void setAssociatedProdAvailTimeslot(String associatedProdAvailTimeslot) {
		this.associatedProdAvailTimeslot = associatedProdAvailTimeslot;
	}

	public int getBalanceDueInHour() {
		return this.balanceDueInHour;
	}

	public void setBalanceDueInHour(int balanceDueInHour) {
		this.balanceDueInHour = balanceDueInHour;
	}

	public Timestamp getCreateDate() {
		return this.createDate;
	}

	public void setCreateDate(Timestamp createDate) {
		this.createDate = createDate;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public BigDecimal getDownPayment() {
		return this.downPayment;
	}

	public void setDownPayment(BigDecimal downPayment) {
		this.downPayment = downPayment;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public BigDecimal getPrintPrice() {
		return this.printPrice;
	}

	public void setPrintPrice(BigDecimal printPrice) {
		this.printPrice = printPrice;
	}

	public String getProductManagerId() {
		return this.productManagerId;
	}

	public void setProductManagerId(String productManagerId) {
		this.productManagerId = productManagerId;
	}

	public String getRemarks() {
		return this.remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public Timestamp getRemindCustomerDate() {
		return this.remindCustomerDate;
	}

	public void setRemindCustomerDate(Timestamp remindCustomerDate) {
		this.remindCustomerDate = remindCustomerDate;
	}

	public String getRuleRestrict() {
		return this.ruleRestrict;
	}

	public void setRuleRestrict(String ruleRestrict) {
		this.ruleRestrict = ruleRestrict;
	}

	public BigDecimal getSalePrice() {
		return this.salePrice;
	}

	public void setSalePrice(BigDecimal salePrice) {
		this.salePrice = salePrice;
	}

	public BigDecimal getSetPrice() {
		return this.setPrice;
	}

	public void setSetPrice(BigDecimal setPrice) {
		this.setPrice = setPrice;
	}

	public Timestamp getStampOnsaleEndDate() {
		return this.stampOnsaleEndDate;
	}

	public void setStampOnsaleEndDate(Timestamp stampOnsaleEndDate) {
		this.stampOnsaleEndDate = stampOnsaleEndDate;
	}

	public Timestamp getStampOnsaleStartDate() {
		return this.stampOnsaleStartDate;
	}

	public void setStampOnsaleStartDate(Timestamp stampOnsaleStartDate) {
		this.stampOnsaleStartDate = stampOnsaleStartDate;
	}

	public Timestamp getStampRedeemableEndDate() {
		return this.stampRedeemableEndDate;
	}

	public void setStampRedeemableEndDate(Timestamp stampRedeemableEndDate) {
		this.stampRedeemableEndDate = stampRedeemableEndDate;
	}

	public Timestamp getStampRedeemableStartDate() {
		return this.stampRedeemableStartDate;
	}

	public void setStampRedeemableStartDate(Timestamp stampRedeemableStartDate) {
		this.stampRedeemableStartDate = stampRedeemableStartDate;
	}

	public Timestamp getUpdateDate() {
		return this.updateDate;
	}

	public void setUpdateDate(Timestamp updateDate) {
		this.updateDate = updateDate;
	}
    
	public String getStampNo() {
        return stampNo;
    }

    public void setStampNo(String stampNo) {
        this.stampNo = stampNo;
    }

//	public List<PresaleStampDefinitionGoodsBindingEntity> getGoodsBindings() {
//		return goodsBindings;
//	}
//
//	public void setGoodsBindings(List<PresaleStampDefinitionGoodsBindingEntity> goodsBindings) {
//		this.goodsBindings = goodsBindings;
//	}

    public Integer getBuyMax() {
        return buyMax;
    }

    public void setBuyMax(Integer buyMax) {
        this.buyMax = buyMax;
    }

	public PresaleStampDefinitionProductBindingEntity getProductBinding() {
		return productBinding;
	}

	public void setProductBinding(PresaleStampDefinitionProductBindingEntity productBinding) {
		this.productBinding = productBinding;
	}

    public String getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(String goodsId) {
        this.goodsId = goodsId;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public BigDecimal getOperationsAmount() {
        return operationsAmount;
    }

    public void setOperationsAmount(BigDecimal operationsAmount) {
        this.operationsAmount = operationsAmount;
    }

    public String getStampRedeemablelTimeslot() {
        return stampRedeemablelTimeslot;
    }

    public void setStampRedeemablelTimeslot(String stampRedeemablelTimeslot) {
        this.stampRedeemablelTimeslot = stampRedeemablelTimeslot;
    }

    public String getRemindCustomerTimeslot() {
        return remindCustomerTimeslot;
    }

    public void setRemindCustomerTimeslot(String remindCustomerTimeslot) {
        this.remindCustomerTimeslot = remindCustomerTimeslot;
    }

    public BigDecimal getStampOrder() {
        return stampOrder;
    }

    public void setStampOrder(BigDecimal stampOrder) {
        this.stampOrder = stampOrder;
    }

}