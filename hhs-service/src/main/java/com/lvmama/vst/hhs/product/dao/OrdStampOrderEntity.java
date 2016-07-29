package com.lvmama.vst.hhs.product.dao;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;


/**
 * The persistent class for the ORD_STAMP_ORDER database table.
 * 
 */
@Entity
@Table(name="ORD_STAMP_ORDER")
@NamedQuery(name="OrdStampOrderEntity.findAll", query="SELECT o FROM OrdStampOrderEntity o")
public class OrdStampOrderEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="ORDER_ID")
	private long orderId;

	@Column(name="CREATE_DATE")
	private Timestamp createDate;

	@Column(name="STAMP_ORDER_CLASSIFICATION")
	private String stampOrderClassification;

	@Column(name="UPDATE_DATE")
	private Timestamp updateDate;

	@Column(name="USER_ID")
	private String userId;

	@Column(name="USER_NO")
	private String userNo;
	
	@Column(name="PAY_TYPE")
	private String payType;	
	
	@Column(name="DOWN_PAYMENT")
	private Long downPayment;
	
	@Column(name="DOWN_DATE")
	private Timestamp downDate;
	
	@Column(name="BALANCE_DUE_WAIT_DATE")
	private Timestamp balanceDueWaitDate;
	
	@Column(name="REMIND_CUSTOMER_DATE")
	private Timestamp remindCustomerDate;
	
	@Column(name="REMIND_CUSTOMER_TIMESLOT")
	private String remindCustomerTimeslot;

	public OrdStampOrderEntity() {
	}

    public long getOrderId() {
        return orderId;
    }

    public void setOrderId(long orderId) {
        this.orderId = orderId;
    }

    public Timestamp getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Timestamp createDate) {
        this.createDate = createDate;
    }

    public String getStampOrderClassification() {
        return stampOrderClassification;
    }

    public void setStampOrderClassification(String stampOrderClassification) {
        this.stampOrderClassification = stampOrderClassification;
    }

    public Timestamp getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Timestamp updateDate) {
        this.updateDate = updateDate;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserNo() {
        return userNo;
    }

    public void setUserNo(String userNo) {
        this.userNo = userNo;
    }

    public String getPayType() {
        return payType;
    }

    public void setPayType(String payType) {
        this.payType = payType;
    }

    public Long getDownPayment() {
        return downPayment;
    }

    public void setDownPayment(Long downPayment) {
        this.downPayment = downPayment;
    }

    public Timestamp getDownDate() {
        return downDate;
    }

    public void setDownDate(Timestamp downDate) {
        this.downDate = downDate;
    }

    public Timestamp getBalanceDueWaitDate() {
        return balanceDueWaitDate;
    }

    public void setBalanceDueWaitDate(Timestamp balanceDueWaitDate) {
        this.balanceDueWaitDate = balanceDueWaitDate;
    }

    public Timestamp getRemindCustomerDate() {
        return remindCustomerDate;
    }

    public void setRemindCustomerDate(Timestamp remindCustomerDate) {
        this.remindCustomerDate = remindCustomerDate;
    }

    public String getRemindCustomerTimeslot() {
        return remindCustomerTimeslot;
    }

    public void setRemindCustomerTimeslot(String remindCustomerTimeslot) {
        this.remindCustomerTimeslot = remindCustomerTimeslot;
    }

}