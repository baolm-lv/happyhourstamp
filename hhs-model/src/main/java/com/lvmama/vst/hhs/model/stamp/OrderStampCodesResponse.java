/**
 * 
 */
package com.lvmama.vst.hhs.model.stamp;

import java.util.List;

import com.lvmama.vst.hhs.common.web.BaseResponse;

/**
 * @author "baolm"
 *
 */
public class OrderStampCodesResponse extends BaseResponse {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    private List<StampCode> stampCodes;

    public List<StampCode> getStampCodes() {
        return stampCodes;
    }

    public void setStampCodes(List<StampCode> stampCodes) {
        this.stampCodes = stampCodes;
    }
}
