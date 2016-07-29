/**
 * 
 */
package com.lvmama.vst.hhs.product.service;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.beanutils.BeanUtilsBean;
import org.apache.commons.beanutils.converters.BigDecimalConverter;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lvmama.vst.back.biz.po.BizDistrict;
import com.lvmama.vst.back.biz.po.BizFlight;
import com.lvmama.vst.back.biz.po.BizTrain;
import com.lvmama.vst.back.client.goods.service.SuppGoodsClientService;
import com.lvmama.vst.back.client.goods.service.SuppGoodsTimePriceClientService;
import com.lvmama.vst.back.client.prod.service.ProdTrafficClientService;
import com.lvmama.vst.back.goods.po.PresaleStampTimePrice;
import com.lvmama.vst.back.goods.po.SuppGoodsBaseTimePrice;
import com.lvmama.vst.back.goods.po.SuppGoodsDesc;
import com.lvmama.vst.back.goods.po.SuppGoodsExp;
import com.lvmama.vst.back.prod.po.ProdLineRoute;
import com.lvmama.vst.back.prod.po.ProdProduct;
import com.lvmama.vst.back.prod.po.ProdTrafficBus;
import com.lvmama.vst.back.prod.po.ProdTrafficFlight;
import com.lvmama.vst.back.prod.po.ProdTrafficGroup;
import com.lvmama.vst.back.prod.po.ProdTrafficTrain;
import com.lvmama.vst.back.prod.po.ProdProduct.PACKAGETYPE;
import com.lvmama.vst.back.prod.vo.ProdTrafficVO;
import com.lvmama.vst.comm.utils.DateUtil;
import com.lvmama.vst.comm.vo.ResultHandleT;
import com.lvmama.vst.comm.vo.Constant.SUB_PRODUCT_TYPE;
import com.lvmama.vst.comm.vo.order.BuyInfo;
import com.lvmama.vst.comm.vo.order.BuyInfo.Item;
import com.lvmama.vst.flight.client.goods.vo.FlightNoVo;
import com.lvmama.vst.hhs.common.utils.HhsConstants;
import com.lvmama.vst.hhs.model.admin.BizCategoryVo;
import com.lvmama.vst.hhs.model.admin.ProductSaleTypeVo;
import com.lvmama.vst.hhs.model.admin.ProductVo;
import com.lvmama.vst.hhs.model.common.Constant.SaleType;
import com.lvmama.vst.hhs.model.product.BranchPropVO;
import com.lvmama.vst.hhs.model.product.GoodsDesc;
import com.lvmama.vst.hhs.model.product.PackageHotel;
import com.lvmama.vst.hhs.model.product.PackageLine;
import com.lvmama.vst.hhs.model.product.PackageLineGroup;
import com.lvmama.vst.hhs.model.product.PackageTicket;
import com.lvmama.vst.hhs.model.product.PackageTransport;
import com.lvmama.vst.hhs.model.product.ProdTraffic;
import com.lvmama.vst.hhs.model.product.ProductAdditionRequest;
import com.lvmama.vst.hhs.model.product.ProductDetails;
import com.lvmama.vst.hhs.model.product.ProductSaleType;
import com.lvmama.vst.hhs.model.product.StampUsage;
import com.lvmama.vst.hhs.model.product.SuppGoods;
import com.lvmama.vst.hhs.model.product.TicketGoods;
import com.lvmama.vst.hhs.product.dao.BizCategoryEntity;
import com.lvmama.vst.hhs.product.dao.BizDistrictEntity;
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
import com.lvmama.vst.hhs.product.repository.ProdPackageGroupHotelRepository;
import com.lvmama.vst.hhs.product.repository.ProdPackageGroupLineRepository;
import com.lvmama.vst.hhs.product.repository.ProdPackageGroupRepository;
import com.lvmama.vst.hhs.product.repository.ProdPackageGroupTicketRepository;
import com.lvmama.vst.hhs.product.repository.ProdPackageGroupTransportRepository;
import com.lvmama.vst.hhs.product.repository.ProdProductBranchRepository;
import com.lvmama.vst.hhs.product.repository.ProductRepository;
import com.lvmama.vst.hhs.product.repository.SuppGoodsRepository;
import com.lvmama.vst.hhs.stamp.util.StampUtils;
import com.lvmama.vst.hhs.stampDefinition.bo.StampGoodsBindingBo;
import com.lvmama.vst.hhs.stampDefinition.bo.StampGroupGoodsBo;
import com.lvmama.vst.hhs.stampDefinition.bo.StampPackageGoodsBo;
import com.lvmama.vst.hhs.stampDefinition.dao.PresaleStampDefinitionGoodsBindingEntity;
import com.lvmama.vst.hhs.stampDefinition.service.StampGoodsService;

/**
 * @author fengyonggang
 *
 */
