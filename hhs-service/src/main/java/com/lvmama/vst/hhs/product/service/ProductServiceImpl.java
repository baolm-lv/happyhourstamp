package com.lvmama.vst.hhs.product.service;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
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
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Sets;
import com.lvmama.vst.back.biz.po.BizCategory;
import com.lvmama.vst.back.biz.po.BizDict;
import com.lvmama.vst.back.client.biz.service.CategoryClientService;
import com.lvmama.vst.back.client.biz.service.DictClientService;
import com.lvmama.vst.back.client.goods.service.SuppGoodsClientService;
import com.lvmama.vst.back.client.goods.service.SuppGoodsTimePriceClientService;
import com.lvmama.vst.back.client.prod.service.ProdGroupDateClientService;
import com.lvmama.vst.back.client.prod.service.ProdProductAdditionClientService;
import com.lvmama.vst.back.client.prod.service.ProdProductClientService;
import com.lvmama.vst.back.client.prod.service.ProdProductPropClientService;
import com.lvmama.vst.back.client.prod.service.ProdTrafficClientService;
import com.lvmama.vst.back.goods.po.PresaleStampTimePrice;
import com.lvmama.vst.back.goods.po.SuppGoodsBaseTimePrice;
import com.lvmama.vst.back.goods.po.SuppGoodsTimePrice;
import com.lvmama.vst.back.prod.po.ProdGroupDate;
import com.lvmama.vst.back.prod.po.ProdProduct;
import com.lvmama.vst.back.prod.po.ProdProductAddtional;
import com.lvmama.vst.back.prod.po.ProdProductProp;
import com.lvmama.vst.comm.utils.DateUtil;
import com.lvmama.vst.comm.vo.ResultHandleT;
import com.lvmama.vst.hhs.common.utils.HhsConstants;
import com.lvmama.vst.hhs.common.utils.HhsUtils;
import com.lvmama.vst.hhs.model.admin.BizCategoryVo;
import com.lvmama.vst.hhs.model.admin.ChangeHotelVo;
import com.lvmama.vst.hhs.model.admin.StampAssociatedProdAvailTimeSlotVo;
import com.lvmama.vst.hhs.model.admin.StampGoodsRelationVo;
import com.lvmama.vst.hhs.model.common.Constant.HotelValidateMessage;
import com.lvmama.vst.hhs.model.common.Constant.SaleType;
import com.lvmama.vst.hhs.model.common.Constant.StampPhase;
import com.lvmama.vst.hhs.model.common.StampDuration;
import com.lvmama.vst.hhs.model.product.BranchPropVO;
import com.lvmama.vst.hhs.model.product.ChangeHotel;
import com.lvmama.vst.hhs.model.product.HotelTimePriceDate;
import com.lvmama.vst.hhs.model.product.HotelValidationRequest;
import com.lvmama.vst.hhs.model.product.HotelValidationResponse;
import com.lvmama.vst.hhs.model.product.ProdGroupDateVO;
import com.lvmama.vst.hhs.model.product.ProdProductAddtionalVO;
import com.lvmama.vst.hhs.model.product.ProdProductModel;
import com.lvmama.vst.hhs.model.product.ProductSaleType;
import com.lvmama.vst.hhs.model.product.StampUsage;
import com.lvmama.vst.hhs.model.product.VisitCalendarRequest;
import com.lvmama.vst.hhs.model.stamp.AdditionalGoods;
import com.lvmama.vst.hhs.model.stamp.ProductDetailsStamps;
import com.lvmama.vst.hhs.model.stamp.StampDetails;
import com.lvmama.vst.hhs.product.dao.BizCategoryEntity;
import com.lvmama.vst.hhs.product.dao.ProdPackageGroupEntity;
import com.lvmama.vst.hhs.product.dao.ProdPackageGroupHotelEntity;
import com.lvmama.vst.hhs.product.dao.ProdPackageGroupLineEntity;
import com.lvmama.vst.hhs.product.dao.ProdPackageGroupTransportEntity;
import com.lvmama.vst.hhs.product.dao.ProdProductBranchEntity;
import com.lvmama.vst.hhs.product.dao.ProdProductEntity;
import com.lvmama.vst.hhs.product.dao.ProdProductSaleReEntity;
import com.lvmama.vst.hhs.product.dao.SuppGoodEntity;
import com.lvmama.vst.hhs.product.dao.SuppGoodsRelationEntity;
import com.lvmama.vst.hhs.product.repository.BizCategoryRepository;
import com.lvmama.vst.hhs.product.repository.BizDistrictRepository;
import com.lvmama.vst.hhs.product.repository.ProdPackageDetailRepository;
import com.lvmama.vst.hhs.product.repository.ProdPackageGroupHotelRepository;
import com.lvmama.vst.hhs.product.repository.ProdPackageGroupLineRepository;
import com.lvmama.vst.hhs.product.repository.ProdPackageGroupRepository;
import com.lvmama.vst.hhs.product.repository.ProdPackageGroupTicketRepository;
import com.lvmama.vst.hhs.product.repository.ProdPackageGroupTransportRepository;
import com.lvmama.vst.hhs.product.repository.ProdProductAddtionalRepository;
import com.lvmama.vst.hhs.product.repository.ProdProductBranchPropRepository;
import com.lvmama.vst.hhs.product.repository.ProdProductBranchRepository;
import com.lvmama.vst.hhs.product.repository.ProdProductSaleReRepository;
import com.lvmama.vst.hhs.product.repository.ProductRepository;
import com.lvmama.vst.hhs.product.repository.SuppGoodsRelationRepository;
import com.lvmama.vst.hhs.product.repository.SuppGoodsRepository;
import com.lvmama.vst.hhs.stamp.service.StampService;
import com.lvmama.vst.hhs.stamp.util.StampUtils;
import com.lvmama.vst.hhs.stampDefinition.bo.StampGoodsBindingBo;
import com.lvmama.vst.hhs.stampDefinition.bo.StampGroupGoodsBo;
import com.lvmama.vst.hhs.stampDefinition.bo.StampPackageGoodsBo;
import com.lvmama.vst.hhs.stampDefinition.dao.PresaleStampDefinitionGoodsBindingEntity;
import com.lvmama.vst.hhs.stampDefinition.dao.PresaleStampDefinitionProductBindingEntity;
import com.lvmama.vst.hhs.stampDefinition.dao.StampDefinitionEntity;
import com.lvmama.vst.hhs.stampDefinition.service.DepartmentService;
import com.lvmama.vst.hhs.stampDefinition.service.StampGoodsService;

@Service
public class ProductServiceImpl implements ProductService{
    
    private static final Logger LOGGER = LoggerFactory.getLogger(ProductServiceImpl.class);

	@Autowired
	private ProductRepository productRepository;
	@Autowired
	private ProdProductClientService productClientService;
	@Autowired
	private CategoryClientService categoryClientService;
	@Autowired
	private ProdGroupDateClientService prodGroupDateClientService;
	@Autowired
	private ProdProductAdditionClientService prodProductAdditionClientRemote;
	@Autowired
	private StampGoodsService stampGoodsService;
	@Autowired
	private StampService stampService;
	@Autowired
	@Qualifier("oracleEntityManager")
	private EntityManager em;
	@Autowired
	private SuppGoodsRepository suppGoodsRepository;
	@Autowired
	private ProdPackageGroupHotelRepository prodPackageGroupHotelRepository;
	@Autowired
	private ProdProductBranchRepository productBranchRepository;
	@Autowired
	private ProdProductBranchPropRepository productBranchPropRepository;
	@Autowired
	private ProdProductAddtionalRepository productAddtionalRepository;
	@Autowired
	private SuppGoodsRelationRepository suppGoodsRelationRepository;
	@Autowired
	private BizCategoryRepository bizCategoryRepository;
	@Autowired
	private ProdPackageGroupRepository prodPackageGroupRepository;
	@Autowired
	private ProdPackageDetailRepository prodPackageDetailRepository;
	@Autowired
	private ProdPackageGroupLineRepository prodPackageGroupLineRepository;
	@Autowired
	private ProdPackageGroupTicketRepository prodPackageGroupTicketRepository;
	@Autowired
	private ProdPackageGroupTransportRepository prodPackageGroupTransportRepository;
	@Autowired
	private BizDistrictRepository bizDistrictRepository;
	@Autowired
	private ProdProductSaleReRepository prodProductSaleReRepository;
	@Autowired
	private ProdProductSaleService prodProductSaleService;
	@Autowired
	private SuppGoodsTimePriceClientService suppGoodsTimePriceClientService;
	@Autowired
	private DictClientService dictClientService;
	@Autowired
	private DepartmentService  departmentService;
	@Autowired
	private ProdProductPropClientService prodProductPropClientService;	
	@Autowired
	private ProdTrafficClientService prodTrafficClientService;
	@Autowired
	private SuppGoodsClientService suppGoodsClientService;
	
	
	public static final String HHS_STAMP_DEFINITION_PRODUCT_KEY = "hhs.model.stamp.ProductDetailsStamps_"; 
	public static final String HHS_STAMP_DEFINITION_PRODUCT_GROUP_DATE_KEY = "hhs.model.stamp.product.groupdate_"; 
	public static final String UNDERLINE   = "_"; 
	
