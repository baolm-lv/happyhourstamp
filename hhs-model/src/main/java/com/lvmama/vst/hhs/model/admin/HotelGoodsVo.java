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
public class HotelGoodsVo implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private long productBranchId;
	private String branchName;
	private List<SuppGoodsVo> goods;
	private Map<Long,List<SuppGoodsVo>> branchGoods;
  

	public long getProductBranchId() {
		return productBranchId;
	}

	public void setProductBranchId(long productBranchId) {
		this.productBranchId = productBranchId;
	}

	public String getBranchName() {
		return branchName;
	}

	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}

	public List<SuppGoodsVo> getGoods() {
		return goods;
	}

	public void setGoods(List<SuppGoodsVo> goods) {
		this.goods = goods;
	}

    public Map<Long, List<SuppGoodsVo>> getBranchGoods() {
        return branchGoods;
    }

    public void setBranchGoods(Map<Long, List<SuppGoodsVo>> branchGoods) {
        this.branchGoods = branchGoods;
    }

  
	
	

}
