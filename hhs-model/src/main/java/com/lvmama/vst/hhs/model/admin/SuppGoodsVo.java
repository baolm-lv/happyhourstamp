/**
 * 
 */
package com.lvmama.vst.hhs.model.admin;

import java.io.Serializable;

/**
 * @author fengyonggang
 *
 */
public class SuppGoodsVo implements Serializable {

	private static final long serialVersionUID = 1L;
	private Long productId;
	private String productName;
	private Long productBranchId;
	private String branchName;
	private Long goodsId;
	private String goodsName;
	private String peopleFlag;//CHILD, AUDLT
	private String categoryId;
	private Long  audlt;
	private Long child;
	private TransportPointVo transportPoint; 
	
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
	public String getPeopleFlag() {
		return peopleFlag;
	}
	public void setPeopleFlag(String peopleFlag) {
		this.peopleFlag = peopleFlag;
	}
    public String getCategoryId() {
        return categoryId;
    }
    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }
    public Long getAudlt() {
        return audlt;
    }
    public void setAudlt(Long audlt) {
        this.audlt = audlt;
    }
    public Long getChild() {
        return child;
    }
    public void setChild(Long child) {
        this.child = child;
    }
    public TransportPointVo getTransportPoint() {
        return transportPoint;
    }
    public void setTransportPoint(TransportPointVo transportPoint) {
        this.transportPoint = transportPoint;
    }
    
    
  
	
	
}
