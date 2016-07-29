package com.lvmama.vst.hhs.model.admin;

import java.io.Serializable;

public class StampGoodsRelationVo implements Serializable{

    /**
     * 
     */
    private static final long serialVersionUID = -2842741498060512045L;
    
    private String productId;
    private String productname;
    private String goodsId;
    private String goodsName;
    private String productBranchId;
    public String getProductId() {
        return productId;
    }
    public void setProductId(String productId) {
        this.productId = productId;
    }
    public String getProductname() {
        return productname;
    }
    public void setProductname(String productname) {
        this.productname = productname;
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
    public String getProductBranchId() {
        return productBranchId;
    }
    public void setProductBranchId(String productBranchId) {
        this.productBranchId = productBranchId;
    }
    
    

}
