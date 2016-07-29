/**
 * 
 */
package com.lvmama.vst.hhs.model.product;

import java.io.Serializable;

/**
 * @author fengyonggang
 *
 */
public class BranchPropVO implements Serializable {

	private static final long serialVersionUID = 1L;
	private Long productBranchPropId;
	private Long propId;
	private String propName;
	private String propValue;
	private String propCode;
	private Long productBranchId;
	
	public BranchPropVO() {}

	public BranchPropVO(Long productBranchPropId, Long propId, String propName, String propValue, Long productBranchId, String propCode) {
		super();
		this.productBranchPropId = productBranchPropId;
		this.propId = propId;
		this.propName = propName;
		this.propValue = propValue;
		this.productBranchId = productBranchId;
		this.propCode = propCode;
	}

	public Long getProductBranchPropId() {
		return productBranchPropId;
	}

	public void setProductBranchPropId(Long productBranchPropId) {
		this.productBranchPropId = productBranchPropId;
	}

	public Long getPropId() {
		return propId;
	}

	public void setPropId(Long propId) {
		this.propId = propId;
	}

	public String getPropName() {
		return propName;
	}

	public void setPropName(String propName) {
		this.propName = propName;
	}

	public String getPropValue() {
		return propValue;
	}

	public void setPropValue(String propValue) {
		this.propValue = propValue;
	}

	public Long getProductBranchId() {
		return productBranchId;
	}

	public void setProductBranchId(Long productBranchId) {
		this.productBranchId = productBranchId;
	}

	public String getPropCode() {
		return propCode;
	}

	public void setPropCode(String propCode) {
		this.propCode = propCode;
	}

}
