package com.lvmama.vst.hhs.model.stamp;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class StampOrderDetails implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private StampDetails stamp;  			//券信息
	private List<StampCode> stampCodes; 	//券码
	private StampContact contact; 			//联系人
	private String orderId; 				//订单ID
	private Date orderDate; 				//下单时间
	private Long price;						//单价
	private int amount;						//数量
	private Long paidPrice;					//已支付金额
	// UNPAY("待支付"), TRANSFERRED("已转移"), PART_PAY("部分支付"), PAYED("已支付 ") CANCEL("已取消") ;
	private String orderStatus;				//订单状态  支付状态  和取消
	private Date latestPayTime;				//最后支付时间
	private Long totalPrice;				//总价
	private String payType;                 //定金PART 尾款LAST 全额FULL
	private Long downPayment;              //定金
	private Date balanceDueWaitDate;       //尾款支付时间
	private Long subsidyAmount;            //补偿金额
	/** 催兑换时间段 */
	private StampRemindCustomerTimeSlot remindCustomerTimeslot;
	
	public StampDetails getStamp() {
		return stamp;
	}
	public void setStamp(StampDetails stamp) {
		this.stamp = stamp;
	}
	public List<StampCode> getStampCodes() {
		return stampCodes;
	}
	public void setStampCodes(List<StampCode> stampCodes) {
		this.stampCodes = stampCodes;
	}
	public StampContact getContact() {
		return contact;
	}
	public void setContact(StampContact contact) {
		this.contact = contact;
	}
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public Date getOrderDate() {
		return orderDate;
	}
	public void setOrderDate(Date orderDate) {
		this.orderDate = orderDate;
	}
	public Long getPrice() {
		return price;
	}
	public void setPrice(Long price) {
		this.price = price;
	}
	public int getAmount() {
		return amount;
	}
	public void setAmount(int amount) {
		this.amount = amount;
	}
	public Long getPaidPrice() {
		return paidPrice;
	}
	public void setPaidPrice(Long paidPrice) {
		this.paidPrice = paidPrice;
	}
	public String getOrderStatus() {
		return orderStatus;
	}
	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}
	public Date getLatestPayTime() {
		return latestPayTime;
	}
	public void setLatestPayTime(Date latestPayTime) {
		this.latestPayTime = latestPayTime;
	}
	public Long getTotalPrice() {
		return totalPrice;
	}
	public void setTotalPrice(Long totalPrice) {
		this.totalPrice = totalPrice;
	}
    public String getPayType() {
        return payType;
    }
    public void setPayType(String payType) {
        this.payType = payType;
    }
    public Long getDownPayment() {
        return downPayment;
    }
    public void setDownPayment(Long downPayment) {
        this.downPayment = downPayment;
    }
    public Date getBalanceDueWaitDate() {
        return balanceDueWaitDate;
    }
    public void setBalanceDueWaitDate(Date balanceDueWaitDate) {
        this.balanceDueWaitDate = balanceDueWaitDate;
    }
    public Long getSubsidyAmount() {
        return subsidyAmount;
    }
    public void setSubsidyAmount(Long subsidyAmount) {
        this.subsidyAmount = subsidyAmount;
    }
    public StampRemindCustomerTimeSlot getRemindCustomerTimeslot() {
        return remindCustomerTimeslot;
    }
    public void setRemindCustomerTimeslot(StampRemindCustomerTimeSlot remindCustomerTimeslot) {
        this.remindCustomerTimeslot = remindCustomerTimeslot;
    }
	
}
