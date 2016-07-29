/**
 * 
 */
package com.lvmama.vst.hhs.stampDefinition.bo;

import java.util.List;

import com.lvmama.vst.hhs.stampDefinition.dao.PresaleStampDefinitionGoodsBindingEntity;

/**
 * @author fengyonggang
 *
 */
public class StampGoodsBindingBo {

	private String type;//AUDLT, CHILD
	private PresaleStampDefinitionGoodsBindingEntity goods;
	private List<StampPackageGoodsBo> packages;
	
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public PresaleStampDefinitionGoodsBindingEntity getGoods() {
		return goods;
	}
	public void setGoods(PresaleStampDefinitionGoodsBindingEntity goods) {
		this.goods = goods;
	}
	public List<StampPackageGoodsBo> getPackages() {
		return packages;
	}
	public void setPackages(List<StampPackageGoodsBo> packages) {
		this.packages = packages;
	}
}
