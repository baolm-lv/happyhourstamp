/**
 * 
 */
package com.lvmama.vst.hhs.stamp.service;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.BeanUtilsBean;
import org.apache.commons.beanutils.converters.BigDecimalConverter;
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

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.lvmama.vst.back.biz.po.BizCategory;
import com.lvmama.vst.back.client.biz.service.CategoryClientService;
import com.lvmama.vst.back.client.ord.service.OrdPersonClientService;
import com.lvmama.vst.back.order.po.OrdPerson;
import com.lvmama.vst.comm.utils.DateUtil;
import com.lvmama.vst.comm.vo.ResultHandleT;
import com.lvmama.vst.comm.vo.order.BuyInfo;
import com.lvmama.vst.hhs.common.exception.StampBizException;
import com.lvmama.vst.hhs.common.exception.StampErrorCodes;
import com.lvmama.vst.hhs.common.utils.HhsConstants;
import com.lvmama.vst.hhs.common.utils.HhsUtils;
import com.lvmama.vst.hhs.model.admin.StampAssociatedProdAvailTimeSlotVo;
import com.lvmama.vst.hhs.model.common.Constant.OperateType;
import com.lvmama.vst.hhs.model.common.Constant.StampPhase;
import com.lvmama.vst.hhs.model.common.Constant.StampStatus;
import com.lvmama.vst.hhs.model.common.StampDuration;
import com.lvmama.vst.hhs.model.product.SimpleGoodsVo;
import com.lvmama.vst.hhs.model.stamp.StampCode;
import com.lvmama.vst.hhs.model.stamp.StampDetails;
import com.lvmama.vst.hhs.model.stamp.StampExchangeOrderItem;
import com.lvmama.vst.hhs.model.stamp.StampExchangeOrderRefund;
import com.lvmama.vst.hhs.model.stamp.StampGoods;
import com.lvmama.vst.hhs.model.stamp.StampOrderPerson;
import com.lvmama.vst.hhs.model.stamp.StampProduct;
import com.lvmama.vst.hhs.model.stamp.StampRedeem;
import com.lvmama.vst.hhs.product.dao.SuppGoodEntity;
import com.lvmama.vst.hhs.product.service.ProductService;
import com.lvmama.vst.hhs.stamp.util.StampUtils;
import com.lvmama.vst.hhs.stampDefinition.dao.PresaleInventoryUnitEntity;
import com.lvmama.vst.hhs.stampDefinition.dao.PresaleStampDefinitionGoodsBindingEntity;
import com.lvmama.vst.hhs.stampDefinition.dao.PresaleStampDefinitionProductBindingEntity;
import com.lvmama.vst.hhs.stampDefinition.dao.StampDefinitionEntity;
import com.lvmama.vst.hhs.stampDefinition.dao.StampEntity;
import com.lvmama.vst.hhs.stampDefinition.repository.StampRepository;
import com.lvmama.vst.hhs.stampDefinition.repositorySlave.PresaleInventoryUnitRepositorySlave;
import com.lvmama.vst.hhs.stampDefinition.repositorySlave.PresaleStampDefinitionGoodsBindingRepositorySlave;
import com.lvmama.vst.hhs.stampDefinition.repositorySlave.PresaleStampDefinitionProductBindingRepositorySlave;
import com.lvmama.vst.hhs.stampDefinition.repositorySlave.StampDefinitionRepositorySlave;
import com.lvmama.vst.hhs.stampDefinition.repositorySlave.StampRepositorySlave;
import com.lvmama.vst.hhs.stampDefinition.service.DepartmentService;

/**
 * @author fengyonggang
 *
 */
@Service
public class StampServiceImpl implements StampService {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(StampServiceImpl.class);

	@Autowired
    private StampDefinitionRepositorySlave stampDefinitionRepositorySlave;  
	@Autowired
    private PresaleStampDefinitionProductBindingRepositorySlave stampDefinitionProductBindingRepositorySlave;
	@Autowired
    private CategoryClientService categoryClientService;
	@Autowired
    private PresaleInventoryUnitRepositorySlave inventoryUnitRepositorySlave;
	@Autowired
    private PresaleStampDefinitionGoodsBindingRepositorySlave goodsBindingRepositorySlave;
	@Autowired
	private StampRepositorySlave stampRepositorySlave;
	@Autowired
	private StampRepository stampRepository;
	@Autowired
	private PreSaleInventoryUnitService preSaleInventoryUnitService;
    @Autowired
    private PresaleInventoryUnitRepositorySlave presaleInventoryUnitRepositorySlave;
//    @Autowired
//	private OrderService orderService;
    @Autowired
	private StampOperationLogService operationLogService;
    @Autowired
    private ProductService productService;
    @Autowired
    private OrdPersonClientService ordPersonClientService;
    @Autowired
    private DepartmentService departmentService;
    public static final String HHS_STAMP_DEFINITION_KEY = "hhs.model.stamp.StampDetails_";   
	@Override
	@Transactional("slaveTransactionManager")
	public StampDefinitionEntity getStampDefinitionById(String id) {
		return stampDefinitionRepositorySlave.findOne(id);
	}
	
