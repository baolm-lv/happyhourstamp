/**
 * 
 */
package com.lvmama.vst.hhs.product.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.lvmama.vst.hhs.product.dao.BizCategoryEntity;

/**
 * @author fengyonggang
 *
 */
@Repository
public interface BizCategoryRepository extends JpaRepository<BizCategoryEntity, Long>{

	@Query(value = "select c.* from biz_category c inner join prod_product p on c.category_id = p.category_id "
			+ "where p.product_id = ?1 ", nativeQuery = true)
	BizCategoryEntity getByProductId(Long productId);
}
