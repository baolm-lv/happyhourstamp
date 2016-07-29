package com.lvmama.vst.hhs.product.dao;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;


/**
 * The persistent class for the SUPP_GOODS_RELATION database table.
 * 
 */
@Entity
@Table(name="SUPP_GOODS_RELATION")
@NamedQuery(name="SuppGoodsRelationEntity.findAll", query="SELECT s FROM SuppGoodsRelationEntity s")
public class SuppGoodsRelationEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="RELATION_ID")
	private long relationId;

	@Column(name="MAIN_GOODS_ID")
	private BigDecimal mainGoodsId;

	@Column(name="MAX_QUANTITY")
	private BigDecimal maxQuantity;

	@Column(name="RELATION_TYPE")
	private String relationType;

	@Column(name="SEC_GOODS_ID")
	private BigDecimal secGoodsId;

	public SuppGoodsRelationEntity() {
	}

	public long getRelationId() {
		return this.relationId;
	}

	public void setRelationId(long relationId) {
		this.relationId = relationId;
	}

	public BigDecimal getMainGoodsId() {
		return this.mainGoodsId;
	}

	public void setMainGoodsId(BigDecimal mainGoodsId) {
		this.mainGoodsId = mainGoodsId;
	}

	public BigDecimal getMaxQuantity() {
		return this.maxQuantity;
	}

	public void setMaxQuantity(BigDecimal maxQuantity) {
		this.maxQuantity = maxQuantity;
	}

	public String getRelationType() {
		return this.relationType;
	}

	public void setRelationType(String relationType) {
		this.relationType = relationType;
	}

	public BigDecimal getSecGoodsId() {
		return this.secGoodsId;
	}

	public void setSecGoodsId(BigDecimal secGoodsId) {
		this.secGoodsId = secGoodsId;
	}

}