	@Override
    @Transactional("slaveTransactionManager")	
	public StampDetails getSaledStamp(String stampId) {
		StampDetails stamp = getStampById(stampId);
		if(stamp.getInventoryLevel() <= 0) {
			throw new RuntimeException("No inventory found for stampId: " + stampId);
		}
		return stamp;
	}
	
	@Override
    @Transactional("slaveTransactionManager")
	@Cacheable(value = "hhs.model.stamp.StampDetails", cacheManager = "expired5MinutesCacheManager", key = "#root.target.HHS_STAMP_DEFINITION_KEY + #id")
    public StampDetails getStampById(String id) {
    	StampDefinitionEntity stampDefinition = this.stampDefinitionRepositorySlave.getById(id);
    	if(stampDefinition == null) {
    		LOGGER.warn("can not find stamp definition by id {}", id);
    		return null;
    	}
    	return getStampDetails(stampDefinition);
    }
	
	@Override
	@Transactional("slaveTransactionManager")
    public StampDetails getStampDetails(StampDefinitionEntity stampDefinition) {
    	StampDetails details = new StampDetails();
    	BeanUtilsBean beanUtilsBean = new BeanUtilsBean();
    	beanUtilsBean.getConvertUtils().register(new BigDecimalConverter(), BigDecimal.class);
    	try {
			beanUtilsBean.copyProperties(details, stampDefinition);
		} catch (Exception e) {
			LOGGER.error("Exception encountered when copy properties", e);
		} 
    	//moenyDivide100(details);
    	if(stampDefinition.getDownPayment() != null){
    		details.setDownPayment(stampDefinition.getDownPayment().longValue());
        }
        if(stampDefinition.getSalePrice() != null){
        	details.setSalePrice(stampDefinition.getSalePrice().longValue());
        }
        if(stampDefinition.getPrintPrice() != null){
        	details.setPrintPrice(stampDefinition.getPrintPrice().longValue());
        }
        if(stampDefinition.getSetPrice() != null){
        	details.setSetPrice(stampDefinition.getSetPrice().longValue());
        }
    	
    	if(StringUtils.isNotBlank(stampDefinition.getAssociatedProdAvailTimeslot())) {
			StampAssociatedProdAvailTimeSlotVo prodAvailTimeSlot = (StampAssociatedProdAvailTimeSlotVo) HhsUtils.jsonToObject(stampDefinition.getAssociatedProdAvailTimeslot(), StampAssociatedProdAvailTimeSlotVo.class);
			LOGGER.debug("TimeSlotObject: {}", prodAvailTimeSlot);
			details.setAssociatedProdAvailTimeSlot(StampUtils.translateAvailTimeslot(prodAvailTimeSlot));
    	}
    	
    	if(StringUtils.isNotBlank(stampDefinition.getStampRedeemablelTimeslot())) {
            StampAssociatedProdAvailTimeSlotVo prodAvailTimeSlot = (StampAssociatedProdAvailTimeSlotVo) HhsUtils.jsonToObject(stampDefinition.getStampRedeemablelTimeslot(), StampAssociatedProdAvailTimeSlotVo.class);
            LOGGER.debug("TimeSlotObject: {}", prodAvailTimeSlot);
            details.setStampRedeemablelTimeslot(StampUtils.translateAvailTimeslot(prodAvailTimeSlot));
        }
    	details.setStampOnsaleDuration(new StampDuration(stampDefinition.getStampOnsaleStartDate(), stampDefinition.getStampOnsaleEndDate()));
    	details.setStampRedeemableDuration(new StampDuration(stampDefinition.getStampRedeemableStartDate(), stampDefinition.getStampRedeemableEndDate()));
    	
    	PresaleStampDefinitionProductBindingEntity productBinding = stampDefinition.getProductBinding();
    	if(productBinding == null) {
    		productBinding = this.stampDefinitionProductBindingRepositorySlave.getByStampDefinition(stampDefinition);
    	}
    	if(productBinding != null) {
    		StampProduct stampProduct = new StampProduct();
    		try {
				BeanUtils.copyProperties(stampProduct, productBinding);
			} catch (Exception e) {
				LOGGER.error("Exception encountered when copy properties", e);
			}
    		stampProduct.setEffectDuration(new StampDuration(productBinding.getEffectDate(), productBinding.getExpiryDate()));
    		ResultHandleT<BizCategory> bT = categoryClientService.findCategoryById(new Long(stampProduct.getCategoryId()==null?"1":stampProduct.getCategoryId()));
    		if(bT.isSuccess() && bT.getReturnContent() != null){
    			stampProduct.setCategoryName(bT.getReturnContent().getCategoryName());
            }
    		details.setBoundMerchant(stampProduct);
    	}
    	
    	PresaleInventoryUnitEntity inventory = this.inventoryUnitRepositorySlave.getByStampDefinitionId(stampDefinition.getId());
    	if(inventory != null) {
    		details.setInventoryLevel(inventory.getInventoryLevel());
    	}
    	List<PresaleStampDefinitionGoodsBindingEntity> goodsBindings = goodsBindingRepositorySlave.getByStampDefinition(stampDefinition);
    	details.setGoods(getPrimaryStampGoods(goodsBindings));
    	
    	Date current = DateUtil.stringToDate(DateUtil.SimpleFormatDateToString(new Date()), DateUtil.SIMPLE_DATE_FORMAT);
		boolean canSale = details.getInventoryLevel() > 0
				&& details.getStampOnsaleDuration() != null
				&& details.getStampOnsaleDuration().getStartDate().compareTo(current) <= 0
				&& details.getStampOnsaleDuration().getEndDate().compareTo(current) >= 0;
		details.setOnSale(canSale);
		
		//售卖方式
		details.setSaleType(productService.getProductSaleTypeByStampId(stampDefinition.getId()));
		
        return details;
    }
	
