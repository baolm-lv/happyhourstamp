/**
 * 
 */
package com.lvmama.vst.hhs.model.product;

import java.util.List;

/**
 * @author fengyonggang
 *
 */
public class BizTrain {

	private Long trainId;
	/**
	 * TYPE_OTHER("100", "其他"),
		TYPE_G("101", "高铁"),
		TYPE_C("102", "城际高铁"),
		TYPE_D("103", "动车"),
		TYPE_T("104", "特快"),
		TYPE_Z("105", "直达"),
		TYPE_K("106", "快速"),
		TYPE_PK("107", "普快"),
		TYPE_N("108", "管内"),
		TYPE_L("109", "临客"),
		TYPE_PM("110", "普慢"),
		TYPE_S("111", "市郊列车"),
		TYPE_P("112", "直特"),
		TYPE_Y("113", "旅游");
	 */
    private Long trainType;
    private String trainNo;
    private Long startStation;
    private Long arriveStation;
    private String startTime;
    private String arriveTime;
    private Long costTime;
    private Long startDistrict;
    private Long arriveDistrict;
    private Long stopCount;
    private Integer mileage;
    private BizTrainStop startTrainStop;
    private BizTrainStop arriveTrainStop;
    private String cancelFlag;
    //用于前台展示，两个站点之间的耗时  
    private String costTimeString;
    private String trainTypeString;
    private String startDistrictString;
    private String arriveDistrictString;
    private String costTimeHour;
    private String costTimeMinute;
    private String startStationString;
    private String arriveStationString;
    /**
     * 坐席
     * @deprecated
     */
    private List<String> seatLevels;
    private List<BizTrainSeat> trainSeatList;
    /**
     * 经停站
     */
    private List<BizTrainStop>trainStops;
    
	public Long getTrainId() {
		return trainId;
	}
	public void setTrainId(Long trainId) {
		this.trainId = trainId;
	}
	public Long getTrainType() {
		return trainType;
	}
	public void setTrainType(Long trainType) {
		this.trainType = trainType;
	}
	public String getTrainNo() {
		return trainNo;
	}
	public void setTrainNo(String trainNo) {
		this.trainNo = trainNo;
	}
	public Long getStartStation() {
		return startStation;
	}
	public void setStartStation(Long startStation) {
		this.startStation = startStation;
	}
	public Long getArriveStation() {
		return arriveStation;
	}
	public void setArriveStation(Long arriveStation) {
		this.arriveStation = arriveStation;
	}
	public String getStartTime() {
		return startTime;
	}
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	public String getArriveTime() {
		return arriveTime;
	}
	public void setArriveTime(String arriveTime) {
		this.arriveTime = arriveTime;
	}
	public Long getCostTime() {
		return costTime;
	}
	public void setCostTime(Long costTime) {
		this.costTime = costTime;
	}
	public Long getStartDistrict() {
		return startDistrict;
	}
	public void setStartDistrict(Long startDistrict) {
		this.startDistrict = startDistrict;
	}
	public Long getArriveDistrict() {
		return arriveDistrict;
	}
	public void setArriveDistrict(Long arriveDistrict) {
		this.arriveDistrict = arriveDistrict;
	}
	public Long getStopCount() {
		return stopCount;
	}
	public void setStopCount(Long stopCount) {
		this.stopCount = stopCount;
	}
	public Integer getMileage() {
		return mileage;
	}
	public void setMileage(Integer mileage) {
		this.mileage = mileage;
	}
	public BizTrainStop getStartTrainStop() {
		return startTrainStop;
	}
	public void setStartTrainStop(BizTrainStop startTrainStop) {
		this.startTrainStop = startTrainStop;
	}
	public BizTrainStop getArriveTrainStop() {
		return arriveTrainStop;
	}
	public void setArriveTrainStop(BizTrainStop arriveTrainStop) {
		this.arriveTrainStop = arriveTrainStop;
	}
	public String getCancelFlag() {
		return cancelFlag;
	}
	public void setCancelFlag(String cancelFlag) {
		this.cancelFlag = cancelFlag;
	}
	public String getCostTimeString() {
		return costTimeString;
	}
	public void setCostTimeString(String costTimeString) {
		this.costTimeString = costTimeString;
	}
	public String getTrainTypeString() {
		return trainTypeString;
	}
	public void setTrainTypeString(String trainTypeString) {
		this.trainTypeString = trainTypeString;
	}
	public String getStartDistrictString() {
		return startDistrictString;
	}
	public void setStartDistrictString(String startDistrictString) {
		this.startDistrictString = startDistrictString;
	}
	public String getArriveDistrictString() {
		return arriveDistrictString;
	}
	public void setArriveDistrictString(String arriveDistrictString) {
		this.arriveDistrictString = arriveDistrictString;
	}
	public String getCostTimeHour() {
		return costTimeHour;
	}
	public void setCostTimeHour(String costTimeHour) {
		this.costTimeHour = costTimeHour;
	}
	public String getCostTimeMinute() {
		return costTimeMinute;
	}
	public void setCostTimeMinute(String costTimeMinute) {
		this.costTimeMinute = costTimeMinute;
	}
	public String getStartStationString() {
		return startStationString;
	}
	public void setStartStationString(String startStationString) {
		this.startStationString = startStationString;
	}
	public String getArriveStationString() {
		return arriveStationString;
	}
	public void setArriveStationString(String arriveStationString) {
		this.arriveStationString = arriveStationString;
	}
	public List<String> getSeatLevels() {
		return seatLevels;
	}
	public void setSeatLevels(List<String> seatLevels) {
		this.seatLevels = seatLevels;
	}
	public List<BizTrainSeat> getTrainSeatList() {
		return trainSeatList;
	}
	public void setTrainSeatList(List<BizTrainSeat> trainSeatList) {
		this.trainSeatList = trainSeatList;
	}
	public List<BizTrainStop> getTrainStops() {
		return trainStops;
	}
	public void setTrainStops(List<BizTrainStop> trainStops) {
		this.trainStops = trainStops;
	}
}