	@Override
	@Transactional("oracleTransactionManager")
	public ProdProductEntity findById(long id) {
		return productRepository.findOne(id);
	}

	@Override
	@Transactional("oracleTransactionManager")
	public SuppGoodEntity getSuppGoodEntity(long suppGoodsId) {
		return suppGoodsRepository.findOne(suppGoodsId);
	}

    @Override
    public ProdProduct getProductById(Long id) {
        ResultHandleT<ProdProduct>  productT = productClientService.getProdProductBy(id);
        ProdProduct product = null;
        if(productT.isFail() || (product = productT.getReturnContent()) == null) {
        	return null;
        }
        return product;
    }

    @Override
    public List<ProdProduct> findProdProductList(Map<String, Object> params) {
        ResultHandleT<List<ProdProduct>>  productTes = productClientService.findProdProductList(params);
        if(productTes == null) {
            LOGGER.debug("findProdProductList not found by id {}", params);
            return new ArrayList<ProdProduct>();
        }
        return productTes.getReturnContent();
    }

    @Override
    public int countFindProdProductList(Map<String, Object> params) {
        ResultHandleT<Long>  productTes = productClientService.countRouteProductList(params);
        if(productTes.getReturnContent() == null) {
            LOGGER.debug("findProdProductList not found by id {}", params);
            return 0;
        }
        return productTes.getReturnContent().intValue();
       
    }

    @Override
    public Long addProduct(ProdProduct product) {
        ResultHandleT<Long>  productTes = productClientService.addProdProduct(product);
        if(productTes.getReturnContent() == null) {
            LOGGER.debug("addProduct not found by id {}",product.getProductName());
            return 0L;
        }
        return productTes.getReturnContent();
    }
    
    @Override
    @Transactional("oracleTransactionManager")
	public List<BranchPropVO> getProductBranchProps(List<Long> productBranchIds) {
    	if(CollectionUtils.isEmpty(productBranchIds)) {
    		return null;
    	}
//    	return productBranchPropRepository.getProductBranchProps(StringUtils.join(productBranchIds, ","));
    	String sql = "SELECT p.product_branch_prop_id,p.prop_id,b.prop_name,p.prod_value,p.product_branch_id,b.prop_code,b.data_from "
    			+ "from prod_product_branch_prop p inner join biz_branch_prop b on p.prop_id=b.prop_id "
    			+ "where p.product_branch_id in (" + StringUtils.join(productBranchIds, ",") + ") ";
    	Query query = em.createNativeQuery(sql);
    	List<?> result = query.getResultList();
    	
    	List<BranchPropVO> list = new ArrayList<BranchPropVO>();
    	for (Object row : result) {
			Object[] cells = (Object[]) row;
			BranchPropVO vo = new BranchPropVO();
			vo.setProductBranchPropId(NumberUtils.toLong(cells[0].toString()));
			vo.setPropId(NumberUtils.toLong(cells[1].toString()));
			vo.setPropName(cells[2].toString());
			vo.setPropValue((String)cells[3]);
			vo.setProductBranchId(NumberUtils.toLong(cells[4].toString()));
			vo.setPropCode((String)cells[5]);
			String dataFrom = (String)cells[6];
			if(StringUtils.isNotBlank(dataFrom)) {
				BizDict dict = getBizDictById(Long.valueOf(vo.getPropValue()));
				vo.setPropValue(dict.getDictName());
			}
			list.add(vo);
		}
    	return list;
    }
    
    private BizDict getBizDictById(Long dictId) {
    	ResultHandleT<BizDict> dictResult = dictClientService.findDictById(dictId);
    	BizDict dict = null;
    	if(dictResult.isSuccess() && (dict = dictResult.getReturnContent()) != null) {
    		return dict;
    	}
    	LOGGER.info(dictResult.getMsg());
    	return null;
    }

    @Override
    @Transactional("oracleTransactionManager")
    public List<ProdProductModel> queryProdProductList(Integer productId, String productName) {
        List<ProdProductModel>  productList = new ArrayList<ProdProductModel>();
        StringBuffer jpql = new StringBuffer();
        jpql.append("SELECT p.* FROM PROD_PRODUCT p where p.category_id in (1,15,16,17,18)  ");//in :categoryIds
        Map<String, Object> paramValues = new HashMap<String, Object>();       
        if(productId !=null && productId !=0 ){
            jpql.append(" and p.product_id="+productId);//.append(productId);
            paramValues.put("productId", productId);
        }
        if(StringUtils.isNotEmpty(productName)){
            jpql.append(" and p.product_name like '%"+productName+"%'");           
        }        
        Query query = em.createNativeQuery(jpql.toString(), ProdProductEntity.class);      
        query.setFirstResult(0);
        query.setMaxResults(20);     
        @SuppressWarnings("unchecked")
		List<ProdProductEntity> list=  query.getResultList();  
        System.out.println(jpql.toString());
        for(ProdProductEntity entity:list){
            ProdProductModel model = new ProdProductModel();
            model.setBizCategoryId(entity.getCategoryId().longValue());
            model.setProductId(entity.getProductId());
            model.setProductName(entity.getProductName());
            ResultHandleT<BizCategory> bT = categoryClientService.findCategoryById(entity.getCategoryId().longValue());
            if(bT.isSuccess()){
            	BizCategoryVo category = new BizCategoryVo();
            	BeanUtils.copyProperties(bT.getReturnContent(), category);
                model.setBizCategory(category);
            }
            productList.add(model);
        }
        
        return productList;
    }
	
    /**
     * 查询时间价格表， 如果没有预售券则返回null
     */
    @Override
    @Cacheable(value = "hhs.model.stamp.ProdGroupDateVO", cacheManager = "expired5MinutesCacheManager", key = "#root.target.HHS_STAMP_DEFINITION_PRODUCT_GROUP_DATE_KEY + #productId + #root.target.UNDERLINE + #startDistrictId + #root.target.UNDERLINE + #startDate + #root.target.UNDERLINE + #endDate")
    public List<ProdGroupDateVO> checkAndGetProdGroupDates(Long productId, Long startDistrictId, String startDate, String endDate) {
    	List<StampDefinitionEntity> stampList = stampService.getAvailableStampByProductIdAndDepartId(productId, startDistrictId, StampPhase.ALL);
    	if(stampList == null || stampList.size() == 0) {
    		LOGGER.info("can not find any available stamps, productId: {}, departId: {}", productId, startDistrictId);
    		return null;
    	}
    	boolean onSale = false;
    	boolean onRedeem = false;
    	Date now = DateUtil.stringToDate(DateUtil.SimpleFormatDateToString(new Date()), DateUtil.SIMPLE_DATE_FORMAT);
    	List<StampDuration> availableDurations = new ArrayList<StampDuration>();
    	for(StampDefinitionEntity stamp : stampList) {
    		StampAssociatedProdAvailTimeSlotVo prodAvailTimeSlot = (StampAssociatedProdAvailTimeSlotVo) HhsUtils.jsonToObject(stamp.getAssociatedProdAvailTimeslot(), StampAssociatedProdAvailTimeSlotVo.class);
			List<StampDuration> durations = StampUtils.translateAvailTimeslot(prodAvailTimeSlot);
			availableDurations.addAll(durations);
			if(now.getTime()>=stamp.getStampOnsaleStartDate().getTime()
			        && now.getTime()<=stamp.getStampOnsaleEndDate().getTime()) {//正在预售
				onSale = true;
			}
			if(now.getTime()>=stamp.getStampRedeemableStartDate().getTime()
			        && now.getTime()<=stamp.getStampRedeemableEndDate().getTime()) { //预售预约
				onRedeem = true;
			}
 		}
    	
    	List<ProdGroupDateVO> result = new ArrayList<ProdGroupDateVO>();
        List<ProdGroupDate> datelist = getProdGroupDates(productId, startDistrictId, startDate, endDate);
        if(datelist != null) {
        	for(ProdGroupDate date : datelist) {
        		Date specDate = date.getSpecDate();
        		ProdGroupDateVO dateVO = new ProdGroupDateVO();
        		BeanUtils.copyProperties(date, dateVO);
        		if(StampUtils.checkDuration(availableDurations, specDate)) { //该出发日期有可用的券
            		//预约和预售同时存在时，预约优先
            		dateVO.setStampFlag(onRedeem ? HhsConstants.ON_REDEEM_FLAG : (onSale ? HhsConstants.ON_SALE_FLAG : null)); //1: 正在预售， 2: 预售预约
    			}
        		result.add(dateVO);
        	}
        } else {
        	LOGGER.info("Can not find ProdGroupDate from dubbo service.");
        }
    	return result;
    }
    