	private List<StampGoods> getPrimaryStampGoods(List<PresaleStampDefinitionGoodsBindingEntity> goodsBindings) {
    	List<StampGoods> list = new ArrayList<StampGoods>();
    	if(goodsBindings != null) {
    		for(PresaleStampDefinitionGoodsBindingEntity entity : goodsBindings) {
    			//主商品
    			if(HhsConstants.BRANCH_FLAG_PRIMARY.equals(entity.getBranchFlag()) || entity.getBranchFlag().startsWith(HhsConstants.BRANCH_FLAG_GROUP_PREFIX)) {
    				list.add(new StampGoods(entity.getId(), new Long(entity.getGoodsId()), entity.getGoodsName(), entity.getBranchFlag(),entity.getCategoryId()));
    			}
    		}
    	}
    	return list;
    }
	
	@Override
    public List<StampDetails> getStampDetails(List<StampDefinitionEntity> stampDefinitions) {
    	List<StampDetails> stampDetails = null;
    	if(stampDefinitions != null) {
        	stampDetails = new ArrayList<StampDetails>();
        	for (StampDefinitionEntity stampDefinition : stampDefinitions) {
        		try {
        			stampDetails.add(getStampDetails(stampDefinition));
        		} catch (Exception e) {
        			LOGGER.info("Found invalid stamp, stampId : {}", stampDefinition.getId());
        		}
        	}
        }
        return stampDetails;
    }
	
	@Override
	@Transactional("slaveTransactionManager")
	public List<StampDefinitionEntity> getStampByProductId(Long productId) {
		return this.stampDefinitionRepositorySlave.getByProductId(String.valueOf(productId));
	}
	
	@Override
	@Transactional("slaveTransactionManager")
	public List<StampDefinitionEntity> getStampByIds(List<String> stampIds) {
		return this.stampDefinitionRepositorySlave.findAll(stampIds);
	}
	
	@Override
	@Transactional("slaveTransactionManager")
	public List<StampDefinitionEntity> getStampByProductIdAndDepartId(Long productId, Long departId) {
		if(departId == null || departId == 0L) {
			return getStampByProductId(productId);
		}
		return stampDefinitionRepositorySlave.getByProductIdAndDepartId(String.valueOf(productId), String.valueOf(departId));
	}
	
