/**
 * 
 */
package com.lvmama.vst.hhs.product.service;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Lists;
import com.lvmama.comm.pet.po.user.UserUser;
import com.lvmama.vst.back.client.dist.service.DistributorClientService;
import com.lvmama.vst.back.client.goods.service.SuppGoodsTimePriceClientService;
import com.lvmama.vst.back.client.ord.service.OrderService;
import com.lvmama.vst.back.client.prod.service.ProdProductClientService;
import com.lvmama.vst.back.dist.po.Distributor;
import com.lvmama.vst.back.goods.po.PresaleStampTimePrice;
import com.lvmama.vst.back.goods.po.SuppGoodsBaseTimePrice;
import com.lvmama.vst.back.order.po.OrdOrder;
import com.lvmama.vst.back.order.po.OrdOrderItem;
import com.lvmama.vst.back.order.po.OrdOrderTravellerConfirm;
import com.lvmama.vst.back.order.po.OrderEnum.NEED_INVOICE_STATUS;
import com.lvmama.vst.back.prod.po.ProdProduct;
import com.lvmama.vst.back.prod.po.ProdProduct.PACKAGETYPE;
import com.lvmama.vst.comm.utils.DateUtil;
import com.lvmama.vst.comm.vo.ResultHandleT;
import com.lvmama.vst.comm.vo.order.BuyInfo;
import com.lvmama.vst.comm.vo.order.BuyInfo.HotelAdditation;
import com.lvmama.vst.comm.vo.order.BuyInfo.InvoiceInfo;
import com.lvmama.vst.comm.vo.order.BuyInfo.Item;
import com.lvmama.vst.comm.vo.order.BuyInfo.ItemPersonRelation;
import com.lvmama.vst.comm.vo.order.BuyInfo.ItemRelation;
import com.lvmama.vst.comm.vo.order.BuyInfo.PersonRelation;
import com.lvmama.vst.comm.vo.order.BuyInfo.Product;
import com.lvmama.vst.comm.vo.order.BuyInfoAddition;
import com.lvmama.vst.comm.vo.order.Person;
import com.lvmama.vst.hhs.common.utils.HhsConstants;
import com.lvmama.vst.hhs.common.utils.HhsUtils;
import com.lvmama.vst.hhs.model.admin.StampAssociatedProdAvailTimeSlotVo;
import com.lvmama.vst.hhs.model.admin.StampOrderVo;
import com.lvmama.vst.hhs.model.common.Constant;
import com.lvmama.vst.hhs.model.common.Constant.OperateType;
import com.lvmama.vst.hhs.model.common.Constant.OrderSubType;
import com.lvmama.vst.hhs.model.common.Constant.SaleType;
import com.lvmama.vst.hhs.model.common.Constant.StampStatus;
import com.lvmama.vst.hhs.model.product.GoodsUsage;
import com.lvmama.vst.hhs.model.product.HotelValidationRequest;
import com.lvmama.vst.hhs.model.product.HotelValidationResponse;
import com.lvmama.vst.hhs.model.product.InvoiceInfoVO;
import com.lvmama.vst.hhs.model.product.ProductSaleType;
import com.lvmama.vst.hhs.model.product.StampBuyInfo;
import com.lvmama.vst.hhs.model.product.StampUsage;
import com.lvmama.vst.hhs.model.product.TravellerDelayInfo;
import com.lvmama.vst.hhs.model.stamp.SaveOrderResponse;
import com.lvmama.vst.hhs.model.stamp.StampCodePrice;
import com.lvmama.vst.hhs.model.stamp.StampContact;
import com.lvmama.vst.hhs.model.stamp.StampOrderPayment;
import com.lvmama.vst.hhs.model.stamp.StampOrderRequest;
import com.lvmama.vst.hhs.product.dao.OrdStampOrderEntity;
import com.lvmama.vst.hhs.product.dao.OrdStampOrderItemEntity;
import com.lvmama.vst.hhs.product.dao.ProdPackageDetailEntity;
import com.lvmama.vst.hhs.product.dao.ProdPackageGroupEntity;
import com.lvmama.vst.hhs.product.dao.ProdPackageGroupHotelEntity;
import com.lvmama.vst.hhs.product.dao.ProdProductBranchEntity;
import com.lvmama.vst.hhs.product.dao.ProdProductEntity;
import com.lvmama.vst.hhs.product.dao.SuppGoodEntity;
import com.lvmama.vst.hhs.product.repository.OrdOrderItemRepository;
import com.lvmama.vst.hhs.product.repository.OrdOrderRepository;
import com.lvmama.vst.hhs.product.repository.OrdStampOrderItemRepository;
import com.lvmama.vst.hhs.product.repository.OrdStampOrderRepository;
import com.lvmama.vst.hhs.product.repository.ProdProductBranchRepository;
import com.lvmama.vst.hhs.product.repository.ProductRepository;
import com.lvmama.vst.hhs.product.repository.SuppGoodsRepository;
import com.lvmama.vst.hhs.stamp.service.StampOperationLogService;
import com.lvmama.vst.hhs.stamp.service.StampService;
import com.lvmama.vst.hhs.stamp.util.StampUtils;
import com.lvmama.vst.hhs.stampDefinition.bo.StampGoodsBindingBo;
import com.lvmama.vst.hhs.stampDefinition.bo.StampGroupGoodsBo;
import com.lvmama.vst.hhs.stampDefinition.bo.StampPackageGoodsBo;
import com.lvmama.vst.hhs.stampDefinition.dao.PresaleStampDefinitionGoodsBindingEntity;
import com.lvmama.vst.hhs.stampDefinition.dao.StampDefinitionEntity;
import com.lvmama.vst.hhs.stampDefinition.dao.StampEntity;
import com.lvmama.vst.hhs.stampDefinition.dao.StampOperationLogEntity;
import com.lvmama.vst.hhs.stampDefinition.service.StampGoodsService;
import com.lvmama.vst.pet.adapter.UserUserProxyAdapter;

/**
 * @author fengyonggang
 *
 */
@Service
public class ProductOrderServiceImpl implements ProductOrderService{

	private static final Logger LOGGER = LoggerFactory.getLogger(ProductOrderServiceImpl.class);
	private static final String STAMP_TYPE = "STAMP";
	
