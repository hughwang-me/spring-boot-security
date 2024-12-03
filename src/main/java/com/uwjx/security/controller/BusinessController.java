package com.uwjx.security.controller;

import com.alibaba.fastjson.JSON;
import com.uwjx.security.domain.Account;
import com.uwjx.security.domain.AccountToken;
import com.uwjx.security.service.AccountService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("business")
@Slf4j
public class BusinessController {

    @PostMapping("getInfo")
    public String getOrders(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        log.warn("信息:{}" , JSON.toJSONString(authentication));
        return JSON.toJSONString(authentication);
    }

    @PreAuthorize("hasRole('USER')")
    @PostMapping("checkRoleUser")
    public String checkRoleFather(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        log.warn("打印当前登录用户的用户信息:{}" , JSON.toJSONString(authentication));
        return "success";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("checkRoleAdmin")
    public String checkRoleAdmin(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        log.warn("打印当前登录用户的用户信息:{}" , JSON.toJSONString(authentication));
        return "success";
    }

    @PreAuthorize("hasAuthority('DELETE')")
    @PostMapping("checkPermissionDelete")
    public String checkPermissionDelete(){
        return "success";
    }

    @PreAuthorize("hasAuthority('READ')")
    @PostMapping("checkPermissionRead")
    public String checkPermissionRead(){
        return "success";
    }
}
