/**
 * 
 */
package com.lvmama.vst.hhs.model.product;

/**
 * @author fengyonggang
 *
 */
public class BizTrainSeat {

	/** 火车坐席主键ID */
	private Long trainSeatId;
	/** 车型ID */
	private Long trainType;
	/** 坐席类型 */
	private String seatType;
	/** 是否为默认坐席 */
	private String isDefault;
	/** 是否有效 */
	private String validFlag;
	
	public Long getTrainSeatId() {
		return trainSeatId;
	}
	public void setTrainSeatId(Long trainSeatId) {
		this.trainSeatId = trainSeatId;
	}
	public Long getTrainType() {
		return trainType;
	}
	public void setTrainType(Long trainType) {
		this.trainType = trainType;
	}
	public String getSeatType() {
		return seatType;
	}
	public void setSeatType(String seatType) {
		this.seatType = seatType;
	}
	public String getIsDefault() {
		return isDefault;
	}
	public void setIsDefault(String isDefault) {
		this.isDefault = isDefault;
	}
	public String getValidFlag() {
		return validFlag;
	}
	public void setValidFlag(String validFlag) {
		this.validFlag = validFlag;
	}
}
