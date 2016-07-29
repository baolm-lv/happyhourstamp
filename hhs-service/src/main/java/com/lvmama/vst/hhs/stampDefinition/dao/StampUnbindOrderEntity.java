package com.lvmama.vst.hhs.stampDefinition.dao;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;

/**
 * The persistent class for the stamp database table.
 * 
 */
@Entity
@Table(name = "stamp_unbind_order")
@NamedQuery(name = "StampUnbindOrderEntity.findAll", query = "SELECT s FROM StampUnbindOrderEntity s")
public class StampUnbindOrderEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private String id;

	@Column(name = "REFUND_ID")
	private String refundId;

	@Column(name="USE_ORDER_ID")
	private String useOrderId;
	
	@Column(name="USE_ORDER_ITEM_ID")
	private String useOrderItemId;
	
	@Column(name="STAMP_CODE")
	private String stampCode;
	
	@Column(name = "CREATE_DATE")
	private Timestamp createDate;

	public StampUnbindOrderEntity() {
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

	public String getRefundId() {
		return refundId;
	}

	public void setRefundId(String refundId) {
		this.refundId = refundId;
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

	public String getStampCode() {
		return stampCode;
	}

	public void setStampCode(String stampCode) {
		this.stampCode = stampCode;
	}

}