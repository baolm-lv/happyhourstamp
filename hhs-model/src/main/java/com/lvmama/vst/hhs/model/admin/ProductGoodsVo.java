/**
 * 
 */
package com.lvmama.vst.hhs.model.admin;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author fengyonggang
 *
 */
public class ProductGoodsVo implements Serializable {

	private static final long serialVersionUID = 1L;

	private BizCategoryVo category;
	private ProductVo product;
	private List<SuppGoodsVo> primaryGoods;
	private List<PackageHotelVo> packageHotels;
	private List<PackageLineVo> packageLines;
	private List<PackageTicketVo> packageTickets;
	private List<PackageTransportVo> packageTransports;
	private List<HotelGoodsVo> hotelGoods;

	public BizCategoryVo getCategory() {
		return category;
	}

	public void setCategory(BizCategoryVo category) {
		this.category = category;
	}

	public ProductVo getProduct() {
		return product;
	}

	public void setProduct(ProductVo product) {
		this.product = product;
	}

	public List<SuppGoodsVo> getPrimaryGoods() {
		return primaryGoods;
	}

	public void setPrimaryGoods(List<SuppGoodsVo> primaryGoods) {
		this.primaryGoods = primaryGoods;
	}

	public List<PackageHotelVo> getPackageHotels() {
		return packageHotels;
	}

	public void setPackageHotels(List<PackageHotelVo> packageHotels) {
		this.packageHotels = packageHotels;
	}

	public List<PackageLineVo> getPackageLines() {
		return packageLines;
	}

	public void setPackageLines(List<PackageLineVo> packageLines) {
		this.packageLines = packageLines;
	}

	public List<PackageTicketVo> getPackageTickets() {
		return packageTickets;
	}

	public void setPackageTickets(List<PackageTicketVo> packageTickets) {
		this.packageTickets = packageTickets;
	}

	public List<PackageTransportVo> getPackageTransports() {
		return packageTransports;
	}

	public void setPackageTransports(List<PackageTransportVo> packageTransports) {
		this.packageTransports = packageTransports;
	}
	
	public void addPackageHotels(PackageHotelVo packageHotel) {
		if(packageHotel == null) 
			return ;
		if(packageHotels == null) 
			packageHotels = new ArrayList<PackageHotelVo>();
		packageHotels.add(packageHotel);
	}

	public void addPackageLines(PackageLineVo packageLine) {
		if(packageLine == null) 
			return ;
		if(packageLines == null)
			packageLines = new ArrayList<PackageLineVo>();
		packageLines.add(packageLine);
	}
	
	public void addPackageTickets(PackageTicketVo vo) {
		if(vo == null) 
			return ;
		if(packageTickets == null) 
			packageTickets = new ArrayList<PackageTicketVo>();
		packageTickets.add(vo);
	}
	
	public void addPackageTransports(PackageTransportVo vo) {
		if(vo == null) 
			return ;
		if(packageTransports == null) 
			packageTransports = new ArrayList<PackageTransportVo>();
		packageTransports.add(vo);
	}

	public List<HotelGoodsVo> getHotelGoods() {
		return hotelGoods;
	}

	public void setHotelGoods(List<HotelGoodsVo> hotelGoods) {
		this.hotelGoods = hotelGoods;
	}
	
}
