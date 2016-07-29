/**
 * 
 */
package com.lvmama.vst.hhs.product.repository;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.lvmama.vst.hhs.product.ProdProductBranchRepositoryCustom;
import com.lvmama.vst.hhs.product.dao.ProdProductBranchEntity;

/**
 * @author fengyonggang
 *
 */
@Repository
public interface ProdProductBranchRepository extends JpaRepository<ProdProductBranchEntity, Long>, ProdProductBranchRepositoryCustom{

	List<ProdProductBranchEntity> getByProductId(BigDecimal productId);
	
	List<ProdProductBranchEntity> getByProductIdAndBranchId(BigDecimal productId, BigDecimal branchId);
	
	@Query(value = "select p.* from prod_product_branch p inner join biz_branch b on p.branch_id=b.branch_id "
			+ "where b.branch_code='addition' and p.product_id=?1 and p.cancel_flag='Y' and b.cancel_flag='Y'", nativeQuery = true)
	List<ProdProductBranchEntity> getAdditionByProudctId(long proudctId);
	
	@Query(value = "select p.* from prod_product_branch p inner join biz_branch b on p.branch_id=b.branch_id "
			+ "where b.attach_flag='Y' and p.product_id=?1 and p.cancel_flag='Y' and b.cancel_flag='Y'", nativeQuery = true)
	List<ProdProductBranchEntity> getPrimaryByProductId(long productId);
	
	@Modifying
    @Query(" update ProdProductBranchEntity s set s.branchName = ?1  where  s.productBranchId = ?2")
    int updateProdProductBranchBranchName(String branchName,Long productBranchId);
	
}
