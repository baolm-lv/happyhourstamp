/**
 * 
 */
package com.lvmama.vst.hhs.model.stamp;

import java.io.Serializable;

/**
 * @author "baolm"
 *
 */
public class UseStampCodeInfo implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    private String useOrderId;
    private String orderId;
    private String orderStatus;
    private String stampNo;
    private String stampName;
    private String serialNumber;
    private Integer price;
    private String bindStatus;

    public String getUseOrderId() {
        return useOrderId;
    }

    public void setUseOrderId(String useOrderId) {
        this.useOrderId = useOrderId;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getStampNo() {
        return stampNo;
    }

    public void setStampNo(String stampNo) {
        this.stampNo = stampNo;
    }

    public String getStampName() {
        return stampName;
    }

    public void setStampName(String stampName) {
        this.stampName = stampName;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public String getBindStatus() {
        return bindStatus;
    }

    public void setBindStatus(String bindStatus) {
        this.bindStatus = bindStatus;
    }

}
