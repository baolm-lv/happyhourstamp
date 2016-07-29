/**
 * 
 */
package com.lvmama.vst.hhs.model.stamp;

import java.io.Serializable;
import java.util.List;

import com.lvmama.vst.hhs.model.product.ProductSaleType;
import com.lvmama.vst.hhs.model.product.SimpleGoodsVo;

/**
 * @author fengyonggang
 *
 */
public class StampRedeem implements Serializable {

	private static final long serialVersionUID = 1L;
	private StampDetails stamp;
	private List<SimpleGoodsVo> goods;	//券对应的所有子商品
    private int buyAmount;              //购买数量
    private int maxAvailableAmount;     //最大可选择数量
    private int minAvailableAmount;		//最小可卖数
    private String peopleType;          //AUDLT, CHILD
    private ProductSaleType saleType;   //按份卖 or 按人卖
    private List<StampCode> codes;		//券码

	public StampDetails getStamp() {
		return stamp;
	}

	public void setStamp(StampDetails stamp) {
		this.stamp = stamp;
	}

	public int getBuyAmount() {
		return buyAmount;
	}

	public void setBuyAmount(int buyAmount) {
		this.buyAmount = buyAmount;
	}

	public int getMaxAvailableAmount() {
		return maxAvailableAmount;
	}

	public void setMaxAvailableAmount(int maxAvailableAmount) {
		this.maxAvailableAmount = maxAvailableAmount;
	}

	public String getPeopleType() {
		return peopleType;
	}

	public void setPeopleType(String peopleType) {
		this.peopleType = peopleType;
	}

	public ProductSaleType getSaleType() {
		return saleType;
	}

	public void setSaleType(ProductSaleType saleType) {
		this.saleType = saleType;
	}

    public List<SimpleGoodsVo> getGoods() {
        return goods;
    }

    public void setGoods(List<SimpleGoodsVo> goods) {
        this.goods = goods;
    }

	public int getMinAvailableAmount() {
		return minAvailableAmount;
	}

	public void setMinAvailableAmount(int minAvailableAmount) {
		this.minAvailableAmount = minAvailableAmount;
	}

	public List<StampCode> getCodes() {
		return codes;
	}

	public void setCodes(List<StampCode> codes) {
		this.codes = codes;
	}

}
