/**
 * 
 */
package com.lvmama.vst.hhs.product.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeMap;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lvmama.vst.back.biz.po.BizBranch;
import com.lvmama.vst.back.biz.po.BizCategory;
import com.lvmama.vst.back.client.biz.service.BranchClientService;
import com.lvmama.vst.back.client.dist.service.DistDistributorProdClientService;
import com.lvmama.vst.back.client.prod.service.ProdCalClientService;
import com.lvmama.vst.back.client.prod.service.ProdProductBranchClientService;
import com.lvmama.vst.back.client.prod.service.ProdProductBranchPropClientService;
import com.lvmama.vst.back.client.prod.service.ProdProductClientService;
import com.lvmama.vst.back.goods.po.SuppGoods;
import com.lvmama.vst.back.goods.po.SuppGoodsSaleRe;
import com.lvmama.vst.back.goods.vo.ProdProductParam;
import com.lvmama.vst.back.prod.po.ProdPackageDetail;
import com.lvmama.vst.back.prod.po.ProdPackageGroup;
import com.lvmama.vst.back.prod.po.ProdProduct;
import com.lvmama.vst.back.prod.po.ProdProductBranch;
import com.lvmama.vst.back.prod.po.ProdProductBranchProp;
import com.lvmama.vst.back.prod.po.ProdProductSaleRe;
import com.lvmama.vst.back.prod.vo.PackageTourProductVo;
import com.lvmama.vst.comm.vo.ResultHandle;
import com.lvmama.vst.comm.vo.ResultHandleT;
import com.lvmama.vst.hhs.common.utils.HhsConstants;
import com.lvmama.vst.hhs.model.product.BranchPropVO;
import com.lvmama.vst.hhs.model.product.InsuranceGoods;
import com.lvmama.vst.hhs.model.product.InsuranceProduct;
import com.lvmama.vst.hhs.model.product.PriceGap;
import com.lvmama.vst.hhs.model.product.ProductAddition;

/**
 * @author fengyonggang
 *
 */
