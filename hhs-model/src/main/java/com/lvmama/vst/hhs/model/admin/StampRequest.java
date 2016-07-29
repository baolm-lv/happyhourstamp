package com.lvmama.vst.hhs.model.admin;

import java.io.Serializable;

public class StampRequest implements Serializable {

    /**
     * stamp 查询
     */
    private static final long serialVersionUID = 9150200338988138960L;
   
    private String productId;
    private String productName;
    private String goodsId;
    private String goodsName;
    private String stampNo;
    private String stampName;
    private String categroyId;
    private String managerName;
    private String managerId;
    public String getProductId() {
        return productId;
    }
    public void setProductId(String productId) {
        this.productId = productId;
    }
    public String getProductName() {
        return productName;
    }
    public void setProductName(String productName) {
        this.productName = productName;
    }
    public String getGoodsId() {
        return goodsId;
    }
    public void setGoodsId(String goodsId) {
        this.goodsId = goodsId;
    }
    public String getGoodsName() {
        return goodsName;
    }
    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }
    public String getStampNo() {
        return stampNo;
    }
    public void setStampNo(String stampNo) {
        this.stampNo = stampNo;
    }
    public String getStampName() {
        return stampName;
    }
    public void setStampName(String stampName) {
        this.stampName = stampName;
    }
    public String getCategroyId() {
        return categroyId;
    }
    public void setCategroyId(String categroyId) {
        this.categroyId = categroyId;
    }
    public String getManagerName() {
        return managerName;
    }
    public void setManagerName(String managerName) {
        this.managerName = managerName;
    }
    public String getManagerId() {
        return managerId;
    }
    public void setManagerId(String managerId) {
        this.managerId = managerId;
    }
    
    
    
    

}
