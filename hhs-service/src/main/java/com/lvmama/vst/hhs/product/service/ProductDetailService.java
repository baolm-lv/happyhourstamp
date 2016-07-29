/**
 * 
 */
package com.lvmama.vst.hhs.product.service;

import com.lvmama.vst.hhs.model.product.ProductAdditionRequest;
import com.lvmama.vst.hhs.model.product.ProductDetails;

/**
 * @author fengyonggang
 *
 */
public interface ProductDetailService {

	ProductDetails getProductDetails(Long productId, ProductAdditionRequest request);
}
