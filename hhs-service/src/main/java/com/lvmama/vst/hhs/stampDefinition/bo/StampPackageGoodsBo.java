/**
 * 
 */
package com.lvmama.vst.hhs.stampDefinition.bo;

import java.util.List;

/**
 * @author fengyonggang
 *
 */
public class StampPackageGoodsBo {

	private String groupType;
	private List<StampGroupGoodsBo> groups;
	public String getGroupType() {
		return groupType;
	}
	public void setGroupType(String groupType) {
		this.groupType = groupType;
	}
	public List<StampGroupGoodsBo> getGroups() {
		return groups;
	}
	public void setGroups(List<StampGroupGoodsBo> groups) {
		this.groups = groups;
	}
}
