/**
 * 
 */
package com.lvmama.vst.hhs.model.admin;

import java.io.Serializable;

/**
 * @author fengyonggang
 *
 */
public class BizCategoryVo implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private long categoryId;
	private String cancelFlag;
	private String categoryCode;
	private String categoryDesc;
	private String categoryName;
	private Long parentId;
	private String processKey;
	private String promTarget;
	private Long seq;
	
	public long getCategoryId() {
		return categoryId;
	}
	public void setCategoryId(long categoryId) {
		this.categoryId = categoryId;
	}
	public String getCancelFlag() {
		return cancelFlag;
	}
	public void setCancelFlag(String cancelFlag) {
		this.cancelFlag = cancelFlag;
	}
	public String getCategoryCode() {
		return categoryCode;
	}
	public void setCategoryCode(String categoryCode) {
		this.categoryCode = categoryCode;
	}
	public String getCategoryDesc() {
		return categoryDesc;
	}
	public void setCategoryDesc(String categoryDesc) {
		this.categoryDesc = categoryDesc;
	}
	public String getCategoryName() {
		return categoryName;
	}
	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}
	public Long getParentId() {
		return parentId;
	}
	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}
	public String getProcessKey() {
		return processKey;
	}
	public void setProcessKey(String processKey) {
		this.processKey = processKey;
	}
	public String getPromTarget() {
		return promTarget;
	}
	public void setPromTarget(String promTarget) {
		this.promTarget = promTarget;
	}
	public Long getSeq() {
		return seq;
	}
	public void setSeq(Long seq) {
		this.seq = seq;
	}
	
}
