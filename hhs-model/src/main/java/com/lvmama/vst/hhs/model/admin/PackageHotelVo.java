/**
 * 
 */
package com.lvmama.vst.hhs.model.admin;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * @author fengyonggang
 *
 */
public class PackageHotelVo implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long groupHotelId;
	private Long groupId;
	private int nightStart;
	private int nightEnd;
	private String remark;
	private List<SuppGoodsVo> goods;
	private List<PackageProductVo> products;
	
	
	public Long getGroupId() {
		return groupId;
	}
	public void setGroupId(Long groupId) {
		this.groupId = groupId;
	}
	public int getNightStart() {
		return nightStart;
	}
	public void setNightStart(int nightStart) {
		this.nightStart = nightStart;
	}
	public int getNightEnd() {
		return nightEnd;
	}
	public void setNightEnd(int nightEnd) {
		this.nightEnd = nightEnd;
	}
	public List<SuppGoodsVo> getGoods() {
		return goods;
	}
	public void setGoods(List<SuppGoodsVo> goods) {
		this.goods = goods;
	}
	public Long getGroupHotelId() {
		return groupHotelId;
	}
	public void setGroupHotelId(Long groupHotelId) {
		this.groupHotelId = groupHotelId;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
   
    public List<PackageProductVo> getProducts() {
        return products;
    }
    public void setProducts(List<PackageProductVo> products) {
        this.products = products;
    }
	
}
