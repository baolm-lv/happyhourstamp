/**
 * 
 */
package com.lvmama.vst.hhs.model.product;

/**
 * @author fengyonggang
 *
 */
public class ProdTrafficTrain {

	private Long trainId;
    private Long productId;
    private String tripType;
    private String trainNo;
    private Long startDistrict;
    private Long endDistrict;
    private String stopoverFlag;
    private String cancelFlag;
    private String memo;
    private Long groupId;
    /** 出发城市 */
    private Long fromCity;
    /** 到达城市 */
    private Long toCity;
    /** 坐席ID（关联Train_Seat表主键） */
    private Long trainSeatId;
    private BizTrain bizTrain;
    //前台用
    private String startDistrictString;
    private String endDistrictString;
    /** 出发城市 名称  在数据库表中不存这个字段,只用来显示关联的结果*/
    private String fromCityName;
    /** 到达城市 名称  在数据库表中不存这个字段,只用来显示关联的结果*/
    private String toCityName;
    
	public Long getTrainId() {
		return trainId;
	}
	public void setTrainId(Long trainId) {
		this.trainId = trainId;
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
	public String getTrainNo() {
		return trainNo;
	}
	public void setTrainNo(String trainNo) {
		this.trainNo = trainNo;
	}
	public Long getStartDistrict() {
		return startDistrict;
	}
	public void setStartDistrict(Long startDistrict) {
		this.startDistrict = startDistrict;
	}
	public Long getEndDistrict() {
		return endDistrict;
	}
	public void setEndDistrict(Long endDistrict) {
		this.endDistrict = endDistrict;
	}
	public String getStopoverFlag() {
		return stopoverFlag;
	}
	public void setStopoverFlag(String stopoverFlag) {
		this.stopoverFlag = stopoverFlag;
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
	public Long getTrainSeatId() {
		return trainSeatId;
	}
	public void setTrainSeatId(Long trainSeatId) {
		this.trainSeatId = trainSeatId;
	}
	public BizTrain getBizTrain() {
		return bizTrain;
	}
	public void setBizTrain(BizTrain bizTrain) {
		this.bizTrain = bizTrain;
	}
	public String getStartDistrictString() {
		return startDistrictString;
	}
	public void setStartDistrictString(String startDistrictString) {
		this.startDistrictString = startDistrictString;
	}
	public String getEndDistrictString() {
		return endDistrictString;
	}
	public void setEndDistrictString(String endDistrictString) {
		this.endDistrictString = endDistrictString;
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