    @Override
    public List<ProdGroupDateVO> getVisitCalendar(Long productId, VisitCalendarRequest request) {
    	List<String> stampIds = new ArrayList<String>();
    	int amount = 0;
    	for(StampUsage usage : request.getStamps()) {
    		stampIds.add(usage.getStampId());
    		amount += usage.getAmount();
    	}
    	if(CollectionUtils.isEmpty(stampIds)) {
    		throw new IllegalArgumentException("no stmapId found");
    	}
    	int [] maxMinQuantity = getStampMaxMinQuantity(stampIds.get(0));
		if(amount > maxMinQuantity[0]) {
			LOGGER.info("can not exceed the max quantity, productId: {}, maxQuantity: {}", productId, maxMinQuantity[0]);
			return null;
		}
    	
    	List<StampDefinitionEntity> stampList = stampService.getStampByIds(stampIds);
    	if(CollectionUtils.isEmpty(stampList)) {
    		LOGGER.info("can not find any available stamps, productId: {}, stampIds: {}", productId, stampIds);
    		return null;
    	}
    	List<StampDuration> availableDurations = new ArrayList<StampDuration>();
    	boolean init = false;
    	for(StampDefinitionEntity stamp : stampList) {
    		StampAssociatedProdAvailTimeSlotVo prodAvailTimeSlot = (StampAssociatedProdAvailTimeSlotVo) HhsUtils.jsonToObject(stamp.getAssociatedProdAvailTimeslot(), StampAssociatedProdAvailTimeSlotVo.class);
			List<StampDuration> durations = StampUtils.translateAvailTimeslot(prodAvailTimeSlot);
			if(CollectionUtils.isEmpty(durations))
				continue;
			if(!init) {
				availableDurations.addAll(durations);
				init = true;
			} else {
				availableDurations = StampUtils.getIntersection(durations, availableDurations);
			}
    	}
    	if(CollectionUtils.isEmpty(availableDurations)) {
    		LOGGER.info("the associated product available time do not have the intersections. productId: {}, stampIds: {}", productId, stampIds);
    		return null;
    	}
    	
    	Date start = null, end = null, tmp = null;
    	for(StampDuration duration : availableDurations) {
    		tmp = duration.getStartDate();
			if(start == null) 
				start = tmp;
			if(start.after(tmp))
				start = tmp;
			
			tmp = duration.getEndDate();
			if(end == null)
				end = tmp;
			if(end.before(tmp))
				end = tmp;
    	}
    	
    	if(start == null || end == null) {
    		LOGGER.info("can not get the start and end date from stamps, productId: {}, stampIds: {}", productId, stampIds);
    		return null;
    	}
    	
    	Date today = new Date();
    	if(start.before(today)) {
    		start = today;
    	}
    	
    	SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
    	List<ProdGroupDateVO> result = new ArrayList<ProdGroupDateVO>();
        List<ProdGroupDate> datelist = getProdGroupDates(productId, request.getStartDistrictId(), format.format(start), format.format(end));
        if(CollectionUtils.isNotEmpty(datelist)) {
        	List<PresaleStampTimePrice> stampDateList = getPresaleTimePrices(stampList, start, end);
        	if(CollectionUtils.isEmpty(stampDateList)) {
        		LOGGER.info("Can not find presale time price from dubbo service. stampList: {}, startDate: {}, endDate: {}", stampIds, start, end);
        		return null;
        	}
        	List<String> timepriceDate = new ArrayList<String>();
        	for(PresaleStampTimePrice timeprice : stampDateList) {
        		if(timeprice != null && timeprice.getApplyDate() != null)
        			timepriceDate.add(format.format(timeprice.getApplyDate()));
        	}
        	LOGGER.debug("the date list of presale timeprice: {}, stampIds: {}", timepriceDate, stampIds);
        	
        	List<Long> goodsIds = getGoodsIdsFromStamps(stampList);
        	if(CollectionUtils.isEmpty(goodsIds)) {
        		LOGGER.info("can not find binding goods, stampList: {}", stampList);
        		return null;
        	}
        	
        	for(ProdGroupDate date : datelist) {
        		Date specDate = date.getSpecDate();
        		if(StampUtils.checkDuration(availableDurations, specDate) && timepriceDate.contains(format.format(specDate))) { //该出发日期有可用的券, 该日期有预售团期表
        			if(date.getStock() == null || date.getStock() == -1 || date.getStock() >= amount) {
        				ProdGroupDateVO dateVO = new ProdGroupDateVO();
        				BeanUtils.copyProperties(date, dateVO);
        				result.add(dateVO);
        			} else {
        				//查询是否可以超卖
        				SuppGoodsBaseTimePrice timepirce = null;
        				boolean valid = true;
        				for(Long goodsId : goodsIds) {
        					ResultHandleT<SuppGoodsBaseTimePrice> timePriceResult = suppGoodsTimePriceClientService.getBaseTimePrice(goodsId, specDate);
        					if(timePriceResult.isSuccess() && (timepirce = timePriceResult.getReturnContent()) != null) {
        						if("N".equals(timepirce.getOnsaleFlag())) {
        							LOGGER.info("the goods {} is not on sale", goodsId);
        							valid = false;
        							break;
        						}
        						if(timepirce.getStock() != null && timepirce.getStock() != -1 && timepirce.getStock() < amount && !"Y".equals(timepirce.getOversellFlag())) {
        							LOGGER.info("the stock is not enough, goodsId: {}, Expect: {}, Actual: {}", goodsId, amount, timepirce.getStock());
        							valid = false;
        							break;
        						}
        					} else {
        						LOGGER.info(timePriceResult.getMsg());
        						valid = false;
        					}
        				}
        				if(valid) {
        					ProdGroupDateVO dateVO = new ProdGroupDateVO();
        					BeanUtils.copyProperties(date, dateVO);
        					result.add(dateVO);
        				}
        			}
    			}
        	}
        } else {
        	LOGGER.info("Can not find ProdGroupDate from dubbo service, productId: {}, startDate: {}, endDate: {}", productId, start, end);
        }
    	return result;
    }
    
    private List<Long> getGoodsIdsFromStamps(List<StampDefinitionEntity> stamps) {
    	if(stamps == null) 
    		return null;
    	List<Long> goodsIds = new ArrayList<Long>();
    	for(StampDefinitionEntity entity : stamps) {
    		List<PresaleStampDefinitionGoodsBindingEntity> goodsBinding = stampGoodsService.getStampGoodsBindingByStampId(entity.getId());
    		if(CollectionUtils.isNotEmpty(goodsBinding)) {
    			for(PresaleStampDefinitionGoodsBindingEntity good : goodsBinding) {
    				goodsIds.add(NumberUtils.toLong(good.getGoodsId()));
    			}
    		}
    	}
    	return goodsIds;
    }
    
