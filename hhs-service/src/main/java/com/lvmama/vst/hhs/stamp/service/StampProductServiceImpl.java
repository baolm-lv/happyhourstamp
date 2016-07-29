package com.lvmama.vst.hhs.stamp.service;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lvmama.vst.back.client.goods.service.SuppGoodsTimePriceClientService;
import com.lvmama.vst.back.goods.po.SuppGoodsBaseTimePrice;
import com.lvmama.vst.back.prod.po.ProdProduct.PACKAGETYPE;
import com.lvmama.vst.comm.utils.DateUtil;
import com.lvmama.vst.comm.vo.ResultHandleT;
import com.lvmama.vst.hhs.common.utils.HhsConstants;
import com.lvmama.vst.hhs.model.common.Constant.SaleType;
import com.lvmama.vst.hhs.model.product.ChangeHotel;
import com.lvmama.vst.hhs.model.product.ProductAddition;
import com.lvmama.vst.hhs.model.product.ProductAdditionRequest;
import com.lvmama.vst.hhs.model.product.ProductSaleType;
import com.lvmama.vst.hhs.model.product.StampUsage;
import com.lvmama.vst.hhs.model.stamp.AdditionalGoods;
import com.lvmama.vst.hhs.product.dao.ProdProductBranchEntity;
import com.lvmama.vst.hhs.product.dao.ProdProductEntity;
import com.lvmama.vst.hhs.product.dao.SuppGoodEntity;
import com.lvmama.vst.hhs.product.service.ProductAdditionService;
import com.lvmama.vst.hhs.product.service.ProductPackageService;
import com.lvmama.vst.hhs.product.service.ProductService;
import com.lvmama.vst.hhs.stamp.util.StampUtils;
import com.lvmama.vst.hhs.stampDefinition.bo.StampGoodsBindingBo;
import com.lvmama.vst.hhs.stampDefinition.bo.StampGroupGoodsBo;
import com.lvmama.vst.hhs.stampDefinition.bo.StampPackageGoodsBo;
import com.lvmama.vst.hhs.stampDefinition.dao.PresaleStampDefinitionGoodsBindingEntity;
import com.lvmama.vst.hhs.stampDefinition.dao.StampDefinitionEntity;
import com.lvmama.vst.hhs.stampDefinition.repositorySlave.PresaleStampDefinitionProductBindingRepositorySlave;
import com.lvmama.vst.hhs.stampDefinition.repositorySlave.StampDefinitionRepositorySlave;
import com.lvmama.vst.hhs.stampDefinition.service.StampGoodsService;

@Service
public class StampProductServiceImpl implements StampProductService {

	private static final Logger LOGGER = LoggerFactory.getLogger(StampProductServiceImpl.class);
	
	@Autowired
	private StampDefinitionRepositorySlave stampRepositorySlave;
	@Autowired
	private StampService stampService;
	@Autowired
	private StampGoodsService stampGoodsService;
	@Autowired
	private ProductService productService;
	@Autowired
	private SuppGoodsTimePriceClientService suppGoodsTimePriceClientService;
	@Autowired
	private ProductAdditionService productAdditionService;
	@Autowired
	private PresaleStampDefinitionProductBindingRepositorySlave productBindingRepositorySlave;
	@Autowired
	private ProductPackageService productPackageService;
	
	public static final String HHS_IS_STAMP_DEFINITION_KEY = "hhs.model.stamp.getStampProductByProductIds_"; 
	public static final String HHS__STAMP_PRODUCT_IS_EXCHANGE = "hhs.model.stamp.productIsExchange_"; 
    public static final String UNDERLINE   = "_"; 

	@Override
	@Transactional("slaveTransactionManager")
	@Cacheable(value = "hhs.model.stamp.ProductDetailsStamps", cacheManager = "expired5MinutesCacheManager", key = "#root.target.HHS_IS_STAMP_DEFINITION_KEY + #ids")
	public List<Long> getStampProductByProductIds(String ids) {
		List<Long> productIds = new ArrayList<Long>();
		if (StringUtils.isNotEmpty(ids)) {
			String[] splitIds = ids.split(",");
			if (splitIds.length != 0) {
				for (int i = 0; i < splitIds.length; i++) {
					productIds.add(Long.valueOf(splitIds[i]));
				}
			}
		}
		if (CollectionUtils.isEmpty(productIds)) {
			return productIds;
		}
		Date date = DateUtil.stringToDate(DateUtil.SimpleFormatDateToString(new Date()), DateUtil.SIMPLE_DATE_FORMAT);

		List<StampDefinitionEntity> stamps = stampRepositorySlave.getByProductId(productIds, new Timestamp(date.getTime()));
		productIds.clear();
		if (CollectionUtils.isEmpty(stamps)) {
			return productIds;
		}
		for (StampDefinitionEntity stamp : stamps) {
			productIds.add(Long.valueOf(stamp.getProductBinding().getProductId()));
		}
		return productIds;
	}
	
