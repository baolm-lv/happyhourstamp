package com.lvmama.vst.hhs.product.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lvmama.vst.back.biz.po.BizCategory;
import com.lvmama.vst.back.client.biz.service.CategoryClientService;
import com.lvmama.vst.comm.vo.ResultHandleT;

@Service
public class CategoryServiceImpl implements CategoryService {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(CategoryServiceImpl.class);
    @Autowired
    private CategoryClientService categoryClientService;
    
    @Override
    public BizCategory findCategoryById(Long categoryId) {
        ResultHandleT<BizCategory> categoryResult = categoryClientService.findCategoryById(categoryId);
        if(categoryResult == null) {
            LOGGER.debug("category not found by id {}", categoryId);
            return null;
        }
        return categoryResult.getReturnContent();
       
    }

    @Override
    public List<BizCategory> findCategoryByAllValid() {
        ResultHandleT<List<BizCategory>> categoryResult = categoryClientService.findCategoryByAllValid();
        if(categoryResult == null) {
            LOGGER.debug("Find all valid categories failed !");
            return null;
        }
        return categoryResult.getReturnContent();
    }

}
