package com.lvmama.vst.hhs.model.stamp;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class StampCode implements Serializable {

	private static final long serialVersionUID = 1L;

	private String id;
	/**
	 * 券码
	 */
	private String serialNumber;
	/**
	 * 券状态
	 */
	private String stampStatus;
	/**
	 * 券订单ID
	 */
	private String orderId;
	/**
	 * 使用券订单ID
	 */
	private String useOrderId;
	/**
	 * 使用券子订单ID
	 */
	private String useOrderItemId;
	/**
	 * 使用券订单品类
	 */
	private String orderType;
	
	private String refundOrderId;
	/**
	 * 券单价
	 */
	private Integer price;
	/**
	 * 券有效期
	 */
	private Date expiredDate;
	
	/**
     * 兑换订单历史记录
     */
    private List<StampUseOrderSimple> useOrderHis;

	public StampCode() {
	}

	public List<StampUseOrderSimple> getUseOrderHis() {
        return useOrderHis;
    }

    public void setUseOrderHis(List<StampUseOrderSimple> useOrderHis) {
        this.useOrderHis = useOrderHis;
    }

    public String getOrderType() {
		return orderType;
	}

	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}

	public String getRefundOrderId() {
		return refundOrderId;
	}

	public void setRefundOrderId(String refundOrderId) {
		this.refundOrderId = refundOrderId;
	}

	public Integer getPrice() {
		return price;
	}

	public void setPrice(Integer price) {
		this.price = price;
	}

	public Date getExpiredDate() {
		return expiredDate;
	}

	public void setExpiredDate(Date expiredDate) {
		this.expiredDate = expiredDate;
	}

	public String getSerialNumber() {
		return serialNumber;
	}

	public void setSerialNumber(String serialNumber) {
		this.serialNumber = serialNumber;
	}

	public String getStampStatus() {
		return stampStatus;
	}

	public void setStampStatus(String stampStatus) {
		this.stampStatus = stampStatus;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getUseOrderId() {
		return useOrderId;
	}

	public void setUseOrderId(String useOrderId) {
		this.useOrderId = useOrderId;
	}

	public String getUseOrderItemId() {
		return useOrderItemId;
	}

	public void setUseOrderItemId(String useOrderItemId) {
		this.useOrderItemId = useOrderItemId;
	}

}
