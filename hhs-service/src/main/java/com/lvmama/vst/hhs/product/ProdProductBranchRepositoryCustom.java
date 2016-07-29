/**
 * 
 */
package com.lvmama.vst.hhs.product;

import java.util.Collection;
import java.util.List;

import com.lvmama.vst.hhs.product.dao.ProdProductBranchEntity;

/**
 * @author fengyonggang
 *
 */
public interface ProdProductBranchRepositoryCustom {

	ProdProductBranchEntity getByGoodsId(Long goodsId);
	
	List<ProdProductBranchEntity> getByGoodsIds(List<Long> goodsIds);
	
	List<ProdProductBranchEntity> getByPackageGroupId(Collection<Long> groupIds);
}