	private List<ChangeHotel> getChangeHotelForSupplyPackage(String stampId, Long productId, Date visitTime, int quantity) {
		List<PresaleStampDefinitionGoodsBindingEntity> bindingGoods = stampGoodsService.getStampGoodsBindingByStampId(stampId);
		if (CollectionUtils.isEmpty(bindingGoods)) {
			return null;
		}

		boolean hasBindedChange = false;
		for(PresaleStampDefinitionGoodsBindingEntity goods : bindingGoods) {
			String branchFlag = goods.getBranchFlag();
			if(branchFlag != null && branchFlag.startsWith(HhsConstants.BRANCH_FLAG_CHANGE_PREFIX)) {
				hasBindedChange = true;
				break;
			}
		}
		
		List<ChangeHotel> changeHotels = null;
		if(!hasBindedChange) {//券未配置可换酒店，查询是否有可换酒店，默认选取价格低的
			LOGGER.debug("did not find change hotel accroding to goods binding table, will try to get one from prod_package_group_hotel");
			changeHotels = getChangeHotel(productId, visitTime, quantity);
		}
		return changeHotels;
	}
	
	private List<ChangeHotel> getChangeHotel(Long productId, Date visitTime, int quantity) {
		List<ChangeHotel> changeHotels = null;
		List<ChangeHotel> allChangeHotels = productService.getChangeHotel(productId, visitTime);
		if(CollectionUtils.isNotEmpty(allChangeHotels)) {
			Map<Long, List<ChangeHotel>> groupHotelMap = new HashMap<Long, List<ChangeHotel>>();
			for(ChangeHotel hotel : allChangeHotels) {
				if(!groupHotelMap.containsKey(hotel.getGroupId()))
					groupHotelMap.put(hotel.getGroupId(), new ArrayList<ChangeHotel>());
				groupHotelMap.get(hotel.getGroupId()).add(hotel);
			}
			
			Map<Long, ChangeHotel> groupHotel = new HashMap<Long, ChangeHotel>();
			StringBuilder errMsg = null;
			for(Entry<Long, List<ChangeHotel>> entry : groupHotelMap.entrySet()) {
				Long groupId = entry.getKey();
				Iterator<ChangeHotel> iter = entry.getValue().iterator();
				while(iter.hasNext()) {
					ChangeHotel ch = iter.next();
					//检查时间价格及库存
					errMsg = new StringBuilder();
					int qty = calcQuantity(HhsConstants.CHANGE, ch.getGoodsId(), 0, 0, quantity, null);
					SuppGoodsBaseTimePrice timePrice = checkGoodsTimePrice(ch.getGoodsId(), visitTime, qty, errMsg);
					if(errMsg.length() > 0) {//不可售
						LOGGER.info(errMsg.toString());
						iter.remove();
					} else {
						ch.setAudltPrice(timePrice.getGoodsAudltPrice());
						ch.setChildPrice(timePrice.getGoodsChildPrice());
						ch.setGapPrice(timePrice.getGoodsGapPrice());
					}
				}
				if(entry.getValue().size() == 0) //该组下没有可售可换酒店
					throw new RuntimeException("on available change hotel found under the groupId: " + groupId);

				//取价格最低的可换酒店
				for(ChangeHotel ch : entry.getValue()) {
					if(!groupHotel.containsKey(ch.getGroupId())) 
						groupHotel.put(ch.getGroupId(), ch);
					else {
						ChangeHotel cur = groupHotel.get(ch.getGroupId());
						if(cur.getAudltPrice() > ch.getAudltPrice())
							groupHotel.put(ch.getGroupId(), ch);
					}
					
				}
			}
			if(groupHotel.size() > 0) {
				changeHotels = new ArrayList<ChangeHotel>();
				changeHotels.addAll(groupHotel.values());
			}
		}
		return changeHotels;
	}
	
