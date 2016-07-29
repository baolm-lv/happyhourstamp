package com.lvmama.vst.hhs.stampDefinition.service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.UUID;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lvmama.vst.back.client.pub.service.ComLogClientService;
import com.lvmama.vst.back.pub.po.ComLog.COM_LOG_LOG_TYPE;
import com.lvmama.vst.back.pub.po.ComLog.COM_LOG_OBJECT_TYPE;
import com.lvmama.vst.hhs.common.utils.HhsConstants;
import com.lvmama.vst.hhs.model.admin.AddStampGoodsRelationRequest;
import com.lvmama.vst.hhs.model.admin.ChangeHotelVo;
import com.lvmama.vst.hhs.model.admin.PackageHotelVo;
import com.lvmama.vst.hhs.model.admin.PackageLineVo;
import com.lvmama.vst.hhs.model.admin.PackageProductBranchVo;
import com.lvmama.vst.hhs.model.admin.PackageProductVo;
import com.lvmama.vst.hhs.model.admin.PackageTicketVo;
import com.lvmama.vst.hhs.model.admin.PackageTransportVo;
import com.lvmama.vst.hhs.model.admin.ProductGoodsVo;
import com.lvmama.vst.hhs.model.admin.StampDetailsVo;
import com.lvmama.vst.hhs.model.admin.StampGoodsVo;
import com.lvmama.vst.hhs.model.admin.SuppGoodsVo;
import com.lvmama.vst.hhs.model.common.Constant.ActivityStatus;
import com.lvmama.vst.hhs.product.dao.ProdPackageDetailEntity;
import com.lvmama.vst.hhs.product.dao.ProdPackageGroupTransportEntity;
import com.lvmama.vst.hhs.product.dao.ProdProductEntity;
import com.lvmama.vst.hhs.product.dao.SuppGoodEntity;
import com.lvmama.vst.hhs.product.service.ProductDetailAdminService;
import com.lvmama.vst.hhs.product.service.ProductPackageService;
import com.lvmama.vst.hhs.product.service.ProductService;
import com.lvmama.vst.hhs.stampDefinition.bo.StampGoodsBindingBo;
import com.lvmama.vst.hhs.stampDefinition.bo.StampGroupGoodsBo;
import com.lvmama.vst.hhs.stampDefinition.bo.StampPackageGoodsBo;
import com.lvmama.vst.hhs.stampDefinition.dao.PresaleStampDefinitionGoodsBindingEntity;
import com.lvmama.vst.hhs.stampDefinition.dao.PresaleStampDefinitionProductBindingEntity;
import com.lvmama.vst.hhs.stampDefinition.dao.PresaleStampDefinitionProductRelationEntity;
import com.lvmama.vst.hhs.stampDefinition.dao.StampDefinitionEntity;
import com.lvmama.vst.hhs.stampDefinition.repository.PresaleStampDefinitionGoodsBindingRepository;
import com.lvmama.vst.hhs.stampDefinition.repository.PresaleStampDefinitionProductBindingRepository;
import com.lvmama.vst.hhs.stampDefinition.repository.StampDefinitionRepository;
import com.lvmama.vst.hhs.stampDefinition.repositorySlave.PresaleStampDefinitionGoodsBindingRepositorySlave;
import com.lvmama.vst.hhs.stampDefinition.repositorySlave.PresaleStampDefinitionProductRelationRepositorySlave;
import com.lvmama.vst.hhs.stampDefinition.repositorySlave.StampDefinitionRepositorySlave;

@Service
public class StampGoodsServiceImpl implements StampGoodsService{

