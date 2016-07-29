/**
 * 
 */
package com.lvmama.vst.hhs.stampDefinition.bo;

import com.lvmama.vst.hhs.stampDefinition.dao.PresaleStampDefinitionGoodsBindingEntity;

/**
 * @author fengyonggang
 *
 */
public class StampGroupGoodsBo {

	private long groupId;
	private PresaleStampDefinitionGoodsBindingEntity goods;
	public long getGroupId() {
		return groupId;
	}
	public void setGroupId(long groupId) {
		this.groupId = groupId;
	}
	public PresaleStampDefinitionGoodsBindingEntity getGoods() {
		return goods;
	}
	public void setGoods(PresaleStampDefinitionGoodsBindingEntity goods) {
		this.goods = goods;
	}
}
