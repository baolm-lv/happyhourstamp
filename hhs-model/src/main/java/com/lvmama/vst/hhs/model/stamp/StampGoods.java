package com.lvmama.vst.hhs.model.stamp;

import java.io.Serializable;

public class StampGoods implements Serializable{

	private static final long serialVersionUID = 1L;
	private Long productBranchId;
	private String branchName;
	private Long goodsId;
	private String goodsName;
	private Long auditPrice;
	private Long childPrice;
	private Long gapPrice; //房差
	private String categoryId;
	
	public StampGoods() {     
    }
	
	public StampGoods(String id, Long goodsId, String goodsName, String branchType,String categoryId) {
        
        this.goodsId = goodsId;
        this.goodsName = goodsName;
        this.categoryId = categoryId;
      
    }
	
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
	public Long getGoodsId() {
		return goodsId;
	}
	public void setGoodsId(Long goodsId) {
		this.goodsId = goodsId;
	}
	public String getGoodsName() {
		return goodsName;
	}
	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}
	public Long getAuditPrice() {
		return auditPrice;
	}
	public void setAuditPrice(Long auditPrice) {
		this.auditPrice = auditPrice;
	}
	public Long getChildPrice() {
		return childPrice;
	}
	public void setChildPrice(Long childPrice) {
		this.childPrice = childPrice;
	}
	public Long getGapPrice() {
		return gapPrice;
	}
	public void setGapPrice(Long gapPrice) {
		this.gapPrice = gapPrice;
	}

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }
	
	
	
}