	private List<ChangeHotel> getChangeHotelForLvmamaPackage(String stampId, Date visitTime, Map<Long, String> productVisitTime, int quantity) {
		List<PresaleStampDefinitionGoodsBindingEntity> bindingGoods = stampGoodsService.getStampGoodsBindingByStampId(stampId);
		if (CollectionUtils.isEmpty(bindingGoods)) {
			return null;
		}
		long tmp = 0L;
		List<Long> changeProductIds = new ArrayList<Long>();//可换酒店对应的产品ID
		Map<Long, Map<Long, List<SuppGoodEntity>>> productGroupGoodsMap = new HashMap<Long, Map<Long,List<SuppGoodEntity>>>();
		for(PresaleStampDefinitionGoodsBindingEntity goods : bindingGoods) {
			SuppGoodEntity goodEntity = productService.getSuppGoodEntity(NumberUtils.toLong(goods.getGoodsId()));
			if(goodEntity == null) {
				LOGGER.info("can not find supp goods based on goodsId: {}", goods.getGoodsId());
				continue;
			}
			long productId = goodEntity.getProductId().longValue();
			if(!productGroupGoodsMap.containsKey(productId)) 
				productGroupGoodsMap.put(productId, new HashMap<Long, List<SuppGoodEntity>>());

			String branchFlag = goods.getBranchFlag();
			if(branchFlag != null && branchFlag.startsWith(HhsConstants.BRANCH_FLAG_CHANGE_PREFIX)) {
				changeProductIds.add(productId);
				String groupId = branchFlag.substring(HhsConstants.BRANCH_FLAG_CHANGE_PREFIX.length());
				if((tmp = NumberUtils.toLong(groupId)) != 0) {
					Map<Long, List<SuppGoodEntity>> groupGoodsMap = productGroupGoodsMap.get(productId);
					if(!groupGoodsMap.containsKey(tmp)) 
						groupGoodsMap.put(tmp, new ArrayList<SuppGoodEntity>());
					groupGoodsMap.get(tmp).add(goodEntity);
				}
			}
		}
		
		Map<Long, List<ChangeHotel>> changeHotelMap = new HashMap<Long, List<ChangeHotel>>();
		for(Entry<Long, Map<Long, List<SuppGoodEntity>>> entry : productGroupGoodsMap.entrySet()) {
			Long productId = entry.getKey();
			Map<Long, List<SuppGoodEntity>> groupGoodsMap = entry.getValue();
			
			if(MapUtils.isEmpty(groupGoodsMap)) {
				LOGGER.debug("did not find change hotel accroding to goods binding table, will try to get one from prod_package_group_hotel");
				String time = productVisitTime.get(productId);
				if(StringUtils.isNotBlank(time)) {
					List<ChangeHotel> changeHotel = getChangeHotel(productId, DateUtil.toSimpleDate(time), quantity);
					changeHotelMap.put(productId, changeHotel);
				} else {
					LOGGER.warn("can not find visit time based on productId: {}", productId);
				}
			}
		}
		
		
		List<ChangeHotel> result = new ArrayList<ChangeHotel>();
		if(MapUtils.isNotEmpty(changeHotelMap)) {
			for(Entry<Long, List<ChangeHotel>> entry : changeHotelMap.entrySet()) {
				if(entry.getValue() != null) {
					if(!changeProductIds.contains(entry.getKey())) {
						result.addAll(entry.getValue());
					}
				}
			}
		}
		
		return result;
	}
	
    @Override
    @Transactional("slaveTransactionManager")
    @Cacheable(value = "hhs.model.stamp.productIsExchange", cacheManager = "expired5MinutesCacheManager", key = "#root.target.HHS__STAMP_PRODUCT_IS_EXCHANGE + #productId +#root.target.UNDERLINE +#startDistrictId")
    public Boolean productIsExchange(Long productId, Long startDistrictId) {
        List<StampDefinitionEntity> stamps = null;
        Date date = DateUtil.stringToDate(DateUtil.SimpleFormatDateToString(new Date()), DateUtil.SIMPLE_DATE_FORMAT);
        Timestamp now = new Timestamp(date.getTime());
        if(startDistrictId == null || startDistrictId == 0L) {
             stamps = stampRepositorySlave.getByProductIdAndRedeemStartDateAndRedeemEndDate(String.valueOf(productId), now );
        }else{
            stamps = stampRepositorySlave.getByProductIdAndDepartIdAndRedeemStartDateAndRedeemEndDate(String.valueOf(productId), String.valueOf(startDistrictId), now);
        }
        if(CollectionUtils.isNotEmpty(stamps)){
            return true ;
        } else{
            return false;
        }
    }
    
