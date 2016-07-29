package com.lvmama.vst.hhs.stampDefinition.dao;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 * The persistent class for the presale_stamp_definition_product_binding database table.
 * 
 */
@Entity
@Table(name = "presale_stamp_definition_product_binding")
@NamedQuery(
        name = "PresaleStampDefinitionProductBinding.findAll",
        query = "SELECT p FROM PresaleStampDefinitionProductBindingEntity p")
public class PresaleStampDefinitionProductBindingEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @Column(name = "CATEGORY_ID")
    private String categoryId;

    @Column(name = "CREATE_DATE")
    private Timestamp createDate;

    @Column(name = "EFFECT_DATE")
    private Timestamp effectDate;

    @Column(name = "EXPIRY_DATE")
    private Timestamp expiryDate;

    @Column(name = "PRODUCT_ID")
    private String productId;
    
    @Column(name="PRODUCT_NAME")
    private String productName;
    
    @Column(name="MANAGER_ID")
    private String managerId;
    
    @Column(name="MANAGER_NAME")
    private String managerName;
    
    @Column(name="BU_CODE")
    private String buCode;

    @Column(name = "UPDATE_DATE")
    private Timestamp updateDate;
    
    @Column(name = "DEPART_ID")
    private String departId;
    
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "STAMP_DEFINITION_ID")
    private StampDefinitionEntity stampDefinition;
   
    @Column(name = "SUB_CATEGORY_ID")
    private String subCategoryId;

    
    public PresaleStampDefinitionProductBindingEntity() {
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCategoryId() {
        return this.categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public Timestamp getCreateDate() {
        return this.createDate;
    }

    public void setCreateDate(Timestamp createDate) {
        this.createDate = createDate;
    }

    public Timestamp getEffectDate() {
        return this.effectDate;
    }

    public void setEffectDate(Timestamp effectDate) {
        this.effectDate = effectDate;
    }

    public Timestamp getExpiryDate() {
        return this.expiryDate;
    }

    public void setExpiryDate(Timestamp expiryDate) {
        this.expiryDate = expiryDate;
    }

    public String getProductId() {
        return this.productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public Timestamp getUpdateDate() {
        return this.updateDate;
    }

    public void setUpdateDate(Timestamp updateDate) {
        this.updateDate = updateDate;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getManagerId() {
        return managerId;
    }

    public void setManagerId(String managerId) {
        this.managerId = managerId;
    }

    public String getManagerName() {
        return managerName;
    }

    public void setManagerName(String managerName) {
        this.managerName = managerName;
    }

    public String getBuCode() {
        return buCode;
    }

    public void setBuCode(String buCode) {
        this.buCode = buCode;
    }

	public StampDefinitionEntity getStampDefinition() {
		return stampDefinition;
	}

	public void setStampDefinition(StampDefinitionEntity stampDefinition) {
		this.stampDefinition = stampDefinition;
	}

	public String getDepartId() {
		return departId;
	}

	public void setDepartId(String departId) {
		this.departId = departId;
	}

    public String getSubCategoryId() {
        return subCategoryId;
    }

    public void setSubCategoryId(String subCategoryId) {
        this.subCategoryId = subCategoryId;
    }
    
	
}