package com.lvmama.vst.hhs.stampDefinition.service;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.BeanUtilsBean;
import org.apache.commons.beanutils.converters.BigDecimalConverter;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.dubbo.common.utils.CollectionUtils;
import com.lvmama.vst.back.biz.po.BizBranch;
import com.lvmama.vst.back.biz.po.BizCategory;
import com.lvmama.vst.back.biz.po.BizEnum;
import com.lvmama.vst.back.client.biz.service.CategoryClientService;
import com.lvmama.vst.back.client.goods.service.SuppGoodsClientService;
import com.lvmama.vst.back.client.prod.service.ProdProductBranchClientService;
import com.lvmama.vst.back.client.prod.service.ProdProductClientService;
import com.lvmama.vst.back.client.prod.service.ProdTrafficClientService;
import com.lvmama.vst.back.client.pub.service.ComLogClientService;
import com.lvmama.vst.back.goods.po.SuppGoods;
import com.lvmama.vst.back.prod.po.ProdProduct;
import com.lvmama.vst.back.prod.po.ProdProductBranch;
import com.lvmama.vst.back.pub.po.ComLog.COM_LOG_LOG_TYPE;
import com.lvmama.vst.back.pub.po.ComLog.COM_LOG_OBJECT_TYPE;
import com.lvmama.vst.comm.utils.DateUtil;
import com.lvmama.vst.comm.vo.ResultHandleT;
import com.lvmama.vst.hhs.common.exception.StampBizException;
import com.lvmama.vst.hhs.common.exception.StampErrorCodes;
import com.lvmama.vst.hhs.common.exception.UntrustedIssuerException;
import com.lvmama.vst.hhs.common.utils.HhsConstants;
import com.lvmama.vst.hhs.common.utils.HhsUtils;
import com.lvmama.vst.hhs.model.admin.BoundMerchantVo;
import com.lvmama.vst.hhs.model.admin.StampAssociatedProdAvailTimeSlotVo;
import com.lvmama.vst.hhs.model.admin.StampCreationRequest;
import com.lvmama.vst.hhs.model.admin.StampDetailsVo;
import com.lvmama.vst.hhs.model.admin.StampGoodsRelationVo;
import com.lvmama.vst.hhs.model.admin.StampGoodsVo;
import com.lvmama.vst.hhs.model.admin.StampRequest;
import com.lvmama.vst.hhs.model.common.StampDuration;
import com.lvmama.vst.hhs.product.service.ProductOrderService;
import com.lvmama.vst.hhs.product.service.ProductService;
import com.lvmama.vst.hhs.stampDefinition.dao.PresaleInventoryUnitEntity;
import com.lvmama.vst.hhs.stampDefinition.dao.PresaleStampDefinitionGoodsBindingEntity;
import com.lvmama.vst.hhs.stampDefinition.dao.PresaleStampDefinitionProductBindingEntity;
import com.lvmama.vst.hhs.stampDefinition.dao.PresaleStampDefinitionProductRelationEntity;
import com.lvmama.vst.hhs.stampDefinition.dao.StampDefinitionEntity;
import com.lvmama.vst.hhs.stampDefinition.repository.PresaleInventoryUnitRepository;
import com.lvmama.vst.hhs.stampDefinition.repository.PresaleStampDefinitionGoodsBindingRepository;
import com.lvmama.vst.hhs.stampDefinition.repository.PresaleStampDefinitionProductBindingRepository;
import com.lvmama.vst.hhs.stampDefinition.repository.PresaleStampDefinitionProductRelationRepository;
import com.lvmama.vst.hhs.stampDefinition.repository.StampDefinitionRepository;
import com.lvmama.vst.hhs.stampDefinition.repositorySlave.PresaleInventoryUnitRepositorySlave;
import com.lvmama.vst.hhs.stampDefinition.repositorySlave.PresaleStampDefinitionGoodsBindingRepositorySlave;
import com.lvmama.vst.hhs.stampDefinition.repositorySlave.PresaleStampDefinitionProductBindingRepositorySlave;
import com.lvmama.vst.hhs.stampDefinition.repositorySlave.PresaleStampDefinitionProductRelationRepositorySlave;
import com.lvmama.vst.hhs.stampDefinition.repositorySlave.StampDefinitionRepositorySlave;

@Service
public class StampManagementServiceImpl implements StampManagementService {

    private static final Logger LOGGER = LoggerFactory.getLogger(StampManagementServiceImpl.class);

    @Autowired
    StampDefinitionRepository stampDefinitionRepository;  
    @Autowired
    StampDefinitionRepositorySlave stampDefinitionRepositorySlave;    
    @Autowired
    PresaleStampDefinitionProductBindingRepository stampDefinitionProductBindingRepository;
    @Autowired
    PresaleStampDefinitionProductBindingRepositorySlave stampDefinitionProductBindingRepositorySlave;
    @Autowired
    PresaleInventoryUnitRepository inventoryUnitRepository;
    @Autowired
    PresaleInventoryUnitRepositorySlave inventoryUnitRepositorySlave;
    @Autowired
    private ProdProductClientService productClientService;
    @Autowired
    private CategoryClientService categoryClientService;
    @Autowired
    private ProdProductBranchClientService prodProductBranchClientService;
    @Autowired
    private SuppGoodsClientService suppGoodsClientService;
    @Autowired
    private PresaleStampDefinitionProductRelationRepository vmProductRepository;
    @Autowired
    private PresaleStampDefinitionGoodsBindingRepositorySlave goodsBindingRepositorySlave;
    @Autowired
    private PresaleStampDefinitionGoodsBindingRepository goodsBindingRepository;
    @Autowired
    private ComLogClientService  comLogClientService;
    @Autowired
    private ProdTrafficClientService prodTrafficClientService;
    @Autowired
	private ProductOrderService productOrderService;
    @Autowired
    private DepartmentService departmentService;
    @Autowired
    private ProductService productService;
    @Autowired
    private PresaleStampDefinitionProductRelationRepositorySlave relationSlave;
    
