/**
 * 
 */
package com.lvmama.vst.hhs.model.admin;

/**
 * @author fengyonggang
 *
 */
public class ProductSaleTypeVo {

	private String saleType; // PEOPLE:按人卖, COPIES:按份卖
	private int adultQuantity; // 按份卖时， 成人数
	private int childQuantity; // 按份卖时， 儿童数

	public String getSaleType() {
		return saleType;
	}

	public void setSaleType(String saleType) {
		this.saleType = saleType;
	}

	public int getAdultQuantity() {
		return adultQuantity;
	}

	public void setAdultQuantity(int adultQuantity) {
		this.adultQuantity = adultQuantity;
	}

	public int getChildQuantity() {
		return childQuantity;
	}

	public void setChildQuantity(int childQuantity) {
		this.childQuantity = childQuantity;
	}

}
