package com.uwjx.security.service.impl;

import com.alibaba.fastjson.JSON;
import com.uwjx.security.domain.AccountToken;
import com.uwjx.security.exception.UserNameErrorException;
import com.uwjx.security.service.AccountService;
import com.uwjx.security.util.JwtUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class AccountServiceImpl implements AccountService {


    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    JwtUtil jwtUtil;

    @Override
    public AccountToken login(String username, String password) {
        try {
            log.warn("调用 UserDetailService进行校验");
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(username, password));
            //准备生产JWT
            log.warn("准备生成JWT");
//            log.warn("信息:{}" , JSON.toJSONString(authentication));
//            List<String> permissions = authentication.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList());
//            log.warn("权限:{}" , JSON.toJSONString(permissions));
//            List<String> roles = authentication.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList());
//            log.warn("角色:{}" , JSON.toJSONString(roles));
            String token = jwtUtil.generateToken(authentication.getName() );
            log.warn("生成的TOKEN:{}" , token);
            SecurityContextHolder.getContext().setAuthentication(authentication);// 将认证信息存储到 SecurityContext 中

            return new AccountToken(token , JwtUtil.EXPIRATION);
        } catch (AuthenticationException e) {
            throw new UserNameErrorException(4001 , "用户名或者密码错误");
        }
    }
}
