package com.lvmama.vst.hhs.model.product;

import java.io.Serializable;
import java.util.Date;

public class ProdGroupDateVO implements Serializable {

	private static final long serialVersionUID = 1L;

	private Date specDate;

    private Long stock;
    
    private Long lowestSaledPrice;
    
    private Long lowestSaledChildPrice = null;
    
	private Long apiSaledPrice; // 对接的成人售价
    
    private ProdLineRouteVO prodLineRoute;
    
	private Long apiChildPrice; // 对接的儿童售价
	
	private String stampFlag; //预售券标示： 1: 正在预售， 2: 预售预约

	public Date getSpecDate() {
		return specDate;
	}

	public void setSpecDate(Date specDate) {
		this.specDate = specDate;
	}

	public Long getStock() {
		return stock;
	}

	public void setStock(Long stock) {
		this.stock = stock;
	}

	public Long getLowestSaledPrice() {
		return lowestSaledPrice;
	}

	public void setLowestSaledPrice(Long lowestSaledPrice) {
		this.lowestSaledPrice = lowestSaledPrice;
	}

	public Long getLowestSaledChildPrice() {
		return lowestSaledChildPrice;
	}

	public void setLowestSaledChildPrice(Long lowestSaledChildPrice) {
		this.lowestSaledChildPrice = lowestSaledChildPrice;
	}

	public Long getApiSaledPrice() {
		return apiSaledPrice;
	}

	public void setApiSaledPrice(Long apiSaledPrice) {
		this.apiSaledPrice = apiSaledPrice;
	}

	public ProdLineRouteVO getProdLineRoute() {
		return prodLineRoute;
	}

	public void setProdLineRoute(ProdLineRouteVO prodLineRoute) {
		this.prodLineRoute = prodLineRoute;
	}

	public Long getApiChildPrice() {
		return apiChildPrice;
	}

	public void setApiChildPrice(Long apiChildPrice) {
		this.apiChildPrice = apiChildPrice;
	}

	public String getStampFlag() {
		return stampFlag;
	}

	public void setStampFlag(String stampFlag) {
		this.stampFlag = stampFlag;
	}

	public static ProdGroupDateVO build(Date date) {
		ProdGroupDateVO vo = new ProdGroupDateVO();
		vo.setApiChildPrice(2000L);
		vo.setApiSaledPrice(3000L);
		vo.setLowestSaledChildPrice(2500L);
		vo.setLowestSaledPrice(2500L);
		ProdLineRouteVO route = new ProdLineRouteVO();
		route.setLineRouteId(100L);
		route.setRouteName("线路123");
		vo.setProdLineRoute(route);
		vo.setSpecDate(date);
		vo.setStampFlag("1");
		vo.setStock(10L);
		return vo;
	}
}