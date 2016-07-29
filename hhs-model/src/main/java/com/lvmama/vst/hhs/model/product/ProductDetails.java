/**
 * 
 */
package com.lvmama.vst.hhs.model.product;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import com.lvmama.vst.hhs.model.admin.BizCategoryVo;
import com.lvmama.vst.hhs.model.admin.ProductVo;

/**
 * @author fengyonggang
 *
 */
public class ProductDetails implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private BizCategoryVo category;
	private ProductVo product;
	private List<SuppGoods> primaryGoods; 				//供应商打包 主规格商品
	private List<PackageHotel> packageHotels;			//自主打包, 可换酒店
	private List<PackageLine> packageLines;				//自主打包
	private List<PackageTicket> packageTickets;			//自主打包
	private List<PackageTransport> packageTransports;	//自主打包
	private List<PackageHotel> changeHotels;			//自主打包 可换酒店
	private ProductSaleType saleType;					//按份卖 or 按人卖
	private List<Map> trafficInfoList;

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

	public List<SuppGoods> getPrimaryGoods() {
		return primaryGoods;
	}

	public void setPrimaryGoods(List<SuppGoods> primaryGoods) {
		this.primaryGoods = primaryGoods;
	}

	public List<PackageHotel> getPackageHotels() {
		return packageHotels;
	}

	public void setPackageHotels(List<PackageHotel> packageHotels) {
		this.packageHotels = packageHotels;
	}

	public List<PackageLine> getPackageLines() {
		return packageLines;
	}

	public void setPackageLines(List<PackageLine> packageLines) {
		this.packageLines = packageLines;
	}

	public List<PackageTicket> getPackageTickets() {
		return packageTickets;
	}

	public void setPackageTickets(List<PackageTicket> packageTickets) {
		this.packageTickets = packageTickets;
	}

	public List<PackageTransport> getPackageTransports() {
		return packageTransports;
	}

	public void setPackageTransports(List<PackageTransport> packageTransports) {
		this.packageTransports = packageTransports;
	}

	public ProductSaleType getSaleType() {
		return saleType;
	}

	public void setSaleType(ProductSaleType saleType) {
		this.saleType = saleType;
	}
	
	public void addPackageHotels(Collection<PackageHotel> packageHotels) {
		if(packageHotels == null) 
			return ;
		if(this.packageHotels == null) 
			this.packageHotels = new ArrayList<PackageHotel>();
		this.packageHotels.addAll(packageHotels);
	}
	
	public void addPackageLines(List<PackageLine> packageLines) {
		if(packageLines == null) 
			return ;
		if(this.packageLines == null) 
			this.packageLines = new ArrayList<PackageLine>();
		this.packageLines.addAll(packageLines);
	}

	public void addPackageTickets(List<PackageTicket> packageTickets) {
		if(packageTickets == null) 
			return ;
		if(this.packageTickets == null) 
			this.packageTickets = new ArrayList<PackageTicket>();
		this.packageTickets.addAll(packageTickets);
	}
	
	public void addPackageTransports(List<PackageTransport> packageTransports) {
		if(packageTransports == null) 
			return ;
		if(this.packageTransports == null) 
			this.packageTransports = new ArrayList<PackageTransport>();
		this.packageTransports.addAll(packageTransports);
	}

	public List<PackageHotel> getChangeHotels() {
		return changeHotels;
	}

	public void setChangeHotels(List<PackageHotel> changeHotels) {
		this.changeHotels = changeHotels;
	}

    public List<Map> getTrafficInfoList() {
        return trafficInfoList;
    }

    public void setTrafficInfoList(List<Map> trafficInfoList) {
        this.trafficInfoList = trafficInfoList;
    }
	
	
}
