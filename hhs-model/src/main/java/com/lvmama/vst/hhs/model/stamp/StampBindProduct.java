package com.lvmama.vst.hhs.model.stamp;

import java.io.Serializable;
import java.util.List;

public class StampBindProduct implements Serializable{

    /**
     * 
     */
    private static final long serialVersionUID = 1603411190128263037L;
    
    
    private StampProduct boundMerchant;             //绑定产品  
    private List<StampGoods> goods;                 //主规格商品
    public StampProduct getBoundMerchant() {
        return boundMerchant;
    }
    public void setBoundMerchant(StampProduct boundMerchant) {
        this.boundMerchant = boundMerchant;
    }
    public List<StampGoods> getGoods() {
        return goods;
    }
    public void setGoods(List<StampGoods> goods) {
        this.goods = goods;
    }
    
    
    

}