    private ProductAddition getStampAdditionForSupplyPackage(Long productId, ProductAdditionRequest request) {
    	if(CollectionUtils.isEmpty(request.getStamps())) 
    		throw new IllegalArgumentException("stamps should not be null");
    	Date visitTime = null;
    	try {
    		visitTime = new SimpleDateFormat("yyyy-MM-dd").parse(request.getVisitTime());
    	} catch (Exception e) {
    		throw new IllegalArgumentException("Invalid date format : " + request.getVisitTime(), e);
    	}
    	long adultQuantity = 0, childQuantity = 0, quantity = 0;
    	String fistStampId = null;
    	List<Long> changeHotelGoods = new ArrayList<Long>();
    	for(StampUsage usage : request.getStamps()) {
    		if(usage.getAmount() < 1) 
    			continue;
    		List<PresaleStampDefinitionGoodsBindingEntity> bindingGoodsList = stampGoodsService.getStampGoodsBindingByStampId(usage.getStampId());
    		if(CollectionUtils.isEmpty(bindingGoodsList)) 
    			throw new RuntimeException("can not find binding goods based on stampId : " + usage.getStampId());
    		
    		boolean isChildStamp = false;
    		for(PresaleStampDefinitionGoodsBindingEntity entity : bindingGoodsList) {
        		if(HhsConstants.BRANCH_FLAG_PRIMARY_CHILD.equals(entity.getBranchFlag())) 
        			isChildStamp = true;
        		if(entity.getBranchFlag().startsWith(HhsConstants.BRANCH_FLAG_CHANGE_PREFIX)) 
        			changeHotelGoods.add(NumberUtils.toLong(entity.getGoodsId()));
        	}
    		
    		if(isChildStamp) 
    			childQuantity += usage.getAmount();
    		else 
    			adultQuantity += usage.getAmount();
    		quantity += usage.getAmount();
    		
    		if(fistStampId == null)
    			fistStampId = usage.getStampId();
    	}
    	
    	if(quantity == 0 ) 
    		throw new IllegalArgumentException("No stamps been used.");
    	
    	ProductAddition addition = new ProductAddition();
    	//多个券同时使用时，所绑定的商品必须是一样的，所以可换酒店计算只需要算一个券
    	List<ChangeHotel> unBindedChanges = getChangeHotelForSupplyPackage(fistStampId, productId, visitTime, (int)quantity);
    	if(CollectionUtils.isNotEmpty(unBindedChanges)) {
    		addition.setChangeHotels(unBindedChanges);
    		for(ChangeHotel ch : unBindedChanges) {
    			changeHotelGoods.add(ch.getGoodsId());
    		}
    	}
    	
    	//附加产品
    	addition.setAdditionGoods(productService.getAdditionGoods(productId, visitTime, (int)quantity));
    	
    	//加载房差和保险
    	productAdditionService.loadingAdditions(adultQuantity, childQuantity, quantity, visitTime, productId, request.getStartDistinctId(), changeHotelGoods, addition);
    	return addition;
    	
    }
    
