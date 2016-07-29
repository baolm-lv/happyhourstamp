/**
 * 
 */
package com.lvmama.vst.hhs.model.product;

/**
 * @author fengyonggang
 *
 */
public class TicketGoods {

	private SuppGoods goods;
	private GoodsDesc desc;
	private String ticketType;//单票: single 还是 套票: suite
	
	public SuppGoods getGoods() {
		return goods;
	}
	public void setGoods(SuppGoods goods) {
		this.goods = goods;
	}
	public GoodsDesc getDesc() {
		return desc;
	}
	public void setDesc(GoodsDesc desc) {
		this.desc = desc;
	}
	public String getTicketType() {
		return ticketType;
	}
	public void setTicketType(String ticketType) {
		this.ticketType = ticketType;
	}
}