	private static final Logger LOGGER = LoggerFactory.getLogger(StampGoodsServiceImpl.class);
	@Autowired
	private PresaleStampDefinitionGoodsBindingRepositorySlave goodsBindingRepositorySlave;
	@Autowired
	private StampDefinitionRepositorySlave stampDefinitionRepositorySlave;
    @Autowired
	private ProductService productService;
    @Autowired
    private PresaleStampDefinitionProductRelationRepositorySlave relationSlave;
    @Autowired
    private PresaleStampDefinitionGoodsBindingRepository goodsBindingRepository;
    @Autowired
    private StampDefinitionRepository stampDefinitionRepository;  
    @Autowired
    private PresaleStampDefinitionProductBindingRepository stampDefinitionProductBindingRepository;
    @Autowired
    private StampManagementService stampManagementService;
    @Autowired
    private ComLogClientService  comLogClientService;
    @Autowired
    private ProductDetailAdminService productDetailAdminService;
    @Autowired
    private ProductPackageService productPackageService;
    
	
	@Override
	@Transactional("slaveTransactionManager")
	public List<PresaleStampDefinitionGoodsBindingEntity> getStampGoodsBindingByStamp(StampDefinitionEntity stamp) {
		return goodsBindingRepositorySlave.getByStampDefinition(stamp);
	}
	
	@Override
	@Transactional("slaveTransactionManager")
	public List<PresaleStampDefinitionGoodsBindingEntity> getStampGoodsBindingByStampId(String stampId) {
		StampDefinitionEntity stamp = stampDefinitionRepositorySlave.findOne(stampId);
    	if(stamp == null) {
    		throw new RuntimeException("invalid stampId : " + stampId);
    	}
		return goodsBindingRepositorySlave.getByStampDefinition(stamp);
	}
	
	@Override
	@Transactional("slaveTransactionManager")
	public StampGoodsBindingBo getStampGoodsByStampId(String stampId) {
		List<PresaleStampDefinitionGoodsBindingEntity> goodsBindings = getStampGoodsBindingByStampId(stampId);
		if(goodsBindings == null) {
			LOGGER.info("no goods binding found, stampId: {}", stampId);
			return null;
		}
		
		StampGoodsBindingBo bo = new StampGoodsBindingBo();
		Map<String, List<StampGroupGoodsBo>> packageGoodsMap = new HashMap<String, List<StampGroupGoodsBo>>();
		for(PresaleStampDefinitionGoodsBindingEntity entity : goodsBindings) {
			if(StringUtils.isEmpty(entity.getBranchFlag())) {
				LOGGER.info("missing the branch flag for stamp goods binding, id: {}", entity.getId());
				continue;
			}
			
			if(entity.getBranchFlag().equals(HhsConstants.BRANCH_FLAG_PRIMARY)) {
				bo.setGoods(entity);
			} else if(entity.getBranchFlag().equals(HhsConstants.BRANCH_FLAG_PRIMARY_AUDLT)) {
				bo.setType(HhsConstants.AUDLT);
				bo.setGoods(entity);
			} else if(entity.getBranchFlag().equals(HhsConstants.BRANCH_FLAG_PRIMARY_CHILD)) {
				bo.setType(HhsConstants.CHILD);
				bo.setGoods(entity);
			} else {
				int lastUnderLineIndex = entity.getBranchFlag().lastIndexOf("_");
				if(lastUnderLineIndex < 1) {
					LOGGER.info("invalid branch flag: {}, id: {}", entity.getBranchFlag(), entity.getId());
					continue;
				}
				String groupId = entity.getBranchFlag().substring(lastUnderLineIndex + 1);
				String groupType = entity.getBranchFlag().substring(0, lastUnderLineIndex);
				
				StampGroupGoodsBo groupGood = new StampGroupGoodsBo();
				groupGood.setGroupId(NumberUtils.toLong(groupId));
				groupGood.setGoods(entity);
				
				if(packageGoodsMap.containsKey(groupType)) {
					packageGoodsMap.get(groupType).add(groupGood);
				} else {
					List<StampGroupGoodsBo> list = new ArrayList<StampGroupGoodsBo>();
					list.add(groupGood);
					packageGoodsMap.put(groupType, list);
				}
			}
		}
		if(packageGoodsMap.size() > 0) {
			List<StampPackageGoodsBo> packages = new ArrayList<StampPackageGoodsBo>();
			for(Entry<String, List<StampGroupGoodsBo>> entry : packageGoodsMap.entrySet()) {
				if(HhsConstants.CHANGE.equals(entry.getKey())) 
					continue;
				StampPackageGoodsBo packageGood = new StampPackageGoodsBo();
				packageGood.setGroupType(entry.getKey());
				packageGood.setGroups(entry.getValue());
				packages.add(packageGood);
			}
			
			//可换酒店排在最后, 可换酒店的visitTime需要取主商品的visitTime
			List<StampGroupGoodsBo> changeHotels = packageGoodsMap.get(HhsConstants.CHANGE);
			if(changeHotels != null) {
				StampPackageGoodsBo packageGood = new StampPackageGoodsBo();
				packageGood.setGroupType(HhsConstants.CHANGE);
				packageGood.setGroups(changeHotels);
				packages.add(packageGood);
			}
			
			bo.setPackages(packages);
		}
		return bo;
	}