    @Override
    public ProductAddition getStampAddition(Long productId, ProductAdditionRequest request) {
    	if(CollectionUtils.isEmpty(request.getStamps())) 
    		throw new IllegalArgumentException("stamps should not be null");
    	Date visitTime = null;
    	try {
    		visitTime = new SimpleDateFormat("yyyy-MM-dd").parse(request.getVisitTime());
    	} catch (Exception e) {
    		throw new IllegalArgumentException("Invalid date format : " + request.getVisitTime(), e);
    	}
    	ProdProductEntity productEntity = productService.getProduct(productId);
    	if(productEntity == null) 
    		throw new RuntimeException("can not find product based on productId: " + productId);
    	
    	if(PACKAGETYPE.SUPPLIER.name().equals(productEntity.getPackageType())) {
    		return getStampAdditionForSupplyPackage(productId, request);
    	}
    	
    	int quantity = 0;//券份数
    	String firstStampId = null;
    	Iterator<StampUsage> iter = request.getStamps().iterator();
    	while(iter.hasNext()) {
    		StampUsage usage = iter.next();
    		if(usage.getAmount() < 1) 
    			iter.remove();
    		quantity += usage.getAmount();
    		if(firstStampId == null) 
    			firstStampId = usage.getStampId();
    	}
    	if(firstStampId == null) 
    		throw new RuntimeException("No stamps been used.");
    	
    	long adultQuantity = 0, childQuantity = 0;
    	ProductSaleType saleType = productService.getProductSaleTypeByStampId(firstStampId);
    	if(SaleType.COPIES.name().equals(saleType.getSaleType())) {//按份卖
    		adultQuantity = saleType.getAdultQuantity() * quantity;
    		childQuantity = saleType.getChildQuantity() * quantity;
    	} else {
    		adultQuantity = quantity;//按人卖，成人数就是券份数，儿童数为0
    	}
    	
    	Map<Long, String> productVisitTime = new HashMap<Long, String>();
    	List<Long> changeHotelGoods = new ArrayList<Long>();
    	Map<Long, Long> goodsProductIds = new HashMap<Long, Long>();
    	for(StampUsage usage : request.getStamps()) {
    		StampGoodsBindingBo goodsBo = stampGoodsService.getStampGoodsByStampId(usage.getStampId());
    		if(goodsBo == null || CollectionUtils.isEmpty(goodsBo.getPackages())) 
    			throw new RuntimeException("can not find binding goods based on stampId : " + usage.getStampId());
    		
    		for(StampPackageGoodsBo packageGood : goodsBo.getPackages()) {
    			String groupType = packageGood.getGroupType();
				for(StampGroupGoodsBo groupGood : packageGood.getGroups()) {
					long groupId = groupGood.getGroupId();
					long goodsId = NumberUtils.toLong(groupGood.getGoods().getGoodsId());
					
					SuppGoodEntity goodEntity = productService.getSuppGoodEntity(goodsId);
					if(goodEntity == null) 
						throw new RuntimeException("can not find supp goods based on goodsId: " + goodsId);
					
					goodsProductIds.put(goodEntity.getSuppGoodsId(), goodEntity.getProductId().longValue());
					
					if(HhsConstants.CHANGE.equals(groupType)) 
						changeHotelGoods.add(goodsId);
					else {
						productVisitTime.put(goodEntity.getProductId().longValue(), request.getVisitTime());
						List<String> visitTimes = productPackageService.getGroupVisitTime(groupId, groupType, visitTime);
						
						if(HhsConstants.LINE_TICKET.equals(groupType)) { //验证门票预订日期限制
							if(goodEntity.getLimitBookDay() != null && goodEntity.getLimitBookDay().intValue() > -1) {
								List<String> limitDates = StampUtils.getVisitDates(goodEntity.getLimitBookDay().intValue());
								visitTimes.retainAll(limitDates);
							}
						}
						
						if(CollectionUtils.isNotEmpty(visitTimes)) {
							StringBuilder errMsg = null;
							Iterator<String> timeIter = visitTimes.iterator();
							while(timeIter.hasNext()) {//线路和门票可以有多个visitTime, 前台用户可以任意选择
								String time = timeIter.next();
								//计算数量
								int qty = calcQuantity(groupType, goodsId, adultQuantity, childQuantity, quantity, saleType.getSaleType());
								//检查时间价格及库存
								errMsg = new StringBuilder();
								checkGoodsTimePrice(goodsId, DateUtil.toSimpleDate(time), qty, errMsg);
								
								if(errMsg.length() > 0) {//不可售
									if(HhsConstants.TRANSPORT.equals(groupType) || HhsConstants.HOTEL.equals(groupType))
										throw new RuntimeException(errMsg.toString());
									timeIter.remove();
									LOGGER.warn(errMsg.toString());
								}
							}
							if(visitTimes.size() == 0) //线路和门票所有visitTime都不可售时，整体不可售 
								throw new RuntimeException(errMsg != null ? errMsg.toString() : "the goods is not on sale, goodsId: " + goodsId);
						} else {
							throw new RuntimeException("can not find valid visitTime based on groupId: " + groupId);
						}
						
						productVisitTime.put(goodEntity.getProductId().longValue(), visitTimes.get(0));
					}
				}
    		}
    	}
    	
    	//验证绑定的可换酒店时间价格及库存
    	if(CollectionUtils.isNotEmpty(changeHotelGoods)) {
    		StringBuilder errMsg = null;
    		for(Long goodsId : changeHotelGoods) {
    			Long prodId = goodsProductIds.get(goodsId);
    			if(prodId != null) {
    				String time = productVisitTime.get(prodId);
    				if(StringUtils.isNotBlank(time)) {
    					//计算数量
    					int qty = calcQuantity(HhsConstants.CHANGE, goodsId, adultQuantity, childQuantity, quantity, saleType.getSaleType());
    					//检查时间价格及库存
    					errMsg = new StringBuilder();
    					checkGoodsTimePrice(goodsId, DateUtil.toSimpleDate(time), qty, errMsg);
    					if(errMsg.length() > 0) {//不可售
    						throw new RuntimeException(errMsg.toString());
						}
    				}
    			}
    		}
    	}
    	
    	ProductAddition addition = new ProductAddition();
    	List<ChangeHotel> changeHotels = getChangeHotelForLvmamaPackage(firstStampId, visitTime, productVisitTime, quantity);
    	for(ChangeHotel ch : changeHotels) {
			changeHotelGoods.add(ch.getGoodsId());
		}
    	addition.setChangeHotels(changeHotels);
    	
    	//附加产品
    	if(MapUtils.isNotEmpty(productVisitTime)) {
    		List<AdditionalGoods> additions = new ArrayList<AdditionalGoods>();
    		for(Entry<Long, String> entry : productVisitTime.entrySet()) {
    			List<AdditionalGoods> tmp = productService.getAdditionGoods(entry.getKey(), DateUtil.toSimpleDate(entry.getValue()), quantity);
				if(CollectionUtils.isNotEmpty(tmp)) 
					additions.addAll(tmp);
				
    		}
    		addition.setAdditionGoods(additions);
    	}
    	
    	//加载房差和保险
    	productAdditionService.loadingAdditions(adultQuantity, childQuantity, (long)quantity, visitTime, productId, request.getStartDistinctId(), changeHotelGoods, addition);
    	return addition;
    }
    
