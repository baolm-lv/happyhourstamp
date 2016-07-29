package com.lvmama.vst.hhs.stampDefinition.repositorySlave.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.lvmama.vst.hhs.model.admin.StampRequest;
import com.lvmama.vst.hhs.stampDefinition.repository.PresaleStampDefinitionGoodsBindingRepository;
import com.lvmama.vst.hhs.stampDefinition.repositorySlave.StampDefinitionRepositoryCustomSlave;

@Repository
public class StampDefinitionRepositorySlaveImpl implements StampDefinitionRepositoryCustomSlave{

	private static final Logger LOGGER = LoggerFactory.getLogger(StampDefinitionRepositorySlaveImpl.class);
	@Autowired
	@Qualifier("slaveEntityManager")
	private EntityManager em;
	
    @SuppressWarnings("unchecked")
    @Override
    public List<String> findByFileds(StampRequest stamp, int startrum, int num) {
        
        StringBuffer sqlStr = new StringBuffer() ;    
        sqlStr.append(" SELECT stamp.id FROM   ")
              .append("  stamp_definition stamp ")
              .append(" LEFT OUTER JOIN presale_stamp_definition_product_binding  ")
              .append(" bind ON  stamp.id = bind.STAMP_DEFINITION_ID  where 1= 1");
		Map<String, Object> map = new HashMap<String, Object>();
		sqlStr.append(buildQuerySQL(stamp, map));
		sqlStr.append(" order by stamp.CREATE_DATE desc ");
		LOGGER.info("====" + sqlStr.toString());
		Query query = em.createNativeQuery(sqlStr.toString());
		query.setFirstResult(startrum - 1);
		query.setMaxResults(num);
		List<String> stampIds = query.getResultList();
		return stampIds;
	}
    

    @Override
    public long countFindByFileds(StampRequest stamp) {
        StringBuffer sqlStr = new StringBuffer() ;
        sqlStr.append(" SELECT count(stamp.id) FROM   ")
            .append("  stamp_definition stamp ")
            .append(" LEFT OUTER JOIN presale_stamp_definition_product_binding  ")
            .append(" bind ON  stamp.id = bind.STAMP_DEFINITION_ID  where 1= 1");
        Map<String, Object> map = new HashMap<String, Object>();
        sqlStr.append(buildQuerySQL(stamp,map));    
        LOGGER.info("----"+sqlStr.toString());
        Query query = em.createNativeQuery(sqlStr.toString());       
        return (long) query.getSingleResult();
    }
    
    private String buildQuerySQL(StampRequest stamp,Map<String,Object> map){
        StringBuffer sqlStr = new StringBuffer(); 
        if(StringUtils.isNotEmpty(stamp.getStampNo())){
            sqlStr.append(" and stamp.stamp_no ='"+stamp.getStampNo()+"'");
        }
        if(StringUtils.isNotEmpty(stamp.getStampName())){
            sqlStr.append(" and stamp.name like '%"+stamp.getStampName()+"%'");
        }
        if(StringUtils.isNotEmpty(stamp.getCategroyId())){
            sqlStr.append(" and bind.category_id ="+stamp.getCategroyId());
        }
        if(StringUtils.isNotEmpty(stamp.getManagerId())){
            sqlStr.append(" and bind.manager_id="+stamp.getManagerId());
        }
        if(StringUtils.isNotEmpty(stamp.getProductId())){
            sqlStr.append(" and bind.product_id="+stamp.getProductId());
        }
        if(StringUtils.isNotEmpty(stamp.getProductName())){
            sqlStr.append(" and  bind.product_name like '%"+stamp.getProductName()+"%'");
       }
        
        if(StringUtils.isNotEmpty(stamp.getManagerName())){
            sqlStr.append(" and bind.manager_name like '%"+stamp.getManagerName()+"%'");
        }
        
        return sqlStr.toString();
    }

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
