/**
 * 
 */
package com.lvmama.vst.hhs.product.service;

import java.util.Date;
import java.util.List;

import com.lvmama.vst.hhs.product.dao.ProdPackageDetailEntity;
import com.lvmama.vst.hhs.product.dao.ProdPackageGroupEntity;
import com.lvmama.vst.hhs.product.dao.ProdPackageGroupHotelEntity;
import com.lvmama.vst.hhs.product.dao.ProdPackageGroupLineEntity;
import com.lvmama.vst.hhs.product.dao.ProdPackageGroupTicketEntity;
import com.lvmama.vst.hhs.product.dao.ProdPackageGroupTransportEntity;

/**
 * @author fengyonggang
 *
 */
public interface ProductPackageService {

	List<String> getGroupVisitTime(Long groupId, String groupType, Date visitTime);
	
	ProdPackageDetailEntity getProductPackageDetail(Long groupId, Long goodsId);
	
	ProdPackageGroupLineEntity getPackageGroupLine(Long groupId);
	
	ProdPackageGroupTicketEntity getPackageGroupTicket(Long groupId);
	
	ProdPackageGroupHotelEntity getPackageGroupHotel(Long groupId);
	
	ProdPackageGroupTransportEntity getPackageGroupTransport(Long groupId);
	
	List<ProdPackageGroupEntity> getPackageGroupChangeByProduct(List<Long> productIds);
	
}
