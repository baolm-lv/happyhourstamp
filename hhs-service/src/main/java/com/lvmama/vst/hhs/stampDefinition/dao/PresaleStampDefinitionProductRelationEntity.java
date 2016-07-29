package com.lvmama.vst.hhs.stampDefinition.dao;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.*;



/**
 * The persistent class for the presale_stamp_definition_product_binding database table.
 * 
 */
@Entity
@Table(name = "presale_stamp_definition_product_relation")
@NamedQuery(
        name = "PresaleStampDefinitionProductRelationEntity.findAll",
        query = "SELECT p FROM PresaleStampDefinitionProductRelationEntity p")
public class PresaleStampDefinitionProductRelationEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @Column(name = "CATEGORY_ID")
    private String categoryId;

    @Column(name = "CREATE_DATE")
    private Date createDate;
    
    @Column(name = "PRODUCT_BRANCH_ID")
    private String productBranchId;

    @Column(name = "GOODS_ID")
    private String goodsId; 

    @Column(name = "PRODUCT_ID")
    private String productId;

    @Column(name = "STAMP_DEFINITION_ID")
    private String stampDefinitionId;

    @Column(name = "UPDATE_DATE")
    private Date updateDate;

    public PresaleStampDefinitionProductRelationEntity() {
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

    public String getGoodsId() {
        return this.goodsId;
    }

    public void setGoodsId(String goodsId) {
        this.goodsId = goodsId;
    }

   

    public String getProductId() {
        return this.productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getStampDefinitionId() {
        return this.stampDefinitionId;
    }

    public void setStampDefinitionId(String stampDefinitionId) {
        this.stampDefinitionId = stampDefinitionId;
    }

  
    public String getProductBranchId() {
        return productBranchId;
    }

    public void setProductBranchId(String productBranchId) {
        this.productBranchId = productBranchId;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date date) {
        this.createDate = date;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }
    
    

}