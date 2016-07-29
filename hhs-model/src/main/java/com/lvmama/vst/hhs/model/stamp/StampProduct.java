package com.lvmama.vst.hhs.model.stamp;

import java.io.Serializable;

import com.lvmama.vst.hhs.model.common.StampDuration;

public class StampProduct implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private String categoryId;
	private String categoryName;
	private String productId;
	private String productName;
	private StampDuration effectDuration;
    private String managerId;
    private String managerName;
    private String subCategoryId;
    private String departId;
    
	
	public String getCategoryId() {
		return categoryId;
	}


	public void setCategoryId(String categoryId) {
		this.categoryId = categoryId;
	}


	public String getCategoryName() {
		return categoryName;
	}


	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}


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


	public StampDuration getEffectDuration() {
		return effectDuration;
	}


	public void setEffectDuration(StampDuration effectDuration) {
		this.effectDuration = effectDuration;
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
	
	public String getSubCategoryId() {
        return subCategoryId;
    }

    public void setSubCategoryId(String subCategoryId) {
        this.subCategoryId = subCategoryId;
    }

    public String getDepartId() {
        return departId;
    }

    public void setDepartId(String departId) {
        this.departId = departId;
    }


    @Override
	public String toString() {
		return "BoundMerchant [categoryId=" + categoryId + ", categoryName=" + categoryName + ", productId="
				+ productId + ", productName=" + productName + ", effectDuration=" + effectDuration + "]";
	}
}
