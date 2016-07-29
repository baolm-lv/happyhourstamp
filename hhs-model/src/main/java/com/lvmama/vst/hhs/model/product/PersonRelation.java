/**
 * 
 */
package com.lvmama.vst.hhs.model.product;

import java.util.List;

/**
 * @author fengyonggang
 *
 */
public class PersonRelation {

	private Long goodsId;
	private List<Integer> seqs;
	public Long getGoodsId() {
		return goodsId;
	}
	public void setGoodsId(Long goodsId) {
		this.goodsId = goodsId;
	}
	public List<Integer> getSeqs() {
		return seqs;
	}
	public void setSeqs(List<Integer> seqs) {
		this.seqs = seqs;
	}
}
