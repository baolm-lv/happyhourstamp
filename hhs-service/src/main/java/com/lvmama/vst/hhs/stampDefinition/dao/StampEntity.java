package com.lvmama.vst.hhs.stampDefinition.dao;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;


/**
 * The persistent class for the stamp database table.
 * 
 */
@Entity
@Table(name = "stamp")
@NamedQuery(name="StampEntity.findAll", query="SELECT s FROM StampEntity s")
public class StampEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private String id;

	@Column(name="CREATE_DATE")
	private Timestamp createDate;

	@Column(name="CUSTOMER_ID")
	private String customerId;

	private String name;

	@Column(name="SERIAL_NUMBER")
	private String serialNumber;

	@Column(name="STAMP_DEFINITION_ID")
	private String stampDefinitionId;

	@Column(name="STAMP_STATUS")
	private String stampStatus;

	@Column(name="UPDATE_DATE")
	private Timestamp updateDate;
	
	@Column(name="ORDER_ID")
	private String orderId;
	
	@Column(name="USE_ORDER_ID")
	private String useOrderId;
	
	@Column(name="USE_ORDER_ITEM_ID")
	private String useOrderItemId;
	
	@Column(name="USE_ORDER_CATEGORY_ID")
	private String useOrderCategoryId;
	
	@Column(name="REFUND_ORDER_ID")
	private String refundOrderId;
	
	private Integer price;
	
	@Column(name="EXPIRED_DATE")
	private Timestamp expiredDate;

	public StampEntity() {
	}

	public String getUseOrderCategoryId() {
		return useOrderCategoryId;
	}

	public void setUseOrderCategoryId(String useOrderCategoryId) {
		this.useOrderCategoryId = useOrderCategoryId;
	}

	public Integer getPrice() {
		return price;
	}

	public void setPrice(Integer price) {
		this.price = price;
	}

	public Timestamp getExpiredDate() {
		return expiredDate;
	}

	public void setExpiredDate(Timestamp expiredDate) {
		this.expiredDate = expiredDate;
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

	public String getCustomerId() {
		return customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSerialNumber() {
		return serialNumber;
	}

	public void setSerialNumber(String serialNumber) {
		this.serialNumber = serialNumber;
	}

	public String getStampDefinitionId() {
		return stampDefinitionId;
	}

	public void setStampDefinitionId(String stampDefinitionId) {
		this.stampDefinitionId = stampDefinitionId;
	}

	public String getStampStatus() {
		return stampStatus;
	}

	public void setStampStatus(String stampStatus) {
		this.stampStatus = stampStatus;
	}

	public Timestamp getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Timestamp updateDate) {
		this.updateDate = updateDate;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

    public String getUseOrderId() {
        return useOrderId;
    }

    public void setUseOrderId(String useOrderId) {
        this.useOrderId = useOrderId;
    }

    public String getUseOrderItemId() {
        return useOrderItemId;
    }

    public void setUseOrderItemId(String useOrderItemId) {
        this.useOrderItemId = useOrderItemId;
    }

    public String getRefundOrderId() {
        return refundOrderId;
    }

    public void setRefundOrderId(String refundOrderId) {
        this.refundOrderId = refundOrderId;
    }

	

}