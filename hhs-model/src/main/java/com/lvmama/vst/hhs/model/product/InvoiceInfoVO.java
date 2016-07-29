/**
 * 
 */
package com.lvmama.vst.hhs.model.product;

/**
 * @author fengyonggang
 *
 */
public class InvoiceInfoVO {

	private boolean needFlag;
	private String title;
	private String content;
	private String contactPerson;
	private String contactTel;
	private String address;
	private String postCode;
	private String amount;
	private String deliveryType;// 送货方式
	private String invoiceType;
	private String companyName;

	public boolean isNeedFlag() {
		return needFlag;
	}

	public void setNeedFlag(boolean needFlag) {
		this.needFlag = needFlag;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getContactPerson() {
		return contactPerson;
	}

	public void setContactPerson(String contactPerson) {
		this.contactPerson = contactPerson;
	}

	public String getContactTel() {
		return contactTel;
	}

	public void setContactTel(String contactTel) {
		this.contactTel = contactTel;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPostCode() {
		return postCode;
	}

	public void setPostCode(String postCode) {
		this.postCode = postCode;
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public String getDeliveryType() {
		return deliveryType;
	}

	public void setDeliveryType(String deliveryType) {
		this.deliveryType = deliveryType;
	}

	public String getInvoiceType() {
		return invoiceType;
	}

	public void setInvoiceType(String invoiceType) {
		this.invoiceType = invoiceType;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	@Override
	public String toString() {
		return "InvoiceInfoVO [needFlag=" + needFlag + ", title=" + title + ", content=" + content + ", contactPerson="
				+ contactPerson + ", contactTel=" + contactTel + ", address=" + address + ", postCode=" + postCode
				+ ", amount=" + amount + ", deliveryType=" + deliveryType + ", invoiceType=" + invoiceType
				+ ", companyName=" + companyName + "]";
	}
}
