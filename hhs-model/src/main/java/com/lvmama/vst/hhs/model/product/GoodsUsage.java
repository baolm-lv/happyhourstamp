package com.lvmama.vst.hhs.model.product;

public class GoodsUsage {

	private Long goodsId;
	private int amount;

	public Long getGoodsId() {
		return goodsId;
	}

	public void setGoodsId(Long goodsId) {
		this.goodsId = goodsId;
	}

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

	@Override
	public String toString() {
		return "GoodsUsage [goodsId=" + goodsId + ", amount=" + amount + "]";
	}

}
