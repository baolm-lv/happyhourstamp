/**
 * 
 */
package com.lvmama.vst.hhs.product.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.lvmama.vst.hhs.product.dao.BizDistrictEntity;

/**
 * @author fengyonggang
 *
 */
@Repository
public interface BizDistrictRepository extends JpaRepository<BizDistrictEntity, Long>{

}
