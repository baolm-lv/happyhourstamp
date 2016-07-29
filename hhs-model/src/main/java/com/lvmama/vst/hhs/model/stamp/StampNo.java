/**
 * 
 */
package com.lvmama.vst.hhs.model.stamp;

import java.io.Serializable;

/**
 * @author fengyonggang
 *
 */
public class StampNo implements Serializable {

	private static final long serialVersionUID = 1L;
	private String id;
	private String stampDefinitionId;
	private String name;
	private String serialNumber;
	private String customerId;
	private String stampStatus;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getStampDefinitionId() {
		return stampDefinitionId;
	}
	public void setStampDefinitionId(String stampDefinitionId) {
		this.stampDefinitionId = stampDefinitionId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSerialNumber() {
		return serialNumber;
	}
	public void setSerialNumber(String serialNumber) {
		this.serialNumber = serialNumber;
	}
	public String getCustomerId() {
		return customerId;
	}
	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}
	public String getStampStatus() {
		return stampStatus;
	}
	public void setStampStatus(String stampStatus) {
		this.stampStatus = stampStatus;
	}
}
