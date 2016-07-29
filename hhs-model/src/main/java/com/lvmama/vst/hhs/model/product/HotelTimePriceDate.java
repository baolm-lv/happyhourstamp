/**
 * 
 */
package com.lvmama.vst.hhs.model.product;

import java.io.Serializable;

/**
 * @author fengyonggang
 *
 */
public class HotelTimePriceDate implements Serializable {

	private static final long serialVersionUID = 1L;
	private String date;
	private String status; // valid, invalid
	private Long stock;
	private Long price;

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Long getStock() {
		return stock;
	}

	public void setStock(Long stock) {
		this.stock = stock;
	}

	public Long getPrice() {
		return price;
	}

	public void setPrice(Long price) {
		this.price = price;
	}

}
