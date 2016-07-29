package com.lvmama.vst.hhs.product.dao;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;


/**
 * The persistent class for the PROD_PRODUCT_ADDTIONAL database table.
 * 
 */
@Entity
@Table(name="PROD_PRODUCT_ADDTIONAL")
@NamedQuery(name="ProdProductAddtionalEntity.findAll", query="SELECT p FROM ProdProductAddtionalEntity p")
public class ProdProductAddtionalEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="PRODUCT_ID")
	private long productId;

	@Column(name="ADULT_MAX_QUANTITY")
	private BigDecimal adultMaxQuantity;

	@Column(name="ADULT_MIN_QUANTITY")
	private BigDecimal adultMinQuantity;

	@Column(name="BACK_TRAFFIC")
	private String backTraffic;

	@Column(name="CHANGE_HOTEL_FLAG")
	private String changeHotelFlag;

	@Column(name="CHANGE_TRAFFIC_FLAG")
	private String changeTrafficFlag;

	@Column(name="CHILD_MAX_QUANTITY")
	private BigDecimal childMaxQuantity;

	@Column(name="CHILD_MIN_QUANTITY")
	private BigDecimal childMinQuantity;

	@Column(name="FREE_INSURANCE_FLAG")
	private String freeInsuranceFlag;

	private String hotel;

	@Column(name="LOWEST_MARKET_PRICE")
	private BigDecimal lowestMarketPrice;

	@Column(name="LOWEST_SALED_PRICE")
	private BigDecimal lowestSaledPrice;

	@Column(name="MOBILE_REBATE")
	private BigDecimal mobileRebate;

	@Column(name="MULTI_ROUTE_FLAG")
	private String multiRouteFlag;

	@Column(name="PC_REBATE")
	private BigDecimal pcRebate;

	@Column(name="QRCODE_PATH")
	private String qrcodePath;

	@Column(name="SALE_TYPE")
	private String saleType;

	@Column(name="SETTLEMENT_PRICE")
	private BigDecimal settlementPrice;

	@Column(name="TO_TRAFFIC")
	private String toTraffic;

	public ProdProductAddtionalEntity() {
	}

	public long getProductId() {
		return this.productId;
	}

	public void setProductId(long productId) {
		this.productId = productId;
	}

	public BigDecimal getAdultMaxQuantity() {
		return this.adultMaxQuantity;
	}

	public void setAdultMaxQuantity(BigDecimal adultMaxQuantity) {
		this.adultMaxQuantity = adultMaxQuantity;
	}

	public BigDecimal getAdultMinQuantity() {
		return this.adultMinQuantity;
	}

	public void setAdultMinQuantity(BigDecimal adultMinQuantity) {
		this.adultMinQuantity = adultMinQuantity;
	}

	public String getBackTraffic() {
		return this.backTraffic;
	}

	public void setBackTraffic(String backTraffic) {
		this.backTraffic = backTraffic;
	}

	public String getChangeHotelFlag() {
		return this.changeHotelFlag;
	}

	public void setChangeHotelFlag(String changeHotelFlag) {
		this.changeHotelFlag = changeHotelFlag;
	}

	public String getChangeTrafficFlag() {
		return this.changeTrafficFlag;
	}

	public void setChangeTrafficFlag(String changeTrafficFlag) {
		this.changeTrafficFlag = changeTrafficFlag;
	}

	public BigDecimal getChildMaxQuantity() {
		return this.childMaxQuantity;
	}

	public void setChildMaxQuantity(BigDecimal childMaxQuantity) {
		this.childMaxQuantity = childMaxQuantity;
	}

	public BigDecimal getChildMinQuantity() {
		return this.childMinQuantity;
	}

	public void setChildMinQuantity(BigDecimal childMinQuantity) {
		this.childMinQuantity = childMinQuantity;
	}

	public String getFreeInsuranceFlag() {
		return this.freeInsuranceFlag;
	}

	public void setFreeInsuranceFlag(String freeInsuranceFlag) {
		this.freeInsuranceFlag = freeInsuranceFlag;
	}

	public String getHotel() {
		return this.hotel;
	}

	public void setHotel(String hotel) {
		this.hotel = hotel;
	}

	public BigDecimal getLowestMarketPrice() {
		return this.lowestMarketPrice;
	}

	public void setLowestMarketPrice(BigDecimal lowestMarketPrice) {
		this.lowestMarketPrice = lowestMarketPrice;
	}

	public BigDecimal getLowestSaledPrice() {
		return this.lowestSaledPrice;
	}

	public void setLowestSaledPrice(BigDecimal lowestSaledPrice) {
		this.lowestSaledPrice = lowestSaledPrice;
	}

	public BigDecimal getMobileRebate() {
		return this.mobileRebate;
	}

	public void setMobileRebate(BigDecimal mobileRebate) {
		this.mobileRebate = mobileRebate;
	}

	public String getMultiRouteFlag() {
		return this.multiRouteFlag;
	}

	public void setMultiRouteFlag(String multiRouteFlag) {
		this.multiRouteFlag = multiRouteFlag;
	}

	public BigDecimal getPcRebate() {
		return this.pcRebate;
	}

	public void setPcRebate(BigDecimal pcRebate) {
		this.pcRebate = pcRebate;
	}

	public String getQrcodePath() {
		return this.qrcodePath;
	}

	public void setQrcodePath(String qrcodePath) {
		this.qrcodePath = qrcodePath;
	}

	public String getSaleType() {
		return this.saleType;
	}

	public void setSaleType(String saleType) {
		this.saleType = saleType;
	}

	public BigDecimal getSettlementPrice() {
		return this.settlementPrice;
	}

	public void setSettlementPrice(BigDecimal settlementPrice) {
		this.settlementPrice = settlementPrice;
	}

	public String getToTraffic() {
		return this.toTraffic;
	}

	public void setToTraffic(String toTraffic) {
		this.toTraffic = toTraffic;
	}

}