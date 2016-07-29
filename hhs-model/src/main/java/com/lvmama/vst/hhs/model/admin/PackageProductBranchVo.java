package com.lvmama.vst.hhs.model.admin;

import java.io.Serializable;
import java.util.List;

public class PackageProductBranchVo implements Serializable{
    
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private Long productBranchId;
    private String branchName;
    private List<SuppGoodsVo> goods;
    public Long getProductBranchId() {
        return productBranchId;
    }
    public void setProductBranchId(Long productBranchId) {
        this.productBranchId = productBranchId;
    }
    public String getBranchName() {
        return branchName;
    }
    public void setBranchName(String branchName) {
        this.branchName = branchName;
    }
    public List<SuppGoodsVo> getGoods() {
        return goods;
    }
    public void setGoods(List<SuppGoodsVo> goods) {
        this.goods = goods;
    }
    
    
    
    

}
