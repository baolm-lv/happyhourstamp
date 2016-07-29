/**
 * 
 */
package com.lvmama.vst.hhs.model.admin;

import java.io.Serializable;

/**
 * @author fengyonggang
 *
 */
public class ProductVo implements Serializable {

	private static final long serialVersionUID = 1L;
	private Long productId;
	private String auditStatus;
	private String cancelFlag;
	private Long categoryId;
	private String companyType;
	private String createUser;
	private Long districtId;
	private String muiltDepartureFlag;
	private String packageType;
	private String productName;
	private String productType;
	private String saleFlag;
	private String sensitiveFlag;
	private String source;
	private Long subCategoryId;
	private String suppProductName;
	private ProductSaleTypeVo saleType;
	public Long getProductId() {
		return productId;
	}
	public void setProductId(Long productId) {
		this.productId = productId;
	}
	public String getAuditStatus() {
		return auditStatus;
	}
	public void setAuditStatus(String auditStatus) {
		this.auditStatus = auditStatus;
	}
	public String getCancelFlag() {
		return cancelFlag;
	}
	public void setCancelFlag(String cancelFlag) {
		this.cancelFlag = cancelFlag;
	}
	public Long getCategoryId() {
		return categoryId;
	}
	public void setCategoryId(Long categoryId) {
		this.categoryId = categoryId;
	}
	public String getCompanyType() {
		return companyType;
	}
	public void setCompanyType(String companyType) {
		this.companyType = companyType;
	}
	public String getCreateUser() {
		return createUser;
	}
	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}
	public Long getDistrictId() {
		return districtId;
	}
	public void setDistrictId(Long districtId) {
		this.districtId = districtId;
	}
	public String getMuiltDepartureFlag() {
		return muiltDepartureFlag;
	}
	public void setMuiltDepartureFlag(String muiltDepartureFlag) {
		this.muiltDepartureFlag = muiltDepartureFlag;
	}
	public String getPackageType() {
		return packageType;
	}
	public void setPackageType(String packageType) {
		this.packageType = packageType;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public String getProductType() {
		return productType;
	}
	public void setProductType(String productType) {
		this.productType = productType;
	}
	public String getSaleFlag() {
		return saleFlag;
	}
	public void setSaleFlag(String saleFlag) {
		this.saleFlag = saleFlag;
	}
	public String getSensitiveFlag() {
		return sensitiveFlag;
	}
	public void setSensitiveFlag(String sensitiveFlag) {
		this.sensitiveFlag = sensitiveFlag;
	}
	public String getSource() {
		return source;
	}
	public void setSource(String source) {
		this.source = source;
	}
	public Long getSubCategoryId() {
		return subCategoryId;
	}
	public void setSubCategoryId(Long subCategoryId) {
		this.subCategoryId = subCategoryId;
	}
	public String getSuppProductName() {
		return suppProductName;
	}
	public void setSuppProductName(String suppProductName) {
		this.suppProductName = suppProductName;
	}
    public ProductSaleTypeVo getSaleType() {
        return saleType;
    }
    public void setSaleType(ProductSaleTypeVo saleType) {
        this.saleType = saleType;
    }
	
	
	
}
