package com.tudog.graphqldemo01.service;

import com.tudog.graphqldemo01.entity.BladeVisual;
import com.tudog.graphqldemo01.entity.BladeVisualCategory;
import com.tudog.graphqldemo01.entity.BladeVisualConfig;
import com.tudog.graphqldemo01.repository.BladeVisualRepository;
import com.tudog.graphqldemo01.tools.BaseService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class BladeVisualService extends BaseService<BladeVisual,Long>{
    @Autowired
    private BladeVisualRepository bladeVisualRepository;

    @Autowired
    private BladeVisualCategoryService categoryService;

    @Autowired
    private BladeVisualConfigService configService;

    @Transactional(readOnly = true)
    public Page<BladeVisual> findByCategory(Long categoryId,int page,int size){
        return bladeVisualRepository.findByCategory(new BladeVisualCategory(categoryId),PageRequest.of(page, size));
    }

    @Transactional
	public void saveVisual(BladeVisual input) {
        if(input.getCategoryId() != null){
            BladeVisualCategory category = categoryService.findById(input.getCategoryId());
            input.setCategory(category);
        }
        if(input.getConfigId() != null){
            BladeVisualConfig config = configService.findById(input.getConfigId());
            input.setConfig(config);
        }
        this.insertOrUpdate(input);
	}

    @Transactional
	public void saveConfig(BladeVisualConfig input) {
        configService.insertOrUpdate(input);
	}
}