@Service
public class ProductDetailServiceImpl implements ProductDetailService {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ProductDetailServiceImpl.class);
	@Autowired
	private ProductRepository productRepository;
	@Autowired
	private ProdProductBranchRepository productBranchRepository;
	@Autowired
	private BizCategoryRepository bizCategoryRepository;
	@Autowired
	private SuppGoodsRepository suppGoodsRepository;
	@Autowired
	private ProdPackageGroupHotelRepository prodPackageGroupHotelRepository;
	@Autowired
	private ProdPackageGroupLineRepository prodPackageGroupLineRepository;
	@Autowired
	private ProdPackageGroupRepository prodPackageGroupRepository;
	@Autowired
	private ProdPackageGroupTicketRepository prodPackageGroupTicketRepository;
	@Autowired
	private BizDistrictRepository bizDistrictRepository;
	@Autowired
	private ProdPackageGroupTransportRepository prodPackageGroupTransportRepository;
	@Autowired
	private ProductService productService;
	@Autowired
	private StampGoodsService stampGoodsService;
	@Autowired
	private ProdProductSaleService prodProductSaleService;
	@Autowired
	private SuppGoodsTimePriceClientService suppGoodsTimePriceClientService;
	@Autowired
	private SuppGoodsClientService suppGoodsClientService;
	@Autowired
	private ProdTrafficClientService prodTrafficClientService;
	@Autowired
	private ProdTrafficClientService prodTrafficClientServiceRemote;
	
	
	@Override
	@Transactional("oracleTransactionManager")
	public ProductDetails getProductDetails(Long productId, ProductAdditionRequest request) {

		if(CollectionUtils.isEmpty(request.getStamps())) {
			throw new IllegalArgumentException("stamp usage should not be null");
		}
		ProdProductEntity productEntity = productRepository.findOne(productId);
		if(productEntity == null) {
			throw new RuntimeException("can not find product by Id " + productId);
		}
		List<ProdProductBranchEntity> productBranchs = productBranchRepository.getByProductId(new BigDecimal(productId));
		if(CollectionUtils.isEmpty(productBranchs)) {
			throw new RuntimeException("can not find any primary product branchs for addition product, productId: " + productId);
		}
		BizCategoryEntity category = bizCategoryRepository.getByProductId(productId);
		if(category == null) {
			throw new RuntimeException("can not find category, productId: " + productId);
		}
		Map<Long, ProdProductBranchEntity> productBranchMap = translateProductBranch(productBranchs);
		
		Date visitDate = null;
		try {
			visitDate = DateUtils.parseDate(request.getVisitTime(), "yyyy-MM-dd");
		} catch (ParseException e) {
			throw new IllegalArgumentException("the visitTime is invalid: " + request.getVisitTime());
		}
		
		ProductDetails productDetail = new ProductDetails();
		productDetail.setTrafficInfoList(getTrafficInfo(productEntity));
		productDetail.setCategory(translateBizCategory(category));
    	productDetail.setProduct(translateProductVO(productEntity));
    	productDetail.setSaleType(productService.getProductSaleTypeByStampId(request.getStamps().get(0).getStampId()));

    	Map<Long, PackageHotel> changeHotelMap = new HashMap<Long, PackageHotel>();
    	Map<Long, SuppGoods> primaryGoodsMap = new HashMap<Long, SuppGoods>();
    	List<StampPackageGoodsBo> packages = null;
    	int amount = 0;
    	boolean childStamp = false;
    	for(StampUsage usage : request.getStamps()) {
    		StampGoodsBindingBo goodsBo = stampGoodsService.getStampGoodsByStampId(usage.getStampId());
    		if(goodsBo == null) 
    			throw new RuntimeException("Data issue, can not find stamp goods based on stampid: " + usage.getStampId());
    		
    		childStamp = HhsConstants.CHILD.equals(goodsBo.getType());//是否儿童券
    		
    		PresaleStampDefinitionGoodsBindingEntity goodsBindingEntity = null;
    		if((goodsBindingEntity = goodsBo.getGoods()) != null) {
    			if(primaryGoodsMap.containsKey(NumberUtils.toLong(goodsBindingEntity.getGoodsId()))) {
    				SuppGoods suppGood = primaryGoodsMap.get(NumberUtils.toLong(goodsBindingEntity.getGoodsId()));
    				if(SaleType.PEOPLE.name().equals(productDetail.getSaleType().getSaleType())) { //按人卖
        				if(childStamp)  
        					suppGood.setChildQuantity(suppGood.getChildQuantity() + usage.getAmount());
        				else
        					suppGood.setAudltQuantity(suppGood.getAudltQuantity() + usage.getAmount());
        			} else { //按份卖
        				suppGood.setAmount(suppGood.getAmount() + usage.getAmount());
        			}
    			} else {
    				SuppGoodEntity suppGoodsEntity = suppGoodsRepository.findOne(NumberUtils.toLong(goodsBindingEntity.getGoodsId()));
        			if(suppGoodsEntity == null) 
        				throw new RuntimeException("Data issue, can not find supp goods based on id: " + goodsBindingEntity.getGoodsId());
        			ProdProductBranchEntity productBranchEntity = productBranchMap.get(suppGoodsEntity.getProductBranchId().longValue());
        			if(productBranchEntity == null) 
        				throw new RuntimeException("Data issue, can not find product branch based on id: " + suppGoodsEntity.getProductBranchId().longValue());
        			
        			SuppGoods suppGood = new SuppGoods();
        			if(SaleType.PEOPLE.name().equals(productDetail.getSaleType().getSaleType())) { //按人卖
        				if(childStamp) 
        					suppGood.setChildQuantity(usage.getAmount());
        				else 
        					suppGood.setAudltQuantity(usage.getAmount());
        			} else { //按份卖
        				suppGood.setAmount(usage.getAmount());
        			}
        			suppGood.setBranchName(productBranchEntity.getBranchName());
        			suppGood.setGoodsId(suppGoodsEntity.getSuppGoodsId());
        			suppGood.setGoodsName(suppGoodsEntity.getGoodsName());
        			suppGood.setProductBranchId(productBranchEntity.getProductBranchId());
        			suppGood.setVisitTime(request.getVisitTime());
        			suppGood.setProps(productService.getProductBranchProps(Arrays.asList(productBranchEntity.getProductBranchId())));
        			primaryGoodsMap.put(suppGood.getGoodsId(), suppGood);
    			}
    		}  
    		
    		packages = goodsBo.getPackages();// 多个券同时使用时，绑定的商品Id是一样的，所以只需要计算一次
    		amount += usage.getAmount();
    	}
    	
    	if(CollectionUtils.isNotEmpty(packages)){
    		List<Long> goodsIds = extractGoodsIdFromPackageGoods(packages);
    		if(CollectionUtils.isNotEmpty(goodsIds)) {
    			//自主打包产品的prod_product_branch中不包括组内的商品的product_branch_id.
    			List<ProdProductBranchEntity> productBranchList = productBranchRepository.getByGoodsIds(goodsIds);
    			if(CollectionUtils.isNotEmpty(productBranchList)) {
    				for(ProdProductBranchEntity entity : productBranchList) {
    					productBranchMap.put(entity.getProductBranchId(), entity);
    				}
    			}
    		}
    		List<Long> productBranchIds = new ArrayList<Long>();
    		productBranchIds.addAll(productBranchMap.keySet());
    		List<BranchPropVO> branchProps = productService.getProductBranchProps(productBranchIds);
    		Map<Long, List<BranchPropVO>> propMap = translateProductBranchProp(branchProps);
    		
    		Map<Long, String> productVisitTime = new HashMap<Long, String>();
    		
    		for(StampPackageGoodsBo packageGood : packages) {
    			if("CHANGE".equals(packageGood.getGroupType())) { //可换酒店
    				List<PackageHotel> changeHotels = null;
    				if(productVisitTime.size() == 0) { //供应商打包
    					changeHotels = getPackageHotel(packageGood, amount, productBranchMap, visitDate, propMap, productDetail.getSaleType(), null);
    				} else {
    					changeHotels = getPackageHotel(packageGood, amount, productBranchMap, null, propMap, productDetail.getSaleType(), productVisitTime);
    				}
    				if(CollectionUtils.isNotEmpty(changeHotels)) {
    					for(PackageHotel ph : changeHotels) {
    						if(changeHotelMap.containsKey(ph.getGoods().getGoodsId())) {
    							SuppGoods suppGoods = changeHotelMap.get(ph.getGoods().getGoodsId()).getGoods();
    							suppGoods.setAmount(suppGoods.getAmount() + ph.getGoods().getAmount());
    							if(childStamp) 
    								suppGoods.setChildQuantity(suppGoods.getChildQuantity() + ph.getGoods().getChildQuantity());
    							else 
    								suppGoods.setAudltQuantity(suppGoods.getAudltQuantity() + ph.getGoods().getAudltQuantity());
    						} else {
    							changeHotelMap.put(ph.getGoods().getGoodsId(), ph);
    						}
    					}
    				}
    			} else if("HOTEL".equals(packageGood.getGroupType())) {
    				productDetail.addPackageHotels(getPackageHotel(packageGood, amount, productBranchMap, visitDate, propMap, productDetail.getSaleType(), null));
    			} else if("LINE".equals(packageGood.getGroupType())) {
    				productDetail.addPackageLines(getPackageLine(packageGood, amount, productBranchMap, visitDate, propMap, productDetail, productVisitTime));
    			} else if("LINE_TICKET".equals(packageGood.getGroupType())) {
    				productDetail.addPackageTickets(getPackageTicket(packageGood, amount, productBranchMap, visitDate, propMap, productDetail.getSaleType()));
    			} else if("TRANSPORT".equals(packageGood.getGroupType())) {
    				productDetail.addPackageTransports(getPackageTransport(packageGood, amount, productBranchMap, visitDate, propMap, productDetail.getSaleType()));
    			} else {
    				LOGGER.info("unsupport the group type: {}", packageGood.getGroupType());
    			}
    		}
    	}
    	
    	
    	if(changeHotelMap.size() > 0) {
    		if(PACKAGETYPE.LVMAMA.name().equals(productDetail.getProduct().getPackageType())) {
    			List<PackageHotel> list = new ArrayList<PackageHotel>();
    			list.addAll(changeHotelMap.values());
    			productDetail.setChangeHotels(list);
    		} else {
    			productDetail.addPackageHotels(changeHotelMap.values());
    		}
    	}
    	if(primaryGoodsMap.size() > 0) {
    		List<SuppGoods> goodsList = new ArrayList<SuppGoods>();
    		goodsList.addAll(primaryGoodsMap.values());
    		productDetail.setPrimaryGoods(goodsList);
    	}
    	return productDetail;
	}
	
	private List<PackageTransport> getPackageTransport(StampPackageGoodsBo packageGood, int amount, Map<Long, ProdProductBranchEntity> productBranchMap, Date visitTime, Map<Long, List<BranchPropVO>> propMap, ProductSaleType saleType) {
		if(packageGood == null || CollectionUtils.isEmpty(packageGood.getGroups())) {
			return null;
		}
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		List<PackageTransport> groups = new ArrayList<PackageTransport>();
		for(StampGroupGoodsBo groupGoodsBo : packageGood.getGroups()) {
			PackageTransport group = new PackageTransport();
			ProdPackageGroupTransportEntity groupTicket = prodPackageGroupTransportRepository.getFirstByGroupId(new BigDecimal(groupGoodsBo.getGroupId()));
			if(groupTicket == null) 
				throw new RuntimeException("Data issue, can not find package group transport based on groupId: " + groupGoodsBo.getGroupId());
			
			List<Long> districtIds = new ArrayList<Long>();
			if(groupTicket.getToDestination() != null)
				districtIds.add(groupTicket.getToDestination().longValue());
			if(groupTicket.getToStartPoint() != null)
				districtIds.add(groupTicket.getToStartPoint().longValue());
			if(groupTicket.getBackDestination() != null)
				districtIds.add(groupTicket.getBackDestination().longValue());
			if(groupTicket.getBackStartPoint() != null)
				districtIds.add(groupTicket.getBackStartPoint().longValue());

			List<BizDistrictEntity> districtList = null;
			if(districtIds.size() > 0) 
				districtList = bizDistrictRepository.findAll(districtIds);
			Map<Long, BizDistrictEntity> districtMap = translateDistrict(districtList);
			
			BizDistrictEntity tmpDist = null;
			group.setGroupId(groupGoodsBo.getGroupId());
			if(groupTicket.getToDestination() != null) {
				group.setToDestination(groupTicket.getToDestination().longValue());
				if((tmpDist = districtMap.get(groupTicket.getToDestination().longValue())) != null)
					group.setToDestinationName(tmpDist.getDistrictName());
			}
			if(groupTicket.getToStartPoint() != null) {
				group.setToStartPoint(groupTicket.getToStartPoint().longValue());
				if((tmpDist = districtMap.get(groupTicket.getToStartPoint().longValue())) != null)
					group.setToStartPointName(tmpDist.getDistrictName());
			}
			if(groupTicket.getBackDestination() != null) {
				group.setBackDestination(groupTicket.getBackDestination().longValue());
				if((tmpDist = districtMap.get(groupTicket.getBackDestination().longValue())) != null)
					group.setBackDestinationName(tmpDist.getDistrictName());
			}
			if(groupTicket.getBackStartPoint() != null) {
				group.setBackStartPoint(groupTicket.getBackStartPoint().longValue());
				if((tmpDist = districtMap.get(groupTicket.getBackStartPoint().longValue())) != null)
					group.setBackStartPointName(tmpDist.getDistrictName());
			}
			if(groupTicket.getToStartDays() != null && groupTicket.getToStartDays().intValue() > 1) {
				group.setToStartDate(format.format(StampUtils.addDay(visitTime, groupTicket.getToStartDays().intValue() - 1)));
			} else {
				group.setToStartDate(format.format(visitTime));
			}
			if(groupTicket.getBackStartDays() != null && groupTicket.getBackStartDays().intValue() > 1) {
				group.setBackStartDate(format.format(StampUtils.addDay(visitTime, groupTicket.getBackStartDays().intValue() - 1)));
			} else {
				group.setBackStartDate(format.format(visitTime));
			}
			group.setTransportType(groupTicket.getTransportType());
			
			PresaleStampDefinitionGoodsBindingEntity goodsBindingEntity = groupGoodsBo.getGoods();
			SuppGoodEntity suppGoodsEntity = suppGoodsRepository.findOne(NumberUtils.toLong(goodsBindingEntity.getGoodsId()));
			if(suppGoodsEntity == null) 
				throw new RuntimeException("Data issue, can not find supp goods based on id: " + goodsBindingEntity.getGoodsId());
			ProdProductBranchEntity productBranchEntity = productBranchMap.get(suppGoodsEntity.getProductBranchId().longValue());
			if(productBranchEntity == null) 
				throw new RuntimeException("Data issue, can not find product branch based on id: " + suppGoodsEntity.getProductBranchId().longValue());
			ProdProductEntity productEntity = productRepository.findOne(productBranchEntity.getProductId().longValue());
			if(productEntity == null) 
				throw new RuntimeException("Data issue, can not find product based on id: " + productBranchEntity.getProductId().longValue());
			
			SuppGoods suppGood = new SuppGoods();
			suppGood.setAmount(amount);
			if(SaleType.PEOPLE.name().equals(saleType.getSaleType())) {
				suppGood.setAudltQuantity(amount);
			} else {
				suppGood.setAudltQuantity(amount * saleType.getAdultQuantity());
				suppGood.setChildQuantity(amount * saleType.getChildQuantity());
			}
			suppGood.setBranchName(productBranchEntity.getBranchName());
			suppGood.setGoodsId(suppGoodsEntity.getSuppGoodsId());
			suppGood.setGoodsName(suppGoodsEntity.getGoodsName());
			suppGood.setProductBranchId(productBranchEntity.getProductBranchId());
			suppGood.setProductId(productEntity.getProductId());
			suppGood.setProductName(productEntity.getProductName());
			if(propMap != null)
				suppGood.setProps(propMap.get(productBranchEntity.getProductBranchId()));
			group.setGoods(suppGood);
			group.setGroupId(groupGoodsBo.getGroupId());
			ProdTrafficVO traffic = prodTrafficClientService.getProdTrafficVOByProductId(productEntity.getProductId());
			{ //无效机票过滤
    			
    			if(traffic ==null){
    			    continue ;
    			}
    			com.lvmama.vst.back.prod.po.ProdTraffic prodTraffic = traffic.getProdTraffic();// 交通信息表
    			String backType = prodTraffic.getBackType();
    	        String toType = prodTraffic.getToType();
    	      
    	        if ("FLIGHT".equals(backType) || "FLIGHT".equals(toType)) {
    	            Iterator<ProdTrafficGroup> groupIterator = traffic.getProdTrafficGroupList().iterator();
    	            while(groupIterator.hasNext()){
    	                ProdTrafficGroup prodTrafficGroup = groupIterator.next();
    	                if(CollectionUtils.isNotEmpty(prodTrafficGroup.getProdTrafficFlightList())){
    	                    int count = prodTrafficGroup.getProdTrafficFlightList().size();
                            Iterator<ProdTrafficFlight> flight = prodTrafficGroup.getProdTrafficFlightList().iterator();
                            int num = 0;
                            while(flight.hasNext()){
                                ProdTrafficFlight  f = flight.next();
                                if("N".equals(f.getCancelFlag())){
                                    num ++;
                                    flight.remove();
                                }
                            }
                            
                            if(count == num){
                                groupIterator.remove();
                            }
    	                }
    	              
    	            }
    	            
    	        }        
	        
			}
			com.lvmama.vst.hhs.model.product.ProdTrafficVO newTraffic = new com.lvmama.vst.hhs.model.product.ProdTrafficVO();
			BeanUtils.copyProperties(traffic, newTraffic);
			ProdTraffic newProdTraffic = new ProdTraffic();
			BeanUtils.copyProperties(traffic.getProdTraffic(), newProdTraffic);
			newTraffic.setProdTraffic(newProdTraffic);
			group.setTraffic(newTraffic);
			
			groups.add(group);
		}
		
		if(CollectionUtils.isNotEmpty(groups)) {
			Collections.sort(groups, new Comparator<PackageTransport>() {
				@Override
				public int compare(PackageTransport o1, PackageTransport o2) {
					long d1 = 0, d2 = 0;
					if(o1.getToStartPoint() != null && StringUtils.isNotBlank(o1.getToStartDate()))
						d1 = DateUtil.toSimpleDate(o1.getToStartDate()).getTime();
					else if(o1.getBackStartPoint() != null && StringUtils.isNotBlank(o1.getBackStartDate()))
						d1 = DateUtil.toSimpleDate(o1.getBackStartDate()).getTime();
					if(o2.getToStartPoint() != null && StringUtils.isNotBlank(o2.getToStartDate()))
						d2 = DateUtil.toSimpleDate(o2.getToStartDate()).getTime();
					else if(o2.getBackStartPoint() != null && StringUtils.isNotBlank(o2.getBackStartDate()))
						d2 = DateUtil.toSimpleDate(o2.getBackStartDate()).getTime();
					return d1 < d2 ? -1 : (d1 > d2 ? 1 : 0);
				}
			});
		}
		
		return groups;
	}
	
	private List<PackageTicket> getPackageTicket(StampPackageGoodsBo packageGood, int amount, Map<Long, ProdProductBranchEntity> productBranchMap, Date visitTime, Map<Long, List<BranchPropVO>> propMap, ProductSaleType saleType) {
		if(packageGood == null || CollectionUtils.isEmpty(packageGood.getGroups())) {
			return null;
		}
		Map<Long, PackageTicket> packageTicketMap = new HashMap<Long, PackageTicket>();
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		for(StampGroupGoodsBo groupGoodsBo : packageGood.getGroups()) {
			ProdPackageGroupTicketEntity groupTicket = prodPackageGroupTicketRepository.getFirstByGroupId(new BigDecimal(groupGoodsBo.getGroupId()));
			if(groupTicket == null) 
				throw new RuntimeException("Data issue, can not find package group ticket based on groupId: " + groupGoodsBo.getGroupId());
			PresaleStampDefinitionGoodsBindingEntity goodsBindingEntity = groupGoodsBo.getGoods();
			SuppGoodEntity suppGoodsEntity = suppGoodsRepository.findOne(NumberUtils.toLong(goodsBindingEntity.getGoodsId()));
			if(suppGoodsEntity == null) 
				throw new RuntimeException("Data issue, can not find supp goods based on id: " + goodsBindingEntity.getGoodsId());
			ProdProductBranchEntity productBranchEntity = productBranchMap.get(suppGoodsEntity.getProductBranchId().longValue());
			if(productBranchEntity == null) 
				throw new RuntimeException("Data issue, can not find product branch based on id: " + suppGoodsEntity.getProductBranchId().longValue());
			ProdProductEntity productEntity = productRepository.findOne(productBranchEntity.getProductId().longValue());
			if(productEntity == null) 
				throw new RuntimeException("Data issue, can not find product based on id: " + productBranchEntity.getProductId().longValue());
			
			PackageTicket group = null;
			if(packageTicketMap.containsKey(groupTicket.getGroupId().longValue())) {
				group = packageTicketMap.get(groupTicket.getGroupId().longValue());
			} else {
				group = new PackageTicket();
				if(StringUtils.isEmpty(groupTicket.getStartDay())) 
					throw new RuntimeException("Data issue, the start day is null, group ticket id: " + groupTicket.getGroupTicketId());
				
				List<String> dates = StampUtils.parseVisitDate(groupTicket.getStartDay(), visitTime);
				if(suppGoodsEntity.getLimitBookDay() != null && suppGoodsEntity.getLimitBookDay().intValue() > -1) {
					List<String> limitDates = StampUtils.getVisitDates(suppGoodsEntity.getLimitBookDay().intValue());
					dates.retainAll(limitDates);
				}
				group.setDates(dates);
				group.setGroupId(groupGoodsBo.getGroupId());
				packageTicketMap.put(groupTicket.getGroupId().longValue(), group);
			}
			
			SuppGoodsBaseTimePrice timeprice = null;
			Iterator<String> iter = group.getDates().iterator();
			while(iter.hasNext()) {
				String date = iter.next();
				Date d = null;
				try {
					d = format.parse(date);
				} catch (ParseException e) {
					LOGGER.error("invalid date format: {}", date, e);
					iter.remove();
					continue;
				}
				ResultHandleT<SuppGoodsBaseTimePrice> timePriceResult = suppGoodsTimePriceClientService.getBaseTimePrice(NumberUtils.toLong(groupGoodsBo.getGoods().getGoodsId()), d);
				if(timePriceResult.isSuccess() && (timeprice = timePriceResult.getReturnContent()) != null) {
					if("N".equals(timeprice.getOnsaleFlag())) {
						iter.remove();
						continue;
					}
					if(timeprice.getStock() != null && timeprice.getStock() != -1 && timeprice.getStock() < amount && "N".equals(timeprice.getOversellFlag())) {
						iter.remove();
						continue;
					}
				} else {
					LOGGER.info(timePriceResult.getMsg());
					iter.remove();
					continue;
				}
				List<PresaleStampTimePrice> stampDateList = suppGoodsTimePriceClientService.getPresaleStampTimePricesBygoodsIdAndTime(NumberUtils.toLong(groupGoodsBo.getGoods().getGoodsId()), d, d);
				if(CollectionUtils.isEmpty(stampDateList)) {
					iter.remove();
				}
			}
			
			if(CollectionUtils.isEmpty(group.getDates())) {
				throw new RuntimeException("no valid travel dates found, groupId: " + groupGoodsBo.getGroupId());
			}
			
			String ticketType = null;
			SuppGoods suppGood = new SuppGoods();
			suppGood.setAmount(amount);
			if(SaleType.PEOPLE.name().equals(saleType.getSaleType())) {
				suppGood.setAudltQuantity(amount);
				suppGood.setChildQuantity(0);
			} else {
				int adultChild = suppGoodsEntity.getAdult().intValue() + suppGoodsEntity.getChild().intValue();
				if(adultChild == 1) { //单门票  adult 和 child 有一个为0，一个为1
					ticketType = "single";
					suppGood.setAudltQuantity(suppGoodsEntity.getAdult().intValue() * saleType.getAdultQuantity() * amount);
					suppGood.setChildQuantity(suppGoodsEntity.getChild().intValue() * saleType.getChildQuantity() * amount);
				} else { //套票
					suppGood.setAmount(amount);
					ticketType = "suites";
				}
			}
			suppGood.setBranchName(productBranchEntity.getBranchName());
			suppGood.setGoodsId(suppGoodsEntity.getSuppGoodsId());
			suppGood.setGoodsName(suppGoodsEntity.getGoodsName());
			suppGood.setProductBranchId(productBranchEntity.getProductBranchId());
			suppGood.setProductId(productEntity.getProductId());
			suppGood.setProductName(productEntity.getProductName());
			SuppGoodsExp goodsExp = suppGoodsClientService.findTicketSuppGoodsExp(suppGoodsEntity.getSuppGoodsId());
			if(goodsExp != null) 
				suppGood.setValidPeriod(String.valueOf(goodsExp.getDays()));
			
			if(propMap != null)
				suppGood.setProps(propMap.get(productBranchEntity.getProductBranchId()));
			
			GoodsDesc desc = new GoodsDesc();
			ResultHandleT<SuppGoodsDesc> goodsDescResult = suppGoodsClientService.findSuppGoodsDescBySuppGoodsId(suppGoodsEntity.getSuppGoodsId());
			SuppGoodsDesc goodsDesc = null;
			if(goodsDescResult.isSuccess() && (goodsDesc = goodsDescResult.getReturnContent()) != null) {
				BeanUtils.copyProperties(goodsDesc, desc);
			} else {
				LOGGER.info(goodsDescResult.getMsg());
			}
			
			TicketGoods ticketGoods = new TicketGoods();
			ticketGoods.setGoods(suppGood);
			ticketGoods.setDesc(desc);
			ticketGoods.setTicketType(ticketType);
			
			group.addTicketGoods(ticketGoods);
		}
		
		List<PackageTicket> groups = new ArrayList<PackageTicket>();
		groups.addAll(packageTicketMap.values());
		return groups;
	}
	
	private List<PackageLine> getPackageLine(StampPackageGoodsBo packageGood, int amount, Map<Long, ProdProductBranchEntity> productBranchMap, Date visitTime, Map<Long, List<BranchPropVO>> propMap, ProductDetails productDetail, Map<Long, String> productVisitTime) {
		if(packageGood == null || CollectionUtils.isEmpty(packageGood.getGroups())) {
			return null;
		}
		ProductSaleType saleType = productDetail.getSaleType();
		Map<String, List<PackageLineGroup>> packageLineMap = new HashMap<String, List<PackageLineGroup>>();
		for(StampGroupGoodsBo groupGoodsBo : packageGood.getGroups()) {
			ProdPackageGroupLineEntity groupLine = prodPackageGroupLineRepository.getFirstByGroupId(new BigDecimal(groupGoodsBo.getGroupId()));
			if(groupLine == null) 
				throw new RuntimeException("Data issue, can not find package group line based on groupId: " + groupGoodsBo.getGroupId());
			PresaleStampDefinitionGoodsBindingEntity goodsBindingEntity = groupGoodsBo.getGoods();
			SuppGoodEntity suppGoodsEntity = suppGoodsRepository.findOne(NumberUtils.toLong(goodsBindingEntity.getGoodsId()));
			if(suppGoodsEntity == null) 
				throw new RuntimeException("Data issue, can not find supp goods based on id: " + goodsBindingEntity.getGoodsId());
			ProdProductBranchEntity productBranchEntity = productBranchMap.get(suppGoodsEntity.getProductBranchId().longValue());
			if(productBranchEntity == null) 
				throw new RuntimeException("Data issue, can not find product branch based on id: " + suppGoodsEntity.getProductBranchId().longValue());
			ProdPackageGroupEntity groupEntity = prodPackageGroupRepository.findOne(groupGoodsBo.getGroupId());
			if(groupEntity == null) 
				throw new RuntimeException("Data issue, can not find package group based on groupId : " + groupGoodsBo.getGroupId());
			BizCategoryEntity groupCategory = bizCategoryRepository.findOne(groupEntity.getCategoryId().longValue());
			if(groupCategory == null) 
				throw new RuntimeException("Data issue, can not find category based on categoryId : " + groupEntity.getCategoryId().longValue());
			ProdProductEntity productEntity = productRepository.findOne(productBranchEntity.getProductId().longValue());
			if(productEntity == null) 
				throw new RuntimeException("Data issue, can not find product based on id: " + productBranchEntity.getProductId().longValue());
			if(productDetail.getTrafficInfoList()==null || productDetail.getTrafficInfoList().isEmpty()){
			    productDetail.setTrafficInfoList(getTrafficInfo(productEntity));
			}
			PackageLineGroup group = new PackageLineGroup();
			
			if(StringUtils.isEmpty(groupLine.getStartDay())) 
				throw new RuntimeException("Data issue, the start day is null, group line id: " + groupLine.getGroupLineId());
			
			group.setDates(StampUtils.parseVisitDate(groupLine.getStartDay(), visitTime));
			group.setStayDay(groupLine.getStayDays().intValue());
			group.setTravelDay(groupLine.getTravelDays().intValue());
			group.setRemark(groupLine.getRemark());

			productVisitTime.put(productEntity.getProductId(), group.getDates().get(0));
			
			SuppGoods suppGood = new SuppGoods();
			suppGood.setAmount(amount);
			if(SaleType.PEOPLE.name().equals(saleType.getSaleType())) {
				suppGood.setAudltQuantity(amount);
			} else {
				suppGood.setAudltQuantity(amount * saleType.getAdultQuantity());
				suppGood.setChildQuantity(amount * saleType.getChildQuantity());
			}
			suppGood.setBranchName(productBranchEntity.getBranchName());
			suppGood.setGoodsId(suppGoodsEntity.getSuppGoodsId());
			suppGood.setGoodsName(suppGoodsEntity.getGoodsName());
			suppGood.setProductBranchId(productBranchEntity.getProductBranchId());
			suppGood.setProductId(productEntity.getProductId());
			suppGood.setProductName(productEntity.getProductName());
			if(propMap != null)
				suppGood.setProps(propMap.get(productBranchEntity.getProductBranchId()));
			
			group.setGoods(suppGood);
			group.setGroupId(groupGoodsBo.getGroupId());
			if("category_route_hotelcomb".equals(groupCategory.getCategoryCode()))  //酒店套餐
				group.setSaleType(SaleType.COPIES.name());
			else 
				group.setSaleType(SaleType.PEOPLE.name());
			
			if(!packageLineMap.containsKey(groupCategory.getCategoryCode())) {
				List<PackageLineGroup> list = new ArrayList<PackageLineGroup>();
				packageLineMap.put(groupCategory.getCategoryCode(), list);
			}
			packageLineMap.get(groupCategory.getCategoryCode()).add(group);
		}
		
		List<PackageLine> groups = new ArrayList<PackageLine>();
		for(Entry<String, List<PackageLineGroup>> entry : packageLineMap.entrySet()) {
			PackageLine group = new PackageLine();
			group.setCategory(entry.getKey());
			group.setGroups(entry.getValue());
			groups.add(group);
		}
		return groups;
	}
	
	private List<PackageHotel> getPackageHotel(StampPackageGoodsBo packageGood, int amount, Map<Long, ProdProductBranchEntity> productBranchMap, Date visitTime, Map<Long, List<BranchPropVO>> propMap, ProductSaleType saleType, Map<Long, String> productVisitTime) {
		if(packageGood == null || CollectionUtils.isEmpty(packageGood.getGroups())) {
			return null;
		}
		List<PackageHotel> groups = new ArrayList<PackageHotel>();
		for(StampGroupGoodsBo groupGoodsBo : packageGood.getGroups()) {
			PresaleStampDefinitionGoodsBindingEntity goodsBindingEntity = groupGoodsBo.getGoods();
			SuppGoodEntity suppGoodsEntity = suppGoodsRepository.findOne(NumberUtils.toLong(goodsBindingEntity.getGoodsId()));
			if(suppGoodsEntity == null) 
				throw new RuntimeException("Data issue, can not find supp goods based on id: " + goodsBindingEntity.getGoodsId());
			ProdProductBranchEntity productBranchEntity = productBranchMap.get(suppGoodsEntity.getProductBranchId().longValue());
			if(productBranchEntity == null) 
				throw new RuntimeException("Data issue, can not find product branch based on id: " + suppGoodsEntity.getProductBranchId().longValue());
			ProdProductEntity productEntity = productRepository.findOne(productBranchEntity.getProductId().longValue());
			if(productEntity == null) 
				throw new RuntimeException("Data issue, can not find product based on id: " + productBranchEntity.getProductId().longValue());
			
			PackageHotel group = new PackageHotel();
			ProdPackageGroupHotelEntity groupHotel = prodPackageGroupHotelRepository.getFirstByGroupId(new BigDecimal(groupGoodsBo.getGroupId()));
			if(groupHotel == null) 
				throw new RuntimeException("Data issue, can not find package group hotel based on groupId: " + groupGoodsBo.getGroupId());
			
			if(visitTime == null && productVisitTime != null) {
				String time = null;
				if((time = productVisitTime.get(productEntity.getProductId())) != null) {
					visitTime = DateUtil.toSimpleDate(time);
				}
			}
			if(visitTime == null) 
				throw new RuntimeException("visitTime should not be null, productId: " + productEntity.getProductId());
			String[] stayDays = StampUtils.parseHotelDays(groupHotel.getStayDays(), visitTime);
			
			group.setStartDate(stayDays[0]);
			group.setEndDate(stayDays[1]);
			group.setRemark(groupHotel.getRemark());
			
			SuppGoods suppGood = new SuppGoods();
			suppGood.setAmount(amount);
			if(SaleType.PEOPLE.name().equals(saleType.getSaleType())) {
				suppGood.setAudltQuantity(amount);
			} else {
				suppGood.setAudltQuantity(amount * saleType.getAdultQuantity());
				suppGood.setChildQuantity(amount * saleType.getChildQuantity());
			}
			suppGood.setBranchName(productBranchEntity.getBranchName());
			suppGood.setGoodsId(suppGoodsEntity.getSuppGoodsId());
			suppGood.setGoodsName(suppGoodsEntity.getGoodsName());
			suppGood.setProductBranchId(productBranchEntity.getProductBranchId());
			suppGood.setProductId(productEntity.getProductId());
			suppGood.setProductName(productEntity.getProductName());
			if(propMap != null)
				suppGood.setProps(propMap.get(productBranchEntity.getProductBranchId()));
			
			if(productBranchEntity.getMaxVisitor() != null)
				group.setMaxVisitor(productBranchEntity.getMaxVisitor().intValue());
			group.setGoods(suppGood);
			group.setGroupId(groupGoodsBo.getGroupId());
			
			groups.add(group);
		}
		return groups;
	}
	
	protected Map<Long, ProdProductBranchEntity> translateProductBranch(List<ProdProductBranchEntity> productBranchs) {
		Map<Long, ProdProductBranchEntity> map = new HashMap<Long, ProdProductBranchEntity>();
		if(productBranchs != null) {
			for(ProdProductBranchEntity entity : productBranchs) {
				map.put(entity.getProductBranchId(), entity);
			}
		}
		return map;
	}
	
	protected BizCategoryVo translateBizCategory(BizCategoryEntity category) {
		BizCategoryVo vo = new BizCategoryVo();
		vo.setCancelFlag(category.getCancelFlag());
		vo.setCategoryCode(category.getCategoryCode());
		vo.setCategoryDesc(category.getCategoryDesc());
		vo.setCategoryId(category.getCategoryId());
		vo.setCategoryName(category.getCategoryName());
		if(category.getParentId() != null)
			vo.setParentId(category.getParentId().longValue());
		vo.setProcessKey(category.getProcessKey());
		vo.setPromTarget(category.getPromTarget());
		if(category.getSeq() != null)
			vo.setSeq(category.getSeq().longValue());
		return vo;
	}
	
	protected ProductVo translateProductVO(ProdProductEntity entity) {
		ProductVo productVo = new ProductVo();
		BeanUtilsBean beanUtilsBean = new BeanUtilsBean();
    	beanUtilsBean.getConvertUtils().register(new BigDecimalConverter(), BigDecimal.class);
    	try {
			beanUtilsBean.copyProperties(productVo, entity);
			 ProdProductSaleReEntity saleRe = prodProductSaleService.getByProduct(entity.getProductId());
             if(saleRe != null) {
                 ProductSaleTypeVo saleType = new ProductSaleTypeVo();
                 saleType.setSaleType(saleRe.getSaleType());
                 saleType.setAdultQuantity(saleRe.getAdult()==null?0:saleRe.getAdult().intValue());
                 saleType.setChildQuantity(saleRe.getChild()==null?0:saleRe.getChild().intValue());
                 productVo.setSaleType(saleType);
             }
		} catch (Exception e) {
			LOGGER.error("Exception encountered when copy properties", e);
		} 
    	return productVo;
	}
	
	protected List<Long> extractGoodsIdFromPackageGoods(List<StampPackageGoodsBo> packages) {
		if(packages == null) 
			return null;
		List<Long> goodsIds = new ArrayList<Long>();
		for(StampPackageGoodsBo bo : packages) {
			for(StampGroupGoodsBo gbo : bo.getGroups()) {
				goodsIds.add(NumberUtils.toLong(gbo.getGoods().getGoodsId()));
			}
		}
		return goodsIds;
	}
	
	protected Map<Long, List<BranchPropVO>> translateProductBranchProp(List<BranchPropVO> branchProps) {
		Map<Long, List<BranchPropVO>> map = new HashMap<Long, List<BranchPropVO>>();
		if(branchProps != null) {
			for(BranchPropVO prop : branchProps) {
				if(map.containsKey(prop.getProductBranchId())) {
					map.get(prop.getProductBranchId()).add(prop);
				} else {
					List<BranchPropVO> list = new ArrayList<BranchPropVO>();
					list.add(prop);
					map.put(prop.getProductBranchId(), list);
				}
			}
		}
		return map;
	}
	
	protected Map<Long, BizDistrictEntity> translateDistrict(List<BizDistrictEntity> districtList) {
		Map<Long, BizDistrictEntity> map = new HashMap<Long, BizDistrictEntity>();
		if(districtList != null) {
			for(BizDistrictEntity entity : districtList) {
				map.put(entity.getDistrictId(), entity);
			}
		}
		return map;
	}
	
	
	private List<Map> getTrafficInfo(ProdProductEntity product) {
    
        // 交通
        List<Map> trafficList = new ArrayList<Map>();       
        ProdTrafficVO trafficVO = prodTrafficClientServiceRemote.getProdTrafficVOByProductId(product.getProductId());
        if(trafficVO==null){
            return null;
        }      
        com.lvmama.vst.back.prod.po.ProdTraffic prodTraffic = trafficVO.getProdTraffic();      
        Map<String, Object> suppGoodsInfoMap = new HashMap<String, Object>();
        trafficList.add(suppGoodsInfoMap);
        Map<String, Object> trafficMap = new HashMap<String, Object>();
        suppGoodsInfoMap.put("trafficMap", trafficMap);
        Map<String, Object> toMap = new HashMap<String, Object>();// 去返程信息
        Map<String, Object> backMap = new HashMap<String, Object>();// 返程信息
        trafficMap.put("toMap", toMap);
        trafficMap.put("backMap", backMap);

        String backType = prodTraffic.getBackType();
        String toType = prodTraffic.getToType();
     

        // 如果是机票或着其它机票
        if (SUB_PRODUCT_TYPE.FLIGHT.getCode().equals(backType) || SUB_PRODUCT_TYPE.FLIGHT.getCode().equals(toType)) {
          return null;
           // 如果是火车票或着其它火车票
        } else if (SUB_PRODUCT_TYPE.TRAIN.getCode().equals(backType) || SUB_PRODUCT_TYPE.TRAIN.getCode().equals(toType)) {

         return null ;
            //如果是巴士票或着其它巴士票
        }else if(SUB_PRODUCT_TYPE.BUS.getCode().equals(backType) || SUB_PRODUCT_TYPE.BUS.getCode().equals(toType)){
            trafficMap.put("trafficType","bus");
            if(prodTraffic != null){
              
            List<Map<String,String>> buses = new ArrayList<Map<String,String>>();
            List<Map<String,Object>> toBuses = new ArrayList<Map<String,Object>>();
            List<Map<String,Object>> backBuses = new ArrayList<Map<String,Object>>();
            List<ProdTrafficGroup> prodTrafficGroups = trafficVO.getProdTrafficGroupList();
          
         /*   for(ProdTrafficGroup ptg : prodTrafficGroups) {
                List<ProdTrafficBus> prodTrafficBusList = ptg.getProdTrafficBusList();
                if(prodTrafficBusList != null && prodTrafficBusList.size() > 0){

                    //现在最多只会有一个中转,所以可以按照下面的写法做
                    for(ProdTrafficBus prodTrafficBus : prodTrafficBusList){
                        if("TO".equals(prodTrafficBus.getTripType())){
                            Map<String, String> bus = new HashMap<String,String>();
                          //  bus.put("date", startDate);// 去程日期
                            bus.put("address",prodTrafficBus.getAdress());//上车地点
                            bus.put("startTime",prodTrafficBus.getStartTime());//上车时间
                            bus.put("memo",prodTrafficBus.getMemo());
                            buses.add(bus);
                         
                        }
                    }

                }
            }*/

            for (ProdTrafficGroup ptg : prodTrafficGroups) {
                List<ProdTrafficBus> prodTrafficBusList = ptg.getProdTrafficBusList();
                if (prodTrafficBusList != null && prodTrafficBusList.size() > 0) {
                    for(ProdTrafficBus prodTrafficBus : prodTrafficBusList){
                        if("TO".equals(prodTrafficBus.getTripType())){
                            
                         //   toMap.put("date", startDate);// 去程日期
                            toMap.put("address",prodTrafficBus.getAdress());//上车地点
                            toMap.put("startTime",prodTrafficBus.getStartTime());//上车时间
                           
                                Map<String,Object> toItem=new HashMap<String, Object>();
                           ///     toItem.put("date", startDate);// 去程日期
                                toItem.put("address",prodTrafficBus.getAdress());//上车地点
                                toItem.put("startTime",prodTrafficBus.getStartTime());//上车时间
                                toItem.put("memo",prodTrafficBus.getMemo());
                                toBuses.add(toItem);
                            
                           

                        }else if ("BACK".equals(prodTrafficBus.getTripType())) {
                          //  backMap.put("date", endDate);// 返程日期
                            backMap.put("address", prodTrafficBus.getAdress());// 上车地点
                            backMap.put("startTime", prodTrafficBus.getStartTime());// 上车时间
                           
                                Map<String,Object> backItem=new HashMap<String, Object>();
                          //      backItem.put("date", startDate);// 去程日期
                                backItem.put("address",prodTrafficBus.getAdress());//上车地点
                                backItem.put("startTime",prodTrafficBus.getStartTime());//上车时间
                                backItem.put("memo",prodTrafficBus.getMemo());
                                backBuses.add(backItem);
                            
                          
                        }
                    }
                }
            }
         
            trafficMap.put("bus", buses);
            trafficMap.put("toBuses", toBuses);
            trafficMap.put("backBuses", backBuses);
            }
        }else if("SHIP".equals(backType) || "SHIP".equals(toType)){
            return null ;
        }
        return trafficList;
	
	}
	
}
