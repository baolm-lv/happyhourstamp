package com.lvmama.vst.hhs.model.admin;

import java.io.Serializable;

public class StampGoodsVo implements Serializable{

	private static final long serialVersionUID = 1L;
	private String id;
	private String goodsId;
	private String goodsName;
	private String branchType;
	private String categoryId;
	
	public StampGoodsVo() {}
	
	public StampGoodsVo(String goodsId, String goodsName, String branchType,String categoryId) {
		this.goodsId = goodsId;
		this.goodsName = goodsName;
		this.branchType = branchType;
		this.categoryId = categoryId;
	}
	
	public StampGoodsVo(String id, String goodsId, String goodsName, String branchType,String categoryId) {
		this.id = id;
		this.goodsId = goodsId;
		this.goodsName = goodsName;
		this.branchType = branchType;
		this.categoryId = categoryId;
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
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
	public String getBranchType() {
		return branchType;
	}
	public void setBranchType(String branchType) {
		this.branchType = branchType;
	}

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }
	
	
}
