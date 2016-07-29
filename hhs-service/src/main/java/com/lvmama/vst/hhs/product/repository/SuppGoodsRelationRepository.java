/**
 * 
 */
package com.lvmama.vst.hhs.product.repository;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.lvmama.vst.hhs.product.dao.SuppGoodsRelationEntity;

/**
 * @author fengyonggang
 *
 */
@Repository
public interface SuppGoodsRelationRepository extends JpaRepository<SuppGoodsRelationEntity, Long>{

	@Query("select s from SuppGoodsRelationEntity s where s.secGoodsId in ?1 ")
	List<SuppGoodsRelationEntity> getBySecGoodsIdIn(List<BigDecimal> goodsIds);
}
