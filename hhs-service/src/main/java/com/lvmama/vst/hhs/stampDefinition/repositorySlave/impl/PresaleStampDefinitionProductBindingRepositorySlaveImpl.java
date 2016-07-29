/*
 * The copyright to the computer software herein is the property of
 * Lvmama. The software may be used and/or copied only
 * with the written permission of Lvmama or in accordance
 * with the terms and conditions stipulated in the agreement/contract
 * under which the software has been supplied.
 */

package com.lvmama.vst.hhs.stampDefinition.repositorySlave.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.alibaba.dubbo.common.utils.CollectionUtils;
import com.lvmama.vst.hhs.stampDefinition.repositorySlave.PresaleStampDefinitionProductBindingRepositoryCustomSlave;


/**
 *
 * @author Luochang Tang
 */
@Repository
public class PresaleStampDefinitionProductBindingRepositorySlaveImpl implements 
                                                PresaleStampDefinitionProductBindingRepositoryCustomSlave{
    
    @Autowired
    @Qualifier("slaveEntityManager")
    private EntityManager em;

    
    @SuppressWarnings("unchecked")
	@Override
    public List<String> getStampDefinitionProductBinding(List<String> categoryId,int start, int num) {
        int max = 0;
        if(num>10000){
            max =10000;
        }else{
            max =num;
        }
        SimpleDateFormat sdf=  new SimpleDateFormat("yyyy-MM-dd 00:00:00");
        String nowDate = sdf.format(new Date());
        StringBuffer jpql = new StringBuffer("select  distinct p.product_id from  presale_stamp_definition_product_binding p  ")
            .append(" inner join presale_inventory_unit pre_inv on p.stamp_definition_id =pre_inv.stamp_definition_id  ")
            .append(" inner join stamp_definition stamp on stamp.id = pre_inv.stamp_definition_id ")
            .append(" where  stamp.activity_status='Y'  ")
            .append(" and stamp.stamp_onsale_start_date<='").append(nowDate).append("'")
            .append(" and stamp.stamp_onsale_end_date>='").append(nowDate).append("'")
            .append(" and pre_inv.inventory_level > 0 ");            
            
        if(CollectionUtils.isNotEmpty(categoryId)){
            if(categoryId.size()==1){
                jpql.append(" and p.category_id ='").append(categoryId.get(0)).append("'");
            }
            if(categoryId.size()>1){
                jpql.append(" and p.category_id  in(");
                int i = 0 ;
                for(String id:categoryId){
                    if(i==0){
                        jpql.append("'").append(id).append("'");
                    }else{
                        jpql.append(",'").append(id).append("'");
                    }
                   i++;
                }
                jpql.append(")");
            }
           
        }
        jpql.append("  limit ").append(start).append(",").append(max);
        Query query = em.createNativeQuery(jpql.toString(), String.class);
        return query.getResultList();
    }

    @Override
    public long countStampDefinitionProductBinding(List<String> categoryId) {
        SimpleDateFormat sdf=  new SimpleDateFormat("yyyy-MM-dd 00:00:00");
        String nowDate = sdf.format(new Date());
        StringBuffer jpql = new StringBuffer("select  count(distinct p.product_id) from  presale_stamp_definition_product_binding p  ")
            .append(" inner join presale_inventory_unit pre_inv on p.stamp_definition_id =pre_inv.stamp_definition_id  ")
            .append(" inner join stamp_definition stamp on stamp.id = pre_inv.stamp_definition_id ")
            .append(" where  stamp.activity_status='Y'  ")
            .append(" and stamp.stamp_onsale_start_date<='").append(nowDate).append("'")
            .append(" and stamp.stamp_onsale_end_date>='").append(nowDate).append("'")
            .append(" and pre_inv.inventory_level > 0 ");            
            
        if(CollectionUtils.isNotEmpty(categoryId)){
            if(categoryId.size()==1){
                jpql.append(" and p.category_id ='").append(categoryId.get(0)).append("'");
            }
            if(categoryId.size()>1){
                jpql.append(" and p.category_id  in(");
                int i = 0 ;
                for(String id:categoryId){
                    if(i==0){
                        jpql.append("'").append(id).append("'");
                    }else{
                        jpql.append(",'").append(id).append("'");
                    }
                   i++;
                }
                jpql.append(")");
            }
           
        }
      
        Query query = em.createNativeQuery(jpql.toString(), Long.class);
        return (long) query.getSingleResult();
    }

}
