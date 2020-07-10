package com.tudog.graphqldemo01.config;

import java.util.List;

import graphql.GraphQLError;
import graphql.kickstart.execution.error.GraphQLErrorHandler;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class GraphQLErrorCustomHandler implements GraphQLErrorHandler {

    @Override
    public List<GraphQLError> processErrors(List<GraphQLError> errors) {
        log.info("Handle errors: {}", errors);
        return errors;
    }
    
}