	@Override
	@Transactional("slaveTransactionManager")
	public List<StampDefinitionEntity> getAvailableStampByProductIdAndDepartId(Long productId, Long departId, StampPhase phase) {
		List<StampDefinitionEntity> result = null;
		Date date = DateUtil.stringToDate(DateUtil.SimpleFormatDateToString(new Date()), DateUtil.SIMPLE_DATE_FORMAT);
		Timestamp now = new Timestamp(date.getTime());
		if(StampPhase.SALE.equals(phase)) {
			if(departId == null || departId == 0L) {
				result = stampDefinitionRepositorySlave.getByProductIdAndStartSaleDateAndEndSaleDate(String.valueOf(productId),  now);
			} else {
				result = stampDefinitionRepositorySlave.getByProductIdAndDepartIdAndStartSaleDateAndEndSaleDate(String.valueOf(productId), String.valueOf(departId),now);
			}
			
			//券售卖的时候验证库存信息
			if(CollectionUtils.isNotEmpty(result)) {
				result = new ArrayList<StampDefinitionEntity>(result);//result is read-only
				List<String> stampIds = extractStampDefinitionIdFromDefinition(result);
				LOGGER.debug("will query inventory by stampIds: {}", stampIds);
				List<PresaleInventoryUnitEntity> inventorys = presaleInventoryUnitRepositorySlave.getByStampDefinitionIdIn(stampIds.toArray(new String[stampIds.size()]));
				Map<String, PresaleInventoryUnitEntity> inventoryMap = translateStampInventories(inventorys);
				Iterator<StampDefinitionEntity> iter = result.iterator();
				while(iter.hasNext()) {
					StampDefinitionEntity entity = iter.next();
					PresaleInventoryUnitEntity inventory = inventoryMap.get(entity.getId());
					List<PresaleStampDefinitionGoodsBindingEntity> goods = goodsBindingRepositorySlave.getByStampDefinition(entity);
					if(inventory == null || inventory.getInventoryLevel() <= 0 || goods ==null || goods.isEmpty() || goods.size()==0) {
						LOGGER.info("inventory is not enough, will remove the stmap: {}", entity.getId());
						iter.remove();
					}
				}
			}
		} else if(StampPhase.REDEEM.equals(phase)) {
			if(departId == null || departId == 0L) {
				result = stampDefinitionRepositorySlave.getByProductIdAndRedeemDate(String.valueOf(productId), now);
			} else {
				result = stampDefinitionRepositorySlave.getByProductIdAndDepartIdAndRedeemDate(String.valueOf(productId), String.valueOf(departId), now);
			}
		} else if(StampPhase.ALL.equals(phase)) {//包括兑换和销售阶段
			if(departId == null || departId == 0L) {
				result = stampDefinitionRepositorySlave.getByProductIdAndSaleStartDateAndRedeemEndDate(String.valueOf(productId), new Timestamp(new Date().getTime()), now);
			} else {
				result = stampDefinitionRepositorySlave.getByProductIdAndDepartIdAndSaleStartDateAndRedeemEndDate(String.valueOf(productId), String.valueOf(departId),  new Timestamp(new Date().getTime()), now);
			}
		}
		return result;
	}
	
	
	private Map<String, PresaleInventoryUnitEntity> translateStampInventories(List<PresaleInventoryUnitEntity> list) {
		if(list == null) 
			return null;
		Map<String, PresaleInventoryUnitEntity> map = new HashMap<String, PresaleInventoryUnitEntity>();
		for(PresaleInventoryUnitEntity entity : list) {
			map.put(entity.getStampDefinitionId(), entity);
		}
		return map;
	}
	
	@Override
	public List<StampDetails> getAvailableStampDetailsByProductIdAndDepartId(Long productId, Long departId, StampPhase phase) {
		List<StampDefinitionEntity> definitionList = getAvailableStampByProductIdAndDepartId(productId, departId, phase);
		if (definitionList == null || definitionList.size() == 0) {
			LOGGER.info("no stamp found based on product Id {}", productId);
			return null;
		}
		return getStampDetails(definitionList);
	}
	
	@Override
	@Transactional("slaveTransactionManager")
	public List<StampRedeem> getRedeemStamps(Long productId, Long userId, Long departId, boolean withCode) {
		List<StampDefinitionEntity> definitionList = getAvailableStampByProductIdAndDepartId(productId, departId, StampPhase.REDEEM);
		if (definitionList == null || definitionList.size() == 0) {
			LOGGER.info("no stamp found based on product Id {}", productId);
			return null;
		}

		//产品下的可用的券ID
		List<String> stampIds = extractStampDefinitionIdFromDefinition(definitionList);

		//用户购买的未使用的券
		Map<String, List<StampEntity>> stampMap = getUnusedStampCodeByStampIdAndCustomerId(stampIds, String.valueOf(userId));
		if(MapUtils.isEmpty(stampMap)) {
			LOGGER.info("no unused stamp code found, userId: {}, productId: {}", userId, productId);
			return null;
		}
		
		List<StampDefinitionEntity> unUsedStamps = new ArrayList<StampDefinitionEntity>();
		for(StampDefinitionEntity definition : definitionList) {
			if(stampMap.containsKey(definition.getId())) {
				unUsedStamps.add(definition);
			}
		}
		
		List<StampDetails> stampDetails = getStampDetails(unUsedStamps);
		if(CollectionUtils.isEmpty(stampDetails)) {
			LOGGER.info("no stamp details found, userId: {}, productId: {}", userId, productId);
			return null;
		}
		
		List<StampRedeem> stampRedeems = new ArrayList<StampRedeem>();
		for(StampDetails stamp : stampDetails) {
			List<StampEntity> stmapCodes = stampMap.get(stamp.getId());
			if(CollectionUtils.isEmpty(stmapCodes)) {
				LOGGER.info("can not find stamp code by stampId {}", stamp.getId());
				continue;
			}
			List<PresaleStampDefinitionGoodsBindingEntity> goodsEntitys = goodsBindingRepositorySlave.getByStampDefinitionIdOrderByGoodsIdAsc(stamp.getId());
			if(CollectionUtils.isEmpty(goodsEntitys)) {
				LOGGER.info("can not find goods binding by stampId {}", stamp.getId());
				continue;
			}
			boolean child = false;
			boolean firstLoop = true;
			int maxQuantity = 0, minQuantity = 0;
			for(PresaleStampDefinitionGoodsBindingEntity goodsBinding : goodsEntitys) {
				if(HhsConstants.BRANCH_FLAG_PRIMARY_CHILD.equals(goodsBinding.getBranchFlag())) {
					child = true;
				}
				SuppGoodEntity suppGoodEntity = productService.getSuppGoodEntity(NumberUtils.toLong(goodsBinding.getGoodsId()));
				if(suppGoodEntity == null) {
					LOGGER.info("Data issue, can not find supp goods based on goodsId: {}", goodsBinding.getGoodsId());
					continue;
				}
				if(firstLoop) {
					maxQuantity = suppGoodEntity.getMaxQuantity().intValue();
					minQuantity = suppGoodEntity.getMinQuantity().intValue();
					firstLoop = false;
				} else {
					if(suppGoodEntity.getMaxQuantity().intValue() < maxQuantity) 
						maxQuantity = suppGoodEntity.getMaxQuantity().intValue();
					if(suppGoodEntity.getMinQuantity().intValue() > minQuantity)
						minQuantity = suppGoodEntity.getMinQuantity().intValue();
				}
			}
			
			StampRedeem stampRedeem = new StampRedeem();
			stampRedeem.setStamp(stamp);
			stampRedeem.setBuyAmount(stmapCodes.size());
			stampRedeem.setPeopleType(child ? HhsConstants.CHILD : HhsConstants.AUDLT);
			stampRedeem.setSaleType(stamp.getSaleType());
			stampRedeem.setGoods(getSuppGoodsVo(goodsEntitys));
			if(withCode) { //加载券码
				stampRedeem.setCodes(translateStampCodes(stampMap.get(stamp.getId())));
			}
			stampRedeem.setMaxAvailableAmount(maxQuantity);
			stampRedeem.setMinAvailableAmount(minQuantity);
			stampRedeems.add(stampRedeem);
		}
		return stampRedeems;
	}
	
