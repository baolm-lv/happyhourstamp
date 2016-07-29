package com.lvmama.vst.hhs.product.service;

import com.lvmama.vst.hhs.product.dao.ProdProductSaleReEntity;

public interface ProdProductSaleService {
    
    ProdProductSaleReEntity  getByProduct(Long productId);

}