    public static final String HHS_STAMP_DEFINITION_KEY = "hhs.stampDefinition.dao.StampDefinitionEntity"; 
   
    
    @Override
    @Transactional("transactionManager")
    public String createStampDefinition(StampCreationRequest createRequest)throws RuntimeException {
        LOGGER.info("======== StampManagementServiceImpl =========");
        moneyTo100(createRequest);
        LOGGER.debug(HhsUtils.objectToJsonString(createRequest));
        
        StampDefinitionEntity stampDefinition = null;
        PresaleInventoryUnitEntity inventory = null;
        PresaleStampDefinitionProductBindingEntity boundMercent = null;
        PresaleStampDefinitionProductRelationEntity vmProductBingding = 
                new PresaleStampDefinitionProductRelationEntity();
        String stampDefinitionId = createRequest.getId();   
        int saveOrUpdate = 0;
        
        Timestamp now = new Timestamp(System.currentTimeMillis());
        
        // insert
        if (stampDefinitionId == null) {
            saveOrUpdate = 1;
            stampDefinitionId = UUID.randomUUID().toString();
            stampDefinition = new StampDefinitionEntity();
            inventory = new PresaleInventoryUnitEntity();
            boundMercent = new PresaleStampDefinitionProductBindingEntity();
            stampDefinition.setId(stampDefinitionId);
            String maxStamp =  this.stampDefinitionRepository.findStampMaxStampNo();
            stampDefinition.setStampNo(stampNo(maxStamp));            
            inventory.setId(UUID.randomUUID().toString());
            inventory.setStampDefinitionId(stampDefinitionId);
            inventory.setCreateDate(now);
            inventory.setUpdateDate(now);
            boundMercent.setId(UUID.randomUUID().toString());
            boundMercent.setManagerId(createRequest.getProductManagerId());
            boundMercent.setManagerName(createRequest.getProductManagerName());
            boundMercent.setCreateDate(now);
            boundMercent.setUpdateDate(now);
//            boundMercent.setStampDefinitionId(stampDefinitionId);
//            boundMercent.setStampDefinition(stampDefinition);
            createStampProduct(createRequest, boundMercent, createRequest.getProductId()+"", vmProductBingding,createRequest.getProductManagerId());
            if(vmProductBingding.getProductId()==null){
                throw new RuntimeException("create vm goodsId  failed : 请重新编辑！！！" );
            }
            vmProductBingding.setCreateDate(new Date());
            vmProductBingding.setUpdateDate(new Date());
            vmProductBingding.setId(UUID.randomUUID().toString());
            vmProductBingding.setStampDefinitionId(stampDefinitionId);
            stampDefinition.setActivityStatus("N");
            stampDefinition.setCreateDate(now);
            stampDefinition.setUpdateDate(now);
            stampDefinition.setStampOrder(BigDecimal.valueOf(createRequest.getStampOrder()));
        } else { // update
            saveOrUpdate =2;
            stampDefinition = this.stampDefinitionRepository.getById(stampDefinitionId);
            
            if (null == stampDefinition) {
                try {
                    throw new Exception("StampDefinitionId is invalid.");
                } catch (Exception e) {
                    LOGGER.info("Caught an exception: " + e.getMessage());
                }
            }
            stampDefinition.setActivityStatus(createRequest.getActivityStatus().toString());
            inventory = this.inventoryUnitRepository.getByStampDefinitionId(stampDefinitionId);
            boundMercent = stampDefinition.getProductBinding();
            if(boundMercent == null) {
            	boundMercent = this.stampDefinitionProductBindingRepository.getByStampDefinition(stampDefinition);
            }
        }

        stampDefinition.setName(createRequest.getStampName());
        stampDefinition.setPrintPrice(new BigDecimal(createRequest.getPrintPrice()==null?0:createRequest.getPrintPrice()));
        stampDefinition.setSalePrice(new BigDecimal(createRequest.getSalePrice()==null?0:createRequest.getSalePrice()));
        stampDefinition.setSetPrice(new BigDecimal(createRequest.getSetPrice()==null?0:createRequest.getSetPrice()));
        stampDefinition.setDownPayment(new BigDecimal(createRequest.getDownpayment()==null?0:createRequest.getDownpayment()));
        stampDefinition.setOperationsAmount(new BigDecimal(createRequest.getOperationsAmount()==null?0:createRequest.getOperationsAmount()));
        if(createRequest.getBuyMax()!=null){
            stampDefinition.setBuyMax(createRequest.getBuyMax());
        }
       
        if (null != createRequest.getRemarks()) {
            stampDefinition.setRemarks(createRequest.getRemarks());
        }
        if (createRequest.getRemindCustomerDate() != null) {
            stampDefinition.setRemindCustomerDate(null);
        }
        if (createRequest.getStampOnsaleDuration() != null) {
            stampDefinition.setStampOnsaleStartDate(
                    new Timestamp(createRequest.getStampOnsaleDuration().getStartDate().getTime()));
            stampDefinition.setStampOnsaleEndDate(
                    new Timestamp(createRequest.getStampOnsaleDuration().getEndDate().getTime()));
        }
        if (createRequest.getStampRedeemableDuration() != null) {
            stampDefinition.setStampRedeemableStartDate(
                    new Timestamp(createRequest.getStampRedeemableDuration().getStartDate().getTime()));
            stampDefinition.setStampRedeemableEndDate(
                    new Timestamp(createRequest.getStampRedeemableDuration().getEndDate().getTime()));
        }
        stampDefinition.setBalanceDueInHour(createRequest.getBalancesDueInHour());

        if(createRequest.getProdAvailTimeSlot() != null) {
			String json = HhsUtils.objectToJsonString(createRequest.getProdAvailTimeSlot());
			LOGGER.debug("TimeSlotJson: {}", json);
			stampDefinition.setAssociatedProdAvailTimeslot(json);
		}
        if(createRequest.getRemindCustomerTimeslotVo()!=null){
            String json = HhsUtils.objectToJsonString(createRequest.getRemindCustomerTimeslotVo());
            LOGGER.debug("RemindCustomerTimeslot Json :{}",json);
            stampDefinition.setRemindCustomerTimeslot(json);
        }
        if(createRequest.getStampRedeemablelTimeslotVo() !=null){
            String josn = HhsUtils.objectToJsonString(createRequest.getStampRedeemablelTimeslotVo());
            LOGGER.debug("StampRedeemablelTimeslot  Json :{}",josn);
            stampDefinition.setStampRedeemablelTimeslot(josn);
        }
       
        stampDefinition.setProductManagerId(createRequest.getProductManagerId());
        stampDefinition.setRuleRestrict(createRequest.getRuleRestrict());

        inventory.setInventoryLevel(createRequest.getInventoryLevel());
        inventory.setInitInventoryLevel(createRequest.getInitInventoryLevel());
        //departmentService.deleteStampDefinitionById(stampDefinition.getId());       
        this.inventoryUnitRepository.save(inventory);
        LOGGER.info("=============== time ================="+stampDefinition.getRemindCustomerDate());
        LOGGER.info("=============== id======stampDefinitionId==========="+stampDefinition.getId());
        stampDefinition = this.stampDefinitionRepository.save(stampDefinition);
        boundMercent.setStampDefinition(stampDefinition);
        boundMercent.setEffectDate(stampDefinition.getStampOnsaleStartDate());
        boundMercent.setExpiryDate(stampDefinition.getStampOnsaleEndDate());
        this.stampDefinitionProductBindingRepository.save(boundMercent);
        int stampHash = Math.abs(stampDefinitionId.hashCode());
        LOGGER.info("=============== LogstampHash ================="+stampHash);
        if(saveOrUpdate==1){          
            comLogClientService.insert(
                    COM_LOG_OBJECT_TYPE.PRE_SALE, new Long(stampHash),  new Long(stampHash),
                    createRequest.getOperationName(), "创建预售券  预售券ID:" + stampDefinition.getStampNo()+","+logs(createRequest,null),
                    COM_LOG_LOG_TYPE.ADD_PRE_SALE.name(),
                    "新增预售券", null);
        }else{
            
            comLogClientService.insert(
                    COM_LOG_OBJECT_TYPE.PRE_SALE, new Long(stampHash),  new Long(stampHash),
                    createRequest.getOperationName(), "修改预售券  预售券ID:" + stampDefinition.getStampNo()+",hashCode"+stampHash,
                    COM_LOG_LOG_TYPE.CHANGE_PRE_SALE.name(),
                    "修改预售券基本信息", null);
            
        }
        if(createRequest.getProductId()!=null){
            this.vmProductRepository.save(vmProductBingding);
        }
       
        LOGGER.info("=============== Save Stamp creation successfully ================="); 
        return stampDefinitionId;
        //this.stampDefinitionRepositorySlave.save(stampDefinition);
    }

