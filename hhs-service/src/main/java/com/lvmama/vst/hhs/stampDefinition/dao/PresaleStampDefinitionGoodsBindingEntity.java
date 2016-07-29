package com.lvmama.vst.hhs.stampDefinition.dao;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;


/**
 * The persistent class for the presale_stamp_definition_goods_binding database table.
 * 
 */
@Entity
@Table(name="presale_stamp_definition_goods_binding")
@NamedQuery(name="PresaleStampDefinitionGoodsBindingEntity.findAll", query="SELECT p FROM PresaleStampDefinitionGoodsBindingEntity p")
public class PresaleStampDefinitionGoodsBindingEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private String id;

	@Column(name="BRANCH_FLAG")
	private String branchFlag;

	@Column(name="CREATE_DATE")
	private Timestamp createDate;

	@Column(name="GOODS_ID")
	private String goodsId;

	@Column(name="GOODS_NAME")
	private String goodsName;

//	@Column(name="STAMP_DEFINITION_ID")
//	private String stampDefinitionId;

	@Column(name="UPDATE_DATE")
	private Timestamp updateDate;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "STAMP_DEFINITION_ID")
	private StampDefinitionEntity stampDefinition;
	
	@Column(name="CATEGORY_ID")
	private String categoryId;

	public PresaleStampDefinitionGoodsBindingEntity() {
	}

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getBranchFlag() {
		return this.branchFlag;
	}

	public void setBranchFlag(String branchFlag) {
		this.branchFlag = branchFlag;
	}

	public Timestamp getCreateDate() {
		return this.createDate;
	}

	public void setCreateDate(Timestamp createDate) {
		this.createDate = createDate;
	}

	public String getGoodsId() {
		return this.goodsId;
	}

	public void setGoodsId(String goodsId) {
		this.goodsId = goodsId;
	}

	public String getGoodsName() {
		return this.goodsName;
	}

	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}

	public Timestamp getUpdateDate() {
		return this.updateDate;
	}

	public void setUpdateDate(Timestamp updateDate) {
		this.updateDate = updateDate;
	}

	public StampDefinitionEntity getStampDefinition() {
		return stampDefinition;
	}

	public void setStampDefinition(StampDefinitionEntity stampDefinition) {
		this.stampDefinition = stampDefinition;
	}

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }
	
	

}