	private List<StampCode> translateStampCodes(List<StampEntity> stamps) {
		if(stamps == null) 
			return null;
		List<StampCode> list = new ArrayList<StampCode>();
		for(StampEntity entity : stamps) {
			StampCode dest = new StampCode();
			org.springframework.beans.BeanUtils.copyProperties(entity, dest);
			list.add(dest);
		}
		return list;
	}
	
	private List<SimpleGoodsVo> getSuppGoodsVo(List<PresaleStampDefinitionGoodsBindingEntity> goodsEntitys) {
		if(goodsEntitys == null) 
			return null;
		List<SimpleGoodsVo> list = new ArrayList<SimpleGoodsVo>();
		for(PresaleStampDefinitionGoodsBindingEntity entity : goodsEntitys) {
			list.add(new SimpleGoodsVo(NumberUtils.toLong(entity.getGoodsId()), entity.getGoodsName()));
		}
		return list;
	}
	
	private Map<String, List<StampEntity>> translateStampCodesToMap(List<StampEntity> stampCodes) {
		if(stampCodes == null) 
			return null;
		Map<String, List<StampEntity>> map = new HashMap<String, List<StampEntity>>();
		for(StampEntity stamp : stampCodes) {
			if(map.containsKey(stamp.getStampDefinitionId())) {
				map.get(stamp.getStampDefinitionId()).add(stamp);
			} else {
				List<StampEntity> list = new ArrayList<StampEntity>();
				list.add(stamp);
				map.put(stamp.getStampDefinitionId(), list);
			}
		}
		return map;
	}
	
	private List<String> extractStampDefinitionIdFromDefinition(List<StampDefinitionEntity> definitionList) {
		if(CollectionUtils.isEmpty(definitionList)) 
			return null;
		ArrayList<String> stampIds = new ArrayList<String>();
		for(StampDefinitionEntity stamp : definitionList) {
			stampIds.add(stamp.getId());
		}
		stampIds.trimToSize();
		return stampIds;
	}

    @Override
    @Transactional("slaveTransactionManager")
    public List<StampEntity> getStampCodeByOrderId(String orderId) {
        return this.stampRepositorySlave.getByOrderId(orderId);
    }
    
    @Override
    @Transactional("slaveTransactionManager")
    public List<StampEntity> getByOrderIdAndStampStatus(String orderId, String stampStatus) {
        return this.stampRepositorySlave.getByOrderIdAndStampStatus(orderId, stampStatus);
    }
    
    @Override
    @Transactional("slaveTransactionManager")
    public List<StampEntity> getUnusedStampCodeByStampIdAndCustomerId(String stampId, String customerId) {
    	return stampRepositorySlave.getByCustomerIdAndStampDefinitionIdAndStampStatus(customerId, stampId, StampStatus.UNUSE.name());
    }
    
    @Override
    @Transactional("slaveTransactionManager")
    public Map<String, List<StampEntity>> getUnusedStampCodeByStampIdAndCustomerId(List<String> stampIds, String customerId) {
    	List<StampEntity> stampCodes = stampRepositorySlave.getByCustomerIdAndStampDefinitionIdInAndStampStatus(customerId, stampIds, StampStatus.UNUSE.name());
    	return translateStampCodesToMap(stampCodes);
    }

