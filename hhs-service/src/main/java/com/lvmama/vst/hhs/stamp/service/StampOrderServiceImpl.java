/**
 * 
 */
package com.lvmama.vst.hhs.stamp.service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.lvmama.channel.util.StringUtil;
import com.lvmama.comm.pet.po.user.UserUser;
import com.lvmama.vst.back.client.dist.service.DistributorClientService;
import com.lvmama.vst.back.client.ord.service.OrderService;
import com.lvmama.vst.back.client.prod.service.ProdProductClientService;
import com.lvmama.vst.back.dist.po.Distributor;
import com.lvmama.vst.back.order.po.OrdOrder;
import com.lvmama.vst.back.order.po.OrdOrderItem;
import com.lvmama.vst.back.order.po.OrdPerson;
import com.lvmama.vst.back.order.po.OrderEnum;
import com.lvmama.vst.back.prod.po.ProdProduct;
import com.lvmama.vst.comm.utils.DateUtil;
import com.lvmama.vst.comm.vo.ResultHandle;
import com.lvmama.vst.comm.vo.ResultHandleT;
import com.lvmama.vst.comm.vo.order.BuyInfo;
import com.lvmama.vst.comm.vo.order.BuyInfo.Item;
import com.lvmama.vst.comm.vo.order.Person;
import com.lvmama.vst.hhs.common.utils.HhsUtils;
import com.lvmama.vst.hhs.model.admin.StampAssociatedProdAvailTimeSlotVo;
import com.lvmama.vst.hhs.model.common.Constant;
import com.lvmama.vst.hhs.model.common.Constant.PAT_TYPE;
import com.lvmama.vst.hhs.model.common.Constant.StampStatus;
import com.lvmama.vst.hhs.model.common.StampDuration;
import com.lvmama.vst.hhs.model.stamp.OrderCancelResponse;
import com.lvmama.vst.hhs.model.stamp.SaveStampOrderResponse;
import com.lvmama.vst.hhs.model.stamp.StampCode;
import com.lvmama.vst.hhs.model.stamp.StampContact;
import com.lvmama.vst.hhs.model.stamp.StampDetails;
import com.lvmama.vst.hhs.model.stamp.StampGoods;
import com.lvmama.vst.hhs.model.stamp.StampOrder;
import com.lvmama.vst.hhs.model.stamp.StampOrderCancelRequest;
import com.lvmama.vst.hhs.model.stamp.StampOrderDetails;
import com.lvmama.vst.hhs.model.stamp.StampOrderRequest;
import com.lvmama.vst.hhs.model.stamp.StampRemindCustomerTimeSlot;
import com.lvmama.vst.hhs.model.stamp.StampUseOrderSimple;
import com.lvmama.vst.hhs.model.stamp.UseStampCodeInfo;
import com.lvmama.vst.hhs.product.dao.OrdStampOrderEntity;
import com.lvmama.vst.hhs.product.dao.OrdStampOrderItemEntity;
import com.lvmama.vst.hhs.product.repository.OrdStampOrderItemRepository;
import com.lvmama.vst.hhs.product.repository.OrdStampOrderRepository;
import com.lvmama.vst.hhs.product.service.ProductOrderService;
import com.lvmama.vst.hhs.stamp.util.StampUtils;
import com.lvmama.vst.hhs.stampDefinition.dao.PresaleStampDefinitionGoodsBindingEntity;
import com.lvmama.vst.hhs.stampDefinition.dao.PresaleStampDefinitionProductRelationEntity;
import com.lvmama.vst.hhs.stampDefinition.dao.StampDefinitionEntity;
import com.lvmama.vst.hhs.stampDefinition.dao.StampEntity;
import com.lvmama.vst.hhs.stampDefinition.dao.StampUnbindOrderEntity;
import com.lvmama.vst.hhs.stampDefinition.repository.PresaleStampDefinitionGoodsBindingRepository;
import com.lvmama.vst.hhs.stampDefinition.repository.StampDefinitionRepository;
import com.lvmama.vst.hhs.stampDefinition.repositorySlave.PresaleStampDefinitionProductRelationRepositorySlave;
import com.lvmama.vst.hhs.stampDefinition.repositorySlave.StampDefinitionRepositorySlave;
import com.lvmama.vst.hhs.stampDefinition.repositorySlave.StampRepositorySlave;
import com.lvmama.vst.hhs.stampDefinition.repositorySlave.StampUnbindOrderRepositorySlave;
import com.lvmama.vst.pet.adapter.UserUserProxyAdapter;

/**
 * @author fengyonggang
 *
 */
@Service
public class StampOrderServiceImpl implements StampOrderService {

