/**
 * 
 */
package com.lvmama.vst.hhs.stamp.service;

import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lvmama.vst.hhs.model.common.Constant.OperateType;
import com.lvmama.vst.hhs.stampDefinition.dao.StampOperationLogEntity;
import com.lvmama.vst.hhs.stampDefinition.repository.StampOperationLogRepository;

/**
 * @author baolm
 *
 */
@Service
public class StampOperationLogServiceImpl implements StampOperationLogService {
	
	private static final Logger logger = LoggerFactory.getLogger(StampOperationLogServiceImpl.class);
	
	@Autowired
	private StampOperationLogRepository operationLogRepository;

	/* (non-Javadoc)
	 * @see com.lvmama.vst.hhs.stamp.service.StampOperationLogService#addLog(com.lvmama.vst.hhs.stampDefinition.dao.StampOperationLogEntity)
	 */
	@Override
	public boolean addLog(OperateType operateType, String objectId, String stampCode, String remark) {

		StampOperationLogEntity operateLog = new StampOperationLogEntity();
		String id = UUID.randomUUID().toString();
		Timestamp date = new Timestamp(System.currentTimeMillis());
		operateLog.setId(id);
		operateLog.setOperateType(operateType.toString());
		operateLog.setObjectId(objectId);
		operateLog.setStampCode(stampCode);
		operateLog.setRemark(remark);
		operateLog.setCreateDate(date);
		
		boolean flag = StampLogThread.logQueue.offer(operateLog);
		if(!flag)
			logger.error("add log fail.");
		
		return flag;
	}
	
	@Override
	public int addLogList(List<StampOperationLogEntity> logList) {
		
		int row = 0;
		for(StampOperationLogEntity log : logList) {
			
			boolean flag = StampLogThread.logQueue.offer(log);
			if(!flag)
				logger.error("add log fail.");
			else
				row++;
		}
		
		return row;
	}
	
	@Transactional("transactionManager")
	public void batchInsert(List<StampOperationLogEntity> logList) {
		if(CollectionUtils.isEmpty(logList)) {
			return;
		}
		operationLogRepository.save(logList);
		//logger.info("save a log list: {}", JSON.toJSONString(logList));
	}
	
}
