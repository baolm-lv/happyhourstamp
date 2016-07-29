/**
 * 
 */
package com.lvmama.vst.hhs.model.product;

import java.io.Serializable;
import java.util.List;

/**
 * @author fengyonggang
 *
 */
public class SuppGoods implements Serializable {

	private static final long serialVersionUID = 1L;
	private Long productId;
	private String productName;
	private Long productBranchId;
	private String branchName;
	private Long goodsId;
	private String goodsName;
	private String visitTime;
	private Integer amount;
	private Integer audltQuantity;
	private Integer childQuantity;
	private String validPeriod; //有效期
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
	public String getVisitTime() {
		return visitTime;
	}
	public void setVisitTime(String visitTime) {
		this.visitTime = visitTime;
	}
	public Integer getAmount() {
		return amount == null ? 0 : amount;
	}
	public void setAmount(Integer amount) {
		this.amount = amount;
	}
	public Integer getAudltQuantity() {
		return audltQuantity == null ? 0 : audltQuantity;
	}
	public void setAudltQuantity(Integer audltQuantity) {
		this.audltQuantity = audltQuantity;
	}
	public Integer getChildQuantity() {
		return childQuantity == null ? 0 : childQuantity;
	}
	public void setChildQuantity(Integer childQuantity) {
		this.childQuantity = childQuantity;
	}
	public List<BranchPropVO> getProps() {
		return props;
	}
	public void setProps(List<BranchPropVO> props) {
		this.props = props;
	}
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
	public String getValidPeriod() {
		return validPeriod;
	}
	public void setValidPeriod(String validPeriod) {
		this.validPeriod = validPeriod;
	}
}
