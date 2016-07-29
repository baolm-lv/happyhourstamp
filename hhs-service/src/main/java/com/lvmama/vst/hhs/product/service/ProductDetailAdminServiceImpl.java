/**
 * 
 */
package com.lvmama.vst.hhs.product.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lvmama.vst.hhs.model.admin.ChangeHotelVo;
import com.lvmama.vst.hhs.model.admin.HotelGoodsVo;
import com.lvmama.vst.hhs.model.admin.PackageChangeHotelGroupVo;
import com.lvmama.vst.hhs.model.admin.PackageHotelVo;
import com.lvmama.vst.hhs.model.admin.PackageLineVo;
import com.lvmama.vst.hhs.model.admin.PackageProductBranchVo;
import com.lvmama.vst.hhs.model.admin.PackageProductVo;
import com.lvmama.vst.hhs.model.admin.PackageTicketVo;
import com.lvmama.vst.hhs.model.admin.PackageTransportVo;
import com.lvmama.vst.hhs.model.admin.ProductGoodsVo;
import com.lvmama.vst.hhs.model.admin.SuppGoodsVo;
import com.lvmama.vst.hhs.model.admin.TransportPointVo;
import com.lvmama.vst.hhs.product.dao.BizCategoryEntity;
import com.lvmama.vst.hhs.product.dao.BizDistrictEntity;
import com.lvmama.vst.hhs.product.dao.ProdPackageDetailEntity;
import com.lvmama.vst.hhs.product.dao.ProdPackageGroupEntity;
import com.lvmama.vst.hhs.product.dao.ProdPackageGroupHotelEntity;
import com.lvmama.vst.hhs.product.dao.ProdPackageGroupLineEntity;
import com.lvmama.vst.hhs.product.dao.ProdPackageGroupTicketEntity;
import com.lvmama.vst.hhs.product.dao.ProdPackageGroupTransportEntity;
import com.lvmama.vst.hhs.product.dao.ProdProductBranchEntity;
import com.lvmama.vst.hhs.product.dao.ProdProductEntity;
import com.lvmama.vst.hhs.product.dao.ProdProductSaleReEntity;
import com.lvmama.vst.hhs.product.dao.SuppGoodEntity;
import com.lvmama.vst.hhs.product.repository.BizCategoryRepository;
import com.lvmama.vst.hhs.product.repository.BizDistrictRepository;
import com.lvmama.vst.hhs.product.repository.ProdPackageDetailRepository;
import com.lvmama.vst.hhs.product.repository.ProdPackageGroupHotelRepository;
import com.lvmama.vst.hhs.product.repository.ProdPackageGroupLineRepository;
import com.lvmama.vst.hhs.product.repository.ProdPackageGroupRepository;
import com.lvmama.vst.hhs.product.repository.ProdPackageGroupTicketRepository;
import com.lvmama.vst.hhs.product.repository.ProdPackageGroupTransportRepository;
import com.lvmama.vst.hhs.product.repository.ProdProductBranchRepository;
import com.lvmama.vst.hhs.product.repository.ProductRepository;
import com.lvmama.vst.hhs.product.repository.SuppGoodsRepository;
import com.lvmama.vst.hhs.stamp.util.StampUtils;

/**
 * @author fengyonggang
 *
 */