    @Override
    @Transactional("slaveTransactionManager")   
    public StampDetailsVo getStampById(String id) {       
    	StampDefinitionEntity stampDefinition = this.stampDefinitionRepositorySlave.getById(id);
    	if(stampDefinition == null) {
    		LOGGER.warn("can not find stamp definition by id {}", id);
    		return null;
    	}
    	return getStampDetails(stampDefinition);
    }

    @Override
    @Transactional("slaveTransactionManager")
    public List<StampDetailsVo> getStampDefinitionByName(String name) {
        List<StampDefinitionEntity> stampDefinitions = this.stampDefinitionRepositorySlave.getByName(name);
        return getStampDetails(stampDefinitions);
    }

    @Override
    public List<StampDetailsVo> getStampDetails(List<StampDefinitionEntity> stampDefinitions) {
    	List<StampDetailsVo> stampDetails = null;
    	if(stampDefinitions != null) {
        	stampDetails = new ArrayList<StampDetailsVo>();
        	for (StampDefinitionEntity stampDefinition : stampDefinitions) {
        		stampDetails.add(getStampDetails(stampDefinition));
        	}
        }
        return stampDetails;
    }
    
    @Override
    @Transactional("slaveTransactionManager")
    public StampDetailsVo getStampDetails(StampDefinitionEntity stampDefinition) {
    	StampDetailsVo details = new StampDetailsVo();
    	
    	BeanUtilsBean beanUtilsBean = new BeanUtilsBean();
    	beanUtilsBean.getConvertUtils().register(new BigDecimalConverter(), BigDecimal.class);
    	try {
			beanUtilsBean.copyProperties(details, stampDefinition);
			details.setGoodsId(stampDefinition.getGoodsId());
	        details.setGoodsName(stampDefinition.getGoodsName());
		} catch (Exception e) {
			LOGGER.error("Exception encountered when copy properties", e);
		} 
    	moenyDivide100(details);
    	if(StringUtils.isNotBlank(stampDefinition.getAssociatedProdAvailTimeslot())) {
			StampAssociatedProdAvailTimeSlotVo prodAvailTimeSlot = (StampAssociatedProdAvailTimeSlotVo) HhsUtils.jsonToObject(stampDefinition.getAssociatedProdAvailTimeslot(), StampAssociatedProdAvailTimeSlotVo.class);
			LOGGER.debug("TimeSlotObject: {}", prodAvailTimeSlot);
			details.setProdAvailTimeSlot(prodAvailTimeSlot);
    	}
    	if(StringUtils.isNotBlank(stampDefinition.getRemindCustomerTimeslot())){
    	    StampAssociatedProdAvailTimeSlotVo prodAvailTimeSlot = (StampAssociatedProdAvailTimeSlotVo) HhsUtils.jsonToObject(stampDefinition.getRemindCustomerTimeslot(), StampAssociatedProdAvailTimeSlotVo.class);
    	    LOGGER.debug("remindCustomerTimeslot: {}", prodAvailTimeSlot);
            details.setRemindCustomerTimeslotVo(prodAvailTimeSlot);
    	}else{
    	    //以前数据修复
    	    if(stampDefinition.getRemindCustomerDate() !=null){
    	        StampAssociatedProdAvailTimeSlotVo prodAvailTimeSlot = new StampAssociatedProdAvailTimeSlotVo();
    	        prodAvailTimeSlot.setStartDate(DateUtil.SimpleFormatDateToString(stampDefinition.getRemindCustomerDate()));
    	        prodAvailTimeSlot.setEndDate(DateUtil.SimpleFormatDateToString(stampDefinition.getRemindCustomerDate()));
    	        Calendar calendar = Calendar.getInstance();  
    	        // 或者用 Date 来初始化 Calendar 对象  
    	        calendar.setTime(stampDefinition.getRemindCustomerDate());  
    	        int weekDay  = calendar.get(Calendar.DAY_OF_WEEK);
    	        List<String> weekDays = new ArrayList<String>();
    	        weekDays.add(String.valueOf(weekDay));
    	    }
    	}
    	if(StringUtils.isNotBlank(stampDefinition.getStampRedeemablelTimeslot())){
            StampAssociatedProdAvailTimeSlotVo prodAvailTimeSlot = (StampAssociatedProdAvailTimeSlotVo) HhsUtils.jsonToObject(stampDefinition.getStampRedeemablelTimeslot(), StampAssociatedProdAvailTimeSlotVo.class);
            LOGGER.debug("stampRedeemablelTimeslot: {}", prodAvailTimeSlot);
            details.setStampRedeemablelTimeslotVo(prodAvailTimeSlot);
            
        }else{
            //以前数据修复
            StampAssociatedProdAvailTimeSlotVo prodAvailTimeSlot = new StampAssociatedProdAvailTimeSlotVo();
            if(stampDefinition.getStampRedeemableStartDate() !=null){
                prodAvailTimeSlot.setStartDate(DateUtil.SimpleFormatDateToString(stampDefinition.getStampRedeemableStartDate()));
            }
            if(stampDefinition.getStampRedeemableEndDate() !=null){
                prodAvailTimeSlot.setEndDate(DateUtil.SimpleFormatDateToString(stampDefinition.getStampRedeemableEndDate()));
            }
        }
    	details.setStampOnsaleDuration(new StampDuration(stampDefinition.getStampOnsaleStartDate(), stampDefinition.getStampOnsaleEndDate()));
    	details.setStampRedeemableDuration(new StampDuration(stampDefinition.getStampRedeemableStartDate(), stampDefinition.getStampRedeemableEndDate()));
    	
    	PresaleStampDefinitionProductBindingEntity productBinding = stampDefinition.getProductBinding();
    	if(productBinding == null) {
    		productBinding = this.stampDefinitionProductBindingRepositorySlave.getByStampDefinition(stampDefinition);
    	}
    	if(productBinding != null) {
    		BoundMerchantVo boundMerchant = new BoundMerchantVo();
    		try {
				BeanUtils.copyProperties(boundMerchant, productBinding);
			} catch (Exception e) {
				LOGGER.error("Exception encountered when copy properties", e);
			}
    		boundMerchant.setEffectDuration(new StampDuration(productBinding.getEffectDate(), productBinding.getExpiryDate()));
    		ResultHandleT<BizCategory> bT = categoryClientService.findCategoryById(new Long(boundMerchant.getCategoryId()==null?"1":boundMerchant.getCategoryId()));
               if(bT.isSuccess()){
                   boundMerchant.setCategoryName(bT.getReturnContent().getCategoryName());
               }
    		details.setBoundMerchant(boundMerchant);
    	}
    	
    	PresaleInventoryUnitEntity inventory = this.inventoryUnitRepositorySlave.getByStampDefinitionId(stampDefinition.getId());
    	if(inventory != null) {
    		details.setInventoryLevel(inventory.getInventoryLevel());
    		details.setInitInventoryLevel(inventory.getInitInventoryLevel());
    	}
    	List<PresaleStampDefinitionGoodsBindingEntity> goodsBindings = goodsBindingRepositorySlave.getByStampDefinition(stampDefinition);
    	details.setStampGoods(getStampGoods(goodsBindings));
    	
    	Date current = DateUtil.stringToDate(DateUtil.SimpleFormatDateToString(new Date()), DateUtil.SIMPLE_DATE_FORMAT);
		boolean canSale = details.getInventoryLevel() > 0
				&& details.getStampOnsaleDuration() != null
				&& details.getStampOnsaleDuration().getStartDate().compareTo(current) <= 0
				&& details.getStampOnsaleDuration().getEndDate().compareTo(current) >= 0;
		details.setCanSale(canSale);
		PresaleStampDefinitionProductRelationEntity relation =relationSlave.getByStampDefinitionId(details.getId());
		if(relation != null){
		    StampGoodsRelationVo stampRelation = new StampGoodsRelationVo();
	        stampRelation.setGoodsId(relation.getGoodsId());
	        stampRelation.setProductBranchId(relation.getProductBranchId());
	        stampRelation.setProductId(relation.getProductId());
	        details.setStampGoodsRelation(stampRelation);
		}
	
    	
        return details;
    }
    
