/**
 * 
 */
package com.lvmama.vst.hhs.stampDefinition.service;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lvmama.vst.hhs.model.admin.StampOrderVo;
import com.lvmama.vst.hhs.product.dao.OrdOrderEntity;
import com.lvmama.vst.hhs.product.repository.OrdOrderRepository;
import com.lvmama.vst.hhs.product.repository.OrdStampOrderRepository;

/**
 * @author "baolm"
 * 
 */
@Service
public class StampOrderManagementServiceImpl implements StampOrderManagementService {

	private static final Logger LOGGER = LoggerFactory.getLogger(StampOrderManagementServiceImpl.class);
	
	@Autowired
	private OrdStampOrderRepository stampOrderRepository;

	@Autowired
	private OrdOrderRepository  ordOrderRepository;

	
	@Override
	@Transactional("slaveTransactionManager")
	public StampOrderVo getById(String id) {
		StampOrderVo order = new StampOrderVo();
//		BeanUtils.copyProperties(entity, order);
		return order;
	}

    @Override
    public List<StampOrderVo> queryStampOrder(Integer orderId, String userName, String mobile, int start, int end) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public long queryStampOrder(Integer orderId, String userName, String mobile) {
        // TODO Auto-generated method stub
        return 0;
    }


    @Override
    @Transactional("oracleTransactionManager")
    public void updateStampOrderLastPayTime(Long orderId, Date lastPayTime) {
        OrdOrderEntity order = ordOrderRepository.findOne(orderId);
        if(order == null){
            LOGGER.info("ordOrder binding found, ordOrderId: {}", orderId);
            return ;
        }
        
        stampOrderRepository.updateOrdStampOrderLastPaytime(orderId, new Timestamp(lastPayTime.getTime()));
    }

}
