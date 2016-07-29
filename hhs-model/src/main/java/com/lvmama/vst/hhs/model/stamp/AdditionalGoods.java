package com.lvmama.vst.hhs.model.stamp;

import java.io.Serializable;
import java.util.List;

import com.lvmama.vst.hhs.model.product.BranchPropVO;

public class AdditionalGoods implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private Long productBranchId;
	private String branchName;
	private Long goodsId;
	private String goodsName;
	private Long price;
	private String relationType; //AMOUNT: 等量, OPTION: 可选, OPTIONAL: 任选
	private String visitTime;
	private List<BranchPropVO> props;
	
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
	public Long getPrice() {
		return price;
	}
	public void setPrice(Long price) {
		this.price = price;
	}
	public String getRelationType() {
		return relationType;
	}
	public void setRelationType(String relationType) {
		this.relationType = relationType;
	}
	public List<BranchPropVO> getProps() {
		return props;
	}
	public void setProps(List<BranchPropVO> props) {
		this.props = props;
	}
	public String getVisitTime() {
		return visitTime;
	}
	public void setVisitTime(String visitTime) {
		this.visitTime = visitTime;
	}
}
