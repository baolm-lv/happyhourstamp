package com.lvmama.vst.hhs.stampDefinition.dao;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;

/**
 * The persistent class for the stamp database table.
 * 
 */
@Entity
@Table(name = "stamp_refund_order")
@NamedQuery(name = "StampRefundOrderEntity.findAll", query = "SELECT s FROM StampRefundOrderEntity s")
public class StampRefundOrderEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private String id;

	@Column(name = "REFUND_APPLY_ID")
	private String refundApplyId;

	@Column(name = "REFUND_ID")
	private String refundId;

	@Column(name = "ORDER_ID")
	private String orderId;

	@Column(name = "USER_ID")
	private String userId;

	@Column(name = "REFUND_NUM")
	private Integer refundNum;

	@Column(name = "STAMP_AMOUNT")
	private Long stampAmount;

	@Column(name = "REFUND_AMOUNT")
	private Long refundAmount;
	
	@Column(name = "REFUND_STATUS")
	private String refundStatus;
	
	@Column(name = "CREATE_DATE")
	private Timestamp createDate;
	
	@Column(name = "UPDATE_DATE")
	private Timestamp updateDate;

	public StampRefundOrderEntity() {
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Timestamp getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Timestamp createDate) {
		this.createDate = createDate;
	}

	public Timestamp getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Timestamp updateDate) {
		this.updateDate = updateDate;
	}

	public String getRefundApplyId() {
		return refundApplyId;
	}

	public void setRefundApplyId(String refundApplyId) {
		this.refundApplyId = refundApplyId;
	}

	public String getRefundId() {
		return refundId;
	}

	public void setRefundId(String refundId) {
		this.refundId = refundId;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public Integer getRefundNum() {
		return refundNum;
	}

	public void setRefundNum(Integer refundNum) {
		this.refundNum = refundNum;
	}

	public Long getStampAmount() {
		return stampAmount;
	}

	public void setStampAmount(Long stampAmount) {
		this.stampAmount = stampAmount;
	}

	public Long getRefundAmount() {
		return refundAmount;
	}

	public void setRefundAmount(Long refundAmount) {
		this.refundAmount = refundAmount;
	}

	public String getRefundStatus() {
		return refundStatus;
	}

	public void setRefundStatus(String refundStatus) {
		this.refundStatus = refundStatus;
	}

}