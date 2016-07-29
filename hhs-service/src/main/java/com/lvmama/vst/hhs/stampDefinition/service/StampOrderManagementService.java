/**
 * 
 */
package com.lvmama.vst.hhs.stampDefinition.service;

import java.util.Date;
import java.util.List;

import com.lvmama.vst.hhs.model.admin.StampOrderVo;

/**
 * @author "baolm"
 *
 */
public interface StampOrderManagementService {
	
	StampOrderVo getById(String id);

	List<StampOrderVo> queryStampOrder(Integer orderId,String userName,String mobile,int start,int end);
	
	long queryStampOrder(Integer orderId,String userName,String mobile);
	
	void updateStampOrderLastPayTime(Long orderId,Date lastPayTime);

}
