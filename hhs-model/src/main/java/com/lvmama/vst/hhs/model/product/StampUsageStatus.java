/**
 * 
 */
package com.lvmama.vst.hhs.model.product;

/**
 * @author fengyonggang
 *
 */
public class StampUsageStatus extends StampUsage{

	private String status;//VALID or INVALID
	private String message;
	private long goodsPrice;		//券对应商品的总价格，用于追加时计算金额, 单价
	
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public long getGoodsPrice() {
		return goodsPrice;
	}
	public void setGoodsPrice(long goodsPrice) {
		this.goodsPrice = goodsPrice;
	}
}
