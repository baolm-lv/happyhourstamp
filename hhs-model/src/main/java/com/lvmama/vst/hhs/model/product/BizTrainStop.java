/**
 * 
 */
package com.lvmama.vst.hhs.model.product;

/**
 * @author fengyonggang
 *
 */
public class BizTrainStop {

	private Long stopId;
    private Long trainId;
    private Long stopStation;
    private Long stopStep;
    private String arrivalTime;
    private String departureTime;
    private Integer stopTime;
    private Integer takeTime;
    private String cancelFlag;
    /** 停靠车站地标名称  */
    private String stopStationString;
    //第几天到达
    private Integer runDays;
    /** 停靠车站所在行政区域 */
    private String stopStationDistrictString;
    
	public Long getStopId() {
		return stopId;
	}
	public void setStopId(Long stopId) {
		this.stopId = stopId;
	}
	public Long getTrainId() {
		return trainId;
	}
	public void setTrainId(Long trainId) {
		this.trainId = trainId;
	}
	public Long getStopStation() {
		return stopStation;
	}
	public void setStopStation(Long stopStation) {
		this.stopStation = stopStation;
	}
	public Long getStopStep() {
		return stopStep;
	}
	public void setStopStep(Long stopStep) {
		this.stopStep = stopStep;
	}
	public String getArrivalTime() {
		return arrivalTime;
	}
	public void setArrivalTime(String arrivalTime) {
		this.arrivalTime = arrivalTime;
	}
	public String getDepartureTime() {
		return departureTime;
	}
	public void setDepartureTime(String departureTime) {
		this.departureTime = departureTime;
	}
	public Integer getStopTime() {
		return stopTime;
	}
	public void setStopTime(Integer stopTime) {
		this.stopTime = stopTime;
	}
	public Integer getTakeTime() {
		return takeTime;
	}
	public void setTakeTime(Integer takeTime) {
		this.takeTime = takeTime;
	}
	public String getCancelFlag() {
		return cancelFlag;
	}
	public void setCancelFlag(String cancelFlag) {
		this.cancelFlag = cancelFlag;
	}
	public String getStopStationString() {
		return stopStationString;
	}
	public void setStopStationString(String stopStationString) {
		this.stopStationString = stopStationString;
	}
	public Integer getRunDays() {
		return runDays;
	}
	public void setRunDays(Integer runDays) {
		this.runDays = runDays;
	}
	public String getStopStationDistrictString() {
		return stopStationDistrictString;
	}
	public void setStopStationDistrictString(String stopStationDistrictString) {
		this.stopStationDistrictString = stopStationDistrictString;
	}
}
