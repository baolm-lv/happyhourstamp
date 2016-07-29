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
public class ProdTrafficVO implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private List<ProdTrafficGroup> prodTrafficGroupList;
	private ProdTraffic prodTraffic;
	public List<ProdTrafficGroup> getProdTrafficGroupList() {
		return prodTrafficGroupList;
	}
	public void setProdTrafficGroupList(List<ProdTrafficGroup> prodTrafficGroupList) {
		this.prodTrafficGroupList = prodTrafficGroupList;
	}
	public ProdTraffic getProdTraffic() {
		return prodTraffic;
	}
	public void setProdTraffic(ProdTraffic prodTraffic) {
		this.prodTraffic = prodTraffic;
	}
}
