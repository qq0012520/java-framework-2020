package com.tudog.graphqldemo01.tools;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * HTTP系统用户工具类，用于获取当前认证的相关信息
 * 该工具类只能在HTTP请求上下文（线程）中使用，在其它上下文中使用无意义
 */
public class HttpUserTool {
    private HttpUserTool(){
        throw new RuntimeException("Not support initiating the Class");
    }

    /**
     * 获取登录验证信息
     */
    public static Authentication getAuthentication(){
        return SecurityContextHolder.getContext().getAuthentication();
    }

    /**
     * 获取登录用户名
     */
    public static String getName(){
        System.out.println(SecurityContextHolder.getContext().getAuthentication());
        if(SecurityContextHolder.getContext().getAuthentication() == null){
            return "nobody";
        }else{
            return SecurityContextHolder.getContext().getAuthentication().getName();
        }
    }

    /**
     * 获取登录用户权限
     */
    public static List<String> getAuthorities(){
        return SecurityContextHolder.getContext()
        .getAuthentication()
        .getAuthorities().stream()
        .map(authority -> authority.getAuthority())
        .collect(Collectors.toList());
    }

    /**
     * 获取登录用户
     */
    public static Object getPrincipal(){
        return SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

}