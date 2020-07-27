package com.tudog.graphqldemo01.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

@Component
public class WebAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
            AuthenticationException authException) throws IOException, ServletException {
        //当用户在未授权的情况下访问受保护的资源时执行
        //响应 401 未认证相应，因为不需要重定向到登录页面
        response.sendError(HttpServletResponse.SC_UNAUTHORIZED,authException.getMessage());
    }
    
}