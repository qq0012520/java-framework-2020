package com.tudog.graphqldemo01.api.blade;

import com.tudog.graphqldemo01.entity.BladeVisual;
import com.tudog.graphqldemo01.entity.BladeVisualConfig;
import com.tudog.graphqldemo01.model.common.WebResult;
import com.tudog.graphqldemo01.service.BladeVisualService;

import org.springframework.beans.factory.annotation.Autowired;

import graphql.kickstart.tools.GraphQLMutationResolver;

public class BladeMutation implements GraphQLMutationResolver{

    @Autowired
    private BladeVisualService bladeVisualService;

    public WebResult saveVisual(BladeVisual input){
        bladeVisualService.saveVisual(input);
        return new WebResult(true);
    }

    public WebResult saveVisualConfig(BladeVisualConfig input){
        bladeVisualService.saveConfig(input);
        return new WebResult(true);
    }
}