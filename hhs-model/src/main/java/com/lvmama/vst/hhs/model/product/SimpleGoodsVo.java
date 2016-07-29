/**
 * 
 */
package com.lvmama.vst.hhs.model.product;

/**
 * @author fengyonggang
 *
 */
public class SimpleGoodsVo {

	private Long goodsId;
	private String goodsName;
	
	public SimpleGoodsVo() {}
	
	public SimpleGoodsVo(Long goodsId, String goodsName) {
		super();
		this.goodsId = goodsId;
		this.goodsName = goodsName;
	}
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
}
