/**
 * 
 */
package com.lvmama.vst.hhs.model.product;

/**
 * @author fengyonggang
 *
 */
public class GoodsDesc {

	private Long descId;//商品描述ID
    private Long suppGoodsId;//供应商商品ID
    private String priceIncludes;//费用包含
    private String typeDesc; //票种说明
    private String needTicket; // 是否需要取票
    private String changeLimitFlag;//取票限制
    private String changeTime;//取票时间
    private String changeAddress;//取票地点
    private String enterStyle;//入园方式
    private String limitFlag;//是否限制入园
    private String limitTime;//入园限制时间
    private String visitAddress;//入园地点 必填
    private String height;//身高
    private String age;//年龄
    private String region;//地域
    private String maxQuantity;//最大限购
    private String express;//快递
    private String entityTicket;//实体票
    private String others;//其他
    private String passFlag;//是否限制通关
    private String passLimitTime;//通关限制时间
    
	public Long getDescId() {
		return descId;
	}
	public void setDescId(Long descId) {
		this.descId = descId;
	}
	public Long getSuppGoodsId() {
		return suppGoodsId;
	}
	public void setSuppGoodsId(Long suppGoodsId) {
		this.suppGoodsId = suppGoodsId;
	}
	public String getPriceIncludes() {
		return priceIncludes;
	}
	public void setPriceIncludes(String priceIncludes) {
		this.priceIncludes = priceIncludes;
	}
	public String getTypeDesc() {
		return typeDesc;
	}
	public void setTypeDesc(String typeDesc) {
		this.typeDesc = typeDesc;
	}
	public String getNeedTicket() {
		return needTicket;
	}
	public void setNeedTicket(String needTicket) {
		this.needTicket = needTicket;
	}
	public String getChangeLimitFlag() {
		return changeLimitFlag;
	}
	public void setChangeLimitFlag(String changeLimitFlag) {
		this.changeLimitFlag = changeLimitFlag;
	}
	public String getChangeTime() {
		return changeTime;
	}
	public void setChangeTime(String changeTime) {
		this.changeTime = changeTime;
	}
	public String getChangeAddress() {
		return changeAddress;
	}
	public void setChangeAddress(String changeAddress) {
		this.changeAddress = changeAddress;
	}
	public String getEnterStyle() {
		return enterStyle;
	}
	public void setEnterStyle(String enterStyle) {
		this.enterStyle = enterStyle;
	}
	public String getLimitFlag() {
		return limitFlag;
	}
	public void setLimitFlag(String limitFlag) {
		this.limitFlag = limitFlag;
	}
	public String getLimitTime() {
		return limitTime;
	}
	public void setLimitTime(String limitTime) {
		this.limitTime = limitTime;
	}
	public String getVisitAddress() {
		return visitAddress;
	}
	public void setVisitAddress(String visitAddress) {
		this.visitAddress = visitAddress;
	}
	public String getHeight() {
		return height;
	}
	public void setHeight(String height) {
		this.height = height;
	}
	public String getAge() {
		return age;
	}
	public void setAge(String age) {
		this.age = age;
	}
	public String getRegion() {
		return region;
	}
	public void setRegion(String region) {
		this.region = region;
	}
	public String getMaxQuantity() {
		return maxQuantity;
	}
	public void setMaxQuantity(String maxQuantity) {
		this.maxQuantity = maxQuantity;
	}
	public String getExpress() {
		return express;
	}
	public void setExpress(String express) {
		this.express = express;
	}
	public String getEntityTicket() {
		return entityTicket;
	}
	public void setEntityTicket(String entityTicket) {
		this.entityTicket = entityTicket;
	}
	public String getOthers() {
		return others;
	}
	public void setOthers(String others) {
		this.others = others;
	}
	public String getPassFlag() {
		return passFlag;
	}
	public void setPassFlag(String passFlag) {
		this.passFlag = passFlag;
	}
	public String getPassLimitTime() {
		return passLimitTime;
	}
	public void setPassLimitTime(String passLimitTime) {
		this.passLimitTime = passLimitTime;
	}
}
