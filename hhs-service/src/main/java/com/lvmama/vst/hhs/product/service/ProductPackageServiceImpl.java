/**
 * 
 */
package com.lvmama.vst.hhs.product.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lvmama.vst.comm.utils.DateUtil;
import com.lvmama.vst.hhs.common.utils.HhsConstants;
import com.lvmama.vst.hhs.product.dao.ProdPackageDetailEntity;
import com.lvmama.vst.hhs.product.dao.ProdPackageGroupEntity;
import com.lvmama.vst.hhs.product.dao.ProdPackageGroupHotelEntity;
import com.lvmama.vst.hhs.product.dao.ProdPackageGroupLineEntity;
import com.lvmama.vst.hhs.product.dao.ProdPackageGroupTicketEntity;
import com.lvmama.vst.hhs.product.dao.ProdPackageGroupTransportEntity;
import com.lvmama.vst.hhs.product.dao.SuppGoodEntity;
import com.lvmama.vst.hhs.product.repository.ProdPackageDetailRepository;
import com.lvmama.vst.hhs.product.repository.ProdPackageGroupHotelRepository;
import com.lvmama.vst.hhs.product.repository.ProdPackageGroupLineRepository;
import com.lvmama.vst.hhs.product.repository.ProdPackageGroupRepository;
import com.lvmama.vst.hhs.product.repository.ProdPackageGroupTicketRepository;
import com.lvmama.vst.hhs.product.repository.ProdPackageGroupTransportRepository;
import com.lvmama.vst.hhs.product.repository.SuppGoodsRepository;
import com.lvmama.vst.hhs.stamp.util.StampUtils;

/**
 * @author fengyonggang
 *
 */
@Service
public class ProductPackageServiceImpl implements ProductPackageService {

	@Autowired
	private ProdPackageGroupLineRepository packageGroupLineRepository;
	@Autowired
	private ProdPackageGroupTicketRepository packageGroupTicketRepository;
	@Autowired
	private ProdPackageGroupTransportRepository packageGroupTransportRepository;
	@Autowired
	private ProdPackageGroupHotelRepository packageGroupHotelRepository;
	@Autowired
	private ProdPackageGroupRepository packageGroupRepository;
	@Autowired
	private SuppGoodsRepository suppGoodsRepository;
	@Autowired
	private ProdPackageDetailRepository prodPackageDetailRepository;
	
	@Override
	@Transactional("oracleTransactionManager")
	public List<String> getGroupVisitTime(Long groupId, String groupType, Date packageDate) {
		List<String> visitTime = null;
		if(HhsConstants.LINE.equals(groupType)) {
			ProdPackageGroupLineEntity groupEntity = getPackageGroupLine(groupId);
			if(groupEntity != null)
				visitTime = StampUtils.parseVisitDate(groupEntity.getStartDay(), packageDate);
		} else if(HhsConstants.LINE_TICKET.equals(groupType)) {
			ProdPackageGroupTicketEntity groupEntity = getPackageGroupTicket(groupId);
			if(groupEntity != null)
				visitTime = StampUtils.parseVisitDate(groupEntity.getStartDay(), packageDate);
		} else if(HhsConstants.HOTEL.equals(groupType) || HhsConstants.CHANGE.equals(groupType)) {
			ProdPackageGroupHotelEntity groupEntity = getPackageGroupHotel(groupId);
			if(groupEntity != null)
				visitTime = StampUtils.parseVisitDate(groupEntity.getStayDays(), packageDate);
		} else if(HhsConstants.TRANSPORT.equals(groupType)) {
			ProdPackageGroupTransportEntity groupEntity = getPackageGroupTransport(groupId);
			if(groupEntity != null) {
				visitTime = new ArrayList<String>();
				if(groupEntity.getToStartDays() != null) {
					if(groupEntity.getToStartDays().intValue() > 1) 
						visitTime.add(DateUtil.formatSimpleDate(StampUtils.addDay(packageDate, groupEntity.getToStartDays().intValue() - 1)));
					else 
						visitTime.add(DateUtil.formatSimpleDate(packageDate));
				} 
				if(groupEntity.getBackStartDays() != null) {
					if(groupEntity.getBackStartDays().intValue() > 1)
						visitTime.add(DateUtil.formatSimpleDate(StampUtils.addDay(packageDate, groupEntity.getBackStartDays().intValue() - 1)));
					else 
						visitTime.add(DateUtil.formatSimpleDate(packageDate));
				}
			}
		}
		return visitTime;
	}
	
	@Override
    @Transactional("oracleTransactionManager")
	public ProdPackageGroupLineEntity getPackageGroupLine(Long groupId) {
		return packageGroupLineRepository.getFirstByGroupId(new BigDecimal(groupId));
	}
	
	@Override
    @Transactional("oracleTransactionManager")
	public ProdPackageGroupTicketEntity getPackageGroupTicket(Long groupId) {
		return packageGroupTicketRepository.getFirstByGroupId(new BigDecimal(groupId));
	}
	
	@Override
    @Transactional("oracleTransactionManager")
	public ProdPackageGroupTransportEntity getPackageGroupTransport(Long groupId) {
		return packageGroupTransportRepository.getFirstByGroupId(new BigDecimal(groupId));
	}
	
	@Override
    @Transactional("oracleTransactionManager")
	public ProdPackageGroupHotelEntity getPackageGroupHotel(Long groupId) {
		return packageGroupHotelRepository.getFirstByGroupId(new BigDecimal(groupId));
	}
	
	@Override
    @Transactional("oracleTransactionManager")
    public ProdPackageDetailEntity getProductPackageDetail(Long groupId, Long goodsId) {
    	SuppGoodEntity goodEntity = suppGoodsRepository.findOne(goodsId);
    	if(goodEntity == null) 
    		return null;
    	ProdPackageDetailEntity detail = prodPackageDetailRepository.getByGroupIdAndObjectIdAndObjectType(new BigDecimal(groupId), goodEntity.getProductBranchId(), "PROD_BRANCH");
    	if(detail == null) 
    		detail = prodPackageDetailRepository.getByGroupIdAndObjectIdAndObjectType(new BigDecimal(groupId), new BigDecimal(goodsId), "SUPP_GOODS");
    	return detail;
    }
	
	@Override
	@Transactional("oracleTransactionManager")
	public List<ProdPackageGroupEntity> getPackageGroupChangeByProduct(List<Long> productIds) {
		if(CollectionUtils.isEmpty(productIds))
			return null;
		return packageGroupRepository.getByProductIdAndGroupType(StampUtils.LongCollectionToBigDecimal(productIds), HhsConstants.CHANGE);
	}
}