    private int[] getStampMaxMinQuantity(String stampId) {
    	List<PresaleStampDefinitionGoodsBindingEntity> goodBindings = stampGoodsService.getStampGoodsBindingByStampId(stampId);
    	int [] maxMin = new int[2];
    	if(CollectionUtils.isNotEmpty(goodBindings)) {
    		boolean firstLoop = true;
			int maxQuantity = 0, minQuantity = 0;
    		for(PresaleStampDefinitionGoodsBindingEntity goodBinding : goodBindings) {
    			SuppGoodEntity suppGoodEntity = getSuppGoodEntity(NumberUtils.toLong(goodBinding.getGoodsId()));
				if(suppGoodEntity == null) {
					LOGGER.info("Data issue, can not find supp goods based on goodsId: {}", goodBinding.getGoodsId());
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
    		maxMin[0] = maxQuantity;
    		maxMin[1] = minQuantity;
    	}
    	return maxMin;
    }
    
    /**
     * 查询预售团期表
     * @param stampList
     * @param startTime
     * @param endTime
     * @return
     */
    private List<PresaleStampTimePrice> getPresaleTimePrices(List<StampDefinitionEntity> stampList, Date startTime, Date endTime) {
    	if(CollectionUtils.isEmpty(stampList))
    		return null;
    	
    	SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
    	List<PresaleStampTimePrice> result = new ArrayList<PresaleStampTimePrice>();
    	for(StampDefinitionEntity definitionEntity : stampList) {
    		List<PresaleStampDefinitionGoodsBindingEntity> goodsBindings = stampGoodsService.getStampGoodsBindingByStampId(definitionEntity.getId());
    		if(CollectionUtils.isEmpty(goodsBindings)) 
    			continue;
    		if(goodsBindings.size() == 1) {
    			List<PresaleStampTimePrice> stampDateList = suppGoodsTimePriceClientService.getPresaleStampTimePricesBygoodsIdAndTime(NumberUtils.toLong(goodsBindings.get(0).getGoodsId()), startTime, endTime);
    			result.addAll(stampDateList);
    			continue;
    		}
    		
    		boolean findInvalidGoods = false;
    		boolean first = true;
    		Set<String> validDates = new HashSet<String>();
    		Map<String, List<PresaleStampTimePrice>> timepriceMap = new HashMap<String, List<PresaleStampTimePrice>>();
    		for(PresaleStampDefinitionGoodsBindingEntity goodsBinding : goodsBindings) {
    			List<PresaleStampTimePrice> stampDateList = suppGoodsTimePriceClientService.getPresaleStampTimePricesBygoodsIdAndTime(NumberUtils.toLong(goodsBinding.getGoodsId()), startTime, endTime);
    			if(CollectionUtils.isNotEmpty(stampDateList)) {
    				Set<String> tmpValids = new HashSet<String>();
    				for(PresaleStampTimePrice tp : stampDateList) {
    					String applyDate = format.format(tp.getApplyDate());
    					if(timepriceMap.containsKey(applyDate)) {
							timepriceMap.get(applyDate).add(tp);
						} else {
							List<PresaleStampTimePrice> list = new ArrayList<PresaleStampTimePrice>();
							list.add(tp);
							timepriceMap.put(applyDate, list);
						}
    					tmpValids.add(applyDate);
    				}
    				if(first) {
    					validDates = tmpValids;
    					first = false;
    				} else {
    					validDates = Sets.intersection(validDates, tmpValids);
    				}
    			} else {
    				LOGGER.info("find goods without time price, goodsId: {}, stampId: {}", goodsBinding.getGoodsId(), definitionEntity.getId());
    				findInvalidGoods = true;
    				break;
    			}
    		}
    		
    		if(findInvalidGoods) {
    			continue;
    		}
    		
    		Set<String> ignoreSet = new HashSet<String>();
    		for(Entry<String, List<PresaleStampTimePrice>> entry : timepriceMap.entrySet()) {
    			if(validDates.contains(entry.getKey())) {
    				result.addAll(entry.getValue());
    			} else {
    				ignoreSet.add(entry.getKey());
    			}
    		}
    		if(ignoreSet.size() > 0) {
    			LOGGER.debug("ignore time price date due to lack of time price for some goods. date set: {}", ignoreSet);
    		}
    	}
    	return result;
    }
    
    /**
     * 调用dubbo接口查询时间价格表
     * @param productId
     * @param startDistrictId
     * @param startDate
     * @param endDate
     * @return
     */
    private List<ProdGroupDate> getProdGroupDates(Long productId, Long startDistrictId, String startDate, String endDate) {
    	Map<String, Object> params = new HashMap<String, Object>();
        params.put("productId",productId);
        try {
			params.put("endDate", DateUtils.parseDate(endDate, "yyyy-MM-dd"));
			params.put("beginDate",DateUtils.parseDate(startDate, "yyyy-MM-dd"));
		} catch (ParseException e) {
			LOGGER.error("Exception encountered when parse date, startDate:{}, endDate:{}", startDate, endDate);
			throw new RuntimeException(e);
		}
        //设置检查参数
        params.put("checkAhead",true);
        params.put("startDistrictId",	startDistrictId);
        ResultHandleT<List<ProdGroupDate>> prodGroupDateListHandleT = prodGroupDateClientService.findProdGroupDateListByParam(productId, params);
        
        if(prodGroupDateListHandleT == null || prodGroupDateListHandleT.isFail()) {
        	LOGGER.warn("Call group date service failed!");
        	if(StringUtils.isNotBlank(prodGroupDateListHandleT.getMsg()))
        		LOGGER.warn(prodGroupDateListHandleT.getMsg());
        	return null;
        }
    	return prodGroupDateListHandleT.getReturnContent();
    }
    
	/**
	 * 查询产品下的预售券信息，适用于产品详情页
	 */
	@Override
    @Cacheable(value = "hhs.model.stamp.ProductDetailsStamps", cacheManager = "expired5MinutesCacheManager", key = "#root.target.HHS_STAMP_DEFINITION_PRODUCT_KEY + #productId + #root.target.UNDERLINE + #startDistrictId")
	public ProductDetailsStamps getProductStampsDetails(Long productId, Long startDistrictId) {
		List<StampDetails> stampList = stampService.getAvailableStampDetailsByProductIdAndDepartId(productId, startDistrictId, StampPhase.ALL);
		if (stampList == null || stampList.size() == 0) {
			LOGGER.info("can not find available stamps based on productId: {}", productId);
			return null;
		}
		ProductDetailsStamps ds = new ProductDetailsStamps();
		ds.setStmaps(stampList);
		ProdProductAddtional additional = queryProdProductAddtional(productId, startDistrictId);
		if (additional != null) {
			ds.setProdProductAddtional(addtionalTranslate(additional));
		} else {
			LOGGER.warn("ProdProductAddtional is null based on productId {}, should not happen!", productId);
		}
		
		List<Date> dateList = new ArrayList<Date>();
		List<Date> dateList2 = new ArrayList<Date>();

		Long minPrice = null;
		boolean childStamp = false;
		for (StampDetails detail : stampList) {
			if(!detail.isOnSale()) {
				continue;
			}
			childStamp = false;
			List<PresaleStampDefinitionGoodsBindingEntity> goodBindings = stampGoodsService.getStampGoodsBindingByStampId(detail.getId());
			if(CollectionUtils.isEmpty(goodBindings)) {
				LOGGER.info("can not find goods bindings based on stampId: {}", detail.getId());
				return null;
			}
			for(PresaleStampDefinitionGoodsBindingEntity goodBinding : goodBindings) {
				if(HhsConstants.BRANCH_FLAG_PRIMARY_CHILD.equals(goodBinding.getBranchFlag())) {
					childStamp = true;
					break;
				}
			}
			if(!childStamp) {
				if(minPrice == null) 
					minPrice = detail.getSalePrice();
				else if (minPrice > detail.getSalePrice()) {
					minPrice = detail.getSalePrice();
				}
			}
			for (StampDuration duration : detail.getAssociatedProdAvailTimeSlot()) {
				dateList.addAll(getDatesBetweenTwoDate(duration.getStartDate(), duration.getEndDate()));
			}
			
		}
        /**
         * 检查是否是单酒店
         */
		ProdProduct pt=getProductById(productId);
		if(pt!=null&&pt.getCategoryId()!=null&&pt.getCategoryId().equals(1L))
			return getProductStampsDetailsByHotels(ds,minPrice,dateList,additional,productId);
		
    	Map<String, Object> params = new HashMap<String, Object>();
        params.put("productId",productId);
        //设置检查参数
        params.put("checkAhead",true);
        params.put("startDistrictId",startDistrictId);
		ResultHandleT<List<ProdGroupDate>> prodGroupDateListHandleT = prodGroupDateClientService.findProdGroupDateListByParam(productId, params);         
		List<ProdGroupDate> datelist = prodGroupDateListHandleT.getReturnContent();
		//计算团期表
		if (CollectionUtils.isNotEmpty(datelist)) {
			boolean flag=true;
			Iterator<ProdGroupDate> iterator = datelist.iterator();
			while(iterator.hasNext()){
			    ProdGroupDate  prodGroupDate = iterator.next();
			    dateList2.add(prodGroupDate.getSpecDate());
                //如果预售券中包含起价的日期就去除
                if(dateList.contains(prodGroupDate.getSpecDate())){
                    iterator.remove();
                }	

			}
		
			// 如果兑换时间完全包含出游时间，起价就是最低的预售券的价格
			if (CollectionUtils.isNotEmpty(dateList)) {
				if (dateList.size() >= dateList2.size()) {
					int nums = 0;
					for (Date date : dateList2) {
						if (dateList.contains(date)) {
							++nums;
						}
					}
					if (nums == dateList2.size()) {
						additional.setLowestSaledPrice(minPrice);
						flag=false;
					}
				}

			}
			if(datelist.size()>0){
				Long lowestPrice=datelist.get(0).getLowestSaledPrice();
				for (ProdGroupDate prodGroupDate : datelist) {
					if(prodGroupDate.getLowestSaledPrice()<lowestPrice)
						lowestPrice=prodGroupDate.getLowestSaledPrice();
				}
				if(flag)
					additional.setLowestSaledPrice(lowestPrice);
			}
			ds.setProdProductAddtional(addtionalTranslate(additional));
		}
		return ds;
	}
	
	/**
	 * 酒店的起价
	 * @param ds
	 * @param minPrice
	 * @param dateList
	 * @param additional
	 * @param productId
	 * @return
	 */
	private ProductDetailsStamps getProductStampsDetailsByHotels(
			ProductDetailsStamps ds, Long minPrice, List<Date> dateList,
			ProdProductAddtional additional, Long productId) {
		List<Date> dateList2 = new ArrayList<Date>();
		// 查询酒店的时间价格表
		List<SuppGoodsTimePrice> datelist = suppGoodsTimePriceClientService
				.selectTimePriceListByProdId(productId).getReturnContent();
		// 计算团期表
		if (CollectionUtils.isNotEmpty(datelist)) {
			boolean flag = true;
			Iterator<SuppGoodsTimePrice> iter = datelist.iterator();
			while (iter.hasNext()) {
				SuppGoodsTimePrice suppGoodsTimePrice = iter.next();
				dateList2.add(suppGoodsTimePrice.getSpecDate());
				// 如果预售券中包含起价的日期就去除
				if (dateList.contains(suppGoodsTimePrice.getSpecDate())) {
					iter.remove();
				}
			}
			// 如果兑换时间完全包含出游时间，起价就是最低的预售券的价格
			if (CollectionUtils.isNotEmpty(dateList)) {
				if (dateList.size() >= dateList2.size()) {
					int nums = 0;
					for (Date date : dateList2) {
						if (dateList.contains(date)) {
							++nums;
						}
					}
					if (nums == dateList2.size()) {
						additional.setLowestSaledPrice(minPrice);
						flag = false;
					}
				}

			}
			if (datelist.size() > 0) {
				Long lowestPrice = datelist.get(0).getPrice();
				for (SuppGoodsTimePrice suppGoodsTimePrice : datelist) {
					if (suppGoodsTimePrice.getPrice() < lowestPrice)
						lowestPrice = suppGoodsTimePrice.getPrice();
				}
				if (flag)
					additional.setLowestSaledPrice(lowestPrice);
			}
			ds.setProdProductAddtional(addtionalTranslate(additional));
		}
		return ds;
	}

	private ProdProductAddtional queryProdProductAddtional(Long productId, Long startDistrictId) {
		if (null != startDistrictId) {
			ProdProduct prodProduct = getProductById(productId);
			if (prodProduct == null) {
				LOGGER.info("no product found based on productId {}", productId);
				return null;
			}
			if ("Y".equals(prodProduct.getMuiltDpartureFlag())) { // 多出发地
				Map<String, Long> multiParam = new HashMap<String, Long>();
				multiParam.put("productId", productId);
				multiParam.put("startDistrictId", startDistrictId);
				return prodProductAdditionClientRemote.selectByConditon(multiParam);
			} else {
				return prodProductAdditionClientRemote.selectByPrimaryKey(productId);
			}
		} else {
			return prodProductAdditionClientRemote.selectByPrimaryKey(productId);

		}
	}

	private ProdProductAddtionalVO addtionalTranslate(
			ProdProductAddtional additional) {
		ProdProductAddtionalVO vo = new ProdProductAddtionalVO();
		BeanUtils.copyProperties(additional, vo);
		return vo;
	}

	
	private List<Date> getDatesBetweenTwoDate(Date beginDate, Date endDate) {
		List<Date> lDate = new ArrayList<Date>();
		SimpleDateFormat sdf=  new SimpleDateFormat("yyyy-MM-dd");
		String beginDateStr = sdf.format(beginDate);
		String endDateStr = sdf.format(endDate);
		if (beginDateStr.equals(endDateStr)) {
			lDate.add(beginDate);
			return lDate;
		}
		lDate.add(beginDate);// 把开始时间加入集合
		Calendar cal = Calendar.getInstance();
		// 使用给定的 Date 设置此 Calendar 的时间
		cal.setTime(beginDate);
		boolean bContinue = true;
		while (bContinue) {
			// 根据日历的规则，为给定的日历字段添加或减去指定的时间量
			cal.add(Calendar.DAY_OF_MONTH, 1);
			// 测试此日期是否在指定日期之后
			if (endDate.after(cal.getTime())) {
				lDate.add(cal.getTime());
			} else {
				break;
			}
		}
		lDate.add(endDate);// 把结束时间加入集合
		return lDate;
	}
	
	@Override
	@Transactional("oracleTransactionManager")
	public List<ChangeHotel> getChangeHotel(Long productId, Date visitTime) {
		List<ProdPackageGroupHotelEntity> packageGroupHotels = prodPackageGroupHotelRepository.getByProductId(productId);
		if(CollectionUtils.isEmpty(packageGroupHotels)) {
			LOGGER.info("can not find package group hotel, productId: {}", productId);
			return null;
		}
		List<Long> groupIds = extractPackageGroupHotelId(packageGroupHotels);
		Map<Long, List<SuppGoodEntity>> groupGoodsMap = new HashMap<Long, List<SuppGoodEntity>>();
		for(Long groupId : groupIds) {
			groupGoodsMap.put(groupId, suppGoodsRepository.getGroupHotelByGroupId(groupId));
		}
		return getChangeHotel(groupGoodsMap, visitTime);
	}
	
	@Override
	@Transactional("oracleTransactionManager")
	public List<ChangeHotel> getChangeHotel(Map<Long, List<SuppGoodEntity>> groupGoodsMap, Date visitTime) {
		if(MapUtils.isEmpty(groupGoodsMap))
			return null;
		List<ProdPackageGroupHotelEntity> groupHotelEntitys = prodPackageGroupHotelRepository.getByGroupIdIn(StampUtils.LongCollectionToBigDecimal(groupGoodsMap.keySet()));
		if(CollectionUtils.isEmpty(groupHotelEntitys)) {
			LOGGER.info("can not find package group hotel, groupIds: {}", groupGoodsMap.keySet());
			return null;
		}
		List<ProdProductBranchEntity> productBranchs = productBranchRepository.getByPackageGroupId(groupGoodsMap.keySet());
		if(CollectionUtils.isEmpty(productBranchs)) {
			LOGGER.info("can not find product branch, groupIds: {}", groupGoodsMap.keySet());
			return null;
		}

		Map<Long, ProdPackageGroupHotelEntity> groupHotelEntityMap = translatePackageGroupHotel(groupHotelEntitys);
		Map<Long, ProdProductBranchEntity> productBranchMap = translateProductBranch(productBranchs);
		
		List<Long> productBranchIds = new ArrayList<Long>();
		productBranchIds.addAll(productBranchMap.keySet());

		List<BranchPropVO> branchProps = getProductBranchProps(productBranchIds);
		Map<Long, List<BranchPropVO>> propMap = translateProductBranchProp(branchProps);
		
		List<ChangeHotel> changeHotels = new ArrayList<ChangeHotel>();
		
		for(Entry<Long, List<SuppGoodEntity>> entry : groupGoodsMap.entrySet()) {
			Long groupId = entry.getKey();
			List<SuppGoodEntity> goodsList = entry.getValue();
			
			ProdPackageGroupHotelEntity groupHotelEntity = groupHotelEntityMap.get(groupId);
			if(groupHotelEntity == null) {
				LOGGER.info("groupHotelEntity is null, groupId: {}", groupId);
				continue;
			}
			
			for(SuppGoodEntity goodsEntity : goodsList) {
				ProdProductBranchEntity productBranchEntity = productBranchMap.get(goodsEntity.getProductBranchId().longValue());
				if(productBranchEntity == null) {
					LOGGER.info("productBranchEntity is null, productBranchId: {}", goodsEntity.getProductBranchId().longValue());
					continue;
				}
				
				ChangeHotel hotel = new ChangeHotel();
				hotel.setProductBranchId(productBranchEntity.getProductBranchId());
				hotel.setBranchName(productBranchEntity.getBranchName());
				String [] stayDays = StampUtils.parseHotelDays(groupHotelEntity.getStayDays(), visitTime);
				hotel.setStart(stayDays[0]);
				hotel.setEnd(stayDays[1]);
				if(StringUtils.isEmpty(groupHotelEntity.getStayDays()) && groupHotelEntity.getStayDays().indexOf(",") > 1) {
					String [] arr = groupHotelEntity.getStayDays().split(",");
					hotel.setDay(arr.length);
				}
				if(hotel.getDay() < 1) 
					hotel.setDay(1);
				hotel.setGoodsId(goodsEntity.getSuppGoodsId());
				hotel.setGoodsName(goodsEntity.getGoodsName());
				hotel.setGroupId(groupHotelEntity.getGroupId().longValue());
				hotel.setProps(propMap.get(productBranchEntity.getProductBranchId()));
				changeHotels.add(hotel);
			}
			
		}
		return changeHotels;
	}
	
	private Map<Long, ProdPackageGroupHotelEntity> translatePackageGroupHotel(List<ProdPackageGroupHotelEntity> groupHotelEntitys) {
		Map<Long, ProdPackageGroupHotelEntity> map = new HashMap<Long, ProdPackageGroupHotelEntity>();
		if(groupHotelEntitys != null) {
			for(ProdPackageGroupHotelEntity entity : groupHotelEntitys) {
				map.put(entity.getGroupId().longValue(), entity);
			}
		}
		return map;
	}
	
	private Map<Long, ProdProductBranchEntity> translateProductBranch(List<ProdProductBranchEntity> productBranchs) {
		Map<Long, ProdProductBranchEntity> map = new HashMap<Long, ProdProductBranchEntity>();
		if(productBranchs != null) {
			for(ProdProductBranchEntity entity : productBranchs) {
				map.put(entity.getProductBranchId(), entity);
			}
		}
		return map;
	}
	
	private Map<Long, List<BranchPropVO>> translateProductBranchProp(List<BranchPropVO> branchProps) {
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
	
	@Override
	@Transactional("oracleTransactionManager")
	public List<AdditionalGoods> getAdditionGoods(Long productId, Date visitDate, int quantity) {
		List<ProdProductBranchEntity> productBranchs = productBranchRepository.getAdditionByProudctId(productId);
		if(CollectionUtils.isEmpty(productBranchs)) {
			LOGGER.debug("can not find any product branchs for addition product, productId: {}", productId);
			return null;
		}
		Map<Long, ProdProductBranchEntity> productBranchMap = translateProductBranch(productBranchs);
		List<SuppGoodEntity> goodsList = suppGoodsRepository.getByProductBranchIdIn(StampUtils.LongCollectionToBigDecimal(productBranchMap.keySet()));
		if(CollectionUtils.isEmpty(goodsList)) {
			LOGGER.debug("can not find any goods for addition product, productId: {}", productId);
			return null;
		}
		
		List<BigDecimal> goodsIds = extractGoodsIds(goodsList);
		
		List<SuppGoodsRelationEntity> relations = suppGoodsRelationRepository.getBySecGoodsIdIn(goodsIds);
		Map<Long, SuppGoodsRelationEntity> relationMap = translateGoodsRelation(relations);
		
		List<Long> branchIds = new ArrayList<Long>();
		branchIds.addAll(productBranchMap.keySet());
		List<BranchPropVO> branchProps = getProductBranchProps(branchIds);
		Map<Long, List<BranchPropVO>> propMap = translateProductBranchProp(branchProps);
		
		List<AdditionalGoods> additions = new ArrayList<AdditionalGoods>();
		SuppGoodsBaseTimePrice timeprice = null;
		for(SuppGoodEntity goodsEntity : goodsList) {
			ProdProductBranchEntity productBranchEntity = productBranchMap.get(goodsEntity.getProductBranchId().longValue());
			if(productBranchEntity == null) {
				LOGGER.info("productBranchEntity is null, productBranchId: {}", goodsEntity.getProductBranchId().longValue());
				continue;
			}
			SuppGoodsRelationEntity relation = relationMap.get(goodsEntity.getSuppGoodsId());
			if(relation == null) {
				LOGGER.info("SuppGoodsRelationEntity is null, secGoodsId: {}", goodsEntity.getSuppGoodsId());
				continue;
			}
			
			//大于最大订购数 或者 小于最小订购数时， 可选和任选不显示附加， 等量不做判断，但是下单会验证
			if(goodsEntity.getMaxQuantity().intValue() < quantity || goodsEntity.getMinQuantity().intValue() > quantity) {
				if("OPTION".equals(relation.getRelationType()) 
						|| "OPTIONAL".equals(relation.getRelationType())) {//AMOUNT: 等量, OPTION: 可选, OPTIONAL: 任选
					LOGGER.info(
							"do not match the maxQuantity or minQuantity, goodsId:{}, maxQuantity:{}, minQuantity:{}, quantity:{}",
							goodsEntity.getSuppGoodsId(), goodsEntity.getMaxQuantity(), goodsEntity.getMinQuantity(), quantity);
					continue;
				}
			}
			
			long price = 0L;
			ResultHandleT<SuppGoodsBaseTimePrice> timePriceResult = suppGoodsTimePriceClientService.getBaseTimePrice(goodsEntity.getSuppGoodsId(), visitDate);
			if(timePriceResult.isSuccess() && (timeprice = timePriceResult.getReturnContent()) != null) {
				if(timeprice.getStock() != null && timeprice.getStock() != -1 && timeprice.getStock() < quantity) {//库存不足
					if("N".equals(timeprice.getOversellFlag())) {//不可超卖
						if("OPTION".equals(relation.getRelationType()) 
								|| "OPTIONAL".equals(relation.getRelationType())) {//AMOUNT: 等量, OPTION: 可选, OPTIONAL: 任选
							LOGGER.info("inventory is not enough, secGoodsId: {}", goodsEntity.getSuppGoodsId());
							continue;
						}
					}
				}
				price = timeprice.getGoodsAudltPrice();
			} else {
				LOGGER.info("can not find time price for addition goods: {}, visitDate: {}, message: {}",goodsEntity.getSuppGoodsId(), visitDate, timePriceResult.getMsg());
				continue;
			}
			
			AdditionalGoods addition = new AdditionalGoods();
			addition.setBranchName(productBranchEntity.getBranchName());
			addition.setGoodsId(goodsEntity.getSuppGoodsId());
			addition.setGoodsName(goodsEntity.getGoodsName());
			addition.setProductBranchId(productBranchEntity.getProductBranchId());
			addition.setRelationType(relation.getRelationType());
			addition.setPrice(price);
			addition.setProps(propMap.get(productBranchEntity.getProductBranchId()));
			addition.setVisitTime(DateUtil.formatSimpleDate(visitDate));
			additions.add(addition);
		}
		return additions;
	}
	
	private List<BigDecimal> extractGoodsIds(List<SuppGoodEntity> goodsList) {
		ArrayList<BigDecimal> goodsIds = new ArrayList<BigDecimal>();
		if(goodsList != null) {
			for(SuppGoodEntity goodsEntity : goodsList) {
				goodsIds.add(new BigDecimal(goodsEntity.getSuppGoodsId()));
			}
		}
		goodsIds.trimToSize();
		return goodsIds;
	}
	
	private Map<Long, SuppGoodsRelationEntity> translateGoodsRelation(List<SuppGoodsRelationEntity> relations) {
		Map<Long, SuppGoodsRelationEntity> map = new HashMap<Long, SuppGoodsRelationEntity>();
		if(relations != null) {
			for(SuppGoodsRelationEntity entity : relations) {
				map.put(entity.getSecGoodsId().longValue(), entity);
			}
		}
		return map;
	}
	
	private List<Long> extractPackageGroupHotelId(List<ProdPackageGroupHotelEntity> groupHotelEntitys) {
		List<Long> list = new ArrayList<Long>();
		if(groupHotelEntitys != null) {
			for(ProdPackageGroupHotelEntity entity : groupHotelEntitys) {
				list.add(entity.getGroupId().longValue());
			}
		}
		return list;
	}
	
	@Override
	@Transactional("oracleTransactionManager")
	public ProductSaleType getProductSaleTypeByStampId(String stampId) {
		StampDefinitionEntity stamp = stampService.getStampDefinitionById(stampId);
		if(stamp == null) 
			throw new RuntimeException("Data issue, can not find stamp based on stampId : " + stampId);
		PresaleStampDefinitionProductBindingEntity productBinding = null;
		if((productBinding = stamp.getProductBinding()) == null) 
			throw new RuntimeException("Data issue, the stamp did not bind any products, stampId : " + stampId);
		String productId = productBinding.getProductId();
		BizCategoryEntity category = bizCategoryRepository.getByProductId(NumberUtils.toLong(productId));
		if(category == null)
			throw new RuntimeException("Data issue, can not find category based on productId : " + productId);
		
		StampGoodsBindingBo goodsBo = stampGoodsService.getStampGoodsByStampId(stampId);
		if(goodsBo == null) 
			throw new RuntimeException("Data issue, can not find goods binding, stampId : " + stampId);
		
		if("category_route_hotelcomb".equals(category.getCategoryCode())) { //酒店套餐
			if(goodsBo.getGoods() == null) 
				throw new RuntimeException("Data issue, can not find goods binding, stampId : " + stampId);
			Long goodsId = NumberUtils.toLong(goodsBo.getGoods().getGoodsId());
			SuppGoodEntity goodEntity = suppGoodsRepository.findOne(goodsId);
			if(goodEntity == null) 
				throw new RuntimeException("Data issue, can not find supp goods based on supp_goods_id : " + goodsId);
			ProductSaleType saleType = new ProductSaleType();
			saleType.setSaleType(SaleType.COPIES.name());
			saleType.setAdultQuantity(goodEntity.getAdult().intValue());
			saleType.setChildQuantity(goodEntity.getChild().intValue());
			return saleType;
		}
		
		if(goodsBo.getPackages() != null) {//自主打包
			for(StampPackageGoodsBo packageBo : goodsBo.getPackages()) {
				if(HhsConstants.LINE.equals(packageBo.getGroupType())) {
					if(CollectionUtils.isEmpty(packageBo.getGroups()))
						throw new RuntimeException("Data issue, can not find line group based on stampId : " + stampId);
					
					for(StampGroupGoodsBo groupGoods : packageBo.getGroups()) {
						Long groupId = groupGoods.getGroupId();
						ProdPackageGroupEntity groupEntity = prodPackageGroupRepository.findOne(groupId);
						if(groupEntity == null) 
							throw new RuntimeException("Data issue, can not package group based on groupId : " + groupId);
						BizCategoryEntity groupCategory = bizCategoryRepository.findOne(groupEntity.getCategoryId().longValue());
						if(groupCategory == null) 
							throw new RuntimeException("Data issue, can not category based on categoryId : " + groupEntity.getCategoryId().longValue());
						
						LOGGER.info("calc sale type category code: {}, groupId: {}", groupCategory.getCategoryCode(), groupId);
						
						if("category_route_hotelcomb".equals(groupCategory.getCategoryCode())) { //酒店套餐
							ProdPackageGroupLineEntity groupLineEntity = prodPackageGroupLineRepository.getFirstByGroupId(new BigDecimal(groupId));
							if(groupLineEntity == null) 
								throw new RuntimeException("Data issue, can not package group line based on groupId : " + groupId);
							
							ProductSaleType saleType = new ProductSaleType();
							saleType.setSaleType(SaleType.COPIES.name());
							saleType.setAdultQuantity(groupLineEntity.getAdult().intValue());
							saleType.setChildQuantity(groupLineEntity.getChild().intValue());
							return saleType;
						}
					}
				}
			}
		}
		
		ProdProductSaleReEntity saleRe = prodProductSaleReRepository.getFirstByProductId(new BigDecimal(productId));
		if(saleRe != null) {
			ProductSaleType saleType = new ProductSaleType();
			saleType.setSaleType(saleRe.getSaleType());
			saleType.setAdultQuantity(saleRe.getAdult().intValue());
			saleType.setChildQuantity(saleRe.getChild().intValue());
			return saleType;
		}
		
		ProductSaleType saleType = new ProductSaleType();
		saleType.setSaleType(SaleType.PEOPLE.name()); //按人卖
		return saleType;
	}
	

	@Override
	public HotelValidationResponse validHotel(Long productId, HotelValidationRequest request) {
		checkParams(request);
		
		if(request.getStartDate().equals(request.getEndDate())) {
			throw new RuntimeException("the startDate and endDate should not be same.");
		}
		List<PresaleStampDefinitionGoodsBindingEntity> goodList = stampGoodsService.getStampGoodsBindingByStampId(request.getStampId());
		if(CollectionUtils.isEmpty(goodList)) {
			throw new RuntimeException("can not find goods binding based on stampId : " + request.getStampId());
		}
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Date startDate = null, endDate = null;
		try {
			startDate = format.parse(request.getStartDate());
		} catch (Exception e) {
			throw new RuntimeException("invalid date format of startDate: " + request.getStartDate());
		}
		try {
			endDate = format.parse(request.getEndDate());
		} catch (Exception e) {
			throw new RuntimeException("invalid date format of endDate: " + request.getEndDate());
		}
		if(startDate.after(endDate)) {
			throw new RuntimeException("start date should be than less end date");
		}
		StampDetails details = stampService.getStampById(request.getStampId());
		if(details == null) 
			throw new RuntimeException("can not find stamp details based on stampId: " + request.getStampId());
		
		PresaleStampDefinitionGoodsBindingEntity goodBinding = goodList.get(0);
		List<PresaleStampTimePrice> presaleTimePrices = suppGoodsTimePriceClientService.getPresaleStampTimePricesBygoodsIdAndTime(NumberUtils.toLong(goodBinding.getGoodsId()), startDate, endDate);
		if(CollectionUtils.isEmpty(presaleTimePrices))
			throw new RuntimeException("can not find presale time price, goodsId: " + goodBinding.getGoodsId() + ", startDate: " + request.getStartDate() + ", endDate: " + request.getEndDate());
		
		boolean valid = true;
		String message = null;
		
		Calendar c = Calendar.getInstance();
		c.setTime(startDate);
		String startDateString = request.getStartDate();
		SuppGoodsTimePrice timeprice = null;
		do {
			if(!StampUtils.checkDuration(details.getAssociatedProdAvailTimeSlot(), c.getTime())) {
				message = HotelValidateMessage.NOT_ONSALE.name();
				LOGGER.info("the hotel is not on sale, goodsId: {}, startDate: {}", goodBinding.getGoodsId(), startDateString);
				valid = false;
				break;
			}
			ResultHandleT<SuppGoodsTimePrice> timePriceResult = suppGoodsTimePriceClientService.getTimePrice(NumberUtils.toLong(goodBinding.getGoodsId()), c.getTime(), true);
			if(timePriceResult.isSuccess() && (timeprice = timePriceResult.getReturnContent()) != null) {
				if("N".equals(timeprice.getOnsaleFlag())) {
					message = HotelValidateMessage.NOT_ONSALE.name();
					LOGGER.info("the hotel is not on sale, goodsId: {}, startDate: {}", goodBinding.getGoodsId(), startDateString);
					valid = false;
					break;
				}
				if(timeprice.getStock() != null && timeprice.getStock() < request.getRoom() && "N".equals(timeprice.getOversellFlag())) {
					message = HotelValidateMessage.OUT_OF_STOCK.name();
					LOGGER.info("the hotel is out of stock, goodsId: {}, startDate: {}", goodBinding.getGoodsId(), startDateString);
					valid = false;
					break;
				}
				boolean hasPresaleTimePrice = false;
				String priceTime = format.format(timeprice.getSpecDate());
				for(PresaleStampTimePrice presaleTimePrice : presaleTimePrices) {
					if(priceTime.equals(format.format(presaleTimePrice.getApplyDate()))) {
						hasPresaleTimePrice = true;
						break;
					}
				}
				if(!hasPresaleTimePrice) {
					message = HotelValidateMessage.NOT_ONSALE.name();
					LOGGER.info("can not find presale time price, goodsId: {}, startDate: {}", goodBinding.getGoodsId(), priceTime);
					valid = false;
					break;
				}
			} else {
				valid = false;
				LOGGER.info(timePriceResult.getMsg());
				message = HotelValidateMessage.NOT_ONSALE.name();
				break;
			}
			c.add(Calendar.DATE, 1);
			startDateString = format.format(c.getTime());
		} while(!startDateString.equals(request.getEndDate()));
		
		
		HotelValidationResponse response = new HotelValidationResponse();
		response.setStatus(valid ? "valid" : "invalid");
		response.setMessage(message);
		response.setEarliestArriveTime(getHotelEarliestArriveTime(productId));
		return response;
	}

	@Override
	public String getHotelEarliestArriveTime(Long productId) {
		return getProductPropValue(productId, 32L);// hard code, 32 is the earliest arrive time
	}
	
	@Override
	public String getHotelLatestLeaveTime(Long productId) {
		return getProductPropValue(productId, 31L);// hard code, 31 is the latest leave time
	}
	
	private String getProductPropValue(Long productId, Long propId) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("propId", propId);
		params.put("productId", productId);
		ResultHandleT<List<ProdProductProp>> propResult = prodProductPropClientService.findProdProductPropList(params);
		
		String propValue = null;
		List<ProdProductProp> props = new ArrayList<ProdProductProp>();
		if(propResult.isSuccess() && (props = propResult.getReturnContent()) != null) {
			if(props.size() > 0) 
				propValue = props.get(0).getPropValue();
		} else {
			LOGGER.info(propResult.getMsg());
		}
		return propValue;
	}
	
	private void checkParams(HotelValidationRequest request) {
		if(StringUtils.isBlank(request.getStampId())) {
			throw new RuntimeException("stampId should not be null");
		} 
		if(request.getRoom() < 1) {
			throw new RuntimeException("room should more than 0");
		}
		if(StringUtils.isBlank(request.getStartDate())) {
			throw new RuntimeException("start date should not be null");
		}
		if(StringUtils.isBlank(request.getEndDate())) {
			throw new RuntimeException("start date should not be null");
		}
	}

    @Override
    @Transactional("oracleTransactionManager")
    public int updateGoodsBu(String bu, Long goodsId) {
        return suppGoodsRepository.updateGoodsBu(bu, goodsId);
    }

    @Override
    @Transactional("oracleTransactionManager")
    public ProdProductEntity getProduct(Long id) {       
        return productRepository.findOne(id);
    }
	
	@Override
	public List<HotelTimePriceDate> getHotelCalendar(Long productId, String stampId) {
		StampDefinitionEntity stampDefinition = stampService.getStampDefinitionById(stampId);
		if(stampDefinition == null) {
			LOGGER.info("can not find stamps based on stampId: {}", stampId);
			return null;
		}
		StampAssociatedProdAvailTimeSlotVo prodAvailTimeSlot = (StampAssociatedProdAvailTimeSlotVo) HhsUtils.jsonToObject(stampDefinition.getAssociatedProdAvailTimeslot(), StampAssociatedProdAvailTimeSlotVo.class);
		if(prodAvailTimeSlot == null) {
			LOGGER.info("can not find the available time slots, stampId: {}", stampId);
			return null;
		}
		List<StampDuration> durations = StampUtils.translateAvailTimeslot(prodAvailTimeSlot);
		if(CollectionUtils.isEmpty(durations)) {
			LOGGER.info("can not find stamps durations based on stampId: {}", stampId);
			return null;
		}
		List<PresaleStampDefinitionGoodsBindingEntity> goodEntitys = stampGoodsService.getStampGoodsBindingByStampId(stampId);
		if(CollectionUtils.isEmpty(goodEntitys)) {
			LOGGER.info("can not find goods binding based on stampId: {}", stampId);
			return null;
		}
		
		Long goodsId = NumberUtils.toLong(goodEntitys.get(0).getGoodsId());
		Date startDate = durations.get(0).getStartDate();
		Date endDate = StampUtils.getStartDate(durations.get(durations.size() - 1).getEndDate());
		Date today = StampUtils.getStartDate(new Date());
		if(startDate.before(today)) {
			startDate = today;
		}
		
		Map<Date, SuppGoodsTimePrice> timePriceMap = new HashMap<Date, SuppGoodsTimePrice>();
		ResultHandleT<List<SuppGoodsTimePrice>> timePriceResult = suppGoodsTimePriceClientService.getTimePriceAfterSpecDate(goodsId, startDate);
		List<SuppGoodsTimePrice> timePrices = null;
		if(timePriceResult.isSuccess() && (timePrices = timePriceResult.getReturnContent()) != null) {
			for(SuppGoodsTimePrice timePrice : timePrices) {
				if(timePrice.getSpecDate().after(endDate))
					break;
				if("N".equals(timePrice.getOnsaleFlag())) 
					continue;
				if("N".equals(timePrice.getOversellFlag()) && timePrice.getStock() != null && timePrice.getStock() == 0) 
					continue;
				if(!StampUtils.checkDuration(durations, timePrice.getSpecDate())) 
					continue;
				timePriceMap.put(timePrice.getSpecDate(), timePrice);
			}
		} else {
			LOGGER.info(timePriceResult.getMsg());
		}
		
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		
		List<PresaleStampTimePrice> presaleTimePrices = suppGoodsTimePriceClientService.getPresaleStampTimePricesBygoodsIdAndTime(goodsId, startDate, endDate);
		List<String> presaleDates = new ArrayList<String>();
		if(CollectionUtils.isNotEmpty(presaleTimePrices)) {
			for(PresaleStampTimePrice timePrice : presaleTimePrices) {
				presaleDates.add(format.format(timePrice.getApplyDate()));
			}
		}
		
		List<HotelTimePriceDate> hotelDates = new ArrayList<HotelTimePriceDate>();
		if(startDate.getTime() == endDate.getTime()) {
			HotelTimePriceDate timePrice = new HotelTimePriceDate();
			timePrice.setDate(format.format(startDate));
			if(timePriceMap.containsKey(startDate) && presaleDates.contains(timePrice.getDate())) {
				timePrice.setStatus("valid");
				timePrice.setStock(timePriceMap.get(startDate).getStock());
				timePrice.setPrice(timePriceMap.get(startDate).getPrice());
			} else {
				timePrice.setStatus("invalid");
			}
			hotelDates.add(timePrice);
		} else {
			long diff = DateUtil.diffDay(startDate, endDate) + 1;
			Calendar c = Calendar.getInstance();
			c.setTime(startDate);
			for(int i=0; i<diff; i++) {
				Date theDate = c.getTime();
				HotelTimePriceDate timePrice = new HotelTimePriceDate();
				timePrice.setDate(format.format(theDate));
				if(timePriceMap.containsKey(theDate) && presaleDates.contains(timePrice.getDate())) {
					timePrice.setStatus("valid");
					timePrice.setStock(timePriceMap.get(theDate).getStock());
					timePrice.setPrice(timePriceMap.get(theDate).getPrice());
				} else {
					timePrice.setStatus("invalid");
				}
				hotelDates.add(timePrice);
				c.add(Calendar.DATE, 1);
			}
		}
		return hotelDates;
	}

    @Override
    @Transactional("oracleTransactionManager")
    public List<ChangeHotelVo> getChangeHotel(Long productId) {
        List<ProdPackageGroupHotelEntity> packageGroupHotels = prodPackageGroupHotelRepository.getByProductId(productId);
        if(CollectionUtils.isEmpty(packageGroupHotels)) {
            LOGGER.info("can not find package group hotel, productId: {}", productId);
            return null;
        }
        List<Long> groupIds = extractPackageGroupHotelId(packageGroupHotels);
        Map<Long, List<SuppGoodEntity>> groupGoodsMap = new HashMap<Long, List<SuppGoodEntity>>();
        for(Long groupId : groupIds) {
            groupGoodsMap.put(groupId, suppGoodsRepository.getGroupHotelByGroupId(groupId));
        }
        return getChangeHotel(groupGoodsMap, productId);
    }
    
    @Override
    @Transactional("oracleTransactionManager")
    public List<ChangeHotelVo> getChangeHotel(Map<Long, List<SuppGoodEntity>> groupGoodsMap, Long productId) {
        if(MapUtils.isEmpty(groupGoodsMap))
            return null;
        List<ProdPackageGroupHotelEntity> groupHotelEntitys = prodPackageGroupHotelRepository.getByGroupIdIn(StampUtils.LongCollectionToBigDecimal(groupGoodsMap.keySet()));
        if(CollectionUtils.isEmpty(groupHotelEntitys)) {
            LOGGER.info("can not find package group hotel, groupHotelIds: {}", groupGoodsMap.keySet());
            return null;
        }
        List<ProdProductBranchEntity> productBranchs = productBranchRepository.getByProductId(new BigDecimal(productId));
        if(CollectionUtils.isEmpty(productBranchs)) {
            LOGGER.info("can not find product branch, productId: {}", productId);
            return null;
        }

        Map<Long, ProdPackageGroupHotelEntity> groupHotelEntityMap = translatePackageGroupHotel(groupHotelEntitys);
        Map<Long, ProdProductBranchEntity> productBranchMap = translateProductBranch(productBranchs);
        
        List<Long> productBranchIds = new ArrayList<Long>();
        productBranchIds.addAll(productBranchMap.keySet());

        List<BranchPropVO> branchProps = getProductBranchProps(productBranchIds);
        Map<Long, List<BranchPropVO>> propMap = translateProductBranchProp(branchProps);
        
        List<ChangeHotelVo> changeHotels = new ArrayList<ChangeHotelVo>();
        
        for(Entry<Long, List<SuppGoodEntity>> entry : groupGoodsMap.entrySet()) {
            Long groupId = entry.getKey();
            List<SuppGoodEntity> goodsList = entry.getValue();
            
            ProdPackageGroupHotelEntity groupHotelEntity = groupHotelEntityMap.get(groupId);
            if(groupHotelEntity == null) {
                LOGGER.info("groupHotelEntity is null, groupId: {}", groupId);
                continue;
            }
           
            for(SuppGoodEntity goodsEntity : goodsList) {
                ProdProductBranchEntity productBranchEntity = productBranchMap.get(goodsEntity.getProductBranchId().longValue());
                if(productBranchEntity == null) {
                    LOGGER.info("productBranchEntity is null, productBranchId: {}", goodsEntity.getProductBranchId().longValue());
                    continue;
                }               
                ProdProductEntity product = getProduct(goodsEntity.getProductId().longValue());
                ChangeHotelVo hotel = new ChangeHotelVo();
                hotel.setProductBranchId(productBranchEntity.getProductBranchId());
                hotel.setBranchName(productBranchEntity.getBranchName());
                hotel.setGoodsId(goodsEntity.getSuppGoodsId());
                hotel.setGoodsName(goodsEntity.getGoodsName());              
                hotel.setGroupId(groupHotelEntity.getGroupId().longValue());
                hotel.setProps(propMap.get(productBranchEntity.getProductBranchId()));
                hotel.setCategoryId(String.valueOf(product.getCategoryId()));
                if(StringUtils.isNotEmpty(groupHotelEntity.getStayDays())){
                    String[] days = groupHotelEntity.getStayDays().split(",");
                    if(days.length>1){
                        hotel.setStart(days[0]);
                        hotel.setEnd(days[days.length-1]);
                    }else{
                        hotel.setStart(days[0]);
                        hotel.setEnd(days[0]);
                    }
                }
                changeHotels.add(hotel);
            }
            
        }
        return changeHotels;
    }
    
    @Override
    @Transactional("oracleTransactionManager")
    public ProdPackageGroupTransportEntity getPackageGroupTransport(Long groupId) {
    	return prodPackageGroupTransportRepository.getFirstByGroupId(new BigDecimal(groupId));
    }
    
    @Override
    @Transactional("oracleTransactionManager")
    public ProdProductBranchEntity getProductBranch(Long goodsId) {
    	return productBranchRepository.getByGoodsId(goodsId);
    }
    
    @Override
    @Transactional("oracleTransactionManager")
    public int updateProductAboutName(StampGoodsRelationVo relation, String name) {
        int result = 0;
        if(StringUtils.isNotEmpty(relation.getProductId())){
            result =  productRepository.updateProdProductGoodsName(name, Long.valueOf(relation.getProductId()));
        }
        if(StringUtils.isNotEmpty(relation.getProductBranchId())){
            result = productBranchRepository.updateProdProductBranchBranchName(name, Long.valueOf(relation.getProductBranchId()));
        }
        if(StringUtils.isNotEmpty(relation.getGoodsId())){
            result = suppGoodsRepository.updateGoodsName(name, Long.valueOf(relation.getGoodsId()));
        }
        return result;
    }
    
}