	@Autowired
	private OrdStampOrderRepository stampOrderRepository;
	@Autowired
	private OrdStampOrderItemRepository stampOrderItemRepository;
	@Autowired
	private OrdOrderRepository orderRepository;
	@Autowired
	private OrdOrderItemRepository orderItemRepository;
	@Autowired
	private SuppGoodsRepository suppGoodsRepository;
	@Autowired
	private ProductRepository productRepository;
/*	@Autowired
	private ProdProductBranchRepository prodProductBranchRepository;*/
	@Autowired
    private OrderService orderService;
	@Autowired
    @Qualifier("oracleEntityManager")
    private EntityManager em;
	@Autowired
	private StampService stampService;
//	@Autowired
//	private StampProductService stampProductService;
	@Autowired
	private SuppGoodsTimePriceClientService suppGoodsTimePriceClientService;
	@Autowired
	private StampGoodsService stampGoodsService;
	@Autowired 
	private ProductService productService;
	@Autowired
	private DistributorClientService distributorClientRemote;
	@Autowired
	private ProdProductClientService prodProductClientService;
	@Autowired
	private ProductAdditionService productAdditionService;
	@Autowired
	private UserUserProxyAdapter userUserProxyAdapter;
	@Autowired
	private StampOperationLogService operationLogService;
	@Autowired
	private ProductPackageService productPackageService;
	@Autowired
	private ProdProductBranchRepository prodProductBranchRepository;
	
	
	@Transactional("oracleTransactionManager")
	public void saveStampOrder(StampOrderRequest request, StampDefinitionEntity definition, long orderId, long orderItemId) {
		LOGGER.info("save stamp order starting...");
		
		Timestamp now = new Timestamp(System.currentTimeMillis());
		
		OrdStampOrderEntity order = new OrdStampOrderEntity();
		order.setCreateDate(now);
		order.setUpdateDate(now);
		order.setOrderId(orderId);
		order.setUserId(String.valueOf(request.getUserNo()));
		StampOrderPayment payment = request.getPayment();
		if(StringUtils.isNotEmpty(payment.getPayType())){
		    if(payment.getPayType().equals(Constant.PAT_TYPE.PART.getCode())){
		        order.setBalanceDueWaitDate(getBalanceDueWaitDate(definition));
		        order.setDownPayment(definition.getDownPayment().longValue()*request.getAmount());
		        order.setPayType(Constant.PAT_TYPE.PART.getCode());
		    }else{
		    	//modify by baolm 全额支付是不记录
//		        order.setBalanceDueWaitDate(new Timestamp(DateUtil.DsDay_HourOfDay(new Date(), definition.getBalanceDueInHour()).getTime()));
                order.setPayType(Constant.PAT_TYPE.FULL.getCode());
		    }
		}
		order.setRemindCustomerDate(definition.getRemindCustomerDate());
		order.setUserNo(request.getUserId());
		order.setStampOrderClassification(STAMP_TYPE);
		order.setRemindCustomerTimeslot(definition.getRemindCustomerTimeslot());
		OrdStampOrderItemEntity item = new OrdStampOrderItemEntity();
		item.setOrderItemId(orderItemId);
		item.setCreateDate(now);
		item.setQuantity(new BigDecimal(request.getAmount()));
		item.setStampDefinitionId(request.getStampId());
		item.setStampName(definition.getName());
		item.setUpdateDate(now);
		item.setUserId(String.valueOf(request.getUserNo()));
		item.setUserNo(request.getUserId());
		item.setOrderId(orderId);
		item.setSubsidyAmount(definition.getOperationsAmount());
		stampOrderRepository.save(order);
		stampOrderItemRepository.save(item);
		
		LOGGER.info("update order and orderitem, sub_type: {}, orderId {}", STAMP_TYPE, orderId);
		orderRepository.updateSubTypeByOrderId(STAMP_TYPE, orderId);
		orderItemRepository.updateSubTypeByOrderId(STAMP_TYPE, new BigDecimal(orderId));
		
	}
	
	@Override
	@Transactional("oracleTransactionManager")
	public List<Long> getOrderIdByUserId(String userId, int startRow, int end) {
		return this.orderRepository.getByUserId(userId, startRow, end);
	}

    @Override
    @Transactional("oracleTransactionManager")
    public Long queryCountStampOrderByUserId(String userId) {
        Long count =this.orderRepository.countByUserId(userId);
        return count;
    }

	@Override
	@Transactional("oracleTransactionManager")
	public List<StampOrderVo> queryStampOrder(String stampId, String orderId, String contactName, String contactMobile, int startRow, int pageSize) {
		StringBuilder sql = new StringBuilder();
		sql.append(
				"select o.order_id,o.create_time,so.down_date,o.payment_time,o.order_status,o.payment_status,so.pay_type,p.full_name,p.mobile ")
				.append(" from ord_order o ").append(" join ord_person p on o.order_id=p.object_id and p.object_type='ORDER'")
				.append(" join ord_stamp_order so on o.order_id=so.order_id")
				.append(" left join ord_order_item oi on o.order_id=oi.order_id ")
				.append(" join ord_stamp_order_item soi on oi.order_item_id=soi.order_item_id ")
				.append(" where o.order_subtype='STAMP' and p.person_type='CONTACT' ");

		if (StringUtils.isNotBlank(stampId)) {
			sql.append(" and soi.stamp_definition_id='").append(stampId).append("'");
		}
		if (StringUtils.isNotBlank(orderId)) {
			sql.append(" and o.order_id=").append(orderId);
		}
		if (StringUtils.isNotBlank(contactName)) {
			sql.append(" and p.full_name like '").append(contactName).append("%'");
		}
		if (StringUtils.isNotBlank(contactMobile)) {
			sql.append(" and p.mobile like '").append(contactMobile).append("%'");
		}
		
		sql.append(" order by o.create_time desc");

		Query query = em.createNativeQuery(sql.toString());
		
		query.setFirstResult(startRow);
		query.setMaxResults(pageSize);

		List<?> result = query.getResultList();
		List<StampOrderVo> stampOrders = Lists.newArrayList();
		for (Object row : result) {
			Object[] cells = (Object[]) row;
			StampOrderVo vo = new StampOrderVo();
			vo.setOrderId(((Number) cells[0]).longValue());
			vo.setCreateTime((Timestamp) cells[1]);
			vo.setFirstPaymentTime((Timestamp) cells[2]);
			vo.setLastPaymentTime((Timestamp) cells[3]);
			vo.setOrderStatus((String) cells[4]);
			vo.setPaymentStatus((String) cells[5]);
			vo.setPayType((String) cells[6]);
			vo.setFullName((String) cells[7]);
			vo.setMobile((String) cells[8]);
			stampOrders.add(vo);
		}
		return stampOrders;
	}
    
    @Override
    @Transactional("oracleTransactionManager")
    public OrdStampOrderItemEntity getStampOrderItemById(long orderItemId) {
    	return stampOrderItemRepository.findOne(orderItemId);
    }

	@Override
	@Transactional("oracleTransactionManager")
	public Long countStampOrder(String stampId, String orderId,
			String contactName, String contactMobile) {
		StringBuilder sql = new StringBuilder();
		sql.append("select count(o.order_id) ").append(" from ord_order o ")
				.append(" join ord_person p on o.order_id=p.object_id ")
				.append(" left join ord_order_item oi on o.order_id=oi.order_id ")
				.append(" join ord_stamp_order_item soi on oi.order_item_id=soi.order_item_id ")
				.append(" where o.order_subtype='STAMP' and p.person_type='CONTACT' ");

		if (StringUtils.isNotBlank(stampId)) {
			sql.append(" and soi.stamp_definition_id='").append(stampId).append("'");
		}
		if (StringUtils.isNotBlank(orderId)) {
			sql.append(" and o.order_id=").append(orderId);
		}
		if (StringUtils.isNotBlank(contactName)) {
			sql.append(" and p.full_name like '").append(contactName).append("%'");
		}
		if (StringUtils.isNotBlank(contactMobile)) {
			sql.append(" and p.mobile like '").append(contactMobile).append("%'");
		}

		Query query = em.createNativeQuery(sql.toString());

		return ((Number) query.getSingleResult()).longValue();
	}

    @Override
    @Transactional("oracleTransactionManager")
    public Long countOrderStampOrderItemByStampDefinitionId(String stampDefinitionId) {
       Long resutl = this.stampOrderItemRepository.countByStampDefinitionId(stampDefinitionId);
        return resutl;
    }


    @Override
    @Transactional("oracleTransactionManager")
    public OrdStampOrderEntity getStampOrderByOrderId(long orderId) {
        return stampOrderRepository.findOne(orderId);
    }

