/**
 * 
 */
package com.lvmama.vst.hhs.stamp.service;

import java.util.List;

import com.lvmama.vst.hhs.model.common.Constant.OperateType;
import com.lvmama.vst.hhs.stampDefinition.dao.StampOperationLogEntity;

/**
 * @author baolm
 *
 */
public interface StampOperationLogService {

	/**
	 * 记录券码操作日志
	 * 
	 * @param operateType
	 *            {@link OperateType}
	 * @param objectId
	 * @param stampCode
	 * @param remark
	 * @return
	 */
	boolean addLog(OperateType operateType, String objectId, String stampCode, String remark);

	int addLogList(List<StampOperationLogEntity> logList);
	
	/**
	 * 由线程调用
	 */
	void batchInsert(List<StampOperationLogEntity> logList);
}