	@Transactional("transactionManager")
    public StampDetailsVo addStampGoodsRelation(AddStampGoodsRelationRequest request) {
		String stampId = request.getStampId();
		StampDefinitionEntity stamp = stampDefinitionRepository.findOne(stampId);
		if (stamp == null) {
			throw new RuntimeException("invalid stampId : " + stampId);
		}
		ProdProductEntity product = null;
		if (stamp.getProductBinding() != null) {
			product = productService.getProduct(Long.valueOf(stamp.getProductBinding().getProductId()));
		}
		LOGGER.info("==add===time===============" + stamp.getRemindCustomerDate());
		List<PresaleStampDefinitionGoodsBindingEntity> goodsBindings = new ArrayList<PresaleStampDefinitionGoodsBindingEntity>();

		List<StampGoodsVo> goodsList = request.getGoodsList();

		if (goodsList == null) {
			throw new RuntimeException("the goods to be added is empty");
		}
        
        PresaleStampDefinitionProductRelationEntity relation = relationSlave.getByStampDefinitionId(stampId);
        
        long startDistrict = 0L; 
        
        for(StampGoodsVo goods : goodsList) {
            PresaleStampDefinitionGoodsBindingEntity goodsBinding = new PresaleStampDefinitionGoodsBindingEntity();
            goodsBinding.setBranchFlag(goods.getBranchType());
            
            Timestamp current = new Timestamp(System.currentTimeMillis());
            goodsBinding.setCreateDate(current);
            goodsBinding.setGoodsId(goods.getGoodsId());
            goodsBinding.setGoodsName(goods.getGoodsName());
            goodsBinding.setId(UUID.randomUUID().toString());
            goodsBinding.setStampDefinition(stamp);
            goodsBinding.setUpdateDate(current);
            goodsBinding.setCategoryId(goods.getCategoryId());
            goodsBindings.add(goodsBinding);
            if(product !=null){
                LOGGER.info("categoryId===================={}", product.getCategoryId());
                if(product.getCategoryId().intValue()==1){
                    if(StringUtils.isNotEmpty(goods.getGoodsId()) &&StringUtils.isNotEmpty(relation.getGoodsId())){
                        
                        SuppGoodEntity hotelGoods = productService.getSuppGoodEntity(Long.valueOf(goods.getGoodsId()));
                        LOGGER.info("======================="+hotelGoods.getBu()+"----------"+relation.getGoodsId());
                        productService.updateGoodsBu(hotelGoods.getBu(),Long.valueOf(relation.getGoodsId()));
                    }
                   
                }
            }
            if("Y".equals(product.getMuiltDepartureFlag()) && goods.getBranchType() != null && goods.getBranchType().startsWith(HhsConstants.TRANSPORT)) {
            	String groupId = goods.getBranchType().substring(HhsConstants.TRANSPORT.length() + 1);
            	ProdPackageGroupTransportEntity transportGroup = productService.getPackageGroupTransport(NumberUtils.toLong(groupId));
            	if(transportGroup != null && transportGroup.getToStartPoint() != null) {//去程
            		ProdPackageDetailEntity packageDetail = productPackageService.getProductPackageDetail(NumberUtils.toLong(groupId), NumberUtils.toLong(goods.getGoodsId()));
            		if(packageDetail != null) {
            			startDistrict = packageDetail.getStartDistrictId().longValue();
            		}
            	}
            }
        }

        //delete the existed goods
        goodsBindingRepository.deleteByStampDefinition(stamp);
        
        goodsBindings = goodsBindingRepository.save(goodsBindings);
        StampDefinitionEntity stamp1 = stampDefinitionRepository.findOne(stampId);
         LOGGER.info("==add===time===11============"+stamp1.getRemindCustomerDate());
        //update status to Y
        stampDefinitionRepository.updateStampActivityStatus(stampId, ActivityStatus.Y.name());
         LOGGER.info("==add===time========22======="+stamp1.getRemindCustomerDate());
        if(startDistrict != 0L) {
            PresaleStampDefinitionProductBindingEntity productBinding = stamp.getProductBinding();
            productBinding.setDepartId(String.valueOf(startDistrict));
            stampDefinitionProductBindingRepository.save(productBinding);
        }
        
        StampDetailsVo details = stampManagementService.getStampDetails(stamp);
        details.setStampGoods(getStampGoods(goodsBindings));
        String logs =bingGoodsLogs(stamp.getProductBinding(), goodsBindings);
        int stampHash = Math.abs(stamp.getId().hashCode());
         comLogClientService.insert(
                 COM_LOG_OBJECT_TYPE.PRE_SALE, new Long(stampHash),  new Long(stampHash),
                 request.getOperationName(), "修改预售券状态  预售券ID:" + stamp.getStampNo()+",修改状态为"+logs,
                 COM_LOG_LOG_TYPE.CHANGE_PRE_SALE.name(),
                 "预售券绑定商品和商品", null);
        return details;
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

    @Transactional("slaveTransactionManager")
    public List<StampGoodsVo> findStampGoods(String stampId) {
        StampDefinitionEntity stamp = stampDefinitionRepositorySlave.findOne(stampId);
        if(stamp == null) {
            throw new RuntimeException("invalid stampId : " + stampId);
        }
        return getStampGoods(goodsBindingRepositorySlave.getByStampDefinition(stamp));
    }
	
    private String bingGoodsLogs(PresaleStampDefinitionProductBindingEntity product,List<PresaleStampDefinitionGoodsBindingEntity> goodsBindings){
        StringBuilder logs = new StringBuilder();
        logs.append("产品Id").append(product.getProductId()).append(" 产品名称：").append(product.getProductName());
        for(PresaleStampDefinitionGoodsBindingEntity goods :goodsBindings){
            logs.append("商品Id").append(goods.getGoodsId()).append("商品名称").append(goods.getGoodsName());
        }
        return logs.toString();
    }

    @Override
    public boolean isModifyActivityStatus(String stampDefinitionId) {
        StampDefinitionEntity stamp = stampDefinitionRepositorySlave.findOne(stampDefinitionId);
        //产品的组
        ProductGoodsVo goodsVo = productDetailAdminService.getProductGoods(Long.valueOf(stamp.getProductBinding().getProductId()));
        //绑定的组
        List<StampGoodsVo> bindGoods =this.findStampGoods(stampDefinitionId);
        //产品组集合
        List<String> productGroupsIds = new ArrayList<String>();
        //产品集合
        List<String> goodsIds = new ArrayList<String>();        
        //绑定的组集合
        List<String> bindProductGroupsIds = new ArrayList<String>();
        //商品集合
        List<String> bindGoodsIds = new ArrayList<String>();        
        productGroupIdsAndGoodsIds(goodsVo,productGroupsIds,goodsIds);
        bindProductGroupIdsGoodsIds(bindGoods,bindProductGroupsIds,bindGoodsIds);
        
        return productGroupsIds.containsAll(bindProductGroupsIds) &&bindProductGroupsIds.containsAll(productGroupsIds)
                &&goodsIds.containsAll(bindGoodsIds);
    }
    
    //货品的分组和Ids
    private void productGroupIdsAndGoodsIds(ProductGoodsVo goodsVo,List<String> productGroupsIds,List<String> goodsIds){
        
        //
        List<SuppGoodsVo> primaryGoods = goodsVo.getPrimaryGoods();
        if(CollectionUtils.isNotEmpty(primaryGoods)){
            for(SuppGoodsVo goods:primaryGoods){
                goodsIds.add(String.valueOf(goods.getGoodsId()));
            }
            
        }
        List<PackageHotelVo> packageHotels = goodsVo.getPackageHotels();
        List<PackageLineVo> packageLines = goodsVo.getPackageLines();
        List<PackageTicketVo> packageTickets = goodsVo.getPackageTickets();
        List<PackageTransportVo> packageTransports = goodsVo.getPackageTransports();
//        List<HotelGoodsVo> hotelGoods = goodsVo.getHotelGoods();
        
        if(CollectionUtils.isNotEmpty(packageHotels)){
            for(PackageHotelVo v:packageHotels){
                productGroupsIds.add(String.valueOf(v.getGroupId()));
                for(PackageProductVo p :v.getProducts()){
                    for(PackageProductBranchVo b:p.getProductBranches()){
                        for(SuppGoodsVo g:b.getGoods()){
                            goodsIds.add(String.valueOf(v.getGroupId())+"-"+g.getGoodsId());
                        }
                    }
                 
                }
            }
        }
        
        if(CollectionUtils.isNotEmpty(packageLines)){
            for(PackageLineVo v:packageLines){
                productGroupsIds.add(String.valueOf(v.getGroupId()));
                for(PackageProductVo p :v.getProducts()){
                    for(PackageProductBranchVo b:p.getProductBranches()){
                        for(SuppGoodsVo g:b.getGoods()){
                            goodsIds.add(String.valueOf(v.getGroupId())+"-"+g.getGoodsId());
                        }
                    }
                   //可换酒店
                    if(CollectionUtils.isNotEmpty(p.getChangeHoteles())){
                        for(ChangeHotelVo hvo:p.getChangeHoteles()){
                            productGroupsIds.add(String.valueOf(hvo.getGroupId()));
                            goodsIds.add(String.valueOf(hvo.getGroupId())+"-"+hvo.getGoodsId());
                        }
                    }
                  
                }
            }
        }
        
        if(CollectionUtils.isNotEmpty(packageTickets)){
            for(PackageTicketVo v:packageTickets){
                productGroupsIds.add(String.valueOf(v.getGroupId()));
                for(PackageProductVo p :v.getProducts()){
                    for(PackageProductBranchVo b:p.getProductBranches()){
                        for(SuppGoodsVo g:b.getGoods()){
                            goodsIds.add(String.valueOf(v.getGroupId())+"-"+g.getGoodsId());
                        }
                    }
                 
                }
            }
        }
        
        if(CollectionUtils.isNotEmpty(packageTransports)){
            for(PackageTransportVo v:packageTransports){
                productGroupsIds.add(String.valueOf(v.getGroupId()));
                for(PackageProductVo p :v.getProducts()){
                    for(PackageProductBranchVo b:p.getProductBranches()){
                        for(SuppGoodsVo g:b.getGoods()){
                            goodsIds.add(String.valueOf(v.getGroupId())+"-"+g.getGoodsId());
                        }
                    }
                 
                }
            }
        }
        
    }
    
    private void bindProductGroupIdsGoodsIds(List<StampGoodsVo> bindGoods,List<String> bindProductGroupsIds,List<String> bindGoodsIds){
        for(StampGoodsVo goods : bindGoods) {
            if(HhsConstants.BRANCH_FLAG_PRIMARY_CHILD.equals(goods.getBranchType())) {
              
                bindGoodsIds.add(goods.getGoodsId());
            } else if(HhsConstants.BRANCH_FLAG_PRIMARY_AUDLT.equals(goods.getBranchType())) {
                bindGoodsIds.add(goods.getGoodsId());
            } else if(goods.getBranchType().indexOf("_")>0){
                bindProductGroupsIds.add(goods.getBranchType().substring(goods.getBranchType().lastIndexOf("_")+1));
                bindGoodsIds.add(goods.getBranchType().substring(goods.getBranchType().lastIndexOf("_")+1)+"-"+goods.getGoodsId());
            }else{
                bindGoodsIds.add(goods.getGoodsId());
            }
        }
    }
    
}
