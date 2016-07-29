package com.lvmama.vst.hhs.product.dao;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;


/**
 * The persistent class for the PROD_PRODUCT_SALE_RE database table.
 * 
 */
@Entity
@Table(name="PROD_PRODUCT_SALE_RE")
@NamedQuery(name="ProdProductSaleReEntity.findAll", query="SELECT p FROM ProdProductSaleReEntity p")
public class ProdProductSaleReEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="PROD_SALE_TYPE_ID")
	private long prodSaleTypeId;

	private BigDecimal adult;

	private BigDecimal child;

	@Column(name="PRODUCT_ID")
	private BigDecimal productId;

	@Column(name="SALE_TYPE")
	private String saleType;

	public ProdProductSaleReEntity() {
	}

	public long getProdSaleTypeId() {
		return this.prodSaleTypeId;
	}

	public void setProdSaleTypeId(long prodSaleTypeId) {
		this.prodSaleTypeId = prodSaleTypeId;
	}

	public BigDecimal getAdult() {
		return this.adult;
	}

	public void setAdult(BigDecimal adult) {
		this.adult = adult;
	}

	public BigDecimal getChild() {
		return this.child;
	}

	public void setChild(BigDecimal child) {
		this.child = child;
	}

	public BigDecimal getProductId() {
		return this.productId;
	}

	public void setProductId(BigDecimal productId) {
		this.productId = productId;
	}

	public String getSaleType() {
		return this.saleType;
	}

	public void setSaleType(String saleType) {
		this.saleType = saleType;
	}

}