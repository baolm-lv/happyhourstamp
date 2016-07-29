/**
 * 
 */
package com.lvmama.vst.hhs.downgrade;

import java.io.Serializable;
import java.util.List;

/**
 * @author fengyonggang
 *
 */
public class DownGradeRequest implements Serializable {

	private static final long serialVersionUID = 1L;
	private List<String> ids;
	private List<String> methods;
	private boolean local;
	
	public List<String> getIds() {
		return ids;
	}
	public void setIds(List<String> ids) {
		this.ids = ids;
	}
	public List<String> getMethods() {
		return methods;
	}
	public void setMethods(List<String> methods) {
		this.methods = methods;
	}
	public boolean isLocal() {
		return local;
	}
	public void setLocal(boolean local) {
		this.local = local;
	}
}
