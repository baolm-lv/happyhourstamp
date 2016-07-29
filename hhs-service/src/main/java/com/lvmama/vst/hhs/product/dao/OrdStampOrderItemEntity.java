package com.lvmama.vst.hhs.product.dao;

import java.io.Serializable;

import javax.persistence.*;

import java.math.BigDecimal;
import java.util.Date;


/**
 * The persistent class for the ORD_STAMP_ORDER_ITEM database table.
 * 
 */
@Entity
@Table(name="ORD_STAMP_ORDER_ITEM")
@NamedQuery(name="OrdStampOrderItemEntity.findAll", query="SELECT o FROM OrdStampOrderItemEntity o")
public class OrdStampOrderItemEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="ORDER_ITEM_ID")
	private long orderItemId;

	@Temporal(TemporalType.DATE)
	@Column(name="CREATE_DATE")
	private Date createDate;

	private BigDecimal quantity;

	@Column(name="STAMP_DEFINITION_ID")
	private String stampDefinitionId;

	@Column(name="STAMP_NAME")
	private String stampName;

	@Temporal(TemporalType.DATE)
	@Column(name="UPDATE_DATE")
	private Date updateDate;

	@Column(name="USER_ID")
	private String userId;

	@Column(name="USER_NO")
	private String userNo;
	
	@Column(name="ORDER_ID")
	private long orderId;
	
	@Column(name="SUBSIDY_AMOUNT")
    private BigDecimal subsidyAmount;

	public OrdStampOrderItemEntity() {
	}

	public long getOrderId() {
		return orderId;
	}

	public void setOrderId(long orderId) {
		this.orderId = orderId;
	}

	public long getOrderItemId() {
		return orderItemId;
	}

	public void setOrderItemId(long orderItemId) {
		this.orderItemId = orderItemId;
	}

	public Date getCreateDate() {
		return this.createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public BigDecimal getQuantity() {
		return this.quantity;
	}

	public void setQuantity(BigDecimal quantity) {
		this.quantity = quantity;
	}

	public String getStampDefinitionId() {
		return this.stampDefinitionId;
	}

	public void setStampDefinitionId(String stampDefinitionId) {
		this.stampDefinitionId = stampDefinitionId;
	}

	public String getStampName() {
		return this.stampName;
	}

	public void setStampName(String stampName) {
		this.stampName = stampName;
	}

	public Date getUpdateDate() {
		return this.updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	public String getUserId() {
		return this.userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserNo() {
		return this.userNo;
	}

	public void setUserNo(String userNo) {
		this.userNo = userNo;
	}

    public BigDecimal getSubsidyAmount() {
        return subsidyAmount;
    }

    public void setSubsidyAmount(BigDecimal subsidyAmount) {
        this.subsidyAmount = subsidyAmount;
    }
	

}