/**
 * 
 */
package com.lvmama.vst.hhs.product.service;

import com.lvmama.vst.hhs.model.admin.ProductGoodsVo;

/**
 * @author fengyonggang
 *
 */
public interface ProductDetailAdminService {

	ProductGoodsVo getProductGoods(Long productId);
}
