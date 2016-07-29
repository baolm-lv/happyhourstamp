/**
 * 
 */
package com.lvmama.vst.hhs.model.product;


/**
 * @author fengyonggang
 *
 */
public class ProdTraffic {

	private Long trafficId;
    //产品ID
    private Long productId;
    //去程交通
    private String toType;
    //返程交通
    private String backType;
    //是否是参考信息
    private String referFlag;
    //上车点是否下单可选
    private String cheseFlag2;
    //去程始发地
    private Long startDistrict;
    //去程目的地
    private Long endDistrict;
    //反程始发地
    private Long backStartDisirict;
    //反程目的地
    private Long backEndDisirict;
    //往返类型
    private String toBackType;
    private String memo;
    
	public Long getTrafficId() {
		return trafficId;
	}
	public void setTrafficId(Long trafficId) {
		this.trafficId = trafficId;
	}
	public Long getProductId() {
		return productId;
	}
	public void setProductId(Long productId) {
		this.productId = productId;
	}
	public String getToType() {
		return toType;
	}
	public void setToType(String toType) {
		this.toType = toType;
	}
	public String getBackType() {
		return backType;
	}
	public void setBackType(String backType) {
		this.backType = backType;
	}
	public String getReferFlag() {
		return referFlag;
	}
	public void setReferFlag(String referFlag) {
		this.referFlag = referFlag;
	}
	public String getCheseFlag2() {
		return cheseFlag2;
	}
	public void setCheseFlag2(String cheseFlag2) {
		this.cheseFlag2 = cheseFlag2;
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
	public Long getBackStartDisirict() {
		return backStartDisirict;
	}
	public void setBackStartDisirict(Long backStartDisirict) {
		this.backStartDisirict = backStartDisirict;
	}
	public Long getBackEndDisirict() {
		return backEndDisirict;
	}
	public void setBackEndDisirict(Long backEndDisirict) {
		this.backEndDisirict = backEndDisirict;
	}
	public String getToBackType() {
		return toBackType;
	}
	public void setToBackType(String toBackType) {
		this.toBackType = toBackType;
	}
	public String getMemo() {
		return memo;
	}
	public void setMemo(String memo) {
		this.memo = memo;
	}
}