@Service
public class ProductAdditionServiceImpl implements ProductAdditionService{
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ProductAdditionServiceImpl.class);
	@Autowired
	private DistDistributorProdClientService distDistributorProdClientRemote;
	@Autowired
	private ProdCalClientService prodCalClientRemote;
	@Autowired
	private BranchClientService branchClientService;
	@Autowired
	private ProdProductClientService productClientService;
	@Autowired
	protected ProdProductBranchPropClientService prodProductBranchPropClientService;
	@Autowired
	protected ProdProductBranchClientService prodProductBranchClientRemote;

	/**
     * 验证产品的销售渠道
     * @param productId
     * @return
     * @throws Exception
     */
    protected boolean verfiedProdDistributor(Long productId, Long distributorId) {
        Map<String, Object> prodDistributorMap = new HashMap<String, Object>();
        prodDistributorMap.put("productId", productId);
        //prodDistributorMap.put("cancelFlag", "Y");
        prodDistributorMap.put("distributorId", distributorId);
        ResultHandle resultHandle = distDistributorProdClientRemote.verfiedProdDistributor(prodDistributorMap);
        return resultHandle.isSuccess();
    }
    
    /**
	  * 获取规格
	  * @param branchId
	  * @return
	  * @throws Exception
	  */
	protected BizBranch getBizBranchById(Long branchId) {
		// 判断产品主规格有效
		ResultHandleT<BizBranch> branchHandler = branchClientService.findBranchById(branchId);
		if (branchHandler == null || branchHandler.getReturnContent() == null) {
			throw new RuntimeException("调用接口 branchClientRemote.findBranchById[" + branchId + " ]失败，" + branchHandler.getMsg());
		}
		return branchHandler.getReturnContent();
	}
	
	/**
	 * 加载房差 和 保险
	 * @param adultQuantity
	 * @param childQuantity
	 * @param quantity
	 * @param selectDateStr
	 * @param productId
	 * @param startDistrictId
	 * @param addition
	 */
	public void loadingAdditions(Long adultQuantity, Long childQuantity,
            Long quantity,Date visitDate, Long productId, Long startDistrictId, List<Long> changeHotelGoods, ProductAddition addition) {
		
		//验证销售渠道是不是驴妈妈前台
        if(!verfiedProdDistributor(productId, 3L)) {
        	throw new RuntimeException("the distributor is not correct.");
        }
		
        if(startDistrictId != null)
        	startDistrictId = startDistrictId==-1L?null:startDistrictId;
        //得到线路产品的信息
        ProdProductParam param = new ProdProductParam();
        param.setActivity(true);
        param.setComPhoto(true);
        param.setFeature(true);
        param.setViewSpot(true);
        param.setServiceRe(true);
        param.setHotelCombFlag(true);//用来判断酒店套餐，如果没有，不进行判断
        param.setBizDistrictId(startDistrictId);
        ResultHandleT<ProdProduct> productHandleT = productClientService.findLineProductByProductId(productId, param);
        if(productHandleT == null || productHandleT.getReturnContent() == null){
            throw new RuntimeException("can not load the ProdProduct based on productId: " + productId);
        }
        ProdProduct product = productHandleT.getReturnContent();
        
        String hotelCombFlag = product.getHotelCombFlag();//商品loading的方式
        PackageTourProductVo ackageTourProductVo = null;
        
        Long adultQuantityParam = 0L;
        Long childQuantityParam = 0L;
        String saleType = product.getProdProductAdditionSaleType();
        if(hotelCombFlag != null && hotelCombFlag.equalsIgnoreCase("N")){
            //如果产品中有套餐
            Long baseAdultQuantity = product.getBaseAdultQuantity();
            Long baseChildQuantity = product.getBaseChildQuantity();
            Long quantityLong = quantity;
            adultQuantityParam = quantityLong*baseAdultQuantity;
            childQuantityParam = quantityLong*baseChildQuantity;
        }else if(saleType.startsWith("COPIES")){
            ProdProductSaleRe saleRe = product.getProdProductSaleReList().get(0);
            Long baseAdultQuantity = (long)saleRe.getAdult();
            Long baseChildQuantity = (long)saleRe.getChild();
            Long quantityLong = quantity;
            adultQuantityParam = quantityLong*baseAdultQuantity;
            childQuantityParam = quantityLong*baseChildQuantity;
        }else{
            //产品中没有套餐
            adultQuantityParam =adultQuantity;
            childQuantityParam = childQuantity;
        }
        Map<String, Object> parameter = new HashMap<String, Object>();
        parameter.put("productId",product.getProductId());
        parameter.put("specDate",visitDate);
        parameter.put("adultQuantity",adultQuantityParam);
        parameter.put("childQuantity",childQuantityParam);
        parameter.put("distributorId",3L);
        //如果是多出发地，则增加参数-->出发地ID
        if("Y".equals(product.getMuiltDpartureFlag())){
        	parameter.put("startDistrictId",startDistrictId);
        }
        //查询方法变更为同名新方法，以Map形式传递参数 add by 20150702
        ResultHandleT<PackageTourProductVo> returnT = prodCalClientRemote.getPackageTourProductVo(parameter);
        if((ackageTourProductVo = returnT.getReturnContent()) == null){
        	LOGGER.info("can not get PackageTourProductVo by :{}", parameter);
        	throw new RuntimeException("can not load the PackageTourProductVo, " + returnT.getMsg());
        }

        String packageType = ackageTourProductVo.getPackageType();
        BizCategory bizCategory = product.getBizCategory();
        
        List<PriceGap> gapList = new ArrayList<PriceGap>();
        //供应商打包
        if(ProdProduct.PACKAGETYPE.SUPPLIER.name().equalsIgnoreCase(packageType)){
            if(bizCategory != null && "category_route_hotelcomb".equalsIgnoreCase(bizCategory.getCategoryCode())){
            	//酒店套餐
            }else{
                //当地游主规格没有在页面显示，但是要传给下单页面
                if(bizCategory != null ){
                    if("category_route_local".equalsIgnoreCase(bizCategory.getCategoryCode()) ||
                            "category_route_group".equalsIgnoreCase(bizCategory.getCategoryCode()) ||
                            "category_route_freedom".equalsIgnoreCase(bizCategory.getCategoryCode())){
                        List<ProdProductBranch> productBranchList = ackageTourProductVo.getProdProductBranchList();
                        if(productBranchList != null && productBranchList.size() > 0){
                            for(int i = 0 ; i < productBranchList.size() ; i++){
                                //只有一个规格，而且只有一个商品
                                ProdProductBranch prodProductBranch = productBranchList.get(i);
                                BizBranch bizBranch = getBizBranchById(prodProductBranch.getBranchId());
                                String attachFlag = bizBranch.getAttachFlag();
                                if("Y".equalsIgnoreCase(attachFlag)){
                                    List<SuppGoods> suppGoodsList = prodProductBranch.getSuppGoodsList();
                                    if(suppGoodsList != null && suppGoodsList.size() > 0){
                                        //房差
                                    	PriceGap gap = getPriceGap(prodProductBranch);
                                    	if(gap != null && gap.getPrice() > 0)
                                    		gapList.add(gap);
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        
        Map<String,List<ProdPackageGroup>> prodPackageGroupMap = ackageTourProductVo.getProdPackageGroupMap();
        if(MapUtils.isNotEmpty(prodPackageGroupMap)) {
        	for(Entry<String, List<ProdPackageGroup>> entry : prodPackageGroupMap.entrySet()) {
        		List<ProdPackageGroup> packageGroups = entry.getValue();
        		if(CollectionUtils.isNotEmpty(packageGroups)) {
        			for(ProdPackageGroup prodPackageGroup : packageGroups) {
        				List<ProdPackageDetail> packageDetails = prodPackageGroup.getProdPackageDetails();
        				if(CollectionUtils.isNotEmpty(packageDetails)) {
        					ProdPackageDetail prodPackageDetail = null;
        					if(HhsConstants.CHANGE.equals(prodPackageGroup.getGroupType())) {
        						if(CollectionUtils.isNotEmpty(changeHotelGoods)) {
        							for(ProdPackageDetail packageDetail : packageDetails) {
        								if(packageDetail.getSuppGoods() != null && changeHotelGoods.contains(packageDetail.getSuppGoods().getSuppGoodsId())) {
        									prodPackageDetail = packageDetail;
        									break;
        								}
        							}
        						}
        					} else {
        						prodPackageDetail = packageDetails.get(0);
        					}
        					if(prodPackageDetail != null) {
        						PriceGap gap = getPriceGap(prodPackageDetail.getProdProductBranch());
        						if(gap != null && gap.getPrice() > 0)
        							gapList.add(gap);
        					}
        				}
        			}
        		}
        	}
        }
        
        if(CollectionUtils.isNotEmpty(gapList)) {
        	int minQuantity = 0, maxQuantity = 0;
        	long fangchaTotal = 0L;
        	for(PriceGap gap : gapList) {
        		if(gap.getMaxQuantity() > 0) 
        			minQuantity = 1;
        		if(maxQuantity == 0) {
        			maxQuantity = gap.getMaxQuantity();
        		} else {
        			maxQuantity = Math.min(maxQuantity, gap.getMaxQuantity());
        		}
        		fangchaTotal += gap.getPrice();
        	}
        	PriceGap gap = new PriceGap();
        	gap.setMinQuantity(minQuantity);
        	gap.setMaxQuantity(maxQuantity);
        	gap.setPrice(fangchaTotal);
        	addition.setGapPrice(gap);
        }

        List<SuppGoodsSaleRe> suppGoodsSaleReList = ackageTourProductVo.getSuppGoodsSaleReList();
        if(suppGoodsSaleReList != null && suppGoodsSaleReList.size() > 0){
            //处理保险的
        	List<InsuranceProduct> insurances = new ArrayList<InsuranceProduct>();
            for(SuppGoodsSaleRe suppGoodsSaleRe : suppGoodsSaleReList){
                List<SuppGoods> insSuppGoodsList = suppGoodsSaleRe.getInsSuppGoodsList();
                if(insSuppGoodsList == null || insSuppGoodsList.size() == 0){
                    continue;
                }
                Map<Long, InsuranceProduct> insurProdMap = new HashMap<Long, InsuranceProduct>();
                for(SuppGoods suppGoods : insSuppGoodsList){
                	InsuranceProduct insurProd = null;
                	List<InsuranceGoods> insurGoods = null;
                	if(insurProdMap.containsKey(suppGoods.getProductId())) {
                		insurProd = insurProdMap.get(suppGoods.getProductId());
                		insurGoods = insurProd.getGoods();
                	} else {
                		insurGoods = new ArrayList<InsuranceGoods>();
                		insurProd = new InsuranceProduct();
                        insurProd.setProductId(suppGoods.getProductId());
                        insurProd.setProductName(suppGoods.getProdProduct().getProductName());
                        insurProd.setGoods(insurGoods);
                        insurProdMap.put(suppGoods.getProductId(), insurProd);
                	}
                	
                    InsuranceGoods insur = new InsuranceGoods();
                    insur.setGoodsId(suppGoods.getSuppGoodsId());
                    insur.setGoodsName(suppGoods.getGoodsName());
                    insur.setProductBranchId(suppGoods.getProductBranchId());
                    if(suppGoods.getProdProductBranch() != null)
                    	insur.setBranchName(suppGoods.getProdProductBranch().getBranchName());
                    insur.setQuantityRange(suppGoods.getSelectQuantityRange());
                    insur.setPrice(getInsurancePrice(suppGoods));
                    
                    Map<String,Object> branchPropParams = new HashMap<String,Object>();
                    branchPropParams.put("productBranchId",suppGoods.getProductBranchId());
                    ResultHandleT<List<ProdProductBranchProp>> result = prodProductBranchPropClientService.findProdProductBranchPropList(branchPropParams);
                    
                    List<ProdProductBranchProp> branchPropList = null;
                    if(result.isSuccess() && (branchPropList = result.getReturnContent()) != null) {
                    	List<BranchPropVO> props = new ArrayList<BranchPropVO>();
                    	for(ProdProductBranchProp branchProp : branchPropList){
                    		BranchPropVO prop = new BranchPropVO();
                    		prop.setProductBranchId(branchProp.getProductBranchId());
                    		prop.setProductBranchPropId(branchProp.getProductBranchPropId());
                    		prop.setPropCode(branchProp.getBizBranchProp().getPropCode());
                    		prop.setPropId(branchProp.getPropId());
                    		prop.setPropName(branchProp.getBizBranchProp().getPropName());
                    		prop.setPropValue(branchProp.getProdValue());
                    		props.add(prop);
                    	}
                    	insur.setProps(props);
                    }
                    insurGoods.add(insur);
                }
                insurances.addAll(insurProdMap.values());
            }
            addition.setInsurances(insurances);
        }
	}
	
	private PriceGap getPriceGap(ProdProductBranch prodProductBranch) {
		Map<String, Long> selectGapPriceMap = prodProductBranch.getSelectGapPriceMap();
        String selectGapQuantityRange = prodProductBranch.getSelectGapQuantityRange();
        if(selectGapPriceMap != null){
            Long price = 0L;
            Set<String> keys = selectGapPriceMap.keySet();
            for(String key : keys){
            	price = selectGapPriceMap.get(key);
            	break;
            }
            if(StringUtils.isNotBlank(selectGapQuantityRange)){
                int fangChaQuantity = 0;
                int fangChaQuantityMax = 0;//房差的最大值
                if(selectGapQuantityRange.startsWith("1")){
                    fangChaQuantity = 1;
                }
                String tempArray[] = selectGapQuantityRange.split(",");
                fangChaQuantityMax = Integer.valueOf(tempArray[tempArray.length -1]);

                PriceGap priceGap = new PriceGap();
                priceGap.setMinQuantity(fangChaQuantity);
                priceGap.setMaxQuantity(fangChaQuantityMax);
                priceGap.setPrice(price);
                return priceGap;
            }
        }
        return null;
	}
	
	private Long getInsurancePrice(SuppGoods suppGoods){
        Map<String, Long> selectPriceMap = suppGoods.getSelectPriceMap();//生成可选select
        Long singlePrice = 0L;
        if(selectPriceMap != null  && selectPriceMap.size() > 0){
            Map<String, Long> goodsMap = new TreeMap<String, Long>();
            Set<String> keys = selectPriceMap.keySet();
            for(String keyTemp : keys){
                goodsMap.put(keyTemp,selectPriceMap.get(keyTemp));
            }
            keys = goodsMap.keySet();
            String[] sortKeys = keys.toArray(new String[keys.size()]);
            Arrays.sort(sortKeys, new Comparator<String>() {
                public int compare(String o1, String o2) {
                    try {
                        return Integer.parseInt(o1.trim()) - Integer.parseInt(o2.trim());
                    }catch(Exception ex){return 0;}
                }
            });
            for(String keyTemp : sortKeys){
            	singlePrice = selectPriceMap.get(keyTemp);
            	return singlePrice;
            }
        }
        return singlePrice;
    }
}