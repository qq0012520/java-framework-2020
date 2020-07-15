package com.tudog.graphqldemo01.config;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.tudog.graphqldemo01.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

@Configuration
public class ApplicationSecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private UserService userService;

    // @Override
    // protected void configure(HttpSecurity http) throws Exception {
    //     http.csrf().disable();
    //     // http.csrf().ignoringAntMatchers("/login");
    //     // http.csrf(csrf ->
    //     // csrf.csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse()));
    //     // http.authorizeRequests().antMatchers("/index.html").permitAll().anyRequest().authenticated().and().formLogin().loginPage("/login").permitAll()
    //     // .and().logout().permitAll();
    //     // http.authorizeRequests().anyRequest().authenticated()
    //     // .and().formLogin().permitAll()
    //     // .and().logout().permitAll();
    // }
    
    /**
     * 系统登录用户
     */
    @Bean
    UserDetailsService customUserDetailsService() {
        return (username) -> userService.findByAccount(username)
        .map(u -> new User(u.getAccount(), u.getPassword(), AuthorityUtils.createAuthorityList("ROLE_ADMIN")))
        .orElseThrow(() -> new UsernameNotFoundException("Could not found user '" + username + "'"));
    }
}