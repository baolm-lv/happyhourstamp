/**
 * 
 */
package com.lvmama.vst.hhs.product.service;

import java.util.Date;
import java.util.List;

import com.lvmama.vst.hhs.model.product.ProductAddition;

/**
 * @author fengyonggang
 *
 */
public interface ProductAdditionService {

	/**
	 * 加载房差和保险
	 * @param adultQuantity
	 * @param childQuantity
	 * @param quantity
	 * @param selectDateStr
	 * @param productId
	 * @param startDistrictId
	 * @param addition
	 */
	void loadingAdditions(Long adultQuantity, Long childQuantity,
            Long quantity, Date visitDate, Long productId, Long startDistrictId, List<Long> changeHotelGoods, ProductAddition addition);
}
