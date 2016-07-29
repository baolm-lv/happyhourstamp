/**
 * 
 */
package com.lvmama.vst.hhs.product.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.lvmama.vst.hhs.model.product.BranchPropVO;
import com.lvmama.vst.hhs.product.dao.ProdProductBranchPropEntity;

/**
 * @author fengyonggang
 *
 */
@Repository
public interface ProdProductBranchPropRepository extends JpaRepository<ProdProductBranchPropEntity, Long> {

	@Query(value = "SELECT new com.lvmama.vst.hhs.model.product.BranchPropVO(p.product_branch_prop_id,p.prop_id,b.prop_name,p.prod_value,p.product_branch_id) "
			+ "FROM prod_product_branch_prop p inner join biz_branch_prop b on p.prop_id=b.prop_id "
			+ "WHERE p.product_branch_id in (?1)", nativeQuery = true)
	List<BranchPropVO> getProductBranchProps(String productBranchIds);
}
