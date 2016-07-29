package com.lvmama.vst.hhs.model.stamp;

import java.io.Serializable;

public class StampOrderRequest implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private String stampId; //券ID
	private int amount;		//数量
	private StampContact contact; //联系人
	private StampOrderPayment payment; //支付信息
	private String userId;	//用户Id
	private Long userNo;	//用户编号
	private String ip;
	private Long distributionId;//分销商渠道
	private Long distributorChannel;
	private String distributorCode;
	
	public String getStampId() {
		return stampId;
	}
	public void setStampId(String stampId) {
		this.stampId = stampId;
	}
	public int getAmount() {
		return amount;
	}
	public void setAmount(int amount) {
		this.amount = amount;
	}
	public StampContact getContact() {
		return contact;
	}
	public void setContact(StampContact contact) {
		this.contact = contact;
	}
	public StampOrderPayment getPayment() {
		return payment;
	}
	public void setPayment(StampOrderPayment payment) {
		this.payment = payment;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
    public Long getDistributionId() {
        return distributionId;
    }
    public void setDistributionId(Long distributionId) {
        this.distributionId = distributionId;
    }
	public Long getDistributorChannel() {
		return distributorChannel;
	}
	public void setDistributorChannel(Long distributorChannel) {
		this.distributorChannel = distributorChannel;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public Long getUserNo() {
		return userNo;
	}
	public void setUserNo(Long userNo) {
		this.userNo = userNo;
	}
	public String getDistributorCode() {
		return distributorCode;
	}
	public void setDistributorCode(String distributorCode) {
		this.distributorCode = distributorCode;
	}
	@Override
	public String toString() {
		return "StampOrderRequest [stampId=" + stampId + ", amount=" + amount + ", contact=" + contact + ", payment="
				+ payment + ", userId=" + userId + ", userNo=" + userNo + ", ip=" + ip + ", distributionId="
				+ distributionId + ", distributorChannel=" + distributorChannel + ", distributorCode="
				+ distributorCode + "]";
	}
}
