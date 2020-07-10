package com.tudog.graphqldemo01.tools;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import graphql.kickstart.servlet.context.GraphQLServletContext;
import graphql.schema.DataFetchingEnvironment;

/**
 * HTTP 工具类，通常用于获取当前HTTP请求上下文中的一些信息
 * 该工具类只能在HTTP请求上下文（线程）中使用，在其它上下文中使用无意义
 */
public class HttpTool {
    private HttpTool(){
        throw new RuntimeException("Not support initiating the Class");
    }
    
    public static HttpServletRequest getHttpServletRequest(DataFetchingEnvironment graphqlDataFechingEnv){
        GraphQLServletContext context = graphqlDataFechingEnv.getContext();
        return context.getHttpServletRequest();
    }

    public static HttpServletResponse getHttpServletResponse(DataFetchingEnvironment graphqlDataFechingEnv){
        GraphQLServletContext context = graphqlDataFechingEnv.getContext();
        return context.getHttpServletResponse();
    }
}