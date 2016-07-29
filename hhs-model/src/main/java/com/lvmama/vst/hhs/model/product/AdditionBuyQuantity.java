/**
 * 
 */
package com.lvmama.vst.hhs.model.product;

/**
 * @author fengyonggang
 *
 */
public class AdditionBuyQuantity {

	private int audltQuantity;//成人数 按人卖
	private int childQuantity;//儿童数 按人卖
	private int quantity;//份数 按份卖
	public int getAudltQuantity() {
		return audltQuantity;
	}
	public void setAudltQuantity(int audltQuantity) {
		this.audltQuantity = audltQuantity;
	}
	public int getChildQuantity() {
		return childQuantity;
	}
	public void setChildQuantity(int childQuantity) {
		this.childQuantity = childQuantity;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	@Override
	public String toString() {
		return "AdditionBuyQuantity [audltQuantity=" + audltQuantity + ", childQuantity=" + childQuantity
				+ ", quantity=" + quantity + "]";
	}
}
