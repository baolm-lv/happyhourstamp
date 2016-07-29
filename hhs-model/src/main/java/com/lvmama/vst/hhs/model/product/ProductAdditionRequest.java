/**
 * 
 */
package com.lvmama.vst.hhs.model.product;

import java.io.Serializable;
import java.util.List;

/**
 * @author fengyonggang
 *
 */
public class ProductAdditionRequest implements Serializable {

	private static final long serialVersionUID = 1L;
	private List<StampUsage> stamps;
	private String visitTime;
	private AdditionBuyQuantity additionBuy;//追加购买
	private Long startDistinctId; 
	public List<StampUsage> getStamps() {
		return stamps;
	}

	public void setStamps(List<StampUsage> stamps) {
		this.stamps = stamps;
	}

	public String getVisitTime() {
		return visitTime;
	}

	public void setVisitTime(String visitTime) {
		this.visitTime = visitTime;
	}

	public AdditionBuyQuantity getAdditionBuy() {
		return additionBuy;
	}

	public void setAdditionBuy(AdditionBuyQuantity additionBuy) {
		this.additionBuy = additionBuy;
	}


	public Long getStartDistinctId() {
		return startDistinctId;
	}

	public void setStartDistinctId(Long startDistinctId) {
		this.startDistinctId = startDistinctId;
	}
}
