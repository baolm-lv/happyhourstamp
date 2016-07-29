package com.lvmama.vst.hhs.model.product;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.lvmama.vst.hhs.model.stamp.StampContact;

public class StampBuyInfo implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private long productId;
	private List<StampUsage> stampUsages;		//券使用列表
	private String userId;
	private Long userNo;
	private String visitTime;					//游玩日期
	private List<StampContact> travellers; 		//游玩人
	private List<GoodsUsage> insurances;		//保险
	private List<GoodsUsage> additions;			//附加
	private List<GoodsUsage> hotelAdditions;	//可换酒店, 券未绑定可换时，需要传递过来
	private int gapPriceAmount;					//房差
	private StampContact contact;				//联系人
	private Long distributionId;				//分销商
	private Long distributorChannel;			//分销商渠道
	private String distributorCode;				//分销商code
	private InvoiceInfoVO invoice;				//发票
	private String ip;
	private Long lineRouteId;					//行程ID
	private String remark;						//备注信息
	private Long startDistrictId;
	private HotelBuyInfo hotelBuyInfo;			//单酒店入住信息
	private AdditionBuyQuantity additionBuy;	//追加购买
	private String travellerDelayFlag;			//订单游玩人是否后置，Y表示后置，N表示不后置
	private TravellerDelayInfo travellerDelay;	//后置游玩人信息
	private List<PersonRelation> personRelations;//自备签游客
	private Map<Long, String> customTravelDate; //用户选择的游玩日期，包括门票和当地游
	private Map<String,String> additionMap = new HashMap<String, String>();
	
	
	public String getTravellerDelayFlag() {
		return travellerDelayFlag;
	}
	public void setTravellerDelayFlag(String travellerDelayFlag) {
		this.travellerDelayFlag = travellerDelayFlag;
	}
	public List<StampUsage> getStampUsages() {
		return stampUsages;
	}
	public void setStampUsages(List<StampUsage> stampUsages) {
		this.stampUsages = stampUsages;
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
	public String getVisitTime() {
		return visitTime;
	}
	public void setVisitTime(String visitTime) {
		this.visitTime = visitTime;
	}
	public List<StampContact> getTravellers() {
		return travellers;
	}
	public void setTravellers(List<StampContact> travellers) {
		this.travellers = travellers;
	}
	public List<GoodsUsage> getAdditions() {
		return additions;
	}
	public void setAdditions(List<GoodsUsage> additions) {
		this.additions = additions;
	}
	public StampContact getContact() {
		return contact;
	}
	public void setContact(StampContact contact) {
		this.contact = contact;
	}
	public long getProductId() {
		return productId;
	}
	public void setProductId(long productId) {
		this.productId = productId;
	}
	public Long getDistributionId() {
		return distributionId;
	}
	public void setDistributionId(Long distributionId) {
		this.distributionId = distributionId;
	}
	public InvoiceInfoVO getInvoice() {
		return invoice;
	}
	public void setInvoice(InvoiceInfoVO invoice) {
		this.invoice = invoice;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public Long getLineRouteId() {
		return lineRouteId;
	}
	public void setLineRouteId(Long lineRouteId) {
		this.lineRouteId = lineRouteId;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public Long getStartDistrictId() {
		return startDistrictId;
	}
	public void setStartDistrictId(Long startDistrictId) {
		this.startDistrictId = startDistrictId;
	}
	public HotelBuyInfo getHotelBuyInfo() {
		return hotelBuyInfo;
	}
	public void setHotelBuyInfo(HotelBuyInfo hotelBuyInfo) {
		this.hotelBuyInfo = hotelBuyInfo;
	}
	public List<GoodsUsage> getInsurances() {
		return insurances;
	}
	public void setInsurances(List<GoodsUsage> insurances) {
		this.insurances = insurances;
	}
	public AdditionBuyQuantity getAdditionBuy() {
		return additionBuy;
	}
	public void setAdditionBuy(AdditionBuyQuantity additionBuy) {
		this.additionBuy = additionBuy;
	}
	public int getGapPriceAmount() {
		return gapPriceAmount;
	}
	public void setGapPriceAmount(int gapPriceAmount) {
		this.gapPriceAmount = gapPriceAmount;
	}
	public Long getDistributorChannel() {
		return distributorChannel;
	}
	public void setDistributorChannel(Long distributorChannel) {
		this.distributorChannel = distributorChannel;
	}
	public List<GoodsUsage> getHotelAdditions() {
		return hotelAdditions;
	}
	public void setHotelAdditions(List<GoodsUsage> hotelAdditions) {
		this.hotelAdditions = hotelAdditions;
	}
	public TravellerDelayInfo getTravellerDelay() {
		return travellerDelay;
	}
	public void setTravellerDelay(TravellerDelayInfo travellerDelay) {
		this.travellerDelay = travellerDelay;
	}
	public List<PersonRelation> getPersonRelations() {
		return personRelations;
	}
	public void setPersonRelations(List<PersonRelation> personRelations) {
		this.personRelations = personRelations;
	}
	public Map<Long, String> getCustomTravelDate() {
		return customTravelDate;
	}
	public void setCustomTravelDate(Map<Long, String> customTravelDate) {
		this.customTravelDate = customTravelDate;
	}
	public String getDistributorCode() {
		return distributorCode;
	}
	public void setDistributorCode(String distributorCode) {
		this.distributorCode = distributorCode;
	}
	
	
	public Map<String, String> getAdditionMap() {
        return additionMap;
    }
    public void setAdditionMap(Map<String, String> additionMap) {
        this.additionMap = additionMap;
    }
    @Override
	public String toString() {
		return "StampBuyInfo [productId=" + productId + ", stampUsages=" + stampUsages + ", userId=" + userId
				+ ", userNo=" + userNo + ", visitTime=" + visitTime + ", travellers=" + travellers + ", insurances="
				+ insurances + ", additions=" + additions + ", hotelAdditions=" + hotelAdditions + ", gapPriceAmount="
				+ gapPriceAmount + ", contact=" + contact + ", distributionId=" + distributionId
				+ ", distributorChannel=" + distributorChannel + ", distributorCode=" + distributorCode + ", invoice="
				+ invoice + ", ip=" + ip + ", lineRouteId=" + lineRouteId + ", remark=" + remark + ", startDistrictId="
				+ startDistrictId + ", hotelBuyInfo=" + hotelBuyInfo + ", additionBuy=" + additionBuy
				+ ", travellerDelayFlag=" + travellerDelayFlag + ", travellerDelay=" + travellerDelay
				+ ", personRelations=" + personRelations + ", customTravelDate=" + customTravelDate + "]";
	}
}
