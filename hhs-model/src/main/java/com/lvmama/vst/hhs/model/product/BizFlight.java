/**
 * 
 */
package com.lvmama.vst.hhs.model.product;

/**
 * @author fengyonggang
 *
 */
public class BizFlight {

	private Long flightId;
	private Long startAirport;
	private Long arriveAirport;
	private Long airplane;
	private Long airline;
	private String flightNo;
	private String startTime;
	private String arriveTime;
	private Long flightTime;
	private Long startDistrict;
	private Long arriveDistrict;
	private String berthInfo;
	private String startTerminal;
	private String arriveTerminal;
	private Long stopCount;
	private String cancelFlag;
	private String shareFlightNo;
	// ---------用于前台展示
	// 机型String
	private String airplaneString;//机型
	private String startAirportString;//始发机场
	private String arriveAirportString;//到达机场
	private String startDistrictString;
	private String arriveDistrictString;
	private String airlineString; //航空公司
	private String flightTimeHour;
	private String flightTimeMinute;
	private String flightTimeString;
	
	public Long getFlightId() {
		return flightId;
	}
	public void setFlightId(Long flightId) {
		this.flightId = flightId;
	}
	public Long getStartAirport() {
		return startAirport;
	}
	public void setStartAirport(Long startAirport) {
		this.startAirport = startAirport;
	}
	public Long getArriveAirport() {
		return arriveAirport;
	}
	public void setArriveAirport(Long arriveAirport) {
		this.arriveAirport = arriveAirport;
	}
	public Long getAirplane() {
		return airplane;
	}
	public void setAirplane(Long airplane) {
		this.airplane = airplane;
	}
	public Long getAirline() {
		return airline;
	}
	public void setAirline(Long airline) {
		this.airline = airline;
	}
	public String getFlightNo() {
		return flightNo;
	}
	public void setFlightNo(String flightNo) {
		this.flightNo = flightNo;
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
	public Long getFlightTime() {
		return flightTime;
	}
	public void setFlightTime(Long flightTime) {
		this.flightTime = flightTime;
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
	public String getBerthInfo() {
		return berthInfo;
	}
	public void setBerthInfo(String berthInfo) {
		this.berthInfo = berthInfo;
	}
	public String getStartTerminal() {
		return startTerminal;
	}
	public void setStartTerminal(String startTerminal) {
		this.startTerminal = startTerminal;
	}
	public String getArriveTerminal() {
		return arriveTerminal;
	}
	public void setArriveTerminal(String arriveTerminal) {
		this.arriveTerminal = arriveTerminal;
	}
	public Long getStopCount() {
		return stopCount;
	}
	public void setStopCount(Long stopCount) {
		this.stopCount = stopCount;
	}
	public String getCancelFlag() {
		return cancelFlag;
	}
	public void setCancelFlag(String cancelFlag) {
		this.cancelFlag = cancelFlag;
	}
	public String getShareFlightNo() {
		return shareFlightNo;
	}
	public void setShareFlightNo(String shareFlightNo) {
		this.shareFlightNo = shareFlightNo;
	}
	public String getAirplaneString() {
		return airplaneString;
	}
	public void setAirplaneString(String airplaneString) {
		this.airplaneString = airplaneString;
	}
	public String getStartAirportString() {
		return startAirportString;
	}
	public void setStartAirportString(String startAirportString) {
		this.startAirportString = startAirportString;
	}
	public String getArriveAirportString() {
		return arriveAirportString;
	}
	public void setArriveAirportString(String arriveAirportString) {
		this.arriveAirportString = arriveAirportString;
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
	public String getAirlineString() {
		return airlineString;
	}
	public void setAirlineString(String airlineString) {
		this.airlineString = airlineString;
	}
	public String getFlightTimeHour() {
		return flightTimeHour;
	}
	public void setFlightTimeHour(String flightTimeHour) {
		this.flightTimeHour = flightTimeHour;
	}
	public String getFlightTimeMinute() {
		return flightTimeMinute;
	}
	public void setFlightTimeMinute(String flightTimeMinute) {
		this.flightTimeMinute = flightTimeMinute;
	}
	public String getFlightTimeString() {
		return flightTimeString;
	}
	public void setFlightTimeString(String flightTimeString) {
		this.flightTimeString = flightTimeString;
	}
}
