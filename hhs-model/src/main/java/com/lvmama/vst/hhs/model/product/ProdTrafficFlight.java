/**
 * 
 */
package com.lvmama.vst.hhs.model.product;


/**
 * @author fengyonggang
 *
 */
public class ProdTrafficFlight {

	private Long flightId;
    private Long productId;
    private String tripType;
    private String flightNo;
    private String cancelFlag;
    private String memo;
    private Long groupId;
    /** 出发城市 */
    private Long fromCity;
    /** 到达城市 */
    private Long toCity;
    /** 舱位等级 */
    private String cabin; //ECONOMY("经济舱"), BUSINESS("商务舱"), FIRST("头等舱");
    private BizFlight bizFlight;
    /** 出发城市 名称  在数据库表中不存这个字段,只用来显示关联的结果*/
    private String fromCityName;
    /** 到达城市 名称  在数据库表中不存这个字段,只用来显示关联的结果*/
    private String toCityName;
    
	public Long getFlightId() {
		return flightId;
	}
	public void setFlightId(Long flightId) {
		this.flightId = flightId;
	}
	public Long getProductId() {
		return productId;
	}
	public void setProductId(Long productId) {
		this.productId = productId;
	}
	public String getTripType() {
		return tripType;
	}
	public void setTripType(String tripType) {
		this.tripType = tripType;
	}
	public String getFlightNo() {
		return flightNo;
	}
	public void setFlightNo(String flightNo) {
		this.flightNo = flightNo;
	}
	public String getCancelFlag() {
		return cancelFlag;
	}
	public void setCancelFlag(String cancelFlag) {
		this.cancelFlag = cancelFlag;
	}
	public String getMemo() {
		return memo;
	}
	public void setMemo(String memo) {
		this.memo = memo;
	}
	public Long getGroupId() {
		return groupId;
	}
	public void setGroupId(Long groupId) {
		this.groupId = groupId;
	}
	public Long getFromCity() {
		return fromCity;
	}
	public void setFromCity(Long fromCity) {
		this.fromCity = fromCity;
	}
	public Long getToCity() {
		return toCity;
	}
	public void setToCity(Long toCity) {
		this.toCity = toCity;
	}
	public String getCabin() {
		return cabin;
	}
	public void setCabin(String cabin) {
		this.cabin = cabin;
	}
	public BizFlight getBizFlight() {
		return bizFlight;
	}
	public void setBizFlight(BizFlight bizFlight) {
		this.bizFlight = bizFlight;
	}
	public String getFromCityName() {
		return fromCityName;
	}
	public void setFromCityName(String fromCityName) {
		this.fromCityName = fromCityName;
	}
	public String getToCityName() {
		return toCityName;
	}
	public void setToCityName(String toCityName) {
		this.toCityName = toCityName;
	}
}
