package com.tudog.graphqldemo01.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

@Component
public class WebAccessDenyHandler implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response,
            AccessDeniedException accessDeniedException) throws IOException, ServletException {
        //当用户试图访问未授权的资源时执行
        //返回 403 Forbidden 相应，不需要重定向到error page
        response.sendError(HttpServletResponse.SC_FORBIDDEN,accessDeniedException.getMessage());
    }
    
}