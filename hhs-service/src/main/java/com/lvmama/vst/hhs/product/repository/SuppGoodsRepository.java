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

import com.lvmama.vst.hhs.product.dao.SuppGoodEntity;

/**
 * @author fengyonggang
 *
 */
@Repository
public interface SuppGoodsRepository extends JpaRepository<SuppGoodEntity, Long> {

	@Query(value = "select g.* from supp_goods g where g.product_branch_id in(select p.object_id from prod_package_detail p where "
			+ "p.group_id = ?1 ) and g.pay_target = 'PREPAID' and g.api_flag = 'N' and g.cancel_flag='Y'", nativeQuery = true)
	List<SuppGoodEntity> getGroupHotelByGroupId(Long groupId);
	
	@Query("SELECT s FROM SuppGoodEntity s WHERE s.productBranchId in ?1 and s.payTarget = 'PREPAID' and s.apiFlag = 'N' and s.cancelFlag = 'Y'")
	List<SuppGoodEntity> getByProductBranchIdIn(List<BigDecimal> branchIds);
	
	@Query("SELECT s FROM SuppGoodEntity s WHERE s.productBranchId in ?1 and s.payTarget = 'PREPAID' and s.apiFlag = 'N' and s.cancelFlag = 'Y'")
	List<SuppGoodEntity> getPackageGoodsByProductBranchIdIn(List<BigDecimal> branchIds);
	
	@Query("SELECT s FROM SuppGoodEntity s WHERE s.suppGoodsId in ?1 and s.payTarget = 'PREPAID' and s.apiFlag = 'N' and s.cancelFlag = 'Y'")
	List<SuppGoodEntity> getPackageGoodsByGoodsIn(List<Long> goodsId);
	
	@Modifying
	@Query(" update SuppGoodEntity s set s.bu = ?1 where s.categoryId=99 and s.suppGoodsId = ?2")
	int updateGoodsBu(String bu,Long goodsId);
	
	@Modifying
    @Query(" update SuppGoodEntity s set s.goodsName = ?1 where s.categoryId=99 and s.suppGoodsId = ?2")
    int updateGoodsName(String goodsName,Long goodsId);
}