    /**
     * 检查时间价格及库存， 返回错误信息， 无错误返回null
     * @param goodsId
     * @param visitTime
     * @param quantity
     * @return
     */
    private SuppGoodsBaseTimePrice checkGoodsTimePrice(Long goodsId, Date visitTime, int quantity, StringBuilder error) {
    	ResultHandleT<SuppGoodsBaseTimePrice> timePriceResult = suppGoodsTimePriceClientService.getBaseTimePrice(goodsId, visitTime);
		SuppGoodsBaseTimePrice timePrice = null;
		if(timePriceResult.isSuccess() && (timePrice = timePriceResult.getReturnContent()) != null) {
			if("N".equals(timePrice.getOnsaleFlag())) {
				error.append("the goods is not on sale, goodsId: " + goodsId);
				return timePrice;
			}
			if(timePrice.getStock() != null && timePrice.getStock() != -1 && timePrice.getStock() < quantity && "N".equals(timePrice.getOversellFlag())) {
				error.append("the stock is not enough, goodsId: " + goodsId + ", Expect: " + quantity + ", Actual: " + timePrice.getStock());
				return timePrice;
			}
			return timePrice;
		} else {
			LOGGER.info(timePriceResult.getMsg());
			error.append("Failed to get the time price for goodsId: " + goodsId);
			return null;
		}
    }
    
