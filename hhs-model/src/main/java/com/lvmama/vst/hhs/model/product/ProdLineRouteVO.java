package com.lvmama.vst.hhs.model.product;

import java.io.Serializable;

public class ProdLineRouteVO implements Serializable {
	
	private static final long serialVersionUID = 1L;

	private Long lineRouteId;

    private String routeName;

	public Long getLineRouteId() {
		return lineRouteId;
	}

	public void setLineRouteId(Long lineRouteId) {
		this.lineRouteId = lineRouteId;
	}

	public String getRouteName() {
		return routeName;
	}

	public void setRouteName(String routeName) {
		this.routeName = routeName;
	}
    

}