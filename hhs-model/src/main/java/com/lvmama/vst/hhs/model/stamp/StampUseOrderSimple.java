/**
 * 
 */
package com.lvmama.vst.hhs.model.stamp;

import java.io.Serializable;

/**
 * @author "baolm"
 *
 */
public class StampUseOrderSimple implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private String useOrderId;
    private String unbindStatus;

    public String getUseOrderId() {
        return useOrderId;
    }

    public void setUseOrderId(String useOrderId) {
        this.useOrderId = useOrderId;
    }

    public String getUnbindStatus() {
        return unbindStatus;
    }

    public void setUnbindStatus(String unbindStatus) {
        this.unbindStatus = unbindStatus;
    }

}
