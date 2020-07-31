package com.tudog.graphqldemo01.api.blade;

import java.util.List;

import com.tudog.graphqldemo01.entity.BladeVisual;
import com.tudog.graphqldemo01.entity.BladeVisualCategory;
import com.tudog.graphqldemo01.service.BladeVisualCategoryService;
import com.tudog.graphqldemo01.service.BladeVisualService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;

import graphql.kickstart.tools.GraphQLQueryResolver;
import graphql.schema.DataFetchingEnvironment;

public class BladeQuery implements GraphQLQueryResolver{
    @Autowired
    private BladeVisualService bladeVisualService;

    @Autowired
    private BladeVisualCategoryService bladeVisualCategoryService;

    public Page<BladeVisual> findByCategory(Long categoryId,Integer page,Integer size){
        return bladeVisualService.findByCategory(categoryId, page, size);
    }

    public List<BladeVisualCategory> findAllCategory(DataFetchingEnvironment env){
        return bladeVisualCategoryService.findAll(env);
    }

    public BladeVisual findVisualById(Long id){
        return bladeVisualService.findById(id);
    }
}