    @Override
    @Transactional("slaveTransactionManager")
    public List<StampDetailsVo> queryStampList(StampRequest stamp, int startRow, int rowNum) {
        List<StampDetailsVo> stampDetails = new ArrayList<StampDetailsVo>();
        List<StampDefinitionEntity> stampes = new ArrayList<StampDefinitionEntity>();       
        List<String>  stampIds = stampDefinitionRepositorySlave.findByFileds(stamp, startRow, rowNum);
        if(CollectionUtils.isNotEmpty(stampIds)){
            for(String id:stampIds){
                stampes.add(stampDefinitionRepositorySlave.getById(id));
            }
        }
        for(StampDefinitionEntity stampd : stampes){
            stampDetails.add(getStampDetails(stampd));
        }
        return stampDetails;
    }

    @Override
    @Transactional("slaveTransactionManager")
    public long countQueryStampList(StampRequest stamp) {
        return stampDefinitionRepositorySlave.countFindByFileds(stamp);
    }

    //创建虚拟产品和规格
	private void createStampProduct(StampCreationRequest request, PresaleStampDefinitionProductBindingEntity boundMercent, String productId,
			PresaleStampDefinitionProductRelationEntity vmProductBingding,String managerId) throws RuntimeException {

		if (StringUtils.isNotEmpty(productId)) {
			try {
				ResultHandleT<ProdProduct> proProductT = productClientService.getProdProductBy(new Long(productId));
				if (proProductT.isSuccess()) {
					ProdProduct product = proProductT.getReturnContent();
					
					Long productCategoryId = product.getBizCategoryId();
					Long subCategoryId = product.getSubCategoryId();
					String productName = product.getProductName();
					product.setBizCategoryId(99L);
					product.setSaleFlag("Y");
					product.setCancelFlag("Y");
					product.setProductName(request.getStampName());
					product.setProductId(null); // 来源
					if(StringUtils.isNotEmpty(managerId)){
					    product.setManagerId(new Long(managerId));
					}
					ResultHandleT<Long> productIdT = productClientService.addProdProduct(product);
					Long id =productIdT.getReturnContent();
			        if (id==null || id==0) {
			            throw new RuntimeException("create vm product  failed :" + id);
			        }
			        
					LOGGER.info("[productId==========={}", productIdT.getReturnContent());
					vmProductBingding.setProductId(productIdT.getReturnContent() + "");
					vmProductBingding.setCategoryId("99");
					// 初始化 bingding
					boundMercent.setCategoryId(String.valueOf(productCategoryId));
					boundMercent.setSubCategoryId(subCategoryId==null?"":String.valueOf(subCategoryId));
					boundMercent.setCreateDate(new Timestamp(System.currentTimeMillis()));
					boundMercent.setProductId(productId);
					boundMercent.setProductName(productName);
					boundMercent.setBuCode(product.getBu());
					// vmProductBingding.setCreateDate();
					// 规格
					ResultHandleT<BizCategory> bizCategoryT = categoryClientService.findCategoryById(product.getBizCategoryId());
					if (bizCategoryT.isSuccess()) {

						BizCategory bizCategory = bizCategoryT.getReturnContent();
						
						if (null != bizCategory) {
							Long majorBranchId = 0L;							
							ResultHandleT<List<ProdProductBranch>> productBranches = prodProductBranchClientService.findProdProductBranchByProductId(new Long(productId));
							ProdProductBranch productBranch = null;
							if(productBranches.isSuccess()){
                                List<ProdProductBranch> branch = productBranches.getReturnContent();
                                if(CollectionUtils.isNotEmpty(branch)){
                                    majorBranchId = branch.get(0).getBranchId();
                                    productBranch = branch.get(0);
                                    productBranch.setProductId(id);
                                    productBranch.setBranchName("预售"+productBranch.getBranchName());
                                    productBranch.setCancelFlag("Y");
                                    productBranch.setRecommendLevel(3L);
                                    productBranch.setSaleFlag("Y");
                                    productBranch.setMaxVisitor(10L);
                                }
                            }else{
    							if (BizEnum.BIZ_CATEGORY_TYPE.category_route_group.name().equalsIgnoreCase(
    							        bizCategory.getCategoryCode())) {// 跟团游
    								majorBranchId = 18L;
    							} else if (BizEnum.BIZ_CATEGORY_TYPE.category_route_freedom.name().equalsIgnoreCase(
    									bizCategory.getCategoryCode())) {// 自由行
    								majorBranchId = 14L;
    							} else if (BizEnum.BIZ_CATEGORY_TYPE.category_route_aero_hotel.name().equalsIgnoreCase(
    									bizCategory.getCategoryCode())) {// 自由行
    								majorBranchId = 29L;
    							} else if (BizEnum.BIZ_CATEGORY_TYPE.category_route_local.name().equalsIgnoreCase(
    									bizCategory.getCategoryCode())) {// 当地游
    								majorBranchId = 22L;
    							} else if (BizEnum.BIZ_CATEGORY_TYPE.category_route_hotelcomb.name().equalsIgnoreCase(
    									bizCategory.getCategoryCode())) {// 酒店套餐
    								majorBranchId = 24L;
    							}else{
    							    majorBranchId = 14L ;
    							}
    							
    							productBranch = assemprodProductBranch(productIdT.getReturnContent(),
                                        majorBranchId, bizCategory.getCategoryCode());
                            }
							 
							ResultHandleT<Long> branchIdT = prodProductBranchClientService
									.addProdProductBranch(productBranch);
							Long branchId =productIdT.getReturnContent();
		                    if (branchId==null || branchId==0) {
		                        throw new RuntimeException("create vm branchId  failed :" + id);
		                    }						
							if (branchIdT.isSuccess()) {
								LOGGER.info("branch====================={}", branchIdT.getReturnContent());
								vmProductBingding.setProductBranchId(branchIdT.getReturnContent() + "");
							}
							// 模拟商品
							ResultHandleT<List<SuppGoods>> suppGoods = suppGoodsClientService
									.findSuppGoodsByProductId(new Long(productId));
							if (suppGoods.isSuccess()) {
							    if(CollectionUtils.isNotEmpty(suppGoods.getReturnContent())){						   
    								for (SuppGoods goods : suppGoods.getReturnContent()) {
    									goods.setProductId(new Long(vmProductBingding.getProductId()));
    									//goods.setBranchId(new Long(vmProductBingding.getProductBranchId()));
    									goods.setProductBranchId(new Long(vmProductBingding.getProductBranchId()));
    									goods.setCancelFlag("Y");
    									goods.setOnlineFlag("Y");
    									goods.setCategoryId(99L);
    									goods.setMaxQuantity(999999L);
    									goods.setMinQuantity(1L);
    									goods.setPayTarget("PREPAID");
    									goods.setGoodsName(request.getStampName());
    									goods.setContractId(HhsConstants.CONTRACT_ID);
    									goods.setSupplierId(HhsConstants.SUPPLIER_ID);
    									if(StringUtils.isNotEmpty(managerId)){
    									    goods.setManagerId(new Long(managerId));
    									}
    									ResultHandleT<Long> goodsIdT = suppGoodsClientService.addSuppGoods(goods);
    									if (goodsIdT.isSuccess()) {
    									    Long goodsId =productIdT.getReturnContent();
    			                            if (goodsId==null || goodsId==0) {
    			                                throw new RuntimeException("create vm goodsId  failed :" + id);
    			                            }
    										LOGGER.info("goodIds===================={}", goodsIdT.getReturnContent());
    										vmProductBingding.setGoodsId(goodsIdT.getReturnContent() + "");
    										return;
    									}
    								}
							    }else{
							      String goodsId =  createSuppGoods(vmProductBingding.getProductId(),vmProductBingding.getProductBranchId(),managerId,product.getBu(),product.getAttributionId(),request.getStampName());
							      if(StringUtils.isNotEmpty(goodsId)){
							          LOGGER.info("goodIds===================={}", goodsId);
							          vmProductBingding.setGoodsId(goodsId);
							          
							      }else{
                                          throw new RuntimeException("create vm goodsId  failed :" + goodsId);
							      }
							     
							    }
							}

						}
					}

				}
			} catch (Exception e) {
				e.printStackTrace();
				LOGGER.error("createStampProduct   error" + e.getMessage());
			}

		}
	}