    /**
     * 券兑换下单
     */
    @Override
    public SaveOrderResponse saveProductOrder(StampBuyInfo request) {
    	LOGGER.info("create product order start...");
    	LOGGER.info("received product order: {}", request);
    	LOGGER.info("received product order: {}",HhsUtils.objectToJsonString(request));
    	if(request.getHotelBuyInfo() != null) {
    		return saveHotelOrder(request);
    	}
    	if(CollectionUtils.isEmpty(request.getStampUsages())) {
    		throw new RuntimeException("no stmaps been used!");
    	}
    	ProdProductEntity product = productService.findById(request.getProductId());
    	if(product == null) {
    		throw new RuntimeException("can not find product, productId:" + request.getProductId());
    	}
    	List<String> unUsedStampIds = extractStampIds(request.getStampUsages());
    	
    	validateRedeemDate(unUsedStampIds);//验证兑换时间
    	
    	Map<String, List<StampEntity>> stampEntityMap = stampService.getUnusedStampCodeByStampIdAndCustomerId(unUsedStampIds, String.valueOf(request.getUserNo()));
    	if(MapUtils.isEmpty(stampEntityMap)) {
    		throw new RuntimeException("no unused stamp code found, stampIds: " + unUsedStampIds + ", userNo: " + request.getUserNo());
    	}
    	Map<String, List<StampEntity>> occupiedEntityMap = new HashMap<String, List<StampEntity>>();
    	for(StampUsage usage : request.getStampUsages()) {
    		List<StampEntity> stampEntitys = stampEntityMap.get(usage.getStampId());
    		if(CollectionUtils.isEmpty(stampEntitys)) {
    			throw new RuntimeException("can not find stamp entity based on stampId: " + usage.getStampId());
    		}
    		if(stampEntitys.size() < usage.getAmount()) {
    			throw new RuntimeException("No enough stamps for stampId " + usage.getStampId() + ". Expect: " + usage.getAmount() + ", but actual: " + stampEntitys.size());
    		}
    		occupiedEntityMap.put(usage.getStampId(), stampEntitys.subList(0, usage.getAmount()));
    	}
    	
    	BuyInfo buyInfo = buildBuyInfo(request, product);
    	
    	checkPresaleTimePrice(buyInfo);//检查预售时间价格表
    	
    	return createOrder(buyInfo, occupiedEntityMap, request, product);
    }
    
	private BuyInfo buildBuyInfo(StampBuyInfo request, ProdProductEntity product) {
		return buildBuyInfo(request, product, 0, null);
	}

	private SaveOrderResponse createOrder(BuyInfo buyInfo, Map<String, List<StampEntity>> occupiedEntityMap, StampBuyInfo request, ProdProductEntity product) {
    	
		Long actualAmount = 0L;
    	for(Entry<String, List<StampEntity>> entry : occupiedEntityMap.entrySet()) {
    		for(StampEntity stamp : entry.getValue())  {
    			actualAmount += stamp.getPrice();
    		}
    	}
    	LOGGER.info("actualAmount is : {}", actualAmount);
    	buyInfo.setActualAmount(actualAmount);
		
    	checkTimePrice(buyInfo, request, product);//检查时间价格表,并设置房差 应付金额
    	
    	ResultHandleT<OrdOrder> orderResult = orderService.createOrder(buyInfo, String.valueOf(buyInfo.getUserId()));
    	OrdOrder returnOrder = null;
		if(orderResult == null || orderResult.isFail() || (returnOrder = orderResult.getReturnContent()) == null) {
			if(orderResult != null && orderResult.getMsg() != null) {
				LOGGER.error(orderResult.getMsg());
			}
			throw new RuntimeException("Create order failed !");
		}
		
		List<OrdOrderItem> orderItems = returnOrder.getOrderItemList();
		if(CollectionUtils.isEmpty(orderItems)) {
			throw new RuntimeException("can not find orderitems, orderId: " + returnOrder.getOrderId());
		}
		
		long orderItemId = 0L;
		int stampItemCount = 0;
		for(OrdOrderItem item : orderItems) {
			if(OrderSubType.STAMP_PROD.name().equals(item.getOrderSubType())) {
				orderItemId = orderItems.get(0).getOrderItemId();
				stampItemCount ++;
			}
		}
		
		//保存券码
		List<StampCodePrice> listStamps=new ArrayList<StampCodePrice>();
		String objectId = String.valueOf(returnOrder.getOrderId());
		String remark = StampStatus.UNUSE + "->" + StampStatus.USED;
		List<StampOperationLogEntity> logList = Lists.newArrayList();
		for(Entry<String, List<StampEntity>> entry : occupiedEntityMap.entrySet()) {
			List<StampEntity> stamps = stampService.getStampCodeByIds(extractStampEntityIds(entry.getValue()));
			for(StampEntity entity : stamps) {
				if(!StampStatus.UNUSE.name().equals(entity.getStampStatus())) {
					throw new RuntimeException("Invalid stamp status found, id: " + entity.getId() + ", status: " + entity.getStampStatus());
				}
				entity.setUseOrderId(String.valueOf(returnOrder.getOrderId()));
				entity.setUseOrderCategoryId(String.valueOf(returnOrder.getCategoryId()));
				entity.setStampStatus(StampStatus.USED.name());
				if(stampItemCount == 1 && orderItemId > 0)
					entity.setUseOrderItemId(String.valueOf(orderItemId));
				
				StampOperationLogEntity log = new StampOperationLogEntity(OperateType.USE.name(), objectId,
						entity.getSerialNumber(), remark);
				logList.add(log);
			}
			List<StampEntity> updatedStamps = stampService.updateStampsForOrder(stamps);
			listStamps.addAll(extractStampMapIds(updatedStamps));
		}
		
		// add operation log
		operationLogService.addLogList(logList);
		SaveOrderResponse resp = new SaveOrderResponse();
		resp.setList(listStamps);
		resp.setOrderId(String.valueOf(returnOrder.getOrderId()));
    	return resp;
    }
	
	private void validateRedeemDate(List<String> stampIds) {
		List<StampDefinitionEntity> stampDefinitions = stampService.getStampByIds(stampIds);
    	if(CollectionUtils.isEmpty(stampDefinitions)) {
    		throw new RuntimeException("can not find stamps based on ids: " + stampIds);
    	}
    	
    	Date today = StampUtils.getStartDate(new Date());
    	for(StampDefinitionEntity definition : stampDefinitions) {
    		if(definition.getStampRedeemableStartDate().getTime() > today.getTime()) 
    			throw new RuntimeException("the stamp redeem has not started. start date: " + definition.getStampRedeemableStartDate());
    		if(definition.getStampRedeemableEndDate().getTime() < today.getTime()) 
    			throw new RuntimeException("the stamp redeem has got expired. end date: " + definition.getStampRedeemableEndDate());
    	}
	}
    
    private SaveOrderResponse saveHotelOrder(StampBuyInfo request) {
    	String stampId = request.getHotelBuyInfo().getStampId();
    	int room = request.getHotelBuyInfo().getRoom();
    	
    	validateRedeemDate(Arrays.asList(stampId));//验证兑换时间
    	
    	HotelValidationRequest validateReq = new HotelValidationRequest();
    	BeanUtils.copyProperties(request.getHotelBuyInfo(), validateReq);
    	HotelValidationResponse validateResp = productService.validHotel(request.getProductId(), validateReq);
    	if(!"valid".equals(validateResp.getStatus())) {
    		throw new RuntimeException("hotel validation failed.");
    	}
    	
    	int days = getDateDiff(request.getHotelBuyInfo().getStartDate(), request.getHotelBuyInfo().getEndDate());
    	int totalUsage = days * room;
    	List<StampEntity> stamps = stampService.getUnusedStampCodeByStampIdAndCustomerId(stampId, String.valueOf(request.getUserNo()));
    	if(CollectionUtils.isEmpty(stamps)) {
    		throw new RuntimeException("no unused stamp code found, stampId: " + stampId + ", userNo: " + request.getUserNo());
    	}
    	if(totalUsage > stamps.size()) {
    		throw new RuntimeException("No enough stamps for stampId " + stampId + ". Expect: " + totalUsage + ", but actual: " + stamps.size());
    	}
    	ProdProductEntity product = productService.findById(request.getProductId());
    	if(product == null) {
    		throw new RuntimeException("can not find product, productId:" + request.getProductId());
    	}
    	Map<String, List<StampEntity>> occupiedEntityMap = new HashMap<String, List<StampEntity>>();
    	occupiedEntityMap.put(stampId, stamps.subList(0, totalUsage));
    	
    	HotelAdditation ht = new HotelAdditation();
		ht.setArrivalTime(request.getHotelBuyInfo().getArrivalTime());
		ht.setLeaveTime(request.getHotelBuyInfo().getEndDate());
		ht.setStayDays(String.valueOf(days));
    	
		if(StringUtils.isBlank(request.getVisitTime())) 
			request.setVisitTime(request.getHotelBuyInfo().getStartDate());
    	BuyInfo buyInfo = buildBuyInfo(request, product, room, ht);
    	
    	return createOrder(buyInfo, occupiedEntityMap, request, product);
    }
    
