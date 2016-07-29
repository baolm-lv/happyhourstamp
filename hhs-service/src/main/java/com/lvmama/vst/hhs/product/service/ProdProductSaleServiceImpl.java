package com.lvmama.vst.hhs.product.service;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lvmama.vst.hhs.product.dao.ProdProductSaleReEntity;
import com.lvmama.vst.hhs.product.repository.ProdProductSaleReRepository;

@Service
public class ProdProductSaleServiceImpl implements ProdProductSaleService {
    
    @Autowired
    private ProdProductSaleReRepository  saleReRepository;

    @Override
    public ProdProductSaleReEntity getByProduct(Long productId) {
        
        return saleReRepository.getFirstByProductId(BigDecimal.valueOf(productId));
    }

}