	/**
	 * 组装产品规格Bean
	 * 
	 * @param productId
	 * @param majorBranchId 主规格Id
	 * @param categoryCode
	 * @return
	 */
	private ProdProductBranch assemprodProductBranch(Long productId, Long majorBranchId, String categoryCode) {
		// 主规格
		ProdProductBranch productBranch = new ProdProductBranch();
		BizBranch bizBranch = new BizBranch();
		bizBranch.setBranchId(majorBranchId);
		productBranch.setBizBranch(bizBranch);
		productBranch.setProductId(productId);
		if (BizEnum.BIZ_CATEGORY_TYPE.category_route_hotelcomb.name().equalsIgnoreCase(categoryCode)) {
			productBranch.setBranchName("套餐");
		} else {
			productBranch.setBranchName("成人/儿童/房差");
		}
		productBranch.setBranchName("预售");
		productBranch.setCancelFlag("Y");
		productBranch.setRecommendLevel(3L);
		productBranch.setSaleFlag("Y");
		productBranch.setMaxVisitor(10L);
		return productBranch;
	}



  
    
    private List<StampGoodsVo> getStampGoods(List<PresaleStampDefinitionGoodsBindingEntity> goodsBindings) {
    	List<StampGoodsVo> list = new ArrayList<StampGoodsVo>();
    	if(goodsBindings != null) {
    		for(PresaleStampDefinitionGoodsBindingEntity entity : goodsBindings) {
    			list.add(new StampGoodsVo(entity.getId(), entity.getGoodsId(), entity.getGoodsName(), entity.getBranchFlag(),entity.getCategoryId()));
    		}
    	}
    	return list;
    }

