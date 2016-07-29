package com.lvmama.vst.hhs.stamp.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lvmama.vst.hhs.model.stamp.StampOrderDetails;

@Service
@Transactional("oracleTransactionManager")
public class OracleStampServiceImpl implements OracleStampService{

    @Override
    public StampOrderDetails getOrderDetails(StampOrderDetails detail) {
        
        
        return null;
    }

}
