package com.tudog.graphqldemo01.config;

import com.tudog.graphqldemo01.tools.EncryptUtil;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

   @Bean
   public PasswordEncoder passwordEncoder() {
      return EncryptUtil.getDelegatingPasswordEncoder();
   }

   @Override
   public void configure(WebSecurity web) {
      web.ignoring()
         .antMatchers(HttpMethod.OPTIONS, "/**")
         // allow anonymous resource requests
         .antMatchers(
            "/",
            "/*.html",
            "/**/favicon.ico",
            "/**/*.html",
            "/**/*.css",
            "/**/*.css.map",
            "/**/*.js",
            "/**/*.js.map"
         );
   }


   @Override
   protected void configure(HttpSecurity httpSecurity) throws Exception {
      httpSecurity
         .securityContext().disable();
   }
}