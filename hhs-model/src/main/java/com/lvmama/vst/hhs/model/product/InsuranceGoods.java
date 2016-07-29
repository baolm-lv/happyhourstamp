/**
 * 
 */
package com.lvmama.vst.hhs.model.product;

import java.util.List;

/**
 * @author fengyonggang
 *
 */
public class InsuranceGoods {

	private Long productBranchId;
	private String branchName;
	private Long goodsId;
	private String goodsName;
	private String quantityRange;
	private Long price;
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
	public String getQuantityRange() {
		return quantityRange;
	}
	public void setQuantityRange(String quantityRange) {
		this.quantityRange = quantityRange;
	}
	public List<BranchPropVO> getProps() {
		return props;
	}
	public void setProps(List<BranchPropVO> props) {
		this.props = props;
	}
	public Long getPrice() {
		return price;
	}
	public void setPrice(Long price) {
		this.price = price;
	}
	
}
