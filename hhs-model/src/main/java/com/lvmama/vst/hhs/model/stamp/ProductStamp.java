package com.lvmama.vst.hhs.model.stamp;

import java.io.Serializable;
import java.util.List;

public class ProductStamp implements Serializable {

	private static final long serialVersionUID = 1L;

	private List<StampDetails> stamp; 					// 券信息
	private List<AdditionalGoods> additionGoods; 	// 附加

	public List<StampDetails> getStamp() {
		return stamp;
	}

	public void setStamp(List<StampDetails> stamp) {
		this.stamp = stamp;
	}

	public List<AdditionalGoods> getAdditionGoods() {
		return additionGoods;
	}

	public void setAdditionGoods(List<AdditionalGoods> additionGoods) {
		this.additionGoods = additionGoods;
	}

}