	private static final Logger LOGGER = LoggerFactory.getLogger(StampOrderServiceImpl.class);
	@Autowired
	private PresaleStampDefinitionProductRelationRepositorySlave prodRelationRepositorySlave;
	@Autowired
	private ProdProductClientService prodProductClientService;
	@Autowired
	private OrderService orderService;
	@Autowired
	private ProductOrderService productOrderService;
	@Autowired
	private StampService stampService;
	@Autowired
	private DistributorClientService distributorClientRemote;
	@Autowired
	private PreSaleInventoryUnitService  preSaleInventoryUnitService;
	@Autowired
	private OrdStampOrderItemRepository ordStampOrderItemRepository;
	@Autowired
	private StampRepositorySlave stampRepositorySlave;
	@Autowired
	private PresaleStampDefinitionGoodsBindingRepository goodsBindingRepository;
	@Autowired
	private StampDefinitionRepository stampDefinitionRepository;
	@Autowired
	private UserUserProxyAdapter userUserProxyAdapter;
	@Autowired
	private OrdStampOrderRepository ordStampOrderRepository;
	@Autowired
    private StampUnbindOrderRepositorySlave stampUnbindOrderRepositorySlave;
	@Autowired
    private StampDefinitionRepositorySlave stampDefinitionRepositorySlave;
	
    public static final String HHS_ORDER_STAMP_KEY = "hhs.model.stamp.StampOrderDetails";

	@Override
	public SaveStampOrderResponse addStampOrder(StampOrderRequest request) {
		LOGGER.info("create stmap order start...");
		LOGGER.info("received stamp order: {}", request);
		LOGGER.info("received stamp order====JSON======"+HhsUtils.objectToJsonString(request));
		//重复下单
	 	/*String key = getBuyInfoHashCode(request);
	    Map<String,Boolean> map=new HashMap<String, Boolean>();
	    map.put(key, true);
	 	getNewKey(key,map);
	 	if(map.get(key)){
	 		throw new RuntimeException("the stamp " + request.getStampId() + "请勿重复提交");
	 	}*/
	 	
		if (null == request.getUserNo() || request.getUserNo().intValue() == 0) {
			throw new RuntimeException("userNo should not be null or 0");
		}
		if (StringUtils.isEmpty(request.getUserId())) {
			throw new RuntimeException("userId should not be null");
		}       
	 	StampDefinitionEntity definition = stampService.getStampDefinitionById(request.getStampId());
	 	if(definition == null) {
	 		throw new RuntimeException("no stamp definition found based on stampId " + request.getStampId());
	 	}
	 	if(definition.getProductBinding() == null) {
	 		throw new RuntimeException("the stamp " + request.getStampId() + " does not bind a product");
	 	}
	 	if("N".equalsIgnoreCase(definition.getActivityStatus())){
			throw new RuntimeException("the stamp " + request.getStampId() + " is not on sell");
	 	}
	 	if((DateUtil.isCompareTime(DateUtil.addDays(definition.getStampRedeemableEndDate(),1), new Date()))){
			throw new RuntimeException("the stamp " + request.getStampId() + " redeem end date should be more than today");
	 	}
	 	if(DateUtil.isCompareTime(DateUtil.addDays(definition.getStampOnsaleEndDate(),1), new Date())){
			throw new RuntimeException("the stamp " + request.getStampId() + " sale end date should be more than today");
	 	}
	    if(request.getAmount() > definition.getBuyMax()){
			throw new RuntimeException("the amount " + request.getAmount() + " should not be more than the max " + definition.getBuyMax());
	    }
	 	/**
	 	 * 券兑换时间
	 	 */
		List<Date>  dates = new ArrayList<Date>();
	 	if(StringUtils.isNotEmpty(definition.getAssociatedProdAvailTimeslot())){
	 		StampAssociatedProdAvailTimeSlotVo prodAvailTimeSlot = (StampAssociatedProdAvailTimeSlotVo) HhsUtils.jsonToObject(definition.getAssociatedProdAvailTimeslot(), StampAssociatedProdAvailTimeSlotVo.class);
	 		List<StampDuration> stampduration=StampUtils.translateAvailTimeslot(prodAvailTimeSlot);
	 		for(StampDuration stampDuration : stampduration){			
				List<Date> datesBetweenTwoDate = HhsUtils.getDatesBetweenTwoDate(stampDuration.getStartDate(), stampDuration.getEndDate());
				dates.addAll(datesBetweenTwoDate);
			}
	 	}
	 	if(CollectionUtils.isNotEmpty(dates)){
	 		Date dateDurateion=dates.get(dates.size()-1);
	 		if(DateUtil.isCompareTime(DateUtil.addDays(dateDurateion,1), new Date())){
		 		throw new RuntimeException("the stamp " + request.getStampId() + " sale duration has got expired.");
		 	}
	 	}
	 	int row = preSaleInventoryUnitService.consumeInventory(request.getStampId(), request.getAmount());
        if(row != 1){
        	throw new RuntimeException("stamp consume inventory failed: " + request.getStampId());
        }
	 	
		BuyInfo buyInfo = buildBuyInfo(request, definition);
		try {
			LOGGER.info("预售券的订单下单-----------------------联系人request"+request.getContact().getPhone());
			LOGGER.info("预售券的订单下单-----------------------联系人buyInfo"+buyInfo.getContact().getPhone()+"book"+buyInfo.getBooker().getPhone());
		} catch (Exception e) {
			LOGGER.info("预售券没有联系人");
		}
		
//		LOGGER.info("create stamp order: {}", JSON.toJSONString(buyInfo));
		//调用dubbo接口下单
		ResultHandleT<OrdOrder> orderResult=null;
		try {
			orderResult = orderService.createOrder(buyInfo, String.valueOf(request.getUserId()));
		} catch (Exception e) {
			LOGGER.error("order create failed",e);
		}
		
		OrdOrder returnOrder = null;
		if(orderResult == null || orderResult.isFail() || (returnOrder = orderResult.getReturnContent()) == null) {
			if(orderResult != null && orderResult.getMsg() != null) {
				LOGGER.error(orderResult.getMsg());
			}
			//退还库存
			preSaleInventoryUnitService.rollbackConsumeInventory(request.getStampId(), request.getAmount());
			throw new RuntimeException("Create order failed !");
		}
		
		long orderId = returnOrder.getOrderId();
		List<OrdOrderItem> orderItems = returnOrder.getOrderItemList();
		OrdOrderItem firstItem = null;
		if(orderItems == null || orderItems.size() == 0 || (firstItem = orderItems.get(0)) == null) {
			throw new RuntimeException("Can not find orderitem !");
		}
		
		LOGGER.info("order creation got success, starting update stamp order and order items.");
		LOGGER.info("保存预售券订单------------------------------------------------------"+orderId);
		//保存券订单 并 更新订单券标示
		productOrderService.saveStampOrder(request, definition, orderId, firstItem.getOrderItemId());
		
		LOGGER.info("starting create stamp code.");
		stampService.saveStamp(buyInfo, definition, orderId);
		
		LOGGER.info("券下单成功------------------------------------------------------"+orderId);
		
		
		SaveStampOrderResponse resp = new SaveStampOrderResponse();
		resp.setOrderId(String.valueOf(orderId));
		LOGGER.info("券下单成功1------------------------------------------------------"+orderId);
		return resp;
	}
	
