package com.lvmama.vst.hhs.model.product;

import java.io.Serializable;

import com.lvmama.vst.hhs.model.admin.BizCategoryVo;

public class ProdProductModel implements Serializable{

    /**
     * 
     */
    private static final long serialVersionUID = -6890328815845799006L;
    
    private Long productId;
    
    private String productName;
    
    private Long bizCategoryId;
    
    private Long subCategoryId;//子品类ID
    
    private BizCategoryVo bizCategory;
    
    private BizCategoryVo subCategory;//子品类  

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

  

    public Long getBizCategoryId() {
        return bizCategoryId;
    }

    public void setBizCategoryId(Long bizCategoryId) {
        this.bizCategoryId = bizCategoryId;
    }

    public Long getSubCategoryId() {
        return subCategoryId;
    }

    public void setSubCategoryId(Long subCategoryId) {
        this.subCategoryId = subCategoryId;
    }

	public BizCategoryVo getBizCategory() {
		return bizCategory;
	}

	public void setBizCategory(BizCategoryVo bizCategory) {
		this.bizCategory = bizCategory;
	}

	public BizCategoryVo getSubCategory() {
		return subCategory;
	}

	public void setSubCategory(BizCategoryVo subCategory) {
		this.subCategory = subCategory;
	}

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }
    
    
}