    private void checkPresaleTimePrice(BuyInfo buyInfo) {
    	SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
    	
    	Map<String, Object> map = new HashMap<String, Object>();
    	if(buyInfo.getItemList() != null) {
    		for(Item item : buyInfo.getItemList()) {
    			if(OrderSubType.STAMP_PROD.name().equals(item.getOrderSubType())) {
    				map.put("goodsId", item.getGoodsId());
    				try {
						map.put("applyDate", format.parse(item.getVisitTime()));
					} catch (ParseException e) {
						throw new RuntimeException("invalid format of visit time: " + item.getVisitTime());
					}
    				List<PresaleStampTimePrice> timeprices = suppGoodsTimePriceClientService.selectPresaleStampTimePrices(map);
    				if(CollectionUtils.isEmpty(timeprices))
    					throw new RuntimeException("can not find presale time price: " + map);
    			}
    		}
    	}
    	
    	if(buyInfo.getProductList() != null) {
    		for(Product prod : buyInfo.getProductList()) {
    			for(Item item : prod.getItemList()) {
        			if(OrderSubType.STAMP_PROD.name().equals(item.getOrderSubType())) {
        				map.put("goodsId", item.getGoodsId());
        				try {
							map.put("applyDate", format.parse(item.getVisitTime()));
						} catch (ParseException e) {
							throw new RuntimeException("invalid format of visit time: " + item.getVisitTime());
						}
        				List<PresaleStampTimePrice> timeprices = suppGoodsTimePriceClientService.selectPresaleStampTimePrices(map);
        				if(CollectionUtils.isEmpty(timeprices))
        					throw new RuntimeException("can not find presale time price: " + map);
        			}
        		}
    		}
    	}
    }
    
    private int getDateDiff(String start, String end) {
    	Date startDate = null, endDate = null;
    	SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd"); 
    	try {
			startDate = format.parse(start);
		} catch (ParseException e) {
			throw new RuntimeException("invalid date format of startDate " + start);
		}
    	try {
    		endDate = format.parse(end);
		} catch (ParseException e) {
			throw new RuntimeException("invalid date format of endDate " + end);
		}
    	return (int)DateUtil.diffDay(startDate, endDate);
    }
    
    private void checkTimePrice(BuyInfo buyInfo, StampBuyInfo request, ProdProductEntity product) {
    	SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
    	
    	Long oughtAmount = 0L;
    	Long gapPrice = 0L;
    	SuppGoodsBaseTimePrice timePrice = null;
    	
    	List<Item> items = new ArrayList<BuyInfo.Item>();
    	if(buyInfo.getItemList() != null)
    		items.addAll(buyInfo.getItemList());
    	if(buyInfo.getProductList() != null) {
    		for(Product prod : buyInfo.getProductList()) {
    			if(prod.getItemList() != null)
    				items.addAll(prod.getItemList());
    		}
    	}
    	
    	if(CollectionUtils.isEmpty(items)) 
    		throw new RuntimeException("can not find order items");
    	
    	for(Item item : items) {
    		Date visitTime;
			try {
				visitTime = format.parse(item.getVisitTime());
			} catch (ParseException e) {
				throw new RuntimeException("invalid format of visit time: " + buyInfo.getVisitTime());
			}
    		ResultHandleT<SuppGoodsBaseTimePrice> timePriceResult = suppGoodsTimePriceClientService.getBaseTimePrice(item.getGoodsId(), visitTime);
    		if(timePriceResult.isFail() || (timePrice = timePriceResult.getReturnContent()) == null) 
    			throw new RuntimeException("can not find time price, goodsId: " + item.getGoodsId() + " visitTime: " + visitTime);
    		if("N".equals(timePrice.getOnsaleFlag())) {
    			throw new RuntimeException("the goods is not onsale, goodsId: " + item.getGoodsId() + " visitTime: " + visitTime);
    		}
    		if(timePrice.getStock() != null && timePrice.getStock() != -1 && timePrice.getStock() < item.getQuantity() && "N".equals(timePrice.getOversellFlag())) {
    			throw new RuntimeException("the inventory is not enough, goodsId: " + item.getGoodsId() + ", visitTime: " + visitTime + ", Expect: " + item.getQuantity() + ", but: " + timePrice.getStock());
    		}
    		if(request.getGapPriceAmount() > 0 && timePrice.getGoodsGapPrice() != null && timePrice.getGoodsGapPrice() > 0) {
    			if(!PACKAGETYPE.LVMAMA.name().equals(product.getPackageType())) 
    				item.setRouteRelation(ItemRelation.ADDITION);
    			item.setGapQuantity(request.getGapPriceAmount());
    			LOGGER.info("Get the change hotel gap price: {}, goodsId: {}", timePrice.getGoodsGapPrice(), item.getGoodsId());
    			gapPrice += timePrice.getGoodsGapPrice() * request.getGapPriceAmount(); 
    		}
    		if(item.getOrderSubType() == null) { //保险， 附加, 可换酒店
    			if(ItemRelation.PACK.equals(item.getRouteRelation())) { //可换酒店
    				oughtAmount += timePrice.getGoodsAudltPrice() * item.getAdultQuantity() + timePrice.getGoodsChildPrice() * item.getChildQuantity();
					LOGGER.info(
							"Get the change hotel price, adultPrice: {}, adultQuantity:{}, childPrice: {}, childQuantity: {}, goodsId: {}",
							timePrice.getGoodsAudltPrice(), item.getAdultQuantity(), timePrice.getGoodsChildPrice(), item.getChildQuantity(), item.getGoodsId());
    			} else {
    				oughtAmount += timePrice.getGoodsAudltPrice() * item.getQuantity();
    				LOGGER.info("Get the insurance or addtion price: {}, goodsId: {}", timePrice.getGoodsAudltPrice(), item.getGoodsId());
    			}
    		}
    	}
    	
    	oughtAmount += gapPrice;
    	
    	if(oughtAmount < 0) {
    		LOGGER.info("the oughtAmount {} is less than 0, set it to 0, productId: {}", oughtAmount, request.getProductId());
    		oughtAmount = 0L;
    	}
    	
    	oughtAmount += buyInfo.getActualAmount();
    	
    	LOGGER.info("oughtAmount is : {}", oughtAmount);
    	buyInfo.setSpreadQuantity(request.getGapPriceAmount());
    	buyInfo.setOughtAmount(oughtAmount);
    	buyInfo.setOrderTotalPrice(oughtAmount);
    }
    
    private List<String> extractStampEntityIds(List<StampEntity> entitys) {
    	if(entitys == null) 
    		return null;
    	List<String> list = new ArrayList<String>();
    	for(StampEntity entity : entitys) {
    		list.add(entity.getId());
    	}
    	return list;
    }
    private List<StampCodePrice> extractStampMapIds(List<StampEntity> entitys){
    	if(entitys == null) 
    		return null;
    	List<StampCodePrice> list = new ArrayList<StampCodePrice>();
    	for(StampEntity entity : entitys) {
    		StampCodePrice code=new StampCodePrice();
    		code.setCode(entity.getSerialNumber());
    		code.setPrice(Long.valueOf(entity.getPrice()));
    		LOGGER.info(entity.getSerialNumber()+"-----------------------+++++++++++++++++++++++++"+code.getPrice());
    		list.add(code);
    	}
    	return list;
    	
    }
    private List<String> extractStampIds(List<StampUsage> usages) {
    	if(usages == null)
    		return null;
    	List<String> stampIds = new ArrayList<String>();
    	for(StampUsage usage : usages) {
    		stampIds.add(usage.getStampId());
    	}
    	return stampIds;
    }
    
