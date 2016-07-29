/**
 * 
 */
package com.lvmama.vst.hhs.model.stamp;

import java.io.Serializable;

/**
 * @author fengyonggang
 *
 */
public class StampOrderPayment implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String payType;//
	private Long price;
	
	public String getPayType() {
		return payType;
	}
	public void setPayType(String payType) {
		this.payType = payType;
	}
	public Long getPrice() {
		return price;
	}
	public void setPrice(Long price) {
		this.price = price;
	}
	@Override
	public String toString() {
		return "StampOrderPayment [payType=" + payType + ", price=" + price + "]";
	}
	
}
