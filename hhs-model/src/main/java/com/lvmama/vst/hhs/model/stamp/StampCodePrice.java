package com.lvmama.vst.hhs.model.stamp;

import java.io.Serializable;

public class StampCodePrice implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	String code;//券码
	Long price;//券的价格
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public Long getPrice() {
		return price;
	}
	public void setPrice(Long price) {
		this.price = price;
	}

}