    private BuyInfo buildBuyInfo(StampBuyInfo request, ProdProductEntity product, int hotelAmount, HotelAdditation hotelAddition) {
    	BuyInfo buyInfo = new BuyInfo();
//    	buyInfo.setAdditionalTravel(additionalTravel);
//    	buyInfo.setAdultQuantity(audltAmount);
//    	buyInfo.setAnonymityBookFlag(anonymityBookFlag);
//    	buyInfo.setBonus(bonus);
//    	buyInfo.setBonusAmountHidden(bonusAmountHidden);
//    	buyInfo.setBonusYuan(bonusYuan);
    	buyInfo.setBooker(buildBooker(request.getUserId()));							//下单人信息
    	buyInfo.setOrderSubType(OrderSubType.STAMP_PROD.name());
//    	buyInfo.setBuCode(buCode);
//    	buyInfo.setCashAmountHidden(cashAmountHidden);
    	buyInfo.setCategoryId(product.getCategoryId().longValue());
//    	buyInfo.setChildQuantity(childQuantity);
//    	buyInfo.setCommissionAmountHidden(commissionAmountHidden);
//    	buyInfo.setCommissionProportion(commissionProportion);
    	buyInfo.setContact(buildContact(request.getContact()));
//    	buyInfo.setCouponList(couponList);
//    	buyInfo.setDiscountAmount(discountAmount);
//    	buyInfo.setDisneyOrderInfo(disneyOrderInfo);
//    	buyInfo.setDistributionChannel(distributionChannel);
    	//上车地点
    	HashMap<String, String> map = (HashMap<String, String>) request.getAdditionMap();
    	if(map !=null && !map.isEmpty()){
    	    Iterator<Map.Entry<String, String>> it = map.entrySet().iterator();
    	    while (it.hasNext()) {
    	     Map.Entry<String, String> entry = it.next();
    	     if(entry.getKey().equals(BuyInfoAddition.frontBusStop+"")){
    	         buyInfo.putAddition(BuyInfoAddition.frontBusStop, entry.getValue());
    	     }else if(entry.getKey().equals(BuyInfoAddition.backBusStop+"")){
    	         buyInfo.putAddition(BuyInfoAddition.backBusStop, entry.getValue());
    	     }
    	    }
    	}
    	
    	
     	if("Y".equalsIgnoreCase(request.getTravellerDelayFlag()))
     		buyInfo.setTravellerDelayFlag("Y");
     	
    	if(request.getDistributionId() == null || request.getDistributionId() == 0) {
    		buyInfo.setDistributionId(2L);
    	} else {
    		buyInfo.setDistributionId(request.getDistributionId());
    	}
    	ResultHandleT<Distributor> distributorResult = distributorClientRemote.findDistributorById(request.getDistributionId());
    	if(distributorResult.isSuccess() && distributorResult.getReturnContent() != null) {
    		buyInfo.setDistributorName(distributorResult.getReturnContent().getDistributorName());
    	}
    	buyInfo.setDistributionChannel(request.getDistributorChannel());
    	buyInfo.setDistributorCode(request.getDistributorCode());
//    	buyInfo.setEmergencyPerson(emergencyPerson);
//    	buyInfo.setExpressage(expressage);
//    	buyInfo.setFaxMemo(faxMemo);
//    	buyInfo.setGiftCardList(giftCardList);
//    	buyInfo.setGuarantee(guarantee);
//    	buyInfo.setGuaranteeRate(guaranteeRate);
//    	buyInfo.setIntentionOrderFlag(intentionOrderFlag);
    	buyInfo.setInvoiceInfo(getInvoiceInfo(request.getInvoice()));
    	buyInfo.setIp(request.getIp());
//    	buyInfo.setIsTestOrder(isTestOrder);
    	if(request.getHotelBuyInfo() == null) {
    		getLineItems(request, buyInfo, product);
    	} else {
    		buyInfo.setItemList(getHotelItems(request, hotelAmount, hotelAddition));
    	}
//    	buyInfo.setItemMap(itemMap);
//    	buyInfo.setLineRoute(lineRoute);
    	buyInfo.setLineRouteId(request.getLineRouteId());
//    	buyInfo.setMaxAdultQuantity(maxAdultQuantity);
//    	buyInfo.setMaxChildQuantity(maxChildQuantity);
//    	buyInfo.setMobileEquipmentNo(mobileEquipmentNo);
//    	buyInfo.setNeedGuarantee(needGuarantee);
    	if(request.getInvoice() != null) {
    		buyInfo.setNeedInvoice(request.getInvoice().isNeedFlag() ? NEED_INVOICE_STATUS.BILL.name() : null);
    	}
//    	buyInfo.setOrderRepeatedMd5(orderRepeatedMd5);
    	
//    	calOrderPrice(request, buyInfo, occupiedEntityMap); //计算应付实付金额
    	
    	buyInfo.setPersonRelationMap(getPersonRelationMap(request));
//    	buyInfo.setProdLineRoute(prodLineRoute);
    	buyInfo.setProdProduct(getProduct(request.getProductId()));
    	buyInfo.setProductId(request.getProductId());
//    	buyInfo.setProductList(productList);
//    	buyInfo.setProductMap(productMap);
//    	buyInfo.setPromotionIdList(promotionIdList);
//    	buyInfo.setPromotionMap(promotionMap);
//    	buyInfo.setQuantity(quantity);
    	buyInfo.setRemark(request.getRemark());
    	buyInfo.setSameVisitTime(request.getVisitTime());
//    	buyInfo.setSendContractFlag(sendContractFlag);
//    	buyInfo.setSmsLvmamaFlag(smsLvmamaFlag);
//    	buyInfo.setSpreadQuantity(spreadQuantity);
    	buyInfo.setStartDistrictId(request.getStartDistrictId());
//    	buyInfo.setStoreCardList(storeCardList);
//    	buyInfo.setSubCategoryId(subCategoryId);
//    	buyInfo.setSubDistributorId(subDistributorId);
//    	buyInfo.setSubmitOrderFlag(submitOrderFlag);
//    	buyInfo.setSuppGoodsId(suppGoodsId);
//    	buyInfo.setTarget(target);
    	buyInfo.setTravellers(getTraveller(request.getTravellers()));
    	buyInfo.setOrderTravellerConfirm(getTravellerDelayInfo(request.getTravellerDelay()));
//    	buyInfo.setTravPersonQuantity(travPersonQuantity);
//    	buyInfo.setUserCouponVoList(userCouponVoList);
    	buyInfo.setUserId(request.getUserId());
    	buyInfo.setUserNo(request.getUserNo());
    	buyInfo.setVisitTime(request.getVisitTime());
//    	buyInfo.setWaitPayment(waitPayment);
//    	buyInfo.setYouhui(youhui);
    	return buyInfo;
    }
    
	private Map<String, PersonRelation> getPersonRelationMap(StampBuyInfo request) {
		Map<String, PersonRelation> map = new HashMap<String, BuyInfo.PersonRelation>();
		if(CollectionUtils.isNotEmpty(request.getPersonRelations())) {
			String key = null; 
			for(com.lvmama.vst.hhs.model.product.PersonRelation pr : request.getPersonRelations()) {
				if(pr.getGoodsId() == null || pr.getGoodsId() == 0) 
					continue;
				if(CollectionUtils.isEmpty(pr.getSeqs()))
					continue;
				
				key = "GOODS_" + pr.getGoodsId();
				if(!map.containsKey(key)) {
					PersonRelation personRelation = new PersonRelation();
					personRelation.setItemPersonRelationList(new ArrayList<BuyInfo.ItemPersonRelation>());
					map.put(key, personRelation);
				}
				for(Integer seq : pr.getSeqs()) {
					ItemPersonRelation itemPersonRelation = new ItemPersonRelation();
					itemPersonRelation.setSeq(seq);
					map.get(key).getItemPersonRelationList().add(itemPersonRelation);
				}
			}
		}
		return map;
	}
    