    @Override
	@Transactional("transactionManager")
	public void updateStampDefinition(StampCreationRequest createRequest) {
        moneyTo100(createRequest);
		String stampId = createRequest.getId();
		if (StringUtils.isBlank(stampId)) {
			throw new IllegalArgumentException("stamp id is null.");
		}
		//departmentService.deleteStampDefinitionById(stampId);
		StampDefinitionEntity stampDefinition = this.stampDefinitionRepository.findOne(stampId);
		if(createRequest.getBoundMerchant() != null ){
		    createRequest.setProductManagerId(createRequest.getBoundMerchant().getManagerId());
		    createRequest.setProductManagerName(createRequest.getBoundMerchant().getManagerName());
		}
		
	
		String logs = logs(createRequest,stampDefinition);
       // StampDetailsVo vo = getStampDetails(stampDefinition);
		if (stampDefinition == null) {
			throw new RuntimeException("StampDefinitionEntity not found, id:" + stampId);
		}

		// 去除不可修改的字段
		long count = productOrderService.countOrderStampOrderItemByStampDefinitionId(stampId);
		if (count == 0) {
			if (null != createRequest.getRemarks()) {
				stampDefinition.setRemarks(createRequest.getRemarks());
			}
			if(createRequest.getProdAvailTimeSlot() != null) {
				String json = HhsUtils.objectToJsonString(createRequest.getProdAvailTimeSlot());
				LOGGER.debug("TimeSlotJson: {}", json);
				stampDefinition.setAssociatedProdAvailTimeslot(json);
			}
		}
		
		stampDefinition.setBuyMax(createRequest.getBuyMax());
		stampDefinition.setName(createRequest.getStampName());
		stampDefinition.setSalePrice(new BigDecimal(createRequest.getSalePrice()==null?0:createRequest.getSalePrice()));
		stampDefinition.setPrintPrice(new BigDecimal(createRequest.getPrintPrice()==null?0:createRequest.getPrintPrice()));
		stampDefinition.setSetPrice(new BigDecimal(createRequest.getSetPrice()==null?0:createRequest.getSetPrice()));
		stampDefinition.setPrintPrice(new BigDecimal(createRequest.getPrintPrice()==null?0:createRequest.getPrintPrice()));
		stampDefinition.setDownPayment(new BigDecimal(createRequest.getDownpayment()==null?0:createRequest.getDownpayment()));
		stampDefinition.setOperationsAmount(new BigDecimal(createRequest.getOperationsAmount()==null?0:createRequest.getOperationsAmount()));
		stampDefinition.setRemindCustomerDate(null);
		 stampDefinition.setStampOrder(BigDecimal.valueOf(createRequest.getStampOrder()));
		if (createRequest.getStampOnsaleDuration() != null) {
			stampDefinition.setStampOnsaleStartDate(new Timestamp(createRequest.getStampOnsaleDuration().getStartDate()
					.getTime()));
			stampDefinition.setStampOnsaleEndDate(new Timestamp(createRequest.getStampOnsaleDuration().getEndDate()
					.getTime()));
		}
		if (createRequest.getStampRedeemableDuration() != null) {
			stampDefinition.setStampRedeemableStartDate(new Timestamp(createRequest.getStampRedeemableDuration()
					.getStartDate().getTime()));
			stampDefinition.setStampRedeemableEndDate(new Timestamp(createRequest.getStampRedeemableDuration()
					.getEndDate().getTime()));
		}
		
		  if(createRequest.getRemindCustomerTimeslotVo()!=null){
	            String json = HhsUtils.objectToJsonString(createRequest.getRemindCustomerTimeslotVo());
	            LOGGER.debug("RemindCustomerTimeslot Json :{}",json);
	            stampDefinition.setRemindCustomerTimeslot(json);
	        }
	        if(createRequest.getStampRedeemablelTimeslotVo() !=null){
	            String josn = HhsUtils.objectToJsonString(createRequest.getStampRedeemablelTimeslotVo());
	            LOGGER.debug("StampRedeemablelTimeslot  Json :{}",josn);
	            stampDefinition.setStampRedeemablelTimeslot(josn);
	        }
		
		stampDefinition.setBalanceDueInHour(createRequest.getBalancesDueInHour());

		stampDefinition.setRuleRestrict(createRequest.getRuleRestrict());
		

		PresaleInventoryUnitEntity inventory = this.inventoryUnitRepository.getByStampDefinitionId(stampId);
		if(inventory == null) {
			throw new RuntimeException("PresaleInventoryUnitEntity not found, stampId:" + stampId);
		}
		
		if (createRequest.getInventoryLevel() != inventory.getInventoryLevel()) {
		    int duration = createRequest.getInventoryLevel() - inventory.getInventoryLevel();		    
			inventory.setInventoryLevel(createRequest.getInventoryLevel());
			inventory.setInitInventoryLevel(inventory.getInitInventoryLevel()+duration);
			this.inventoryUnitRepository.save(inventory);
		}
		PresaleStampDefinitionProductBindingEntity productBinding = stampDefinition.getProductBinding();
		if (productBinding == null) {
			productBinding = this.stampDefinitionProductBindingRepository.getByStampDefinition(stampDefinition);
		}
		productBinding.setManagerId(createRequest.getBoundMerchant().getManagerId());
		productBinding.setManagerName(createRequest.getBoundMerchant().getManagerName());
		int stampHash = Math.abs(stampDefinition.getId().hashCode());
		  comLogClientService.insert(
                  COM_LOG_OBJECT_TYPE.PRE_SALE, new Long(stampHash),  new Long(stampHash),
                  createRequest.getOperationName(), "修改预售券  预售券ID:" + stampDefinition.getStampNo()+","+logs,
                  COM_LOG_LOG_TYPE.CHANGE_PRE_SALE.name(),
                  "修改预售券基本信息", null);
		this.stampDefinitionProductBindingRepository.save(productBinding);

		this.stampDefinitionRepository.save(stampDefinition);
		departmentService.deleteStampDefinitionById(stampDefinition.getId());
		PresaleStampDefinitionProductBindingEntity product = stampDefinition.getProductBinding();
        if(product != null){
            departmentService.deleteProductIsExchange(product.getProductId(), product.getDepartId());
            departmentService.deleteProductStampsDetails(product.getProductId(), product.getDepartId());
            departmentService.deleteGetStampProductByProductIds(product.getProductId());
        }
	}
	