    @Override
    @Transactional("transactionManager")
    public String saveStamp(BuyInfo buyInfo, StampDefinitionEntity definition, long orderId) {
        int amount = buyInfo.getQuantity();
        StringBuilder ids = new StringBuilder();
        String remark = "NULL->" + StampStatus.BALANCED_DUE;
        for(int i=0;i<amount;i++){
            StampEntity stamp = new StampEntity();
            stamp.setCreateDate(new Timestamp(System.currentTimeMillis()));
            stamp.setCustomerId(String.valueOf(buyInfo.getUserNo()));
            stamp.setId(UUID.randomUUID().toString());
            stamp.setOrderId(String.valueOf(orderId));
            stamp.setName(definition.getName());
            stamp.setSerialNumber(HhsUtils.generateStampNo());
            stamp.setStampDefinitionId(definition.getId());
            stamp.setStampStatus(StampStatus.BALANCED_DUE.name()); // 未支付
            stamp.setUpdateDate(new Timestamp(System.currentTimeMillis()));
            stamp.setPrice(Integer.valueOf(buyInfo.getItemList().get(0).getPrice())); // 冗余券单价
            stamp.setExpiredDate(definition.getStampRedeemableEndDate()); // 券有效期
            this.stampRepository.save(stamp);
            LOGGER.info("create stamp {}", stamp.getId());
            ids.append(stamp.getId()).append(",");
            departmentService.deleteStampDefinitionById(definition.getId());
            PresaleStampDefinitionProductBindingEntity product = definition.getProductBinding();
            if(product != null){
                departmentService.deleteProductIsExchange(product.getProductId(), product.getDepartId());
                departmentService.deleteProductStampsDetails(product.getProductId(), product.getDepartId());
                departmentService.deleteGetStampProductByProductIds(product.getProductId());
            }
           
            operationLogService.addLog(OperateType.CREATE, String.valueOf(orderId), stamp.getSerialNumber(), remark);
        }
     
        return ids.toString();
    }    
    
	@Override
	@Transactional("slaveTransactionManager")
	public List<StampExchangeOrderItem> getStampExchangeOrder(String useOrderId) {

		List<StampEntity> entityList = stampRepositorySlave.getByUseOrderId(useOrderId);
		if(CollectionUtils.isEmpty(entityList)) 
			return null;
		
		Map<String, StampExchangeOrderItem> map = Maps.newConcurrentMap();
		for(StampEntity stamp : entityList) {
			
			if(!StampStatus.USED.toString().equals(stamp.getStampStatus()))
				continue;
			
			String useOrderItemId = stamp.getUseOrderItemId();
			
			if(useOrderItemId == null) {
				LOGGER.info("绑定多个商品的券暂不支持退款，在此过滤掉：{}", JSON.toJSONString(stamp));
				continue;
			}
			
			StampCode code = new StampCode();
			org.springframework.beans.BeanUtils.copyProperties(stamp, code);
			code.setExpiredDate(new Date(stamp.getExpiredDate().getTime()));
			
			if(map.containsKey(useOrderItemId)) {
				map.get(useOrderItemId).getUsedStampCodes().add(code);
			} else {
				StampExchangeOrderItem item = new StampExchangeOrderItem();
				item.setUseOrderItemId(useOrderItemId);
//				StampDefinitionEntity stampDefinition = stampDefinitionRepository.findOne(stamp.getStampDefinitionId());
//				item.setExpiredTime(stampDefinition.getStampRedeemableEndDate());
				
				item.setExpiredTime(stamp.getExpiredDate());
				
//				OrdOrder order = orderService.queryOrdorderByOrderId(Long.valueOf(stamp.getOrderId()));
//				if (order == null) {
//					throw new RuntimeException("order not found");
//				}
//				if (CollectionUtils.isEmpty(order.getOrderItemList())) {
//					throw new RuntimeException("order item not found ");
//				}
//				long stampAmount = order.getOrderItemList().get(0).getPrice();
//				item.setStampAmount(stampAmount);
				
				item.setStampAmount(stamp.getPrice()==null?0:stamp.getPrice().longValue());// 冗余单价
				List<StampCode> codes = Lists.newArrayList();
				codes.add(code);
				item.setUsedStampCodes(codes);
				
				map.put(useOrderItemId, item);
			}
		}
		
		return Lists.newArrayList(map.values());
	}

	@Override
	@Transactional("transactionManager")
	public void paymentComplete(String orderId) {
		List<StampEntity> list = stampRepository.getByOrderId(orderId);
		if (CollectionUtils.isEmpty(list))
			throw new RuntimeException("stamp not found,orderId=" + orderId);

		int row = stampRepository.compareAndSetStatusByOrderId(orderId, StampStatus.BALANCED_DUE.name(),
				StampStatus.UNUSE.name());
		if (row != list.size()) {
			throw new RuntimeException("update stamp status to UNUSE failed, orderId=" + orderId);
		}
		String remark = StampStatus.BALANCED_DUE + "->" + StampStatus.UNUSE;
		for (StampEntity s : list) {
			operationLogService.addLog(OperateType.PAYED, orderId, s.getSerialNumber(), remark);
		}
	}
	
