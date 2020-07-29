package com.tudog.graphqldemo01.config;

import com.tudog.graphqldemo01.security.WebAccessDenyHandler;
import com.tudog.graphqldemo01.security.WebAuthenticationEntryPoint;
import com.tudog.graphqldemo01.security.WebAuthenticationFailureHandler;
import com.tudog.graphqldemo01.security.WebAuthenticationSuccessHandler;
import com.tudog.graphqldemo01.security.WebLogoutSuccessHandler;
import com.tudog.graphqldemo01.service.UserService;
import com.tudog.graphqldemo01.tools.EncryptUtil;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.web.filter.CorsFilter;

@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

   private final UserService userService;

   private final CorsFilter corsFilter;
   private final WebAuthenticationEntryPoint authenticationErrorHandler;
   private final WebAccessDenyHandler webAccessDenyHandler;
   private final WebAuthenticationSuccessHandler webAuthenticationSuccessHandle;
   private final WebLogoutSuccessHandler webLogoutSuccessHandler;
   private final WebAuthenticationFailureHandler webAuthenticationFailureHandler;

   @Bean
   public PasswordEncoder passwordEncoder() {
      return EncryptUtil.getDelegatingPasswordEncoder();
   }

   public WebSecurityConfig(UserService userService,
      CorsFilter corsFilter
      ,WebAuthenticationEntryPoint authenticationErrorHandler
      ,WebAccessDenyHandler webAccessDenyHandler
      ,WebAuthenticationSuccessHandler webAuthenticationSuccessHandler
      ,WebLogoutSuccessHandler webLogoutSuccessHandler
      ,WebAuthenticationFailureHandler webAuthenticationFailureHandler){
      this.userService = userService;
      this.corsFilter = corsFilter;
      this.authenticationErrorHandler = authenticationErrorHandler;
      this.webAccessDenyHandler = webAccessDenyHandler;
      this.webAuthenticationSuccessHandle = webAuthenticationSuccessHandler;
      this.webLogoutSuccessHandler = webLogoutSuccessHandler;
      this.webAuthenticationFailureHandler = webAuthenticationFailureHandler;
   }

   @Override
   public void configure(WebSecurity web) {
      web.ignoring()
         .antMatchers(HttpMethod.OPTIONS, "/**")
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
      httpSecurity.securityContext().disable();
      // .csrf(csrf -> csrf.csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse()))
      // .addFilterBefore(corsFilter, UsernamePasswordAuthenticationFilter.class)
      // .formLogin().successHandler(webAuthenticationSuccessHandle).failureHandler(webAuthenticationFailureHandler).permitAll()
      // .and()
      // .logout().logoutUrl("/logout").logoutSuccessHandler(webLogoutSuccessHandler).permitAll()
      // .and()
      // .exceptionHandling()
      // .authenticationEntryPoint(authenticationErrorHandler)
      // .accessDeniedHandler(webAccessDenyHandler)
      // .and()
      // .authorizeRequests()
      // .anyRequest().authenticated();

   }

   @Bean
   UserDetailsService customUserDetailsService(){
      return (account) -> userService.findByAccount(account)
         .map(u -> new User(u.getAccount(), u.getPassword(), AuthorityUtils.createAuthorityList("authorities")))
         .orElseThrow(() -> new UsernameNotFoundException("Could not found user '" + account + "'"));
   }
}