	private BuyInfo buildBuyInfo(StampOrderRequest request, StampDefinitionEntity definition) {
		Long productId = NumberUtils.toLong(definition.getProductBinding().getProductId());
		
		BuyInfo buyInfo = new BuyInfo();
		buyInfo.setAdultQuantity(1); 													//默认成人数为1
		buyInfo.setBooker(buildBooker(request.getUserId()));							//下单人信息
		buyInfo.setCategoryId(NumberUtils.toLong(definition.getProductBinding().getCategoryId()));
		buyInfo.setChildQuantity(0); 													//儿童数
		buyInfo.setContact(buildContact(request.getContact()));	
		if(null ==request.getDistributionId()) {
		    buyInfo.setDistributionId(2L);//渠道, 2是后台主站     
		} else {
		    buyInfo.setDistributionId(request.getDistributionId());     
		}
		LOGGER.info("order distribution: {}, {}", request.getDistributionId(), buyInfo.getDistributionId());
		
		ResultHandleT<Distributor> dis = distributorClientRemote.findDistributorById(request.getDistributionId());
		if(dis.isSuccess() && dis.getReturnContent() != null){
		    buyInfo.setDistributorName(dis.getReturnContent().getDistributorName());
		}
		buyInfo.setDistributionChannel(request.getDistributorChannel());
		buyInfo.setDistributorCode(request.getDistributorCode());
		buyInfo.setIp(request.getIp());
		buyInfo.setItemList(getItems(request.getStampId(), request, definition));
		buyInfo.setOrderTotalPrice(definition.getSalePrice().longValue() * request.getAmount()); //总价
		buyInfo.setOughtAmount(buyInfo.getOrderTotalPrice());
		buyInfo.setProdProduct(getProduct(productId));
		buyInfo.setProductId(productId);
		buyInfo.setQuantity(request.getAmount());
		buyInfo.setUserId(request.getUserId());
		buyInfo.setUserNo(request.getUserNo());
		Date date = null;
        if(StringUtils.isNotBlank(definition.getAssociatedProdAvailTimeslot())) {
            StampAssociatedProdAvailTimeSlotVo prodAvailTimeSlot = (StampAssociatedProdAvailTimeSlotVo) HhsUtils.jsonToObject(definition.getAssociatedProdAvailTimeslot(), StampAssociatedProdAvailTimeSlotVo.class);
            List<StampDuration>  times =StampUtils.translateAvailTimeslot(prodAvailTimeSlot);
            date= times.get(times.size()-1).getEndDate();
            LOGGER.info("游玩时间是兑换时间的最后一天-----------------------"+date);
        }
		
		buyInfo.setVisitTime(DateUtil.formatDate(date==null?new Date():date, DateUtil.PATTERN_yyyy_MM_dd)); 
		return buyInfo;
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
	
	@Transactional("slaveTransactionManager")
	private List<Item> getItems(String stampId, StampOrderRequest request, StampDefinitionEntity definition) {
		PresaleStampDefinitionProductRelationEntity productRelation = prodRelationRepositorySlave.getByStampDefinitionId(stampId);
		if(productRelation == null) {
			throw new RuntimeException("No virtual product found which binding to the stamp " + stampId);
		}
		List<Item> list = new ArrayList<BuyInfo.Item>();
		list.add(buildItem(productRelation, request, definition));
		return list;
	}
	
	private Item buildItem(PresaleStampDefinitionProductRelationEntity productRelation, StampOrderRequest request, StampDefinitionEntity definition) {
		Item item = new Item();
		item.setAdultQuantity(1); //成人数 默认1
		item.setGoodsId(NumberUtils.toLong(productRelation.getGoodsId()));
		item.setPrice(String.valueOf(definition.getSalePrice().longValue()));
		item.setProductCategoryId(NumberUtils.toLong(productRelation.getCategoryId()));
		item.setQuantity(request.getAmount());
		item.setSettlementPrice("0");
		item.setTotalAmount((long)(request.getAmount()*definition.getSalePrice().longValue()));
		item.setTotalSettlementPrice(0L);
		Date date = null;
        if(StringUtils.isNotBlank(definition.getAssociatedProdAvailTimeslot())) {
            StampAssociatedProdAvailTimeSlotVo prodAvailTimeSlot = (StampAssociatedProdAvailTimeSlotVo) HhsUtils.jsonToObject(definition.getAssociatedProdAvailTimeslot(), StampAssociatedProdAvailTimeSlotVo.class);
            List<StampDuration>  times =StampUtils.translateAvailTimeslot(prodAvailTimeSlot);
            date= times.get(times.size()-1).getEndDate();
            LOGGER.info("游玩时间是兑换时间的最后一天-----------------------"+date);
        }
		
        item.setVisitTime(DateUtil.formatDate(date==null?new Date():date, DateUtil.PATTERN_yyyy_MM_dd)); 
		//item.setVisitTime(DateUtil.formatDate(new Date(), DateUtil.PATTERN_yyyy_MM_dd));
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
    public StampOrderDetails getStampOrderDetails(long orderId) {
    	OrdOrder order = orderService.queryOrdorderByOrderId(orderId);	   
		if (order == null) {
	        throw new RuntimeException("no order found based on orderId " + orderId);
	    }
		OrdOrderItem firstItem = null;
		if(CollectionUtils.isEmpty(order.getOrderItemList()) || (firstItem = order.getOrderItemList().get(0)) == null) {
			throw new RuntimeException("no order item found based on orderId " + orderId);
		}
		
	    StampOrderDetails  detail = new StampOrderDetails();
	    detail.setAmount(firstItem.getQuantity().intValue());
	    detail.setContact(getStampContact(order.getContactPerson()));
	    detail.setOrderDate(order.getCreateTime());
	    detail.setOrderId(String.valueOf(order.getOrderId()));
	    if(OrderEnum.ORDER_STATUS.CANCEL.name().equals(order.getOrderStatus())){
	    	detail.setOrderStatus(OrderEnum.ORDER_STATUS.CANCEL.name());
	    }else{
	    	detail.setOrderStatus(order.getPaymentStatus());
	    }
	    detail.setLatestPayTime(order.getPaymentTime());
	    detail.setTotalPrice(order.getOughtAmount());
	    detail.setPrice(firstItem.getPrice());
	    detail.setPaidPrice(order.getActualAmount());
	    OrdStampOrderEntity  ordStampOrder = productOrderService.getStampOrderByOrderId(orderId);
	    if (ordStampOrder == null) {
            throw new RuntimeException("no order found OrdStampOrderEntity on orderId " + orderId);
        }
	    if(StringUtils.isNotBlank(ordStampOrder.getRemindCustomerTimeslot())) {
	        StampAssociatedProdAvailTimeSlotVo vo = HhsUtils.jsonToObject(ordStampOrder.getRemindCustomerTimeslot(), StampAssociatedProdAvailTimeSlotVo.class);
	        StampRemindCustomerTimeSlot remindTime = new StampRemindCustomerTimeSlot();
	        remindTime.setStartDate(vo.getStartDate());
	        remindTime.setEndDate(vo.getEndDate());
	        remindTime.setWeekDays(vo.getWeekDays());
	        detail.setRemindCustomerTimeslot(remindTime);
	    }
	    detail.setPayType(ordStampOrder.getPayType());
	    detail.setBalanceDueWaitDate(ordStampOrder.getBalanceDueWaitDate());
	    detail.setDownPayment(ordStampOrder.getDownPayment());
	   
	    OrdStampOrderItemEntity stampOrderItem = productOrderService.getStampOrderItemById(firstItem.getOrderItemId());
	    if(stampOrderItem == null) {
	    	throw new RuntimeException("no stamp order item found based on orderItemId " + firstItem.getOrderItemId());
	    }
	    StampDetails stampDetail = stampService.getStampById(stampOrderItem.getStampDefinitionId());
	    detail.setStamp(stampDetail);
	    detail.setSubsidyAmount(stampOrderItem.getSubsidyAmount()==null?0L:stampOrderItem.getSubsidyAmount().longValue());
	    List<StampEntity> stamps = stampService.getStampCodeByOrderId(String.valueOf(order.getOrderId()));
	    List<StampCode> codes = new ArrayList<StampCode>();
	    if(CollectionUtils.isNotEmpty(stamps)){
	    	for(StampEntity stamp:stamps){
	    		StampCode code = new StampCode();
	    		org.springframework.beans.BeanUtils.copyProperties(stamp, code);
	    		if(null != stamp.getExpiredDate())
	    		    code.setExpiredDate(new Date(stamp.getExpiredDate().getTime()));
	    		if(StringUtils.isNotBlank(stamp.getUseOrderCategoryId()))
	    			code.setOrderType(stamp.getUseOrderCategoryId());
	    		
	    		//TODO check
	    		List<StampUnbindOrderEntity> unbindList = stampUnbindOrderRepositorySlave.getByStampCode(stamp.getSerialNumber());
	    		List<StampUseOrderSimple> useOrderHis = Lists.newArrayList();
	    		if(CollectionUtils.isNotEmpty(unbindList)) {
	    		    for(StampUnbindOrderEntity unbindOrder : unbindList) {
	    		        StampUseOrderSimple useOrder = new StampUseOrderSimple();
	    		        useOrder.setUseOrderId(unbindOrder.getUseOrderId());
	    		        useOrder.setUnbindStatus(Constant.StampUnbindStatus.UNBIND.name());
	    		        useOrderHis.add(useOrder);
	    		    }
	    		}
	    		if(StringUtils.equals(stamp.getStampStatus(), Constant.StampStatus.USED.name())) {
	    		    StampUseOrderSimple useOrder = new StampUseOrderSimple();
                    useOrder.setUseOrderId(stamp.getUseOrderId());
                    useOrder.setUnbindStatus(Constant.StampUnbindStatus.INUSE.name());
	    		    useOrderHis.add(useOrder);
	    		}
	    		code.setUseOrderHis(useOrderHis);
	    		codes.add(code);
	    	}
	    } else {
	    	LOGGER.warn("can not find stamp code based on orderId {}", order.getOrderId());
	    }
	    detail.setStampCodes(codes);
	    return detail;
    }
	
	private StampContact getStampContact(OrdPerson person) {
		StampContact contact = new StampContact();
		try {
			BeanUtils.copyProperties(contact,person);
		} catch (Exception e) {
			LOGGER.error("copy properties failed!", e);
		}
		return contact;
	}
	
	@Override
	public List<StampOrder> getStampOrderList(String userId, int start, int end) {
		List<Long> orderIds = productOrderService.getOrderIdByUserId(userId, start, end);
		if(CollectionUtils.isEmpty(orderIds)) {
			LOGGER.info("can not find any orders under user {}", userId);
			return null;
		}
		
		List<StampOrder> stampOrders = new ArrayList<StampOrder>();
		
		List<OrdOrder> orders = orderService.queryOrdorderByOrderIdList(orderIds);
		if(orders != null) {
			for (OrdOrder order : orders) {
				OrdOrderItem firstItem = null;
				if(CollectionUtils.isEmpty(order.getOrderItemList()) || (firstItem = order.getOrderItemList().get(0)) == null) {
					LOGGER.info("no order item found based on orderId {}", order.getOrderId());
					continue;
				}
				OrdStampOrderItemEntity stampOrderItem = productOrderService.getStampOrderItemById(firstItem.getOrderItemId());
			    if(stampOrderItem == null) {
			    	LOGGER.info("no stamp order item found based on orderItemId " + firstItem.getOrderItemId());
			    	continue;
			    }
				
				StampOrder stampOrder = new StampOrder();				
				stampOrder.setAmount(firstItem.getQuantity().intValue());
				stampOrder.setCreateDate(order.getCreateTime());
				stampOrder.setLatestPayTime(order.getWaitPaymentTime());
				stampOrder.setOrderId(String.valueOf(order.getOrderId()));
				if(OrderEnum.ORDER_STATUS.CANCEL.name().equals(order.getOrderStatus())){
					stampOrder.setOrderStatus(OrderEnum.ORDER_STATUS.CANCEL.name());
				}else{
					stampOrder.setOrderStatus(order.getPaymentStatus());
				}
				stampOrder.setLatestPayTime(order.getPaymentTime());
				stampOrder.setTotalAmount(order.getOughtAmount());
				stampOrder.setPrice(firstItem.getPrice());
				stampOrder.setPaidPrice(order.getActualAmount());
				
				StampDetails stampDetail = stampService.getStampById(stampOrderItem.getStampDefinitionId());
				stampOrder.setStampId(stampDetail.getId());
				stampOrder.setStampName(stampDetail.getName());
				stampOrder.setStampRedeemableDate(stampDetail.getStampRedeemableDuration());
				stampOrder.setAvailTimeSlot(stampDetail.getAssociatedProdAvailTimeSlot());
				stampOrder.setProductName(stampDetail.getBoundMerchant().getProductName());
				stampOrder.setProductId(stampDetail.getBoundMerchant().getProductId());
				stampOrder.setDepartId(stampDetail.getBoundMerchant().getDepartId());
				
				int unuseCodeNum = 0;
				List<StampEntity> stampList = stampService.getByOrderIdAndStampStatus(String.valueOf(order.getOrderId()),StampStatus.UNUSE.name());
				if(CollectionUtils.isNotEmpty(stampList)) {
					unuseCodeNum = stampList.size();
				}
				stampOrder.setUnuseCodeNum(unuseCodeNum);
				
				stampOrders.add(stampOrder);
			}
			
			Collections.sort(stampOrders, new Comparator<StampOrder>() {
				@Override
				public int compare(StampOrder o1, StampOrder o2) {
					return o2.getCreateDate().compareTo(o1.getCreateDate());
				}
			});
		}
		return stampOrders;
	}

	@Override
	public OrderCancelResponse cancelOrder(StampOrderCancelRequest stampOrderCancelRequest) {
		//验证券是否在可退款的时间内。是否已经用完
		
		OrdOrder order = orderService.queryOrdorderByOrderId(stampOrderCancelRequest.getOrderId());
		
		boolean hasPaid = order.hasFullPayment() && order.hasPayed();
		String orderId = String.valueOf(stampOrderCancelRequest.getOrderId());
		stampService.invalidStampCode(orderId, hasPaid);
		
		ResultHandle rl = null;
		try {
			//dubbo调原来的订单取消接口
			rl = orderService.cancelOrder(
					stampOrderCancelRequest.getOrderId(),
					stampOrderCancelRequest.getCancelCode(),
					stampOrderCancelRequest.getReason(),
					stampOrderCancelRequest.getOperatorId(),
					stampOrderCancelRequest.getMemo());
		} catch (Exception e) {
			LOGGER.error("cancel stamp order error: " + orderId, e);
		}
		if(rl == null || rl.isFail()) {
			LOGGER.error("cancel stamp order failed: " + orderId + "," + rl == null ? null : rl.getMsg());
			try {
				stampService.rollbackInvalidStampCode(orderId, hasPaid);
			} catch (Exception e) {
				LOGGER.error("#monitor-stamp: rollbackInvalidStampCode failed," + orderId, e);
			}
		}

		OrderCancelResponse res = new OrderCancelResponse();
		res.setSuccess(rl.isSuccess());
		return res;
	}

	@Override
	public boolean paymentDeposit(String orderId, Long actualAmount) {
		Long oid = Long.valueOf(orderId);
		OrdStampOrderEntity stampOrder = productOrderService.getStampOrderByOrderId(oid);
		if (stampOrder != null && StringUtils.equals(stampOrder.getPayType(), PAT_TYPE.PART.name())
				&& stampOrder.getDownPayment().equals(actualAmount)) {
			productOrderService.updateDownDate(oid);
			return true;
		}
		return false;
	}

	@Override
	@Transactional("oracleTransactionManager")
	public Long getProductByOrderId(Long orderId) {
		OrdStampOrderItemEntity ordStamp= ordStampOrderItemRepository.getByOrderId(orderId);
		String stampDefinitionId=ordStamp.getStampDefinitionId();
		StampDefinitionEntity stamp=stampService.getStampDefinitionById(stampDefinitionId);
		return Long.valueOf(stamp.getProductBinding().getProductId());
	}

    @Override
    @Transactional("slaveTransactionManager")
    public List<StampOrderDetails> getStampCodeList(String useOrderId, String stampId) {
        List<StampOrderDetails> orderDetail = new ArrayList<StampOrderDetails>();
        List<StampEntity> entityList = stampRepositorySlave.getByUseOrderId(useOrderId);
        if(com.alibaba.dubbo.common.utils.CollectionUtils.isEmpty(entityList)){
            return orderDetail;
        }
        Iterator<StampEntity> it1 = entityList.iterator();
        Map<String,Object> ht = new HashMap<String, Object>();
        while(it1.hasNext()){
          StampEntity obj = it1.next();
          ht.put(obj.getOrderId(), obj);          
        }
        for (String key : ht.keySet()) {
            StampOrderDetails detail = this.getStampOrderDetails(Long.valueOf(key));
            StampDefinitionEntity stampDefinition = stampDefinitionRepository.getById(detail.getStamp().getId());
            List<PresaleStampDefinitionGoodsBindingEntity> goodsBindings = goodsBindingRepository.getByStampDefinition(stampDefinition);
            detail.getStamp().setGoods(getPrimaryStampGoods(goodsBindings));
            orderDetail.add(detail);
          
        }
        return orderDetail;
    }
    

    private List<StampGoods> getPrimaryStampGoods(List<PresaleStampDefinitionGoodsBindingEntity> goodsBindings) {
        List<StampGoods> list = new ArrayList<StampGoods>();
        if(goodsBindings != null) {
            for(PresaleStampDefinitionGoodsBindingEntity entity : goodsBindings) {
                //主商品
                list.add(new StampGoods(entity.getId(), new Long(entity.getGoodsId()), entity.getGoodsName(), entity.getBranchFlag(),entity.getCategoryId()));
               
            }
        }
        return list;
    }
	

	private Person buildBooker(String userNo) {
		UserUser user = userUserProxyAdapter.getUserUserByUserNo(userNo);
		Person person = new Person();
		person.setFullName(user.getUserName());
		person.setMobile(user.getMobileNumber());
		return person;
		
	}

    @Override
    @Transactional("oracleTransactionManager")
    public boolean updateOrderStampPayType(Long orderId, String payType) {
        boolean result = false;
        OrdOrder order = orderService.queryOrdorderByOrderId(orderId);     
        if (order == null) {
            throw new RuntimeException("no order found based on orderId " + orderId);
        }
        if(!order.getPaymentStatus().equals("UNPAY")){
            throw new RuntimeException("order payment_status is not UNPAY  payment_status" + order.getPaymentStatus());
        }
        OrdOrderItem firstItem = null;
        if(CollectionUtils.isEmpty(order.getOrderItemList()) || (firstItem = order.getOrderItemList().get(0)) == null) {
            throw new RuntimeException("no order item found based on orderId " + orderId);
        }
        OrdStampOrderItemEntity stampOrderItem = productOrderService.getStampOrderItemById(firstItem.getOrderItemId());
        if(stampOrderItem == null) {
            throw new RuntimeException("no stamp order item found based on orderItemId " + firstItem.getOrderItemId());
        }
        StampDetails stampDetail = stampService.getStampById(stampOrderItem.getStampDefinitionId());
        Timestamp balanceDueWaitDate = null;
        Long downPayment = null ;
        if(StringUtil.isNotEmptyString(payType)){
            if(payType.equals(Constant.PAT_TYPE.PART.getCode())){
                downPayment = stampDetail.getDownPayment();
            }
            if(payType.equals(Constant.PAT_TYPE.FULL.getCode())){               
                balanceDueWaitDate = new Timestamp(stampOrderItem.getCreateDate().getTime()+3600*stampDetail.getBalanceDueInHour());
            }
            try{
                ordStampOrderRepository.updatePayTypeByOrderId(orderId, payType, balanceDueWaitDate, downPayment);
                result= true;
            }catch(Exception e){
                result= false;
            }
            ordStampOrderRepository.updatePayTypeByOrderId(orderId, payType, balanceDueWaitDate, downPayment);
        }
        return result;
    }

    @Override
    public List<StampCode> getOrderStampCodesOnly(String orderId) {
        List<StampEntity> list = stampRepositorySlave.getByOrderId(orderId);
        if(CollectionUtils.isEmpty(list))
            return null;
        
        List<StampCode> codes = Lists.newArrayListWithCapacity(list.size());
        for(StampEntity stamp : list) {
            StampCode code = new StampCode();
            org.springframework.beans.BeanUtils.copyProperties(stamp, code);
            codes.add(code);
        }
        return codes;
    }

    @Override
    public List<UseStampCodeInfo> getUseStampCodes(String useOrderId) {
        
        List<StampEntity> inuseStamps = stampRepositorySlave.getByUseOrderId(useOrderId);
        List<StampUnbindOrderEntity> unbindStamps = stampUnbindOrderRepositorySlave.getByUseOrderId(useOrderId);
        if(CollectionUtils.isEmpty(inuseStamps) && CollectionUtils.isEmpty(unbindStamps))
            return null;
        
        List<UseStampCodeInfo> result = Lists.newArrayListWithCapacity(inuseStamps.size()+unbindStamps.size());
        
        Set<String> stampOrderIds = Sets.newHashSet();
        
        // get inuse stamp codes
        if(CollectionUtils.isNotEmpty(inuseStamps)) {
            for(StampEntity stamp : inuseStamps) {
                
                // stamp code not in use.
                if(!StringUtils.equals(stamp.getStampStatus(), Constant.StampStatus.USED.name()))
                    continue;
                
                stampOrderIds.add(stamp.getOrderId());
                
                UseStampCodeInfo useStamp = new UseStampCodeInfo();
                useStamp.setOrderId(stamp.getOrderId());
                useStamp.setUseOrderId(useOrderId);
                useStamp.setBindStatus(Constant.StampUnbindStatus.INUSE.name());
                useStamp.setPrice(stamp.getPrice());
                useStamp.setSerialNumber(stamp.getSerialNumber());
                
                StampDefinitionEntity stampDefinition = stampDefinitionRepositorySlave.getById(stamp.getStampDefinitionId());
                useStamp.setStampNo(stampDefinition.getStampNo());
                useStamp.setStampName(stampDefinition.getName());
                
                result.add(useStamp);
            }
        }
        
        // get unbind stamp codes
        if(CollectionUtils.isNotEmpty(unbindStamps)) {
            for(StampUnbindOrderEntity stampUnbind : unbindStamps) {
                StampEntity stamp = stampRepositorySlave.getBySerialNumber(stampUnbind.getStampCode());
                stampOrderIds.add(stamp.getOrderId());
                
                UseStampCodeInfo useStamp = new UseStampCodeInfo();
                useStamp.setOrderId(stamp.getOrderId());
                useStamp.setUseOrderId(useOrderId);
                useStamp.setBindStatus(Constant.StampUnbindStatus.UNBIND.name());
                useStamp.setPrice(stamp.getPrice());
                useStamp.setSerialNumber(stamp.getSerialNumber());
                
                StampDefinitionEntity stampDefinition = stampDefinitionRepositorySlave.getById(stamp.getStampDefinitionId());
                useStamp.setStampNo(stampDefinition.getStampNo());
                useStamp.setStampName(stampDefinition.getName());
                
                result.add(useStamp);
            }
        }
        
        Map<String, OrdOrder> map = Maps.newHashMap();
        for(String orderId : stampOrderIds) {
            OrdOrder order = orderService.queryOrdorderByOrderId(Long.valueOf(orderId));
            map.put(orderId, order);
        }
        
        // set stamp order status
        for(UseStampCodeInfo useStamp : result) {
            if(map.get(useStamp.getOrderId()) != null)
                useStamp.setOrderStatus(map.get(useStamp.getOrderId()).getOrderStatus());
        }
        
        return result;
    }
    
/*    private  String getBuyInfoHashCode(StampOrderRequest request){
		JSONObject obj = JSONObject.fromObject(request);
		return DigestUtils.shaHex(obj.toString());
	}
    
 
    @Cacheable(value = "key", cacheManager = "expiredHalfMinutesCacheManager", key = "#key")
    private String getNewKey(String key,Map map){
    	map.put(key, false);
    	return key;
    }*/
    
}
