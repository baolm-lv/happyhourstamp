/**
 * 
 */
package com.lvmama.vst.hhs.product.repository.impl;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.lvmama.vst.hhs.product.ProdProductBranchRepositoryCustom;
import com.lvmama.vst.hhs.product.dao.ProdProductBranchEntity;

/**
 * @author fengyonggang
 *
 */
@Repository
public class ProdProductBranchRepositoryImpl implements ProdProductBranchRepositoryCustom {

	@Autowired
	@Qualifier("oracleEntityManager")
	private EntityManager em;

	@SuppressWarnings("unchecked")
	@Override
	public List<ProdProductBranchEntity> getByGoodsIds(List<Long> goodsIds) {
		if (CollectionUtils.isEmpty(goodsIds))
			return null;
		String sql = "select p.* from prod_product_branch p inner join supp_goods g on p.product_branch_id = g.product_branch_id "
				+ "where g.supp_goods_id in (" + StringUtils.join(goodsIds, ",") + ")";
		Query query = em.createNativeQuery(sql, ProdProductBranchEntity.class);
		return query.getResultList();
	}
	
	@Override
	public ProdProductBranchEntity getByGoodsId(Long goodsId) {
		List<ProdProductBranchEntity> list = getByGoodsIds(Arrays.asList(goodsId));
		if(CollectionUtils.isNotEmpty(list))
			return list.get(0);
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ProdProductBranchEntity> getByPackageGroupId(Collection<Long> groupIds) {
		if (CollectionUtils.isEmpty(groupIds))
			return null;
		String sql = "select b.* from prod_product_branch b inner join prod_package_group g on b.product_id = g.product_id "
				+ "where g.group_id IN (" + StringUtils.join(groupIds, ",") + ")";
		Query query = em.createNativeQuery(sql, ProdProductBranchEntity.class);
		return query.getResultList();
	}
}
