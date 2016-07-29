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
public class UseStampCodeResponse extends BaseResponse {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    
    private List<UseStampCodeInfo> useStampCodes;

    public List<UseStampCodeInfo> getUseStampCodes() {
        return useStampCodes;
    }

    public void setUseStampCodes(List<UseStampCodeInfo> useStampCodes) {
        this.useStampCodes = useStampCodes;
    }

}
