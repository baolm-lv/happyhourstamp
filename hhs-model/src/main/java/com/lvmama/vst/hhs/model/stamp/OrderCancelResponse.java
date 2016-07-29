package com.lvmama.vst.hhs.model.stamp;

import java.io.Serializable;

public class OrderCancelResponse implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private boolean success;

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}
	
}
