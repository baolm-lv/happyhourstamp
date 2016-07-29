/**
 * 
 */
package com.lvmama.vst.hhs.model.product;

import java.io.Serializable;
import java.util.List;

import com.lvmama.vst.hhs.model.stamp.AdditionalGoods;

/**
 * @author fengyonggang
 *
 */
public class ProductAddition implements Serializable {

	private static final long serialVersionUID = 1L;
//	private List<StampUsageStatus> stamps;
	private List<AdditionalGoods> additionGoods; 	// 附加
	private List<ChangeHotel> changeHotels;			// 可换酒店
	private PriceGap gapPrice;						// 房差
	private List<InsuranceProduct> insurances;				// 保险

//	public List<StampUsageStatus> getStamps() {
//		return stamps;
//	}
//
//	public void setStamps(List<StampUsageStatus> stamps) {
//		this.stamps = stamps;
//	}

	public List<AdditionalGoods> getAdditionGoods() {
		return additionGoods;
	}

	public void setAdditionGoods(List<AdditionalGoods> additionGoods) {
		this.additionGoods = additionGoods;
	}

	public List<ChangeHotel> getChangeHotels() {
		return changeHotels;
	}

	public void setChangeHotels(List<ChangeHotel> changeHotels) {
		this.changeHotels = changeHotels;
	}

	public PriceGap getGapPrice() {
		return gapPrice;
	}

	public void setGapPrice(PriceGap gapPrice) {
		this.gapPrice = gapPrice;
	}

	public List<InsuranceProduct> getInsurances() {
		return insurances;
	}

	public void setInsurances(List<InsuranceProduct> insurances) {
		this.insurances = insurances;
	}

}
