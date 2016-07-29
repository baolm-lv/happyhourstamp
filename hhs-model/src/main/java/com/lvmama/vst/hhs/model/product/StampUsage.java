package com.lvmama.vst.hhs.model.product;

public class StampUsage {

	private String stampId;
	private int amount;

	public String getStampId() {
		return stampId;
	}

	public void setStampId(String stampId) {
		this.stampId = stampId;
	}

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

	@Override
	public String toString() {
		return "StampUsage [stampId=" + stampId + ", amount=" + amount + "]";
	}

}
