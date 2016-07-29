package com.lvmama.vst.hhs.model.admin;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.util.StringUtils;

import com.lvmama.vst.hhs.model.common.Constant.ORDER_STATUS;
import com.lvmama.vst.hhs.model.common.Constant.PAT_TYPE;

public class StampOrderVo implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long orderId;
	private Date createTime;
	private Date firstPaymentTime;
	private Date lastPaymentTime;
	private String fullName;
	private String mobile;
	private String orderStatus;
	private String paymentStatus;
	private String payType;

	public String getOstatus() {
		if (StringUtils.isEmpty(orderStatus))
			return orderStatus;
		return ORDER_STATUS.getCnName(orderStatus);
	}

	public String getPayTime() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date payTime = firstPaymentTime; // 首付时间
		if (!StringUtils.isEmpty(payType) && PAT_TYPE.FULL.name().equals(payType))
			payTime = lastPaymentTime;
		if(payTime == null)
			return null;
		return sdf.format(payTime);
	}

	public String getLastPayTime() {
		
		if (!StringUtils.isEmpty(payType) && PAT_TYPE.FULL.name().equals(payType))
			return "不用支付";

		if(!"PAYED".equals(paymentStatus))
			return null;
		
		return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(lastPaymentTime);
	}

	public String getPayType() {
		return payType;
	}

	public void setPayType(String payType) {
		this.payType = payType;
	}

	public Long getOrderId() {
		return orderId;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getFirstPaymentTime() {
		return firstPaymentTime;
	}

	public void setFirstPaymentTime(Date firstPaymentTime) {
		this.firstPaymentTime = firstPaymentTime;
	}

	public Date getLastPaymentTime() {
		return lastPaymentTime;
	}

	public void setLastPaymentTime(Date lastPaymentTime) {
		this.lastPaymentTime = lastPaymentTime;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}

	public String getPaymentStatus() {
		return paymentStatus;
	}

	public void setPaymentStatus(String paymentStatus) {
		this.paymentStatus = paymentStatus;
	}

}
