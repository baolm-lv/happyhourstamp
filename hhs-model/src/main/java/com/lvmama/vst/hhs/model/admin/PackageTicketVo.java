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
public class PackageTicketVo implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Long groupTicketId;
	private Long groupId;
	private String day;
	private List<SuppGoodsVo> goods;
	private List<PackageProductVo> products;
	public Long getGroupTicketId() {
		return groupTicketId;
	}
	public void setGroupTicketId(Long groupTicketId) {
		this.groupTicketId = groupTicketId;
	}
	public Long getGroupId() {
		return groupId;
	}
	public void setGroupId(Long groupId) {
		this.groupId = groupId;
	}
	public String getDay() {
		return day;
	}
	public void setDay(String day) {
		this.day = day;
	}
	public List<SuppGoodsVo> getGoods() {
		return goods;
	}
	public void setGoods(List<SuppGoodsVo> goods) {
		this.goods = goods;
	}
  
    public List<PackageProductVo> getProducts() {
        return products;
    }
    public void setProducts(List<PackageProductVo> products) {
        this.products = products;
    }
	
	
}
