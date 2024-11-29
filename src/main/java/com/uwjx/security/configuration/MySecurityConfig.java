package com.uwjx.security.configuration;

import com.uwjx.security.filter.MyJwtFilter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.util.Collections;

@Slf4j
@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true) // 启用注解鉴权
public class MySecurityConfig {

    @Autowired
    MyAuthenticationEntryPoint myAuthenticationEntryPoint;
    @Autowired
    MyJwtFilter myJwtFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http    // 禁用表单登录
                .formLogin().disable()
                //关闭CSRF
                .csrf().disable()
                //开启 HTTP 认证
                .httpBasic()
                .and()
                //配置接口权限
                .authorizeHttpRequests()
                .antMatchers("/account/**").permitAll() //登录接口不限制
                .anyRequest().authenticated() //其余接口限制必须登录
                .and()
                .exceptionHandling()
                .authenticationEntryPoint(myAuthenticationEntryPoint)
                .and()
                .addFilterBefore(myJwtFilter , UsernamePasswordAuthenticationFilter.class)
                .sessionManagement().disable(); // 禁用会话，保证无状态方式
        return http.build();
    }

    //使用 BCrypt 加密方式
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        log.warn("@模拟加载用户信息到内存@");
        return new InMemoryUserDetailsManager(
                User.withUsername("wanghuan")
                        .password(new BCryptPasswordEncoder().encode("123456"))
                        .roles("father" , "son" , "mother")
                        .authorities("query" , "delete" , "modify" , "insert" , "acl_code101")
                        .build(),
                User.withUsername("wangqian")
                        .password(new BCryptPasswordEncoder().encode("123456"))
                        .roles("son")
                        .authorities("query" , "acl_code101" , "acl_code102" )
                        .build()
        );
    }
}