    private OrdOrderTravellerConfirm getTravellerDelayInfo(TravellerDelayInfo info) {
    	OrdOrderTravellerConfirm confirm = null;
    	if(info != null) {
    		confirm = new OrdOrderTravellerConfirm();
    		confirm.setContainBaby(info.getContainBaby());
    		confirm.setContainForeign(info.getContainForeign());
    		confirm.setContainOldMan(info.getContainOldMan());
    		confirm.setContainPregnantWomen(info.getContainPregnantWomen());
    		confirm.setCreateTime(new Date());
    	}
    	return confirm;
    }
    
	private List<Person> getTraveller(List<StampContact> travellers) {
		List<Person> persons = new ArrayList<Person>();
		if(travellers != null) {
			for(StampContact contact : travellers) {
				persons.add(buildContact(contact));
			}
		}
		return persons;
	}
	
	private Person buildContact(StampContact contact) {
		Person person = new Person();
		person.setLastName(contact.getLastName());
		person.setFirstName(contact.getFirstName());
		person.setFullName(contact.getFullName());
		person.setPhone(contact.getPhone());
		person.setMobile(contact.getPhone());
		person.setGender(contact.getGender());
		person.setIdType(contact.getIdType());
		person.setIdNo(contact.getIdNo());
		person.setEmail(contact.getEmail());
		person.setPeopleType(contact.getPeopleType());
		return person;
	}
	
	private Person buildBooker(String userNo) {
		UserUser user = userUserProxyAdapter.getUserUserByUserNo(userNo);
		if(user == null) {
			throw new RuntimeException("can not find user by userNo(UUID): " + userNo);
		}
		Person person = new Person();
		person.setFullName(user.getUserName());
		person.setMobile(user.getMobileNumber());
		return person;
	}
    
    private InvoiceInfo getInvoiceInfo(InvoiceInfoVO vo) {
    	if(vo==null)
    		return null;
    	InvoiceInfo info = new InvoiceInfo();
    	BeanUtils.copyProperties(vo, info);
    	return info;
    }
    
	private List<Item> getHotelItems(StampBuyInfo request, int totalUsage, HotelAdditation hd) {
		//单酒店
		String stampId = request.getHotelBuyInfo().getStampId();
		List<PresaleStampDefinitionGoodsBindingEntity> goodsBindings = stampGoodsService.getStampGoodsBindingByStampId(stampId);
		if(CollectionUtils.isEmpty(goodsBindings)) {
			throw new RuntimeException("can not find binding goods based on stampId: " + stampId);
		}
		List<Item> list = new ArrayList<BuyInfo.Item>();
		for(PresaleStampDefinitionGoodsBindingEntity goodsBinding : goodsBindings) {
			Item item = buildItem(NumberUtils.toLong(goodsBinding.getGoodsId()), totalUsage, request.getVisitTime(), HhsConstants.AUDLT, OrderSubType.STAMP_PROD.name());
			item.setHotelAdditation(hd);
			item.setMainItem("true");
			list.add(item);
		}
		return list;
	}
    
