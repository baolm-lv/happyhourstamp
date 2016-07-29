/**
 * 
 */
package com.lvmama.vst.hhs.model.product;

/**
 * @author fengyonggang
 *
 */
public class PriceGap {

	private long price;
	private int maxQuantity;
	private int minQuantity;

	public long getPrice() {
		return price;
	}

	public void setPrice(long price) {
		this.price = price;
	}

	public int getMaxQuantity() {
		return maxQuantity;
	}

	public void setMaxQuantity(int maxQuantity) {
		this.maxQuantity = maxQuantity;
	}

	public int getMinQuantity() {
		return minQuantity;
	}

	public void setMinQuantity(int minQuantity) {
		this.minQuantity = minQuantity;
	}
}
