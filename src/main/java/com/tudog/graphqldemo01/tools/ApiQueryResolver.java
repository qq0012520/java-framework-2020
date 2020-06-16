package com.tudog.graphqldemo01.tools;

import graphql.kickstart.tools.GraphQLQueryResolver;

public abstract class ApiQueryResolver implements GraphQLQueryResolver{

    public ApiQueryResolver query(Integer id){
        return this;
    }
}