	@Override
	@Transactional("transactionManager")
	public void updateStampActivityStatus(String id, String activityStatus,String operationName) {
	    
	    StampDefinitionEntity stampDefinition = this.stampDefinitionRepositorySlave.getById(id);
        if(stampDefinition == null)
           throw new StampBizException(StampErrorCodes.E_ENTITY_2001, "StampDefinitionEntity not found,id="+id);
        StampDetailsVo vo = getStampDetails(stampDefinition);
	    int stampHash = Math.abs(id.hashCode());
	    String logs ="";
	    if(activityStatus.equals("Y")){
	        logs ="有效";
	    }else{
	        logs ="无效";
	    }
	    comLogClientService.insert(
                COM_LOG_OBJECT_TYPE.PRE_SALE, new Long(stampHash),  new Long(stampHash),
                operationName, "修改预售券状态  预售券ID:" + stampDefinition.getStampNo()+",修改状态为"+logs,
                COM_LOG_LOG_TYPE.CHANGE_PRE_SALE.name(),
                "修改预售券状态", null);
		this.stampDefinitionRepository.updateStampActivityStatus(id, activityStatus);
		departmentService.deleteStampDefinitionById(stampDefinition.getId());
        PresaleStampDefinitionProductBindingEntity product = stampDefinition.getProductBinding();
        if(product != null){
            departmentService.deleteProductIsExchange(product.getProductId(), product.getDepartId());
            departmentService.deleteProductStampsDetails(product.getProductId(), product.getDepartId());
            departmentService.deleteGetStampProductByProductIds(product.getProductId());
        }
	}
	
	@Override
	@Transactional("transactionManager")
	public void deleteStampDefinition(String id) {

		if (StringUtils.isBlank(id)) {
			throw new UntrustedIssuerException("id is null");
		}
		
		Long count = productOrderService.countOrderStampOrderItemByStampDefinitionId(id);
		if(count != null && count > 0) {
			throw new UntrustedIssuerException("have orders, can not del.");
		}

		this.stampDefinitionRepository.delete(id);
	}
	
	private String stampNo(String maxStamp){
	    String stampNo ="YS";
	    if(maxStamp==null){
              stampNo =stampNo+1;
        }else{
             stampNo = stampNo + (new Integer(maxStamp.substring(2))+1);           
        }
	    
	    return stampNo;
	}

    @Override
    @Transactional("slaveTransactionManager")
	public Boolean goodsIsPreSaleByTime(String productId, String goodsId, Timestamp date) {
		List<StampDefinitionEntity> stamps = this.stampDefinitionRepositorySlave.getByProductIdAndStartSaleDateAndEndSaleDate(productId, date);
		if (CollectionUtils.isEmpty(stamps)) {
			return false;
		}
		for (StampDefinitionEntity stamp : stamps) {
			List<PresaleStampDefinitionGoodsBindingEntity> goods = this.goodsBindingRepositorySlave.getByStampDefinition(stamp);
			if (CollectionUtils.isEmpty(goods)) {
				return false;
			}
			for (PresaleStampDefinitionGoodsBindingEntity good : goods) {
				if (good.getGoodsId().equals(goodsId)) {
					return true;
				}
			}
		}
		return false;
	}

	private String createSuppGoods(String productId, String branchId,String managerId,String bu,Long attributionId,String goodsName){
       SuppGoods goods = new SuppGoods();      
       goods.setContractId(HhsConstants.CONTRACT_ID);
       goods.setSupplierId(HhsConstants.SUPPLIER_ID);
       goods.setDistributorIds("3");
       goods.setProductId(new Long(productId));
       goods.setProductBranchId(new Long(branchId));
       goods.setGoodsName(goodsName);
       goods.setPayTarget("PREPAID");
       goods.setLvmamaFlag("Y");
       goods.setDistributeFlag("Y");
       goods.setPackageFlag("N");
       goods.setFaxFlag("N");
       goods.setGoodsType("NOTICETYPE_DISPLAY");
       goods.setNoticeType("SMS");
       goods.setFiliale("SH_FILIALE");
       goods.setMinQuantity(1L);
       goods.setMaxQuantity(9999l);
       goods.setBranchId(new Long(branchId));      
       goods.setCurrencyType("CNY");
       goods.setPriceType("MULTIPLE_PRICE");
       goods.setCreateTime(new Date());
       goods.setCreateUser("预售后台");
       goods.setApiFlag("N");
       goods.setCategoryId(99l);
       goods.setBu(bu);
       goods.setBuyoutFlag("N");
       goods.setCancelFlag("Y");
       goods.setOnlineFlag("Y");
       if(StringUtils.isNotEmpty(managerId)){
           goods.setManagerId(new Long(managerId));
           goods.setContentManagerId(new Long(managerId));
       }
       LOGGER.info("=========="+goods.getBranchId()+"-------------------"+goods.getProductBranchId());
       goods.setAttributionId(attributionId);
       ResultHandleT<Long> goodsIdT = suppGoodsClientService.addSuppGoods(goods);
       if (goodsIdT.isSuccess()) {
           Long goodsId =goodsIdT.getReturnContent();
           if (goodsId==null || goodsId==0) {
               throw new RuntimeException("create vm goodsId  failed :" + goodsId);
           }
           return String.valueOf(goodsId);
       
       }
       
       return null;
   }
   
   private void moneyTo100(StampCreationRequest createRequest){
       if(  createRequest.getDownpayment()!=null &&0.0 != createRequest.getDownpayment()){
           createRequest.setDownpayment(createRequest.getDownpayment()*100);
       }
       if(createRequest.getPrintPrice()!=null && 0.0 != createRequest.getPrintPrice()){
           createRequest.setPrintPrice(createRequest.getPrintPrice()*100);
       }
       if( createRequest.getSalePrice()!=null && 0.0 != createRequest.getSalePrice()){
           createRequest.setSalePrice(createRequest.getSalePrice()*100);
       }
       if(createRequest.getSetPrice() !=null && 0.0 !=createRequest.getSetPrice()){
           createRequest.setSetPrice(createRequest.getSetPrice()*100);
       }
       if(createRequest.getOperationsAmount() !=null && 0.0 !=createRequest.getOperationsAmount()){
           createRequest.setOperationsAmount(createRequest.getOperationsAmount()*100);
       }
       
   }
   
   private void moenyDivide100(StampDetailsVo detail){
       if(detail.getDownPayment() !=null && 0.0!=detail.getDownPayment()){
           detail.setDownPayment(detail.getDownPayment()/100);
       }
       if( detail.getSalePrice() !=null &&0.0 != detail.getSalePrice()){
           detail.setSalePrice(detail.getSalePrice()/100);
       }
       if(detail.getPrintPrice()!=null && 0.0 != detail.getPrintPrice()){
           detail.setPrintPrice(detail.getPrintPrice()/100);
       }
       if(detail.getOperationsAmount()!=null&& 0 != detail.getOperationsAmount().intValue()){
           detail.setOperationsAmount(detail.getOperationsAmount()/100);
       }
   }
   
