/**
 * 
 */
package com.lvmama.vst.hhs.product.repository;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.lvmama.vst.hhs.product.dao.ProdPackageGroupHotelEntity;

/**
 * @author fengyonggang
 *
 */
@Repository
public interface ProdPackageGroupHotelRepository extends JpaRepository<ProdPackageGroupHotelEntity, Long>{

	@Query(value = "select * from prod_package_group_hotel where group_id in "
			+ "(select group_id from prod_package_group where product_id = ?1 )", nativeQuery = true)
	List<ProdPackageGroupHotelEntity> getByProductId(Long productId);
	
	ProdPackageGroupHotelEntity getFirstByGroupId(BigDecimal groupId);
	
	@Query("select s from ProdPackageGroupHotelEntity s where s.groupId in ?1 ")
	List<ProdPackageGroupHotelEntity> getByGroupIdIn(List<BigDecimal> groupIds);
}
