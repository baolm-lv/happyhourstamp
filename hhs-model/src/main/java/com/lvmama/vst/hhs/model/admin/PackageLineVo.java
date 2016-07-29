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
public class PackageLineVo implements Serializable {

	private static final long serialVersionUID = 1L;
	private Long groupLineId;
	private Long groupId;
	private String startDay;
	private String travelDay;
	private String stayDay;
	private String remark;
	private List<SuppGoodsVo> goods;
	private List<PackageProductVo> products;

	public Long getGroupLineId() {
		return groupLineId;
	}

	public void setGroupLineId(Long groupLineId) {
		this.groupLineId = groupLineId;
	}

	public Long getGroupId() {
		return groupId;
	}

	public void setGroupId(Long groupId) {
		this.groupId = groupId;
	}

	public String getStartDay() {
		return startDay;
	}

	public void setStartDay(String startDay) {
		this.startDay = startDay;
	}

	public String getTravelDay() {
		return travelDay;
	}

	public void setTravelDay(String travelDay) {
		this.travelDay = travelDay;
	}

	public String getStayDay() {
		return stayDay;
	}

	public void setStayDay(String stayDay) {
		this.stayDay = stayDay;
	}

	public List<SuppGoodsVo> getGoods() {
		return goods;
	}

	public void setGoods(List<SuppGoodsVo> goods) {
		this.goods = goods;
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
