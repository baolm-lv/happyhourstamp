/**
 * 
 */
package com.lvmama.vst.hhs.product.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.lvmama.vst.hhs.product.dao.ProdProductAddtionalEntity;

/**
 * @author fengyonggang
 *
 */
@Repository
public interface ProdProductAddtionalRepository extends JpaRepository<ProdProductAddtionalEntity, Long>{

}
