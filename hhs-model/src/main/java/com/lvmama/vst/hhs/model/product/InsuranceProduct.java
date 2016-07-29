/**
 * 
 */
package com.lvmama.vst.hhs.model.product;

import java.util.List;

/**
 * @author fengyonggang
 *
 */
public class InsuranceProduct {

	private Long productId;
	private String productName;
	private List<InsuranceGoods> goods;
	
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
	public List<InsuranceGoods> getGoods() {
		return goods;
	}
	public void setGoods(List<InsuranceGoods> goods) {
		this.goods = goods;
	}
}