    private void getLineItems(StampBuyInfo request, BuyInfo buyInfo, ProdProductEntity product) {
    	String visitTime = request.getVisitTime();
    	Date visitDate = DateUtil.toSimpleDate(visitTime);
    	
    	List<Item> list = new ArrayList<BuyInfo.Item>();
    	List<Long> hotelGoodsId = new ArrayList<Long>();
    	Set<Long> subProductIds = new HashSet<Long>();
    	Set<Long> bindedChangeProductIds = new HashSet<Long>(); //绑定过change的productId
    	Map<Long, String> productVisitTime = new HashMap<Long, String>();
    	Map<String, Item> itemMap = new HashMap<String, BuyInfo.Item>();
    	int adultQuantity = 0, childQuantity = 0, quantity = 0; 
    	ProductSaleType saleType = null;
    	for(StampUsage usage : request.getStampUsages()) {
    		if(usage.getAmount() <= 0) {
    			LOGGER.info("ignore the stamp {} which amount is {}", usage.getAmount(), usage.getStampId());
    			continue; 
    		}
    		
    		if(saleType == null)
    			saleType = productService.getProductSaleTypeByStampId(usage.getStampId());
    		
    		StampGoodsBindingBo goodsBo = stampGoodsService.getStampGoodsByStampId(usage.getStampId());
    		
			if(SaleType.COPIES.name().equals(saleType.getSaleType())) { //按份卖
				adultQuantity += saleType.getAdultQuantity() * usage.getAmount();
				childQuantity += saleType.getChildQuantity() * usage.getAmount();
			} else {
				if(HhsConstants.CHILD.equals(goodsBo.getType())) {
    				childQuantity += usage.getAmount();
    			} else {
    				adultQuantity += usage.getAmount();
    			}
			}
			quantity += usage.getAmount();
    		
    		if(goodsBo.getGoods() != null) {
    			String goodsId = goodsBo.getGoods().getGoodsId();
    			if(itemMap.containsKey(goodsId)) {
    				Item item = itemMap.get(goodsId);
    				if(HhsConstants.CHILD.equals(goodsBo.getType())) {
    					item.setChildQuantity(item.getChildQuantity() + usage.getAmount());
    				} else {
    					item.setAdultQuantity(item.getAdultQuantity() + usage.getAmount());
    				}
    				item.setQuantity(item.getQuantity() + usage.getAmount());
    			} else {
    				Item item = buildItem(NumberUtils.toLong(goodsId), usage.getAmount(), visitTime, goodsBo.getType(), OrderSubType.STAMP_PROD.name());
    				item.setMainItem("true");
    				item.setRouteRelation(ItemRelation.MAIN);
    				itemMap.put(goodsId, item);
    			}
    		} 
    		if(CollectionUtils.isNotEmpty(goodsBo.getPackages())){
    			for(StampPackageGoodsBo packageGood : goodsBo.getPackages()) {
    				for(StampGroupGoodsBo groupGood : packageGood.getGroups()) {
    					String goodsId = groupGood.getGoods().getGoodsId();
    					if(itemMap.containsKey(goodsId)) {
    						Item item = itemMap.get(goodsId);
    						//重新计算数量 
    						calcQuantity(saleType, goodsId, packageGood.getGroupType(), item, usage.getAmount(), false);
    					} else {
    						SuppGoodEntity goodEntity = suppGoodsRepository.findOne(NumberUtils.toLong(goodsId));
							if(goodEntity == null) 
								throw new RuntimeException("Data issue, can not find supp goods based on goodsId: " + goodsId);
							subProductIds.add(goodEntity.getProductId().longValue());
							
							if(HhsConstants.CHANGE.equals(packageGood.getGroupType())) 
								bindedChangeProductIds.add(goodEntity.getProductId().longValue());
							else 
								productVisitTime.put(goodEntity.getProductId().longValue(), request.getVisitTime());
    						
    						Item item = buildItem(NumberUtils.toLong(goodsId), usage.getAmount(), visitTime, null, OrderSubType.STAMP_PROD.name());
    						item.setProductCategoryId(product.getCategoryId().longValue());
    						item.setRouteRelation(ItemRelation.PACK);
    						ProdPackageDetailEntity detail = productPackageService.getProductPackageDetail(groupGood.getGroupId(), NumberUtils.toLong(goodsId));
    						if(detail == null) {
    							throw new RuntimeException("Data issue, can not find product package detail, groupId: " + groupGood.getGroupId() + ", goodsId: " + goodsId);
    						}
    						item.setDetailId(detail.getDetailId());
    						
    						//计算数量 
    						calcQuantity(saleType, goodsId, packageGood.getGroupType(), item, usage.getAmount(), true);
    						
    						//设置子订单visitTime
    						if(!HhsConstants.CHANGE.equals(packageGood.getGroupType())) {
    							List<String> visitTimes = productPackageService.getGroupVisitTime(groupGood.getGroupId(), packageGood.getGroupType(), visitDate);
    							if(CollectionUtils.isNotEmpty(visitTimes)) {
    								item.setVisitTime(visitTimes.get(0));
    							}
    							
    							if(CollectionUtils.isNotEmpty(visitTimes)) {
        							productVisitTime.put(goodEntity.getProductId().longValue(), visitTimes.get(0));
        						}
    						}
    						
    						//用户选择的visitTime
    						if(request.getCustomTravelDate() != null && request.getCustomTravelDate().containsKey(item.getGoodsId())) {
								item.setVisitTime(request.getCustomTravelDate().get(item.getGoodsId()));
							}
    						
    						//设置酒店附加信息
    						if(HhsConstants.HOTEL.equals(packageGood.getGroupType())) {//酒店
    							HotelAdditation hotelAdditation = new HotelAdditation();
    							hotelAdditation.setArrivalTime(productService.getHotelEarliestArriveTime(goodEntity.getProductId().longValue()));
    							ProdPackageGroupHotelEntity groupHotel = productPackageService.getPackageGroupHotel(groupGood.getGroupId());
    							if(groupHotel == null) 
    								throw new RuntimeException("Data issue, can not find package group hotel based on groupId: " + groupGood.getGroupId());
    							if(groupHotel.getStayDays() == null) 
    								throw new RuntimeException("Data issue, the stay days should not be null, groupId: " + groupGood.getGroupId());
    							String stayDays [] = groupHotel.getStayDays().split(",");
    							Date leaveDate = DateUtil.addDays(DateUtil.toSimpleDate(visitTime), NumberUtils.toInt(stayDays[stayDays.length - 1]));
    							hotelAdditation.setLeaveTime(DateUtil.formatSimpleDate(leaveDate));
    							item.setHotelAdditation(hotelAdditation);
    							
    							hotelGoodsId.add(item.getGoodsId());
    						} 
    						itemMap.put(goodsId, item);
    					}
    				}
    			}
    		}
    		
    	}
    	list.addAll(itemMap.values());
    	
    	//酒店需要根据最大入住人数计算房间数
    	if(CollectionUtils.isNotEmpty(hotelGoodsId)) {
    		for(Item item : list) {
    			if(hotelGoodsId.contains(item.getGoodsId())) {
    				ProdProductBranchEntity branchEntity = prodProductBranchRepository.getByGoodsId(item.getGoodsId());
    				if(branchEntity == null) 
    					throw new RuntimeException("Data issue, can not find product branch based on goodsId: " + item.getGoodsId());
    				if(branchEntity.getMaxVisitor() != null) {
    					int maxVisitor = branchEntity.getMaxVisitor().intValue();
    					int room = 0;
    					if(SaleType.COPIES.name().equals(saleType.getSaleType())) {
    						room = (int)Math.ceil((double)item.getAdultQuantity() / maxVisitor);
    					} else {
    						room = item.getAdultQuantity();
    					}
    					item.setQuantity(room);
    				}
    			}
    		}
    	}
    	
    	//券未绑定的可换酒店
    	if(CollectionUtils.isNotEmpty(request.getHotelAdditions())) {
    		Map<Long, List<Long>> productGroups = new HashMap<Long, List<Long>>();
    		if(PACKAGETYPE.LVMAMA.name().equals(product.getPackageType())) {
    			subProductIds.removeAll(bindedChangeProductIds);
    			List<ProdPackageGroupEntity> groups = productPackageService.getPackageGroupChangeByProduct(Arrays.asList(subProductIds.toArray(new Long[subProductIds.size()])));
    			if(CollectionUtils.isNotEmpty(groups)) {
    				for(ProdPackageGroupEntity group : groups) {
    					if(!productGroups.containsKey(group.getProductId().longValue())) 
    						productGroups.put(group.getProductId().longValue(), new ArrayList<Long>());
    					productGroups.get(group.getProductId().longValue()).add(group.getGroupId());
    				}
    			}
    		}
    		
    		for(GoodsUsage usage : request.getHotelAdditions()) {
    			Item item = buildItem(usage.getGoodsId(), usage.getAmount(), visitTime, HhsConstants.AUDLT, null);
    			item.setAdultQuantity(adultQuantity);
    			item.setChildQuantity(childQuantity);
    			item.setQuantity(item.getAdultQuantity() + item.getChildQuantity());
    			item.setRouteRelation(ItemRelation.PACK);
    			item.setProductCategoryId(product.getCategoryId().longValue());
    			
    			if(PACKAGETYPE.LVMAMA.name().equals(product.getPackageType())) {
    				SuppGoodEntity suppGoods = productService.getSuppGoodEntity(item.getGoodsId());
    				if(suppGoods == null) 
    					throw new RuntimeException("can not find supp goods based on goodsId: " + item.getGoodsId());
    				List<Long> groupIds = null;
    				if((groupIds = productGroups.get(suppGoods.getProductId().longValue())) != null) {
    					for(Long groupId : groupIds) {
    						ProdPackageDetailEntity detail = productPackageService.getProductPackageDetail(groupId, item.getGoodsId());
    						if(detail != null) {
    							item.setDetailId(detail.getDetailId());
    							break;
    						}
    					}
    				}
    				if(item.getDetailId() == null) {
    					throw new RuntimeException("the detailId of item should not be null, goodsId: " + item.getGoodsId());
    				}
    				item.setVisitTime(productVisitTime.get(suppGoods.getProductId().longValue()));
    			}
    			list.add(item);
    		}
    	}
    	
    	//券绑定的可换酒店
    	if(PACKAGETYPE.LVMAMA.name().equals(product.getPackageType()) && CollectionUtils.isNotEmpty(bindedChangeProductIds)) {
    		for(Item item : list) {
    			SuppGoodEntity suppGoods = productService.getSuppGoodEntity(item.getGoodsId());
				if(suppGoods == null) 
					throw new RuntimeException("can not find supp goods based on goodsId: " + item.getGoodsId());
    			
    			if(bindedChangeProductIds.contains(suppGoods.getProductId().longValue())) {
    				item.setVisitTime(productVisitTime.get(suppGoods.getProductId().longValue()));
    			}
    		}
    	}
    	
    	//保险
    	if(CollectionUtils.isNotEmpty(request.getInsurances())) {
    		for(GoodsUsage usage : request.getInsurances()) {
    			if(usage.getGoodsId() == null || usage.getGoodsId() == 0) 
    				continue;
    			if(usage.getAmount() < 1) 
    				continue;
    			list.add(buildItem(usage.getGoodsId(), usage.getAmount(), visitTime, HhsConstants.AUDLT, null));
    		}
    	}
    	
    	//附加
    	if(CollectionUtils.isNotEmpty(request.getAdditions())) {
    		for(GoodsUsage usage : request.getAdditions()) {
    			if(usage.getGoodsId() == null || usage.getGoodsId() == 0) 
    				continue;
    			if(usage.getAmount() < 1) 
    				continue;
    			Item item = buildItem(usage.getGoodsId(), usage.getAmount(), visitTime, HhsConstants.AUDLT, null);
    			item.setRouteRelation(ItemRelation.ADDITION);
    			
    			if(PACKAGETYPE.LVMAMA.name().equals(product.getPackageType())) {
    				SuppGoodEntity suppGoods = productService.getSuppGoodEntity(item.getGoodsId());
    				if(suppGoods == null) 
    					throw new RuntimeException("can not find supp goods based on goodsId: " + item.getGoodsId());
    				
    				item.setVisitTime(productVisitTime.get(suppGoods.getProductId().longValue()));
    			}
    			list.add(item);
    		}
    	}
    	
    	if(PACKAGETYPE.LVMAMA.name().equals(product.getPackageType())) {
    		List<Item> packItems = new ArrayList<BuyInfo.Item>();
    		List<Item> additionItems = new ArrayList<BuyInfo.Item>();
    		for(Item item : list) {
    			if(ItemRelation.PACK.equals(item.getRouteRelation())) 
    				packItems.add(item);
    			else 
    				additionItems.add(item);
    		}
    		buyInfo.setItemList(additionItems);
    		
    		Product p = new Product();
    		p.setAdultQuantity(adultQuantity);
			p.setChildQuantity(childQuantity);
			p.setItemList(packItems);
			p.setProductId(product.getProductId());
			p.setProductName(product.getProductName());
			if(SaleType.COPIES.name().equals(saleType.getSaleType())) {
				p.setQuantity(quantity);
			}
			p.setVisitTime(request.getVisitTime());
			buyInfo.setProductList(Arrays.asList(p));
    	} else {
    		buyInfo.setItemList(list);
    	}
    }
    
