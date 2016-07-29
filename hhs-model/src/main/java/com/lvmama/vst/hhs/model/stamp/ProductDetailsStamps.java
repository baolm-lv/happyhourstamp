/**
 * 
 */
package com.lvmama.vst.hhs.model.stamp;

import java.io.Serializable;
import java.util.List;

import com.lvmama.vst.hhs.model.product.ProdProductAddtionalVO;

/**
 * @author fengyonggang
 *
 */
public class ProductDetailsStamps implements Serializable {

	private static final long serialVersionUID = 1L;

	private ProdProductAddtionalVO prodProductAddtional;// 附加表(起价)

	private List<StampDetails> stmaps; // 券信息

	public ProdProductAddtionalVO getProdProductAddtional() {
		return prodProductAddtional;
	}

	public void setProdProductAddtional(ProdProductAddtionalVO prodProductAddtional) {
		this.prodProductAddtional = prodProductAddtional;
	}

	public List<StampDetails> getStmaps() {
		return stmaps;
	}

	public void setStmaps(List<StampDetails> stmaps) {
		this.stmaps = stmaps;
	}

}
