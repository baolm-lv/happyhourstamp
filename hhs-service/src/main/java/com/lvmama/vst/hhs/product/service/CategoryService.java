package com.lvmama.vst.hhs.product.service;

import java.util.List;

import com.lvmama.vst.back.biz.po.BizCategory;

public interface CategoryService {
    
    /**
     * 查询品类
     * @param categoryId
     * @return
     */
    BizCategory findCategoryById(Long categoryId);
    
    /**
     * 所有有效的品类
     * @return
     */
    List<BizCategory> findCategoryByAllValid();

}
