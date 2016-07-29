/**
 * 
 */
package com.lvmama.vst.hhs.model.product;

import java.io.Serializable;
import java.util.List;

/**
 * @author fengyonggang
 *
 */
public class PackageLine implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String category;//跟团游 自由行 当地游 酒店套餐
	private List<PackageLineGroup> groups;
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public List<PackageLineGroup> getGroups() {
		return groups;
	}
	public void setGroups(List<PackageLineGroup> groups) {
		this.groups = groups;
	}
	
}
