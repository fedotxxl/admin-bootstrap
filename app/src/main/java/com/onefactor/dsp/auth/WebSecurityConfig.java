/*
 * WebSecurityConfig
 * Copyright (c) 2012 Cybervision. All rights reserved.
 */
package com.onefactor.dsp.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.authentication.encoding.ShaPasswordEncoder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

/**
 * Конфигурация Spring Security
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled=true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private AuthenticationEntryPoint authenticationEntryPoint;

//    @Autowired
//    private PersistentTokenRepository tokenRepository;
//
//    @Autowired
//    private MyUserDetailsService userDetailsService;
//
//    @Autowired
//    private RememberMeServices rememberMeServices;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests().antMatchers("/api/auth/**", "/api/nginx/**").permitAll();
        http.authorizeRequests().antMatchers("/api/**", "/app/**").authenticated();
        http.csrf().disable();
        http.logout().permitAll().logoutRequestMatcher(new AntPathRequestMatcher("/logout")).logoutSuccessUrl("/").invalidateHttpSession(true);
        http.exceptionHandling().authenticationEntryPoint(authenticationEntryPoint);
//        http.rememberMe().rememberMeServices(rememberMeServices).key("myRememberMeKey");
    }

//    @Override
//    protected void configure(AuthenticationManagerBuilder authManagerBuilder) throws Exception {
//        authManagerBuilder.authenticationProvider(this.getDaoAuthenticationProvider());
//    }

//    @Bean
//    public AuthenticationManager authenticationManager(UserDetailsService userDetailsService) throws Exception {
//        return new AuthenticationManagerBuilder(ObjectPostProcessor.QUIESCENT_POSTPROCESSOR)
//                .userDetailsService(userDetailsService)
//                .build();
//    }

    @Bean
    public AuthenticationEntryPoint authenticationEntryPoint() {
        return new LoginUrlOr403AuthenticationEntryPoint("/auth/login.html");
    }

//    @Bean
//    public RememberMeServices rememberMeServices() {
//        PersistentTokenBasedRememberMeServices rememberMeServices = new PersistentTokenBasedRememberMeServices("myRememberMeKey", userDetailsService, tokenRepository);
//        rememberMeServices.setAlwaysRemember(true);
//        rememberMeServices.setTokenValiditySeconds(60*60*24);
//        return rememberMeServices;
//    }

    @Bean
    public ShaPasswordEncoder passwordEncoder() {
        return new ShaPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider(ShaPasswordEncoder encoder, MyUserDetailsService userDetailsService) {
        DaoAuthenticationProvider p = new DaoAuthenticationProvider();
        p.setPasswordEncoder(encoder);
        p.setUserDetailsService(userDetailsService);
        return p;
    }

//    @Override
//    protected void configure(AuthenticationManagerBuilder authManagerBuilder)
//            throws Exception {
//        authManagerBuilder.authenticationProvider(this.getDaoAuthenticationProvider());
//    }

//    @Bean
//    public AuthenticationManager authenticationManager(AuthenticationManagerBuilder authManagerBuilder) throws Exception {
//        return authManagerBuilder.inMemoryAuthentication().withUser("client")
//                .password("secret").roles("USER").and().and().build();
//    }
}