   private String logs(StampCreationRequest request,StampDefinitionEntity stampDefinition){
       StringBuilder logBuf = new StringBuilder();
       if(null ==stampDefinition){
          
           if(StringUtils.isNotEmpty(request.getStampName())){
               logBuf.append("名称:").append(request.getStampName()).append(" ");
           }
           logBuf.append("销售价格").append(request.getSalePrice().intValue()/100).append("元").append(" ");
           logBuf.append("驴妈妈价格").append(request.getPrintPrice().intValue()/100).append("元").append(" ");
           logBuf.append("运营投放金额").append(request.getOperationsAmount().intValue()/100).append("元").append(" ");
           logBuf.append("初始化库存").append(request.getInitInventoryLevel()).append(" ");
           logBuf.append("剩余库存").append(request.getInventoryLevel()).append(" ");
           if(request.getBuyMax()!=null && request.getBuyMax() !=0){
               logBuf.append("最大购买份数").append(request.getBuyMax()).append(" ");
           }
           logBuf.append("尾款最后支付时间小时数"+request.getBalancesDueInHour()).append(" ");
           logBuf.append("预售券销售开始时间").append(DateUtil.SimpleFormatDateToString(request.getStampOnsaleDuration().getStartDate())).append(" ");
           logBuf.append("预售券销售结束时间").append(DateUtil.SimpleFormatDateToString(request.getStampOnsaleDuration().getEndDate())).append(" ");
           logBuf.append("预售券兑换开始时间").append(request.getStampRedeemablelTimeslotVo().getStartDate()).append(" ");
           logBuf.append("预售券兑换结束时间").append(request.getStampRedeemablelTimeslotVo().getEndDate()).append(" ");
          // logBuf.append("催兑换时间").append(DateUtil.SimpleFormatDateToString(request.getRemindCustomerDate())).append(" ");
           logBuf.append("催兑换开始时间").append(request.getRemindCustomerTimeslotVo().getStartDate());
           logBuf.append("催兑换结束时间").append(request.getRemindCustomerTimeslotVo().getEndDate());
           logBuf.append("费用包含").append(request.getRemarks()).append(" ");
           logBuf.append("产品经理").append(request.getProductManagerName()).append(" ");
           logBuf.append("产品Id").append(request.getProductId()).append(" ");
           
       }else{
           StampDetailsVo vo= getStampDetails(stampDefinition);
           if(!request.getStampName().equals(vo.getName())){
               logBuf.append("名称修改为：").append(request.getStampName()).append(" ");
           }
           if(request.getSalePrice().intValue()/100 != vo.getSalePrice().intValue()){
               logBuf.append("销售价格修改为").append(request.getSalePrice().intValue()/100).append("元").append(" ");
           }
           if(request.getPrintPrice().intValue()/100 != vo.getPrintPrice().intValue()){
               logBuf.append("驴妈妈价格修改为").append(request.getPrintPrice().intValue()/100).append("元").append(" ");
           }
           if(stampDefinition.getOperationsAmount()==null){
                   logBuf.append("运营投放金额修改为").append(request.getOperationsAmount().intValue()/100).append("元").append(" ");
               
           }else{
               if(request.getOperationsAmount().intValue() !=stampDefinition.getOperationsAmount().intValue()){
                   logBuf.append("运营投放金额修改为").append(request.getOperationsAmount().intValue()/100).append("元").append(" ");
               }
           }
         
           if(request.getInitInventoryLevel() != vo.getInitInventoryLevel()){
               logBuf.append("库存修改为").append(request.getInitInventoryLevel()).append(" ");
           }
           if(request.getBuyMax() != null && vo.getBuyMax()==0){
               logBuf.append("库存修改为").append(request.getBuyMax());
           }
           if(request.getBuyMax() != null && vo.getBuyMax()!=0){
               if(request.getBuyMax().intValue() != vo.getBuyMax()){
                   logBuf.append("最大购买份数修改为").append(request.getBuyMax().intValue());
               }
           }
           if(request.getBalancesDueInHour() != vo.getBalanceDueInHour()){
               logBuf.append("尾款最后支付时间小时数修改为").append(request.getBalancesDueInHour()).append(" ");
           }
           if(request.getStampOnsaleDuration().getStartDate().getTime() !=vo.getStampOnsaleDuration().getStartDate().getTime()){
               logBuf.append("预售券销售开始时间修改为").append(DateUtil.SimpleFormatDateToString(request.getStampOnsaleDuration().getStartDate())).append(" ");
           }
           if(request.getStampOnsaleDuration().getEndDate().getTime() !=vo.getStampOnsaleDuration().getEndDate().getTime()){
               logBuf.append("预售券销售结束时间修改为").append(DateUtil.SimpleFormatDateToString(request.getStampOnsaleDuration().getEndDate())).append(" ");
           }
           if(!request.getStampRedeemablelTimeslotVo().getStartDate().equals(vo.getStampRedeemablelTimeslotVo().getStartDate())){
               logBuf.append("预售券兑换开始时间修改为").append(request.getStampRedeemablelTimeslotVo().getStartDate()).append(" ");
           }
           if(!request.getStampRedeemablelTimeslotVo().getEndDate().equals(vo.getStampRedeemablelTimeslotVo().getEndDate())){
               logBuf.append("预售券兑换结束时间修改为").append(request.getStampRedeemablelTimeslotVo().getEndDate()).append(" ");
           }
           if(!request.getRemindCustomerTimeslotVo().getStartDate().equals(vo.getRemindCustomerTimeslotVo().getStartDate())){
               logBuf.append("催兑换开始时间").append(request.getRemindCustomerTimeslotVo().getStartDate()).append(" ");
           }
           if(!request.getRemindCustomerTimeslotVo().getEndDate().equals(vo.getRemindCustomerTimeslotVo().getEndDate())){
               logBuf.append("催兑换结束时间").append(request.getRemindCustomerTimeslotVo().getEndDate()).append(" ");
           }
        /*   if(!request.getRemarks().equals(vo.getRemarks())){
               logBuf.append("费用包含修改为").append(vo.getRemarks()).append(" ");
           }*/
           if(!request.getBoundMerchant().getManagerName().equals(vo.getBoundMerchant().getManagerName())){
               logBuf.append("产品经理修改为").append(request.getProductManagerName()).append(" ");
           }
       }
      
       
       return logBuf.toString();
   }   

}
