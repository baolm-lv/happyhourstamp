package com.lvmama.vst.hhs.product.service;


import java.util.Date;
import java.util.List;
import java.util.Map;

import com.lvmama.vst.back.prod.po.ProdProduct;
import com.lvmama.vst.hhs.model.admin.ChangeHotelVo;
import com.lvmama.vst.hhs.model.admin.StampGoodsRelationVo;
import com.lvmama.vst.hhs.model.product.BranchPropVO;
import com.lvmama.vst.hhs.model.product.ChangeHotel;
import com.lvmama.vst.hhs.model.product.HotelTimePriceDate;
import com.lvmama.vst.hhs.model.product.HotelValidationRequest;
import com.lvmama.vst.hhs.model.product.HotelValidationResponse;
import com.lvmama.vst.hhs.model.product.ProdGroupDateVO;
import com.lvmama.vst.hhs.model.product.ProdProductModel;
import com.lvmama.vst.hhs.model.product.ProductSaleType;
import com.lvmama.vst.hhs.model.product.VisitCalendarRequest;
import com.lvmama.vst.hhs.model.stamp.AdditionalGoods;
import com.lvmama.vst.hhs.model.stamp.ProductDetailsStamps;
import com.lvmama.vst.hhs.product.dao.ProdPackageGroupTransportEntity;
import com.lvmama.vst.hhs.product.dao.ProdProductBranchEntity;
import com.lvmama.vst.hhs.product.dao.ProdProductEntity;
import com.lvmama.vst.hhs.product.dao.SuppGoodEntity;

public interface ProductService {

	ProdProductEntity findById(long id);
	
	SuppGoodEntity getSuppGoodEntity(long suppGoodsId);
	
	ProdProduct getProductById(Long id);
	
	List<ProdProduct> findProdProductList(Map<String, Object> params);
	
	int countFindProdProductList(Map<String, Object> params);
	
	Long   addProduct(ProdProduct product);
	
	List<ProdProductModel> queryProdProductList(Integer productId,String productName);
	
	/**
	 * 查询时间价格表
	 * @param productId
	 * @param startDistrictId  出发地
	 * @param startMonth 起始时间: yyyyMMdd
	 * @param endMonth 结束时间: yyyyMMdd
	 * @return
	 */
	List<ProdGroupDateVO> checkAndGetProdGroupDates(Long productId, Long startDistrictId, String startDate, String endDate);
	
	/**
	 * 查询产品详情页预售券信息
	 * @param productId
	 * @param startDistrictId
	 * @return
	 */
	ProductDetailsStamps getProductStampsDetails(Long productId, Long startDistrictId);
	
	/**
	 * 查询可换酒店
	 * @param groupGoodsMap
	 * @param productId
	 * @param visitTime
	 * @return
	 */
	List<ChangeHotel> getChangeHotel(Map<Long, List<SuppGoodEntity>> groupGoodsMap, Date visitTime);
	
	/**
	 * 查询可换酒店
	 * @param productId
	 * @return
	 */
	List<ChangeHotel> getChangeHotel(Long productId, Date visitTime);
	
	/**
	 * 查询附加
	 * @param productId
	 * @return
	 */
	List<AdditionalGoods> getAdditionGoods(Long productId, Date visitDate, int quantity);
	
	List<ProdGroupDateVO> getVisitCalendar(Long productId, VisitCalendarRequest request);

	List<BranchPropVO> getProductBranchProps(List<Long> productBranchIds);
	
	ProductSaleType getProductSaleTypeByStampId(String stampId);

	HotelValidationResponse validHotel(Long productId, HotelValidationRequest request);
	
	String getHotelLatestLeaveTime(Long productId);
	
	String getHotelEarliestArriveTime(Long productId);
	
	int updateGoodsBu(String bu,Long goodsId);
	
	ProdProductEntity getProduct(Long id);
	
	List<HotelTimePriceDate> getHotelCalendar(Long productId, String stampId);
	
	/**
     * 后台查询可换酒店
     * @param productId
     * @return
     */
    List<ChangeHotelVo> getChangeHotel(Long productId);
    
    /**
     * 查询可换酒店
     * @param groupGoodsMap
     * @param productId
     * @param visitTime
     * @return
     */
    List<ChangeHotelVo> getChangeHotel(Map<Long, List<SuppGoodEntity>> groupGoodsMap, Long productId);
  
    ProdPackageGroupTransportEntity getPackageGroupTransport(Long groupId);
    
    ProdProductBranchEntity getProductBranch(Long goodsId);
    
    int updateProductAboutName(StampGoodsRelationVo relation,String name);
    
}
