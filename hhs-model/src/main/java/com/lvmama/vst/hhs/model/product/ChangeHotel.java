package com.lvmama.vst.hhs.model.product;

import java.io.Serializable;
import java.util.List;

public class ChangeHotel implements Serializable {

	private static final long serialVersionUID = 1L;
	
    private Long goodsId;
    private String goodsName;
    private Long productBranchId;
    private String branchName;
    private Long groupId;
    private List<BranchPropVO> props;
    private long childPrice;
    private long audltPrice;
    private long gapPrice;	//房差
    private int day;//入住几天
    private String start;//入住时间
    private String end;//离店时间
    
	public Long getGoodsId() {
		return goodsId;
	}
	public void setGoodsId(Long goodsId) {
		this.goodsId = goodsId;
	}
	public String getGoodsName() {
		return goodsName;
	}
	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}
	public Long getProductBranchId() {
		return productBranchId;
	}
	public void setProductBranchId(Long productBranchId) {
		this.productBranchId = productBranchId;
	}
	public String getBranchName() {
		return branchName;
	}
	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}
	public List<BranchPropVO> getProps() {
		return props;
	}
	public void setProps(List<BranchPropVO> props) {
		this.props = props;
	}
	public Long getGroupId() {
		return groupId;
	}
	public void setGroupId(Long groupId) {
		this.groupId = groupId;
	}
	public int getDay() {
		return day;
	}
	public void setDay(int day) {
		this.day = day;
	}
	public String getStart() {
		return start;
	}
	public void setStart(String start) {
		this.start = start;
	}
	public String getEnd() {
		return end;
	}
	public void setEnd(String end) {
		this.end = end;
	}
	public long getChildPrice() {
		return childPrice;
	}
	public void setChildPrice(long childPrice) {
		this.childPrice = childPrice;
	}
	public long getAudltPrice() {
		return audltPrice;
	}
	public void setAudltPrice(long audltPrice) {
		this.audltPrice = audltPrice;
	}
	public long getGapPrice() {
		return gapPrice;
	}
	public void setGapPrice(long gapPrice) {
		this.gapPrice = gapPrice;
	}
}