    private int calcQuantity(String groupType, Long goodsId, long adultQuantity, long childQuantity, long quantity, String saleType) {
    	int qty = 0;
    	if(HhsConstants.HOTEL.equals(groupType)) {
			ProdProductBranchEntity productBranch = productService.getProductBranch(goodsId);
    		if(productBranch == null) 
    			throw new RuntimeException("Data issue, can not find product branch based on goodsId: " + goodsId);
    		if(productBranch.getMaxVisitor() == null || productBranch.getMaxVisitor().intValue() == 0) 
    			throw new RuntimeException("Data issue, max visitor is not valid, productBranchId: " + productBranch.getProductBranchId());
    		
    		int maxVisitor = productBranch.getMaxVisitor().intValue();
    		int minRoom = (int)Math.ceil((double)adultQuantity / maxVisitor);
    		int maxRoom = (int)adultQuantity;
    		qty = SaleType.COPIES.name().equals(saleType) ? minRoom : maxRoom;
		} else if(HhsConstants.TRANSPORT.equals(groupType)) {
			qty = (int)(adultQuantity + childQuantity);
		} else if(HhsConstants.LINE.equals(groupType)) {
			qty = (int)(adultQuantity + childQuantity);
		} else if(HhsConstants.LINE_TICKET.equals(groupType)) {
			if(SaleType.COPIES.name().equals(saleType)) {
				SuppGoodEntity goodEntity = productService.getSuppGoodEntity(goodsId);
				if(goodEntity == null) 
					throw new RuntimeException("Data issue, can not find supp goods based on goodsId: " + goodsId);
				int adultChild = goodEntity.getAdult().intValue() + goodEntity.getChild().intValue();
				if(adultChild == 1) { //单门票, adult 和 child 有一个为0，一个为1
					qty = (int)(adultQuantity * goodEntity.getAdult().intValue() + childQuantity * goodEntity.getChild().intValue());
				} else { //套票
					qty = (int) quantity;
				}
			} else {
				qty = (int) quantity;
			}
		} else if(HhsConstants.CHANGE.equals(groupType)) {
			qty = (int) quantity;
		}
    	return qty;
    }
    
    /**
     * 追加购买，计算券对应商品的金额
     * @param bindingGoodsList
     * @param visitTime
     * @param isChildStamp
     * @return
     */
    @SuppressWarnings("unused")
	private long getStampGoodsPrice(List<PresaleStampDefinitionGoodsBindingEntity> bindingGoodsList, Date visitTime, boolean isChildStamp) {
    	if(CollectionUtils.isEmpty(bindingGoodsList) || visitTime == null) {
    		throw new IllegalArgumentException("goods and visitTime should not be null");
    	}
    	//TODO 按份数卖的时候。。。
    	long totalPrice = 0L; //券对应所有商品的价格之和
    	for(PresaleStampDefinitionGoodsBindingEntity entity : bindingGoodsList) {
    		ResultHandleT<SuppGoodsBaseTimePrice> timePriceResult = suppGoodsTimePriceClientService.getBaseTimePrice(NumberUtils.toLong(entity.getGoodsId()), visitTime);
    		SuppGoodsBaseTimePrice timePrice = null;
    		if(timePriceResult.isSuccess() && (timePrice = timePriceResult.getReturnContent()) != null) {
    			if(isChildStamp) {
    				totalPrice += timePrice.getGoodsChildPrice();
    			} else {
    				totalPrice += timePrice.getGoodsAudltPrice();
    			}
    		} else {
    			LOGGER.info(timePriceResult.getMsg());
    			
    		}
    	}
    	return totalPrice;
    }

    @Override
    @Transactional("slaveTransactionManager")
    public List<Long> getStampProduct(String categoryIds,int startRow,int num) {
        List<String>  categoryIdList = new ArrayList<String>();
        if(StringUtils.isNotEmpty(categoryIds)){
            String[] splitIds = categoryIds.split(",");
            if (splitIds.length != 0) {
                for (int i = 0; i < splitIds.length; i++) {
                    categoryIdList.add(splitIds[i]);
                }
            }
        }
        List<String> result = productBindingRepositorySlave.getStampDefinitionProductBinding(categoryIdList,startRow, num);
        List<Long>  productResult = new ArrayList<Long>();
        if(com.alibaba.dubbo.common.utils.CollectionUtils.isNotEmpty(result)){
            for(String productId:result){
                if(StringUtils.isNotEmpty(productId)){
                    productResult.add(Long.valueOf(productId));
                }
               
            }
        }
        return productResult;
    }

    @Override
    @Transactional("slaveTransactionManager")
    public long countStampProduct(String categoryIds) {
        List<String>  categoryIdList = new ArrayList<String>();
        if(StringUtils.isNotEmpty(categoryIds)){
            String[] splitIds = categoryIds.split(",");
            if (splitIds.length != 0) {
                for (int i = 0; i < splitIds.length; i++) {
                    categoryIdList.add(splitIds[i]);
                }
            }
        }
        return productBindingRepositorySlave.countStampDefinitionProductBinding(categoryIdList);
    }

    @Override
    @Transactional("slaveTransactionManager")
    public boolean canProductModify(String productId) {
        Long stampDefintionCount = stampRepositorySlave.getByProductIdAndActivityStatus(productId);
        if(stampDefintionCount==null || stampDefintionCount.intValue() == 0){
            return true;
        }else{
            return false;
        }
       
    }
}