	@Override
	@Transactional("transactionManager")
	public void updateStampsForOrder(List<String> ids, String orderId) {
		updateStampsForOrder(ids, orderId, null);
	}
	
	@Override
	public void updateStampsForOrder(List<String> ids, String orderId, String orderItemId) {
		if(CollectionUtils.isEmpty(ids)) 
			throw new RuntimeException("id should not be null");
		if(StringUtils.isEmpty(orderId)) 
			throw new RuntimeException("orderId should not be null");
		
		int row = 0;
		if(StringUtils.isNotBlank(orderItemId)) {
			row = stampRepository.updateForOrder(ids, orderId, orderItemId, StampStatus.UNUSE.name(), StampStatus.USED.name());
		} else {
			row = stampRepository.updateForOrder(ids, orderId, StampStatus.UNUSE.name(), StampStatus.USED.name());
		}
		
		if(row != ids.size()) 
			throw new RuntimeException("Update failed due to data issue. The expected update number is " + ids.size() + ", but actual is " + row);
		for (String id : ids) {
			operationLogService.addLog(OperateType.USE, orderId, id, StampStatus.UNUSE + "->" + StampStatus.USED);
		}
	}
	

	@Transactional("transactionManager")
	public int unbindStamp(String id, String oldStatus, String newStatus) {
		return stampRepository.unbindStamp(id, oldStatus, newStatus);
	}
	
	@Override
	@Transactional("transactionManager")
	public void invalidStampCode(String orderId, boolean hasPaid) {

		List<StampEntity> list = stampRepository.getByOrderId(orderId);
		if (CollectionUtils.isEmpty(list))
			throw new RuntimeException("stamp not found,orderId=" + orderId);
		
		String stampDefinitionId = list.get(0).getStampDefinitionId();
		
		String remark = null;
		int row = 0;
		if(hasPaid) { // 已付订单取消
			// 验证券状态
			for(StampEntity stamp : list) {
				if(StampStatus.BALANCED_DUE.name().equals(stamp.getStampStatus())) {
					throw new RuntimeException("paid order but not paid stamp error, stamp=" + stamp.getSerialNumber());
				}
			}
			remark = StampStatus.UNUSE + "->" + StampStatus.PAID_INVALID;
			row = stampRepository.compareAndSetStatusByOrderId(orderId, StampStatus.UNUSE.name(),
					StampStatus.PAID_INVALID.name());
			LOGGER.info("paid order invalid, orderId={}, invalid stamp num={}", orderId, row);
			
			list = stampRepository.getByOrderIdAndStampStatus(orderId, StampStatus.PAID_INVALID.name());
			
			if (list != null && list.size() > 0 && row == 0) {
				throw new RuntimeException("repeat request, orderId=" + orderId);
			}
			
		} else { // 未付订单取消
			remark = StampStatus.BALANCED_DUE + "->" + StampStatus.INVALID;

			row = stampRepository.compareAndSetStatusByOrderId(orderId, StampStatus.BALANCED_DUE.name(),
					StampStatus.INVALID.name());
			if (row == 0) {
				throw new RuntimeException("repeat request, orderId=" + orderId);
			}
		}
		
        preSaleInventoryUnitService.rollbackConsumeInventory(stampDefinitionId, row);
		
		for (StampEntity s : list) {
			operationLogService.addLog(OperateType.INVALID, orderId, s.getSerialNumber(), remark);
		}
	}

	@Override
	@Transactional("transactionManager")
	public void rollbackInvalidStampCode(String orderId, boolean hasPaid) {

		List<StampEntity> list = stampRepository.getByOrderId(orderId);
		if (CollectionUtils.isEmpty(list))
			throw new RuntimeException("stamp not found,orderId=" + orderId);
		String stampDefinitionId=list.get(0).getStampDefinitionId();
		String remark = null;
		int row = 0;
		if(hasPaid) {
			
			remark = StampStatus.PAID_INVALID + "->" + StampStatus.UNUSE;
			row = stampRepository.compareAndSetStatusByOrderId(orderId, StampStatus.PAID_INVALID.name(),
					StampStatus.UNUSE.name());
			LOGGER.info("paid order invalid, orderId={}, rollback invalid stamp num={}", orderId, row);
			
			list = stampRepository.getByOrderIdAndStampStatus(orderId, StampStatus.UNUSE.name());
			
		} else {
			
			remark = StampStatus.INVALID + "->" + StampStatus.BALANCED_DUE;
			row = stampRepository.compareAndSetStatusByOrderId(orderId, StampStatus.INVALID.name(),
					StampStatus.BALANCED_DUE.name());
			if (row != list.size()) {
				throw new RuntimeException("update stamp status to BALANCED_DUE failed, orderId=" + orderId);
			}
		}
		
		int updated = preSaleInventoryUnitService.consumeInventory(stampDefinitionId, row);
		if(updated == 0)
			LOGGER.error("#monitor-stamp consume stamp inventory failed at rollback invalid: {}", orderId);
		
		for (StampEntity s : list) {
			operationLogService.addLog(OperateType.ROLLBACK_INVALID, orderId, s.getSerialNumber(), remark);
		}
	}
	
