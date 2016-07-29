/*
 * The copyright to the computer software herein is the property of
 * Lvmama. The software may be used and/or copied only
 * with the written permission of Lvmama or in accordance
 * with the terms and conditions stipulated in the agreement/contract
 * under which the software has been supplied.
 */

package com.lvmama.vst.hhs.product.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.lvmama.vst.hhs.product.dao.OrdOrderEntity;


/**
 *
 * @author Luochang Tang
 */
@Repository
public interface OrdOrderRepository extends JpaRepository<OrdOrderEntity, Long> {

	@Modifying
	@Query("update OrdOrderEntity o set o.orderSubtype = ?1 where o.orderId = ?2")
	void updateSubTypeByOrderId(String subType, long orderId);
	
	@Query(value = "select order_id from (select a.order_id ,ROWNUM RN from  "
	         +" (select ord.order_id from ord_order ord where ord.user_id= ?1 and ord.order_subtype='STAMP'  ORDER BY ord.create_time desc  ) a) "
	         +" where RN BETWEEN ?2 and ?3",
	           nativeQuery = true)
	List<Long>  getByUserId(String userId ,int start,int end);
	
	@Query(value = "select count(*) from (select a.order_id ,ROWNUM RN from  "
            +" (select ord.order_id from ord_order ord where ord.user_id= ?1 and ord.order_subtype='STAMP' ) a) ",
              nativeQuery = true)
	Long  countByUserId(String userId);
	
	@Query(value = "select ord. from (select a.order_id ,ROWNUM RN from  "
            +" (select ord.order_id from ord_order ord where ord.user_id= ?1 and ord.order_subtype='STAMP' ) a) ",
              nativeQuery = true)
	List<Long> backQueryOrder(Long orderId,String userName,String mobile,int start,int end);

	@Modifying
	@Query(value="update ord_order o set o.wait_payment_time = "
			+ "(select so.balance_due_wait_date from ord_stamp_order so where so.order_id=o.order_id)"
			+ " where o.order_id = ?1",
			nativeQuery=true)
	int updateWaitPaymentTimeByOrderId(Long orderId);
	
}