@Service
public class ProductDetailAdminServiceImpl extends ProductDetailServiceImpl implements ProductDetailAdminService {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ProductDetailAdminServiceImpl.class);
	
	@Autowired
	private ProductRepository productRepository;
	@Autowired
	private SuppGoodsRepository suppGoodsRepository;
	@Autowired
	private ProdProductBranchRepository productBranchRepository;
	@Autowired
	private BizCategoryRepository bizCategoryRepository;
	@Autowired
	private ProdPackageGroupRepository prodPackageGroupRepository;
	@Autowired
	private ProductService productService;
	@Autowired
	private ProdProductSaleService prodProductSaleService;
	@Autowired
	private ProdPackageGroupHotelRepository prodPackageGroupHotelRepository;
	@Autowired
	private ProdPackageDetailRepository prodPackageDetailRepository;
	@Autowired
	private ProdPackageGroupLineRepository prodPackageGroupLineRepository;
	@Autowired
	private ProdPackageGroupTicketRepository prodPackageGroupTicketRepository;
	@Autowired
	private BizDistrictRepository bizDistrictRepository;
	@Autowired
	private ProdPackageGroupTransportRepository prodPackageGroupTransportRepository;

	@Transactional("oracleTransactionManager")
	public ProductGoodsVo getProductGoods(Long productId) {
		ProdProductEntity productEntity = productRepository.findOne(productId);
		if(productEntity == null) {
			LOGGER.info("can not find product by Id {}", productId);
			return null;
		}
		List<ProdProductBranchEntity> productBranchs = productBranchRepository.getPrimaryByProductId(productId);
		if(CollectionUtils.isEmpty(productBranchs)) {
			LOGGER.debug("can not find any primary product branchs for addition product, productId: {}", productId);
			return null;
		}
		BizCategoryEntity category = bizCategoryRepository.getByProductId(productId);
		if(category == null) {
			LOGGER.debug("can not find category, productId: {}", productId);
			return null;
		}
		
		Map<Long, ProdProductBranchEntity> productBranchMap = translateProductBranch(productBranchs);
		
		ProductGoodsVo productGoodsVo = new ProductGoodsVo();
		productGoodsVo.setCategory(translateBizCategory(category));
		productGoodsVo.setProduct(translateProductVO(productEntity));

		List<SuppGoodEntity> goodEntitys = suppGoodsRepository.getByProductBranchIdIn(StampUtils.LongCollectionToBigDecimal(productBranchMap.keySet()));
		if(CollectionUtils.isNotEmpty(goodEntitys)) {
			List<SuppGoodsVo> goodsVoList = new ArrayList<SuppGoodsVo>();
			for(SuppGoodEntity goodEntity : goodEntitys) {
				ProdProductBranchEntity productBranch = productBranchMap.get(goodEntity.getProductBranchId().longValue());
				if(productBranch == null) {
					LOGGER.info("productBranchEntity is null, productBranchId: {}", goodEntity.getProductBranchId().longValue());
					continue;
				}
				if(goodEntity.getProductId().longValue() != productId) {
					LOGGER.debug("ignore goods {} which is not belong to product: {}", goodEntity.getProductId().longValue(), productId);
					continue;
				}
				SuppGoodsVo goodsVo = new SuppGoodsVo();
				goodsVo.setGoodsId(goodEntity.getSuppGoodsId());
				goodsVo.setGoodsName(goodEntity.getGoodsName());
				goodsVo.setProductBranchId(productBranch.getProductBranchId());
				goodsVo.setBranchName(productBranch.getBranchName());
				ProdProductEntity product = productService.getProduct(goodEntity.getProductId().longValue());
				goodsVo.setProductId(product.getProductId());
				goodsVo.setProductName(product.getProductName());
				goodsVo.setCategoryId(goodEntity.getCategoryId()==null?"":String.valueOf(goodEntity.getCategoryId().intValue()));
				goodsVo.setPeopleFlag(goodEntity.getGoodsSpec());
				goodsVo.setAudlt(goodEntity.getAdult()==null?0L:goodEntity.getAdult().longValue());
				goodsVo.setChild(goodEntity.getChild()==null?0L:goodEntity.getChild().longValue());
				goodsVoList.add(goodsVo);
			}
			if(goodsVoList.size() > 0) {
				if("hotel".equals(category.getProcessKey())) {
					productGoodsVo.setHotelGoods(translateHotelGoods(goodsVoList));
				} else {
					productGoodsVo.setPrimaryGoods(goodsVoList);
				}
			}
		}
		
		List<ProdPackageGroupEntity> packageGroupEntitys = prodPackageGroupRepository.getByProductId(new BigDecimal(productId));
		if(CollectionUtils.isNotEmpty(packageGroupEntitys)) {
			for(ProdPackageGroupEntity packageGroupEntity : packageGroupEntitys) {
				if("CHANGE".equals(packageGroupEntity.getGroupType())) {
					productGoodsVo.addPackageHotels(getPackageHotelVO(productEntity, packageGroupEntity));
				} else if("HOTEL".equals(packageGroupEntity.getGroupType())) {
					productGoodsVo.addPackageHotels(getPackageHotelVO(productEntity, packageGroupEntity));
				} else if("LINE".equals(packageGroupEntity.getGroupType())) {
					productGoodsVo.addPackageLines(getPackageLineVO(productEntity, packageGroupEntity));
				} else if("LINE_TICKET".equals(packageGroupEntity.getGroupType())) {
					productGoodsVo.addPackageTickets(getPackageTicketVO(productEntity, packageGroupEntity));
				} else if("TRANSPORT".equals(packageGroupEntity.getGroupType())) {
					productGoodsVo.addPackageTransports(getPackageTransportVO(productEntity, packageGroupEntity));
				} else {
					LOGGER.info("unsupport the group type: {}", packageGroupEntity.getGroupType());
				}
			}
		}

		return productGoodsVo;
		
	}
	
	private List<HotelGoodsVo> translateHotelGoods(List<SuppGoodsVo> goodsVoList) {
		if(goodsVoList == null) 
			return null;
		Map<Long, List<SuppGoodsVo>> goodsMap = new HashMap<Long, List<SuppGoodsVo>>();
		Map<Long, String> branchMap = new HashMap<Long, String>();
		for(SuppGoodsVo goods : goodsVoList) {
			if(goodsMap.containsKey(goods.getProductBranchId())) {
				goodsMap.get(goods.getProductBranchId()).add(goods);
			} else {
				List<SuppGoodsVo> list = new ArrayList<SuppGoodsVo>();
				list.add(goods);
				goodsMap.put(goods.getProductBranchId(), list);
			}
			branchMap.put(goods.getProductBranchId(), goods.getBranchName());
		}
		
		List<HotelGoodsVo> voList = new ArrayList<HotelGoodsVo>();
		for(Entry<Long, List<SuppGoodsVo>> entry : goodsMap.entrySet()) {
			HotelGoodsVo vo = new HotelGoodsVo();
			vo.setProductBranchId(entry.getKey());
			vo.setGoods(entry.getValue());
			vo.setBranchName(branchMap.get(entry.getKey()));
			voList.add(vo);
		}
		return voList;
	}
	
	private PackageTransportVo getPackageTransportVO(ProdProductEntity productEntity, ProdPackageGroupEntity packageGroupEntity) {
		if(productEntity == null || packageGroupEntity == null) {
			return null;
		}
		ProdPackageGroupTransportEntity groupTicket = prodPackageGroupTransportRepository.getFirstByGroupId(new BigDecimal(packageGroupEntity.getGroupId()));
		if(groupTicket == null) {
			LOGGER.info("can not find package group transport, groupId: {}", packageGroupEntity.getGroupId());
			return null;
		}
		List<ProdPackageDetailEntity> packageDetails = prodPackageDetailRepository.getByGroupId(new BigDecimal(packageGroupEntity.getGroupId()));
		  Map<Long,Long> districtIdsMap = new HashMap<Long,Long>();
	        if(CollectionUtils.isNotEmpty(packageDetails)){
	            for(ProdPackageDetailEntity detail:packageDetails){
	                if(detail.getObjectId()!=null && detail.getStartDistrictId() !=null)
	                districtIdsMap.put(detail.getObjectId().longValue(), detail.getStartDistrictId().longValue());
	            }
	        }
		List<SuppGoodEntity> goodsList = getPackageGoods(packageDetails);
		if(CollectionUtils.isEmpty(goodsList)) {
			LOGGER.info("can not find goods entity, groupId: {}", packageGroupEntity.getGroupId());
			return null;
		}

		List<ProdProductBranchEntity> productBranchs = productBranchRepository.findAll(extractProductBranchIds(goodsList));
		Map<Long, ProdProductBranchEntity> productBranchMap = translateProductBranch(productBranchs);

		List<Long> districtIds = new ArrayList<Long>();
		if(groupTicket.getToDestination()!=null){
		    districtIds.add(groupTicket.getToDestination().longValue());
		}
		if(groupTicket.getToStartPoint()!=null){
		    districtIds.add(groupTicket.getToStartPoint().longValue());
		}
		if(groupTicket.getBackDestination() !=null ){
		    districtIds.add(groupTicket.getBackDestination().longValue());
		}
		if(groupTicket.getBackStartPoint()!=null){
		    districtIds.add(groupTicket.getBackStartPoint().longValue());
		}
		if(CollectionUtils.isNotEmpty(packageDetails)){
		    for(ProdPackageDetailEntity detail :packageDetails){
		        if(detail.getStartDistrictId() != null){
		            districtIds.add(detail.getStartDistrictId().longValue());
		        }
		       
		    }
		}
		
		List<BizDistrictEntity> districtList = bizDistrictRepository.findAll(districtIds);
		Map<Long, BizDistrictEntity> districtMap = translateDistrict(districtList);
		
		BizDistrictEntity tmpDist = null;
		
		List<SuppGoodsVo> volist = new ArrayList<SuppGoodsVo>();
		//按份卖处理
        copiesHandleGoods(productEntity.getProductId(), goodsList);
		for(SuppGoodEntity goodEntity : goodsList) {
			ProdProductBranchEntity productBranchEntity = productBranchMap.get(goodEntity.getProductBranchId().longValue());
			if(productBranchEntity == null) {
				LOGGER.info("can not find product branch, productBranchId: {}", goodEntity.getProductBranchId().longValue());
				continue;
			}
			SuppGoodsVo hotel = new SuppGoodsVo();
			hotel.setProductBranchId(goodEntity.getProductBranchId().longValue());
			hotel.setBranchName(productBranchEntity.getBranchName());
			hotel.setGoodsId(goodEntity.getSuppGoodsId());
			hotel.setGoodsName(goodEntity.getGoodsName());
			ProdProductEntity product = productService.getProduct(goodEntity.getProductId().longValue());
            hotel.setProductId(product.getProductId());
            hotel.setProductName(product.getProductName());
			hotel.setCategoryId(goodEntity.getCategoryId()==null?"":String.valueOf(goodEntity.getCategoryId().intValue()));
            hotel.setPeopleFlag(goodEntity.getGoodsSpec());
            hotel.setAudlt(goodEntity.getAdult()==null?0L:goodEntity.getAdult().longValue());
            hotel.setChild(goodEntity.getChild()==null?0L:goodEntity.getChild().longValue());
            //封装  大交通的 出发地 和目的地
            TransportPointVo pointVo = new TransportPointVo();
            if(groupTicket.getToStartPoint()!=null){
                if((tmpDist = districtMap.get(districtIdsMap.get(hotel.getProductBranchId()))) != null){
                    pointVo.setToStartPoint(districtIdsMap.get(hotel.getProductBranchId()));
                    pointVo.setToStartPointName(tmpDist.getDistrictName());
                }else if((tmpDist = districtMap.get(groupTicket.getToStartPoint().longValue())) != null){
                    pointVo.setToStartPoint(groupTicket.getToStartPoint().longValue());
                    pointVo.setToStartPointName(tmpDist.getDistrictName());
                }
               
            }
            if(groupTicket.getToDestination()!=null){
                if((tmpDist = districtMap.get(groupTicket.getToDestination().longValue())) != null){
                    pointVo.setToDestination(groupTicket.getToDestination().longValue());
                    pointVo.setToDestinationName(tmpDist.getDistrictName());
                }
                  
            }
            if(groupTicket.getBackDestination() !=null ){
                if((tmpDist = districtMap.get(districtIdsMap.get(hotel.getProductBranchId()))) != null){
                    pointVo.setBackDestination(districtIdsMap.get(hotel.getProductBranchId()));
                    pointVo.setBackDestinationName(tmpDist.getDistrictName());
                }else if((tmpDist = districtMap.get(groupTicket.getBackDestination().longValue())) != null){
                    pointVo.setBackDestination(groupTicket.getBackDestination().longValue());
                    pointVo.setBackDestinationName(tmpDist.getDistrictName());
                }
            }
            if(groupTicket.getBackStartPoint()!=null){
                if((tmpDist = districtMap.get(groupTicket.getBackStartPoint().longValue())) != null){
                    pointVo.setBackStartPoint(groupTicket.getBackStartPoint().longValue());
                    pointVo.setBackStartPointName(tmpDist.getDistrictName());
                }
            }
            hotel.setTransportPoint(pointVo);
            
			volist.add(hotel);
		}
		
		if(CollectionUtils.isEmpty(volist)) {
			LOGGER.info("can not find goods, groupId: {}", packageGroupEntity.getGroupId());
			return null;
		}
		
		PackageTransportVo transportVo = new PackageTransportVo();
		transportVo.setGroupTransportId(groupTicket.getGroupTransportId());
		transportVo.setGroupId(packageGroupEntity.getGroupId());
		if(groupTicket.getToDestination() != null) {
			transportVo.setToDestination(groupTicket.getToDestination().longValue());
			if((tmpDist = districtMap.get(groupTicket.getToDestination().longValue())) != null)
				transportVo.setToDestinationName(tmpDist.getDistrictName());
		}
		if(groupTicket.getToStartPoint() != null) {
			transportVo.setToStartPoint(groupTicket.getToStartPoint().longValue());
			if((tmpDist = districtMap.get(groupTicket.getToStartPoint().longValue())) != null)
				transportVo.setToStartPointName(tmpDist.getDistrictName());
		}
		if(groupTicket.getToStartDays() != null) {
			transportVo.setToStartDay(groupTicket.getToStartDays().intValue());
		}
		if(groupTicket.getBackDestination() !=null){
			transportVo.setBackDestination(groupTicket.getBackDestination().longValue());
		}
	    if(groupTicket.getBackDestination() !=null){
	        if((tmpDist = districtMap.get(groupTicket.getBackDestination().longValue())) != null)
	            transportVo.setBackDestinationName(tmpDist.getDistrictName());
	    }
	    
	    if(groupTicket.getBackStartPoint()!=null){
	        transportVo.setBackStartPoint(groupTicket.getBackStartPoint().longValue());
	        if((tmpDist = districtMap.get(groupTicket.getBackStartPoint().longValue())) != null)
	            transportVo.setBackStartPointName(tmpDist.getDistrictName());
	    }
		
		if(groupTicket.getBackStartDays() !=null){
		    transportVo.setBackStartDay(groupTicket.getBackStartDays().intValue());
		}
		
		transportVo.setTransportType(groupTicket.getTransportType());
		transportVo.setGoods(volist);
		
		  //封装产品  规格 商品
		transportVo.setProducts(getInnerPackageProductVo(volist));
		//可换酒店
       // lineChangeHotel(transportVo.getProducts());
		return transportVo;
	}
	
	private PackageTicketVo getPackageTicketVO(ProdProductEntity productEntity, ProdPackageGroupEntity packageGroupEntity) {
		if(productEntity == null || packageGroupEntity == null) {
			return null;
		}
		ProdPackageGroupTicketEntity groupTicket = prodPackageGroupTicketRepository.getFirstByGroupId(new BigDecimal(packageGroupEntity.getGroupId()));
		if(groupTicket == null) {
			LOGGER.info("can not find package group ticket, groupId: {}", packageGroupEntity.getGroupId());
			return null;
		}
		List<ProdPackageDetailEntity> packageDetails = prodPackageDetailRepository.getByGroupId(new BigDecimal(packageGroupEntity.getGroupId()));
	
		List<SuppGoodEntity> goodsList = getPackageGoods(packageDetails);
		if(CollectionUtils.isEmpty(goodsList)) {
			LOGGER.info("can not find goods entity, groupId: {}", packageGroupEntity.getGroupId());
			return null;
		}

		List<ProdProductBranchEntity> productBranchs = productBranchRepository.findAll(extractProductBranchIds(goodsList));
		Map<Long, ProdProductBranchEntity> productBranchMap = translateProductBranch(productBranchs);
		
		List<SuppGoodsVo> volist = new ArrayList<SuppGoodsVo>();
		//按份卖处理
        copiesHandleGoods(productEntity.getProductId(), goodsList);
		for(SuppGoodEntity goodEntity : goodsList) {
			ProdProductBranchEntity productBranchEntity = productBranchMap.get(goodEntity.getProductBranchId().longValue());
			if(productBranchEntity == null) {
				LOGGER.info("can not find product branch, productBranchId: {}", goodEntity.getProductBranchId().longValue());
				continue;
			}
			SuppGoodsVo hotel = new SuppGoodsVo();
			hotel.setProductBranchId(goodEntity.getProductBranchId().longValue());
			hotel.setBranchName(productBranchEntity.getBranchName());
			hotel.setGoodsId(goodEntity.getSuppGoodsId());
			hotel.setGoodsName(goodEntity.getGoodsName());
			ProdProductEntity product = productService.getProduct(goodEntity.getProductId().longValue());
            hotel.setProductId(product.getProductId());
            hotel.setProductName(product.getProductName());
			hotel.setCategoryId(goodEntity.getCategoryId()==null?"":String.valueOf(goodEntity.getCategoryId().intValue()));
            hotel.setPeopleFlag(goodEntity.getGoodsSpec());
            hotel.setAudlt(goodEntity.getAdult()==null?0L:goodEntity.getAdult().longValue());
            hotel.setChild(goodEntity.getChild()==null?0L:goodEntity.getChild().longValue());
			volist.add(hotel);
		}
		if(CollectionUtils.isEmpty(volist)) {
			LOGGER.info("can not find goods, groupId: {}", packageGroupEntity.getGroupId());
			return null;
		}
		
		PackageTicketVo ticketVo = new PackageTicketVo();
		ticketVo.setGroupTicketId(groupTicket.getGroupTicketId());
		ticketVo.setGroupId(packageGroupEntity.getGroupId());
		ticketVo.setDay(groupTicket.getStartDay());
		ticketVo.setGoods(volist);
	
		  //封装产品  规格 商品
		ticketVo.setProducts(getInnerPackageProductVo(volist));
		//可换酒店
       // lineChangeHotel(ticketVo.getProducts());
		return ticketVo;
	}
	
	private PackageLineVo getPackageLineVO(ProdProductEntity productEntity, ProdPackageGroupEntity packageGroupEntity) {
		if(productEntity == null || packageGroupEntity == null) {
			return null;
		}
		ProdPackageGroupLineEntity groupLine = prodPackageGroupLineRepository.getFirstByGroupId(new BigDecimal(packageGroupEntity.getGroupId()));
		if(groupLine == null) {
			LOGGER.info("can not find package group line, groupId: {}", packageGroupEntity.getGroupId());
			return null;
		}
		List<ProdPackageDetailEntity> packageDetails = prodPackageDetailRepository.getByGroupId(new BigDecimal(packageGroupEntity.getGroupId()));
		List<SuppGoodEntity> goodsList = getPackageGoods(packageDetails);
		if(CollectionUtils.isEmpty(goodsList)) {
			LOGGER.info("can not find goods entity, groupId: {}", packageGroupEntity.getGroupId());
			return null;
		}

		List<ProdProductBranchEntity> productBranchs = productBranchRepository.findAll(extractProductBranchIds(goodsList));
		Map<Long, ProdProductBranchEntity> productBranchMap = translateProductBranch(productBranchs);
		
		List<SuppGoodsVo> volist = new ArrayList<SuppGoodsVo>();
		//按份卖处理
        copiesHandleGoods(productEntity.getProductId(), goodsList);
		for(SuppGoodEntity goodEntity : goodsList) {
			ProdProductBranchEntity productBranchEntity = productBranchMap.get(goodEntity.getProductBranchId().longValue());
			if(productBranchEntity == null) {
				LOGGER.info("can not find product branch, productBranchId: {}", goodEntity.getProductBranchId().longValue());
				continue;
			}
			SuppGoodsVo hotel = new SuppGoodsVo();
			hotel.setProductBranchId(goodEntity.getProductBranchId().longValue());
			hotel.setBranchName(productBranchEntity.getBranchName());
			hotel.setGoodsId(goodEntity.getSuppGoodsId());
			hotel.setGoodsName(goodEntity.getGoodsName());
			ProdProductEntity product = productService.getProduct(goodEntity.getProductId().longValue());
            hotel.setProductId(product.getProductId());
            hotel.setProductName(product.getProductName());
			hotel.setCategoryId(goodEntity.getCategoryId()==null?"":String.valueOf(goodEntity.getCategoryId().intValue()));
            hotel.setPeopleFlag(goodEntity.getGoodsSpec());
            hotel.setAudlt(goodEntity.getAdult()==null?0L:goodEntity.getAdult().longValue());
            hotel.setChild(goodEntity.getChild()==null?0L:goodEntity.getChild().longValue());
			volist.add(hotel);
		}
		
		if(CollectionUtils.isEmpty(volist)) {
			LOGGER.info("can not find goods, groupId: {}", packageGroupEntity.getGroupId());
			return null;
		}
		
		PackageLineVo lineVo = new PackageLineVo();
		lineVo.setGroupLineId(groupLine.getGroupLineId());
		lineVo.setGroupId(packageGroupEntity.getGroupId());
		lineVo.setStartDay(groupLine.getStartDay());
		lineVo.setTravelDay(String.valueOf(groupLine.getTravelDays()));
		lineVo.setStayDay(String.valueOf(groupLine.getStayDays()));
		lineVo.setRemark(groupLine.getRemark());
		lineVo.setGoods(volist);
		
		//封装产品  规格 商品
		lineVo.setProducts(getInnerPackageProductVo(volist));
		//可换酒店
		lineChangeHotel(lineVo.getProducts());
		return lineVo;
	}
	
	private PackageHotelVo getPackageHotelVO(ProdProductEntity productEntity, ProdPackageGroupEntity packageGroupEntity) {
		if(productEntity == null || packageGroupEntity == null) {
			return null;
		}
		ProdPackageGroupHotelEntity groupHotel = prodPackageGroupHotelRepository.getFirstByGroupId(new BigDecimal(packageGroupEntity.getGroupId()));
		if(groupHotel == null) {
			LOGGER.info("can not find package group hotel, groupId: {}", packageGroupEntity.getGroupId());
			return null;
		}
		List<ProdPackageDetailEntity> packageDetails = prodPackageDetailRepository.getByGroupId(new BigDecimal(packageGroupEntity.getGroupId()));
		List<SuppGoodEntity> goodsList = getPackageGoods(packageDetails);
		if(CollectionUtils.isEmpty(goodsList)) {
			LOGGER.debug("can not find goods entity, groupId: {}", packageGroupEntity.getGroupId());
			return null;
		}

		List<ProdProductBranchEntity> productBranchs = productBranchRepository.findAll(extractProductBranchIds(goodsList));
		Map<Long, ProdProductBranchEntity> productBranchMap = translateProductBranch(productBranchs);
		
		List<SuppGoodsVo> volist = new ArrayList<SuppGoodsVo>();
		//按份卖处理
        copiesHandleGoods(productEntity.getProductId(), goodsList);
		for(SuppGoodEntity goodEntity : goodsList) {
			ProdProductBranchEntity productBranchEntity = productBranchMap.get(goodEntity.getProductBranchId().longValue());
			if(productBranchEntity == null) {
				LOGGER.info("can not find product branch, productBranchId: {}", goodEntity.getProductBranchId().longValue());
				continue;
			}
			SuppGoodsVo hotel = new SuppGoodsVo();
			hotel.setProductBranchId(goodEntity.getProductBranchId().longValue());
			hotel.setBranchName(productBranchEntity.getBranchName());
			hotel.setGoodsId(goodEntity.getSuppGoodsId());
			hotel.setGoodsName(goodEntity.getGoodsName());
			ProdProductEntity product = productService.getProduct(goodEntity.getProductId().longValue());
            hotel.setProductId(product.getProductId());
            hotel.setProductName(product.getProductName());
			hotel.setCategoryId(goodEntity.getCategoryId()==null?"":String.valueOf(goodEntity.getCategoryId().intValue()));
			hotel.setPeopleFlag(goodEntity.getGoodsSpec());
			hotel.setAudlt(goodEntity.getAdult()==null?0L:goodEntity.getAdult().longValue());
			hotel.setChild(goodEntity.getChild()==null?0L:goodEntity.getChild().longValue());
			volist.add(hotel);
		}
		
		if(CollectionUtils.isEmpty(volist)) {
			LOGGER.debug("can not find goods, groupId: {}", packageGroupEntity.getGroupId());
			return null;
		}
		
		PackageHotelVo hotelVo = new PackageHotelVo();
		hotelVo.setGroupHotelId(groupHotel.getGroupHotelId());
		hotelVo.setGroupId(packageGroupEntity.getGroupId());
		hotelVo.setRemark(groupHotel.getRemark());
		if(StringUtils.isNotBlank(groupHotel.getStayDays())) {
			String [] stayDays = groupHotel.getStayDays().split(",");
			hotelVo.setNightStart(stayDays.length > 0 ? NumberUtils.toInt(stayDays[0], 1) : 1);
			hotelVo.setNightEnd(stayDays.length > 1 ? NumberUtils.toInt(stayDays[stayDays.length-1], hotelVo.getNightStart()) : hotelVo.getNightStart());
		} else {
			hotelVo.setNightStart(1);
			hotelVo.setNightEnd(1);
		}
		hotelVo.setGoods(volist);
		//封装产品  规格 商品
		hotelVo.setProducts(getInnerPackageProductVo(volist));
		//按份分组
		
		//封装产品  规格 商品
	//	hotelVo.setProducts(getInnerPackageProductVo(volist));
		return hotelVo;
	}
	
	private List<SuppGoodEntity> getPackageGoods(List<ProdPackageDetailEntity> packageDetails) {
		if(packageDetails == null) 
			return null;
		List<BigDecimal> productBranchIds = new ArrayList<BigDecimal>();
		List<Long> suppGoodsIds = new ArrayList<Long>();
		for(ProdPackageDetailEntity entity : packageDetails) {
			if("PROD_BRANCH".equals(entity.getObjectType())) {
				productBranchIds.add(entity.getObjectId());
			} else if("SUPP_GOODS".equals(entity.getObjectType())) {
				suppGoodsIds.add(entity.getObjectId().longValue());
			}
		}
		List<SuppGoodEntity> result = new ArrayList<SuppGoodEntity>();
		if(productBranchIds.size() > 0) {
			List<SuppGoodEntity> goods = suppGoodsRepository.getPackageGoodsByProductBranchIdIn(productBranchIds);
			result.addAll(goods);
		}
		if(suppGoodsIds.size() > 0) {
			List<SuppGoodEntity> goods = suppGoodsRepository.getPackageGoodsByGoodsIn(suppGoodsIds);
			result.addAll(goods);
		}
		return result;
	}
	
	
	private List<Long> extractProductBranchIds(List<SuppGoodEntity> goodsList) {
		if(goodsList == null) 
			return null;
		List<Long> list = new ArrayList<Long>();
		for(SuppGoodEntity entity : goodsList) {
			list.add(entity.getProductBranchId().longValue());
		}
		return list;
	}
	
	private void lineChangeHotel(List<PackageProductVo> products){
	    if(CollectionUtils.isNotEmpty(products)){
	        for(PackageProductVo vo:products){
	            vo.setChangeHoteles(productService.getChangeHotel(vo.getProductId()));
	            //分组可换酒店
	           vo.setChangeHotelGroup(packageChangeHotelGroupVo(vo.getChangeHoteles()));
	        }
	    }
	}
	
	private List<PackageChangeHotelGroupVo> packageChangeHotelGroupVo(List<ChangeHotelVo> hotels){
	    List<PackageChangeHotelGroupVo> result = new ArrayList<PackageChangeHotelGroupVo>();
	    if(CollectionUtils.isNotEmpty(hotels)){
	        Map<Long,List<ChangeHotelVo>> groupHotel = new HashMap<Long,List<ChangeHotelVo>>();
	       
	        for(ChangeHotelVo hotel :hotels){
	            List<ChangeHotelVo> changHotels = groupHotel.get(hotel.getGroupId());
	            if(changHotels ==null){
	                changHotels = new ArrayList<ChangeHotelVo>();
	                PackageChangeHotelGroupVo  hotelGroup = new PackageChangeHotelGroupVo();
	                hotelGroup.setEnd(hotel.getEnd());
	                hotelGroup.setStart(hotel.getStart());
	                hotelGroup.setGroupId(hotel.getGroupId());
	                result.add(hotelGroup);
	            }
	            changHotels.add(hotel);
              
                groupHotel.put(hotel.getGroupId(), changHotels);
	            
	        }
	        
	        if(CollectionUtils.isNotEmpty(result)){
	            for(PackageChangeHotelGroupVo vo :result){
	               vo.setChangeHotelVoes(groupHotel.get(vo.getGroupId()));
	                
	            }
	        }
	    }
	    
	    return result;
	}
	
	private void copiesHandleGoods(Long productId, List<SuppGoodEntity> goodsList) {
		ProdProductSaleReEntity saleRe = prodProductSaleService.getByProduct(productId);
		// boolean isCopies = false;
		if (saleRe != null) {
			if (saleRe.getSaleType().equals("COPIES")) {
				int adult = saleRe.getAdult() == null ? 0 : saleRe.getAdult().intValue();
				int child = saleRe.getChild() == null ? 0 : saleRe.getChild().intValue();
				// isCopies = true ;
				if (CollectionUtils.isNotEmpty(goodsList)) {
					Iterator<SuppGoodEntity> iterator = goodsList.iterator();
					while (iterator.hasNext()) {
						SuppGoodEntity goods = iterator.next();
						int adult1 = goods.getAdult() == null ? 0 : saleRe.getAdult().intValue();
						int child1 = goods.getChild() == null ? 0 : saleRe.getChild().intValue();
						if (adult == adult1 && child == child1 || adult1 == 1) {

						} else if (adult1 == 0 || child1 == 0) {

						} else {
							iterator.remove();
						}

					}
				}
			}
		}
		// return isCopies;
	}
	
	// 封装数据
	private List<PackageProductVo> getInnerPackageProductVo(List<SuppGoodsVo> suppGoodsList) {
		List<PackageProductVo> resultList = new ArrayList<PackageProductVo>();
		Map<Long, List<SuppGoodsVo>> map = new HashMap<Long, List<SuppGoodsVo>>();
		// 产品封装
		if (CollectionUtils.isNotEmpty(suppGoodsList)) {
			Long key = null;
			PackageProductVo packageProductVo = null;
			List<SuppGoodsVo> branchGoods = null;
			for (SuppGoodsVo goods : suppGoodsList) {
				key = goods.getProductId();
				branchGoods = map.get(key);
				if (null == branchGoods) {
					branchGoods = new ArrayList<SuppGoodsVo>();
					packageProductVo = new PackageProductVo();
					packageProductVo.setProductId(goods.getProductId());
					packageProductVo.setProductName(goods.getProductName());
					resultList.add(packageProductVo);
				}
				branchGoods.add(goods);
				map.put(key, branchGoods);
			}

		}
		// 商品封装
		if (CollectionUtils.isNotEmpty(resultList)) {

			for (PackageProductVo vo : resultList) {
				Map<Long, List<SuppGoodsVo>> mapBranch = new HashMap<Long, List<SuppGoodsVo>>();
				List<PackageProductBranchVo> branchList = new ArrayList<PackageProductBranchVo>();
				vo.setProductBranches(branchList);
				List<SuppGoodsVo> branchGoods = map.get(vo.getProductId());
				List<SuppGoodsVo> goodsList = null;
				PackageProductBranchVo branchVo = null;
				Long key = null;
				if (CollectionUtils.isNotEmpty(branchGoods)) {
					for (SuppGoodsVo goods : branchGoods) {
						key = goods.getProductBranchId();
						goodsList = mapBranch.get(key);
						if (null == goodsList) {
							goodsList = new ArrayList<SuppGoodsVo>();
							branchVo = new PackageProductBranchVo();
							branchVo.setProductBranchId(key);
							branchVo.setBranchName(goods.getBranchName());
							branchList.add(branchVo);
						}
						goodsList.add(goods);
						mapBranch.put(key, goodsList);
					}
					if (CollectionUtils.isNotEmpty(branchList)) {
						for (PackageProductBranchVo branch : branchList) {
							branch.setGoods(mapBranch.get(branch.getProductBranchId()));
						}
					}
				}

			}
		}
		return resultList;
	}

}
