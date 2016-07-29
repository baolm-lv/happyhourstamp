package com.lvmama.vst.hhs.model.stamp;

import java.io.Serializable;
import java.util.List;

import com.lvmama.vst.hhs.model.product.ChangeHotel;

public class StampItem implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -7739596156669039797L;
	private Long branchId;
	private String branchName;
	//可换酒店
    private List<ChangeHotel> hotels; 
    private AdditionalGoods goods;
	public Long getBranchId() {
		return branchId;
	}
	public void setBranchId(Long branchId) {
		this.branchId = branchId;
	}
	public String getBranchName() {
		return branchName;
	}
	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}
	public List<ChangeHotel> getHotels() {
		return hotels;
	}
	public void setHotels(List<ChangeHotel> hotels) {
		this.hotels = hotels;
	}
	public AdditionalGoods getGoods() {
		return goods;
	}
	public void setGoods(AdditionalGoods goods) {
		this.goods = goods;
	}
	public static StampItem build() {
		StampItem so = new StampItem();
		so.setBranchId(11111L);
		so.setBranchName("亲子");
//		so.setHotels(Arrays.asList(new ChangeHotel(111L,"格林豪泰","1,2",1224L),new ChangeHotel(111L,"格林豪泰1","3",1224L)));
//		so.setGoods(new AdditionalGoods(111L,"成人",3213413L,"游乐园"));
		return so;
	}
    
     

}