	@Override
	@Transactional("slaveTransactionManager")
	public List<StampEntity> getStampCodeByIds(List<String> ids) {
		return stampRepository.findAll(ids);
	}
    
	@Override
	public List<StampEntity> updateStampsForOrder(List<StampEntity> stamps) {
		return stampRepository.save(stamps);
	}

    @Override
    @Transactional("slaveTransactionManager")
	public List<StampGoods> vstGetStampBindGoods(String id) {
		StampDefinitionEntity stampDefinition = this.stampDefinitionRepositorySlave.getById(id);
		if (stampDefinition == null) {
			LOGGER.warn("can not find stamp definition by id {}", id);
			return null;
		}
		List<PresaleStampDefinitionGoodsBindingEntity> goodsBindings = goodsBindingRepositorySlave.getByStampDefinition(stampDefinition);
		return getStampGoods(goodsBindings);
	}
    
    private List<StampGoods> getStampGoods(List<PresaleStampDefinitionGoodsBindingEntity> goodsBindings) {
        List<StampGoods> list = new ArrayList<StampGoods>();
        if(goodsBindings != null) {
            for(PresaleStampDefinitionGoodsBindingEntity entity : goodsBindings) {
                //主商品
                list.add(new StampGoods(entity.getId(), new Long(entity.getGoodsId()), entity.getGoodsName(), entity.getBranchFlag(),entity.getCategoryId()));
            }
        }
        return list;
    }
    
    @Override
    @Transactional("slaveTransactionManager")
    public StampOrderPerson getStampOrderPerson(String stampId, Long userId) {
    	if(StringUtils.isEmpty(stampId)) {
    		throw new IllegalArgumentException("stampId should not be null");
    	}
    	if(userId == null || userId < 1) {
    		throw new IllegalArgumentException("userId should not be null");
    	}
    	List<StampEntity> stamps = stampRepository.getByCustomerIdAndStampDefinitionId(String.valueOf(userId), stampId);
    	if(CollectionUtils.isEmpty(stamps)) {
    		LOGGER.info("can not find any stamps, customerId: {}, stampId: {}", userId, stampId);
    		return null;
    	}
    	String orderId = stamps.get(0).getOrderId();
    	Map<String, Object> params = new HashMap<String, Object>();
    	params.put("personType", "CONTACT");
    	params.put("objectId", Long.valueOf(orderId));
    	List<OrdPerson> persons = ordPersonClientService.findOrdPersonList(params);
    	if(CollectionUtils.isEmpty(persons)) {
    		LOGGER.info("can not find order booker, orderId: {}", orderId);
    		return null;
    	}
    	StampOrderPerson person = new StampOrderPerson();
    	org.springframework.beans.BeanUtils.copyProperties(persons.get(0), person);
    	return person;
    }

	@Override
	@Transactional("slaveTransactionManager")
	public List<StampExchangeOrderRefund> getExchangeOrderStamps(String useOrderId) {
		
		List<StampEntity> entityList = stampRepositorySlave.getByUseOrderId(useOrderId);
		if(CollectionUtils.isEmpty(entityList)) 
			throw new StampBizException(StampErrorCodes.E_REQ_1001, "错误的订单号");
		
		Map<String, StampExchangeOrderRefund> map = Maps.newConcurrentMap();
		for(StampEntity stamp : entityList) {
			
			if(!StampStatus.USED.toString().equals(stamp.getStampStatus()))
				continue;
			
			String sdId = stamp.getStampDefinitionId();
			
			StampCode code = new StampCode();
			org.springframework.beans.BeanUtils.copyProperties(stamp, code);
			code.setExpiredDate(new Date(stamp.getExpiredDate().getTime()));
			
			if(map.containsKey(sdId)) {
				map.get(sdId).getCanRefundStampCodes().add(code);
			} else {
				
				StampExchangeOrderRefund stampRefund = new StampExchangeOrderRefund();
				StampDefinitionEntity stampDefinition = stampDefinitionRepositorySlave.findOne(sdId);
				stampRefund.setId(sdId);
				stampRefund.setStampNo(stampDefinition.getStampNo());
				stampRefund.setName(stampDefinition.getName());
				stampRefund.setStampAmount(stamp.getPrice()==null?0:stamp.getPrice().longValue());// 冗余单价
				stampRefund.setExpiredTime(stamp.getExpiredDate());
				stampRefund.setProductId(stampDefinition.getProductBinding().getProductId());
				stampRefund.setProductName(stampDefinition.getProductBinding().getProductName());
				List<StampCode> codes = Lists.newArrayList();
				codes.add(code);
				stampRefund.setCanRefundStampCodes(codes);
				
				map.put(sdId, stampRefund);
			}
		}
		
		return Lists.newArrayList(map.values());
	}
}
