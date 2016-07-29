package com.lvmama.vst.hhs.model.admin;

import java.io.Serializable;
import java.util.List;

public class PackageProductVo implements Serializable{

    /**
     * 
     */
    private static final long serialVersionUID = -3333053939814906352L;
    private Long productId;
    private String productName;
    private List<PackageProductBranchVo> productBranches;
    private List<ChangeHotelVo> changeHoteles;
    private List<PackageChangeHotelGroupVo> changeHotelGroup;
    public Long getProductId() {
        return productId;
    }
    public void setProductId(Long productId) {
        this.productId = productId;
    }
    public String getProductName() {
        return productName;
    }
    public void setProductName(String productName) {
        this.productName = productName;
    }
    public List<PackageProductBranchVo> getProductBranches() {
        return productBranches;
    }
    public void setProductBranches(List<PackageProductBranchVo> productBranches) {
        this.productBranches = productBranches;
    }
    public List<ChangeHotelVo> getChangeHoteles() {
        return changeHoteles;
    }
    public void setChangeHoteles(List<ChangeHotelVo> changeHoteles) {
        this.changeHoteles = changeHoteles;
    }
    public List<PackageChangeHotelGroupVo> getChangeHotelGroup() {
        return changeHotelGroup;
    }
    public void setChangeHotelGroup(List<PackageChangeHotelGroupVo> changeHotelGroup) {
        this.changeHotelGroup = changeHotelGroup;
    }
    
    
    
    

}
