/**
 * 
 */
package com.lvmama.vst.hhs.model.admin;

import java.io.Serializable;

/**
 * @author baolm
 *
 */
public class StampActiveRequest implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5734631874166795584L;

	private String id;
	private String activityStatus;
	private String operationName;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getActivityStatus() {
		return activityStatus;
	}

	public void setActivityStatus(String activityStatus) {
		this.activityStatus = activityStatus;
	}

    public String getOperationName() {
        return operationName;
    }

    public void setOperationName(String operationName) {
        this.operationName = operationName;
    }
	
	

}
