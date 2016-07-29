/**
 * 
 */
package com.lvmama.vst.hhs.stampDefinition.dts;

import com.lvmama.vst.hhs.common.web.BaseResponse;
import com.lvmama.vst.hhs.model.admin.StampActiveRequest;

/**
 * @author baolm
 *
 */
public interface IStampManagementDts {

	BaseResponse updateStampActivityStatus(StampActiveRequest request);

}
