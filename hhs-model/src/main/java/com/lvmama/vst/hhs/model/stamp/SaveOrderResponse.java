package com.lvmama.vst.hhs.model.stamp;

import java.io.Serializable;
import java.util.List;

public class SaveOrderResponse implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String orderId;
	
    private List<StampCodePrice> list;


	public String getOrderId() {
		return orderId;
	}

	public List<StampCodePrice> getList() {
		return list;
	}

	public void setList(List<StampCodePrice> list) {
		this.list = list;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	
}
