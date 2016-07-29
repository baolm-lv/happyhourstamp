package com.lvmama.vst.hhs.product.dao;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;


/**
 * The persistent class for the BIZ_CATEGORY database table.
 * 
 */
@Entity
@Table(name="BIZ_CATEGORY")
@NamedQuery(name="BizCategoryEntity.findAll", query="SELECT b FROM BizCategoryEntity b")
public class BizCategoryEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="CATEGORY_ID")
	private long categoryId;

	@Column(name="CANCEL_FLAG")
	private String cancelFlag;

	@Column(name="CATEGORY_CODE")
	private String categoryCode;

	@Column(name="CATEGORY_DESC")
	private String categoryDesc;

	@Column(name="CATEGORY_NAME")
	private String categoryName;

	@Column(name="PARENT_ID")
	private BigDecimal parentId;

	@Column(name="PROCESS_KEY")
	private String processKey;

	@Column(name="PROM_TARGET")
	private String promTarget;

	private BigDecimal seq;

	public BizCategoryEntity() {
	}

	public long getCategoryId() {
		return this.categoryId;
	}

	public void setCategoryId(long categoryId) {
		this.categoryId = categoryId;
	}

	public String getCancelFlag() {
		return this.cancelFlag;
	}

	public void setCancelFlag(String cancelFlag) {
		this.cancelFlag = cancelFlag;
	}

	public String getCategoryCode() {
		return this.categoryCode;
	}

	public void setCategoryCode(String categoryCode) {
		this.categoryCode = categoryCode;
	}

	public String getCategoryDesc() {
		return this.categoryDesc;
	}

	public void setCategoryDesc(String categoryDesc) {
		this.categoryDesc = categoryDesc;
	}

	public String getCategoryName() {
		return this.categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public BigDecimal getParentId() {
		return this.parentId;
	}

	public void setParentId(BigDecimal parentId) {
		this.parentId = parentId;
	}

	public String getProcessKey() {
		return this.processKey;
	}

	public void setProcessKey(String processKey) {
		this.processKey = processKey;
	}

	public String getPromTarget() {
		return this.promTarget;
	}

	public void setPromTarget(String promTarget) {
		this.promTarget = promTarget;
	}

	public BigDecimal getSeq() {
		return this.seq;
	}

	public void setSeq(BigDecimal seq) {
		this.seq = seq;
	}

}