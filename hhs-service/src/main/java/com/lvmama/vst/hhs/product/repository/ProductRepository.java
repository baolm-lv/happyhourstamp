package com.lvmama.vst.hhs.product.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.lvmama.vst.hhs.product.dao.ProdProductEntity;

@Repository
public interface ProductRepository extends JpaRepository<ProdProductEntity, Long> {
     
    ProdProductEntity getByProductId(Long productId);
    
    @Modifying
    @Query(" update ProdProductEntity s set s.productName = ?1 where s.categoryId=99 and s.productId = ?2")
    int updateProdProductGoodsName(String productName,Long productId);
    
    @Query(value = "select * from prod_product where product_id in(select product_id from supp_goods where supp_goods_id = ?1)", nativeQuery = true)
    ProdProductEntity getByGoodsId(Long goodsId);
    
}
