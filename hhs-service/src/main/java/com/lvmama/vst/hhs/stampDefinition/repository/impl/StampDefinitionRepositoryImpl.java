package com.lvmama.vst.hhs.stampDefinition.repository.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.lvmama.vst.hhs.stampDefinition.repository.PresaleStampDefinitionGoodsBindingRepository;
import com.lvmama.vst.hhs.stampDefinition.repository.StampDefinitionRepositoryCustom;

@Repository
public class StampDefinitionRepositoryImpl implements StampDefinitionRepositoryCustom{

	private static final Logger LOGGER = LoggerFactory.getLogger(StampDefinitionRepositoryImpl.class);
	@Autowired
	@Qualifier("entityManager")
	private EntityManager em;
	@Autowired 
	private PresaleStampDefinitionGoodsBindingRepository goodsRepository;
  

    @Override
    public String findStampMaxStampNo() {
        StringBuffer sqlStr = new StringBuffer() ;
        sqlStr.append("select stamp.stamp_no from stamp_definition stamp order by create_date desc limit 1" );       
        Query query = em.createNativeQuery(sqlStr.toString());     
        @SuppressWarnings("unchecked")
		List<String> list = query.getResultList();
        if(CollectionUtils.isEmpty(list)) 
        	return null;
        return list.get(0);
    }
	
}
