package com.lvmama.vst.hhs.product.dao;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;


/**
 * The persistent class for the BIZ_DISTRICT database table.
 * 
 */
@Entity
@Table(name="BIZ_DISTRICT")
@NamedQuery(name="BizDistrictEntity.findAll", query="SELECT b FROM BizDistrictEntity b")
public class BizDistrictEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="DISTRICT_ID")
	private long districtId;

	@Column(name="CANCEL_FLAG")
	private String cancelFlag;

	@Column(name="CITY_NAME")
	private String cityName;

	@Column(name="CITY_PIN_YIN")
	private String cityPinYin;

	@Column(name="DISTRICT_ALIAS")
	private String districtAlias;

	@Column(name="DISTRICT_NAME")
	private String districtName;

	@Column(name="DISTRICT_TYPE")
	private String districtType;

	@Column(name="EN_NAME")
	private String enName;

	@Column(name="FOREIGN_FLAG")
	private String foreignFlag;

	@Column(name="HOTEL_NUM")
	private BigDecimal hotelNum;

	@Column(name="LOCAL_LANG")
	private String localLang;

	@Column(name="PARENT_ID")
	private BigDecimal parentId;

	private String pinyin;

	@Column(name="PROVINCE_NAME")
	private String provinceName;

	@Column(name="PROVINCE_PIN_YIN")
	private String provincePinYin;

	@Column(name="SHORT_PINYIN")
	private String shortPinyin;

	@Column(name="URL_PIN_YIN")
	private String urlPinYin;

	public BizDistrictEntity() {
	}

	public long getDistrictId() {
		return this.districtId;
	}

	public void setDistrictId(long districtId) {
		this.districtId = districtId;
	}

	public String getCancelFlag() {
		return this.cancelFlag;
	}

	public void setCancelFlag(String cancelFlag) {
		this.cancelFlag = cancelFlag;
	}

	public String getCityName() {
		return this.cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public String getCityPinYin() {
		return this.cityPinYin;
	}

	public void setCityPinYin(String cityPinYin) {
		this.cityPinYin = cityPinYin;
	}

	public String getDistrictAlias() {
		return this.districtAlias;
	}

	public void setDistrictAlias(String districtAlias) {
		this.districtAlias = districtAlias;
	}

	public String getDistrictName() {
		return this.districtName;
	}

	public void setDistrictName(String districtName) {
		this.districtName = districtName;
	}

	public String getDistrictType() {
		return this.districtType;
	}

	public void setDistrictType(String districtType) {
		this.districtType = districtType;
	}

	public String getEnName() {
		return this.enName;
	}

	public void setEnName(String enName) {
		this.enName = enName;
	}

	public String getForeignFlag() {
		return this.foreignFlag;
	}

	public void setForeignFlag(String foreignFlag) {
		this.foreignFlag = foreignFlag;
	}

	public BigDecimal getHotelNum() {
		return this.hotelNum;
	}

	public void setHotelNum(BigDecimal hotelNum) {
		this.hotelNum = hotelNum;
	}

	public String getLocalLang() {
		return this.localLang;
	}

	public void setLocalLang(String localLang) {
		this.localLang = localLang;
	}

	public BigDecimal getParentId() {
		return this.parentId;
	}

	public void setParentId(BigDecimal parentId) {
		this.parentId = parentId;
	}

	public String getPinyin() {
		return this.pinyin;
	}

	public void setPinyin(String pinyin) {
		this.pinyin = pinyin;
	}

	public String getProvinceName() {
		return this.provinceName;
	}

	public void setProvinceName(String provinceName) {
		this.provinceName = provinceName;
	}

	public String getProvincePinYin() {
		return this.provincePinYin;
	}

	public void setProvincePinYin(String provincePinYin) {
		this.provincePinYin = provincePinYin;
	}

	public String getShortPinyin() {
		return this.shortPinyin;
	}

	public void setShortPinyin(String shortPinyin) {
		this.shortPinyin = shortPinyin;
	}

	public String getUrlPinYin() {
		return this.urlPinYin;
	}

	public void setUrlPinYin(String urlPinYin) {
		this.urlPinYin = urlPinYin;
	}

}