    /**
     * 计算数量 
     * @param saleType
     * @param goodsId
     * @param groupType
     * @param item
     * @param amount 券的份数
     * @param init 是否初始值
     */
    private void calcQuantity(ProductSaleType saleType, String goodsId, String groupType, Item item, int amount, boolean init) {
    	if(SaleType.COPIES.name().equals(saleType.getSaleType())) { //按份卖
			if(HhsConstants.LINE_TICKET.equals(groupType)) {
				SuppGoodEntity goodEntity = suppGoodsRepository.findOne(NumberUtils.toLong(goodsId));
				if(goodEntity == null) 
					throw new RuntimeException("Data issue, can not find supp goods based on goodsId: " + goodsId);
				int adultChild = goodEntity.getAdult().intValue() + goodEntity.getChild().intValue();
				if(adultChild == 1) { //单门票  adult 和 child 有一个为0，一个为1
					item.setAdultQuantity((init ? 0 : item.getAdultQuantity()) + goodEntity.getAdult().intValue() * saleType.getAdultQuantity() * amount);
					item.setChildQuantity((init ? 0 : item.getChildQuantity()) + goodEntity.getChild().intValue() * saleType.getChildQuantity() * amount);
					item.setQuantity(item.getAdultQuantity() + item.getChildQuantity());
				} else { //套票
					item.setAdultQuantity((init ? 0 : item.getAdultQuantity()) + goodEntity.getAdult().intValue() * amount);
					item.setChildQuantity((init ? 0 : item.getChildQuantity()) + goodEntity.getChild().intValue() * amount);
					item.setQuantity((int)(item.getAdultQuantity() / saleType.getAdultQuantity()));
				}
			} else {
				item.setAdultQuantity((init ? 0 : item.getAdultQuantity()) + saleType.getAdultQuantity() * amount);
				item.setChildQuantity((init ? 0 : item.getChildQuantity()) + saleType.getChildQuantity() * amount);
				item.setQuantity((init ? 0 : item.getQuantity()) + amount);
			}
		} else {
			item.setAdultQuantity((init ? 0 : item.getAdultQuantity()) + amount);
			item.setChildQuantity(0);
			item.setQuantity((init ? 0 : item.getQuantity()) + amount);
		}
    }
    
    private Item buildItem(Long goodsId, int quantity, String visitTime, String type, String subType) {
    	Item item = new Item();
//    	item.setAdditionalFlightNoVoList(additionalFlightNoVoList);
//    	item.setAdultAmt();
    	item.setAdultQuantity(HhsConstants.AUDLT.equals(type) || StringUtils.isBlank(type) ? quantity : 0);
//    	item.setBackDate(backDate);
//    	item.setBuCode(buCode);
//    	item.setCheckStockQuantity(checkStockQuantity);
//    	item.setChildAmt(childAmt);
    	item.setChildQuantity(HhsConstants.CHILD.equals(type) ? quantity : 0);
//    	item.setCircusActInfo(circusActInfo);
//    	item.setContent(content);
//    	item.setDisneyItemOrderInfo(disneyItemOrderInfo);
//    	item.setDisplayTime(displayTime);
//    	item.setFlightNoVo(flightNoVo);
//    	item.setGapQuantity(gapQuantity);
    	item.setGoodsId(goodsId);
//    	item.setGoodType(goodType);
//    	item.setHotelcombOptions(hotelcombOptions);
//    	item.setIsDisneyGood(isDisneyGood);
//    	item.setItemPersonRelationList(itemPersonRelationList);
//    	item.setMainItem(mainItem);
    	item.setOrderSubType(subType);
//    	item.setOwnerQuantity(ownerQuantity);
//    	item.setPrice(price);
//    	item.setPriceTypeList(priceTypeList);
//    	item.setProductCategoryId(productCategoryId);
    	item.setQuantity(quantity);
//    	item.setRoomMaxInPerson(roomMaxInPerson);
//    	item.setRouteRelation(routeRelation);
//    	item.setSettlementPrice(settlementPrice);
//    	item.setShareDayLimit(shareDayLimit);
//    	item.setSharedStockList(sharedStockList);
//    	item.setShareTotalStock(shareTotalStock);
//    	item.setTaobaoETicket(taobaoETicket);
//    	item.setToDate(toDate);
//     	item.setTotalAmount(totalPrice);
//    	item.setTotalSettlementPrice(totalSettlementPrice);
    	item.setVisitTime(visitTime);
//    	item.setWifiAdditation(wifiAdditation);
    	return item;
    }
    
    private ProdProduct getProduct(Long productId) {
		ResultHandleT<ProdProduct> result = prodProductClientService.getProdProductBy(productId);
		ProdProduct product = null;
		if(result.isFail() || (product = result.getReturnContent()) == null) {
			throw new RuntimeException("product not found based on productId " + productId);
		}
		return product;
	}

	@Override
	@Transactional("oracleTransactionManager")
	public void updateDownDate(Long orderId) {
		Timestamp now = new Timestamp(System.currentTimeMillis());
		int row = stampOrderRepository.updateDownDateByOrderId(orderId, now);
		if(row != 1) {
			throw new RuntimeException("update downDate failed: " + orderId);
		}
		orderRepository.updateWaitPaymentTimeByOrderId(orderId);
	}
	
	private Timestamp  getBalanceDueWaitDate(StampDefinitionEntity definition){
	    
	    if(definition.getStampRedeemableEndDate()!=null){
	        return definition.getStampRedeemableEndDate();
	    }
	    if(StringUtils.isNotEmpty(definition.getStampRedeemablelTimeslot())){
	        StampAssociatedProdAvailTimeSlotVo prodAvailTimeSlot = (StampAssociatedProdAvailTimeSlotVo) HhsUtils.jsonToObject(definition.getStampRedeemablelTimeslot(), StampAssociatedProdAvailTimeSlotVo.class);
	        if(StringUtils.isNotEmpty(prodAvailTimeSlot.getEndDate())){
	            int week = DateUtil.getDayOfWeek(prodAvailTimeSlot.getEndDate());
	            List<String> weekdays = prodAvailTimeSlot.getWeekDays();
	            if(CollectionUtils.isNotEmpty(weekdays)){
	                int weekday =Integer.valueOf(weekdays.get(weekdays.size()-1))-1;
	                if(week==weekday){
	                    return new Timestamp( DateUtil.stringToDate(prodAvailTimeSlot.getEndDate(), DateUtil.PATTERN_yyyy_MM_dd).getTime()); 
	                }else if(weekday > week){
	                    Calendar calendar = Calendar.getInstance();
	                    calendar.setTime( DateUtil.stringToDate(prodAvailTimeSlot.getEndDate(), DateUtil.PATTERN_yyyy_MM_dd));
	                    int banlan = week - weekday+7;
	                    calendar.add(Calendar.DAY_OF_MONTH, -banlan);
	                    return new Timestamp(calendar.getTime().getTime());
	                }else{
	                    Calendar calendar = Calendar.getInstance();
                        calendar.setTime( DateUtil.stringToDate(prodAvailTimeSlot.getEndDate(), DateUtil.PATTERN_yyyy_MM_dd));
                        int banlan = weekday-week;
                        calendar.add(Calendar.DAY_OF_MONTH, banlan);
                        return new Timestamp(calendar.getTime().getTime());
	                }
	            }
	        }
	       
	    }
	    Calendar calendar = Calendar.getInstance();
	    calendar.add(Calendar.DAY_OF_MONTH, 7);
        return new Timestamp(calendar.getTime().getTime());	    
	}
	
	

}
