package com.lvmama.vst.hhs.stampDefinition.dao;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 * The persistent class for the stamp database table.
 * 
 */
@Entity
@Table(name = "stamp_operate_log")
@NamedQuery(name = "StampOperationLogEntity.findAll", query = "SELECT s FROM StampOperationLogEntity s")
public class StampOperationLogEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private String id;

	@Column(name = "CREATE_DATE")
	private Timestamp createDate;
	/**
	 * use,cancelUse,refundApply,refundSuccess,refundFail,invalid,expired
	 */
	@Column(name = "OPERATE_TYPE")
	private String operateType;
	/**
	 * orderId / refundOrderId
	 */
	@Column(name = "OBJECT_ID")
	private String objectId;
	@Column(name = "STAMP_CODE")
	private String stampCode;

	private String remark;

	public StampOperationLogEntity() {
	}
	
	public StampOperationLogEntity(String operateType, String objectId, String stampCode, String remark) {
		super();
		this.id = UUID.randomUUID().toString();
		this.createDate = new Timestamp(System.currentTimeMillis());
		this.operateType = operateType;
		this.objectId = objectId;
		this.stampCode = stampCode;
		this.remark = remark;
	}

	public StampOperationLogEntity(String id, Timestamp createDate, String operateType, String objectId,
			String stampCode, String remark) {
		super();
		this.id = id;
		this.createDate = createDate;
		this.operateType = operateType;
		this.objectId = objectId;
		this.stampCode = stampCode;
		this.remark = remark;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
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

	public String getOperateType() {
		return operateType;
	}

	public void setOperateType(String operateType) {
		this.operateType = operateType;
	}

	public String getObjectId() {
		return objectId;
	}

	public void setObjectId(String objectId) {
		this.objectId = objectId;
	}

	public String getStampCode() {
		return stampCode;
	}

	public void setStampCode(String stampCode) {
		this.stampCode = stampCode;
	}

}