package com.lvmama.vst.hhs.model.product;

import java.io.Serializable;

public class ProdProductAddtionalVO implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long productId;

	private Long lowestMarketPrice;

	private Long lowestSaledPrice;

	private String toTraffic;

	private String backTraffic;

	private String changeTrafficFlag;

	private String hotel;

	private String changeHotelFlag;

	private String saleType;

	private String multiRouteFlag;

	// 送保险
	private String freeInsuranceFlag;

	/**
	 * 返现金额（移动端）
	 */
	private Long mobileRebate;

	/**
	 * 返现金额（PC端）
	 */
	private Long pcRebate;

	/** 二维码地址 **/
	private String qrCodePath;

	/**
	 * 成人最小预定数
	 */
	private Long adultMinQuantity;

	private Long adultMaxQuantity;

	private Long childMinQuantity;

	private Long childMaxQuantity;

	private Long settlementPrice;

	public Long getProductId() {
		return productId;
	}

	public void setProductId(Long productId) {
		this.productId = productId;
	}

	public Long getLowestMarketPrice() {
		return lowestMarketPrice;
	}

	public void setLowestMarketPrice(Long lowestMarketPrice) {
		this.lowestMarketPrice = lowestMarketPrice;
	}

	public Long getLowestSaledPrice() {
		return lowestSaledPrice;
	}

	public void setLowestSaledPrice(Long lowestSaledPrice) {
		this.lowestSaledPrice = lowestSaledPrice;
	}

	public String getToTraffic() {
		return toTraffic;
	}

	public void setToTraffic(String toTraffic) {
		this.toTraffic = toTraffic;
	}

	public String getBackTraffic() {
		return backTraffic;
	}

	public void setBackTraffic(String backTraffic) {
		this.backTraffic = backTraffic;
	}

	public String getChangeTrafficFlag() {
		return changeTrafficFlag;
	}

	public void setChangeTrafficFlag(String changeTrafficFlag) {
		this.changeTrafficFlag = changeTrafficFlag;
	}

	public String getHotel() {
		return hotel;
	}

	public void setHotel(String hotel) {
		this.hotel = hotel;
	}

	public String getChangeHotelFlag() {
		return changeHotelFlag;
	}

	public void setChangeHotelFlag(String changeHotelFlag) {
		this.changeHotelFlag = changeHotelFlag;
	}

	public String getSaleType() {
		return saleType;
	}

	public void setSaleType(String saleType) {
		this.saleType = saleType;
	}

	public String getMultiRouteFlag() {
		return multiRouteFlag;
	}

	public void setMultiRouteFlag(String multiRouteFlag) {
		this.multiRouteFlag = multiRouteFlag;
	}

	public String getFreeInsuranceFlag() {
		return freeInsuranceFlag;
	}

	public void setFreeInsuranceFlag(String freeInsuranceFlag) {
		this.freeInsuranceFlag = freeInsuranceFlag;
	}

	public Long getMobileRebate() {
		return mobileRebate;
	}

	public void setMobileRebate(Long mobileRebate) {
		this.mobileRebate = mobileRebate;
	}

	public Long getPcRebate() {
		return pcRebate;
	}

	public void setPcRebate(Long pcRebate) {
		this.pcRebate = pcRebate;
	}

	public String getQrCodePath() {
		return qrCodePath;
	}

	public void setQrCodePath(String qrCodePath) {
		this.qrCodePath = qrCodePath;
	}

	public Long getAdultMinQuantity() {
		return adultMinQuantity;
	}

	public void setAdultMinQuantity(Long adultMinQuantity) {
		this.adultMinQuantity = adultMinQuantity;
	}

	public Long getAdultMaxQuantity() {
		return adultMaxQuantity;
	}

	public void setAdultMaxQuantity(Long adultMaxQuantity) {
		this.adultMaxQuantity = adultMaxQuantity;
	}

	public Long getChildMinQuantity() {
		return childMinQuantity;
	}

	public void setChildMinQuantity(Long childMinQuantity) {
		this.childMinQuantity = childMinQuantity;
	}

	public Long getChildMaxQuantity() {
		return childMaxQuantity;
	}

	public void setChildMaxQuantity(Long childMaxQuantity) {
		this.childMaxQuantity = childMaxQuantity;
	}

	public Long getSettlementPrice() {
		return settlementPrice;
	}

	public void setSettlementPrice(Long settlementPrice) {
		this.settlementPrice = settlementPrice;
	}
	

}