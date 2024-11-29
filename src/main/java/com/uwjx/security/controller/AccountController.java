package com.uwjx.security.controller;

import com.alibaba.fastjson.JSON;
import com.uwjx.security.domain.Account;
import com.uwjx.security.domain.AccountToken;
import com.uwjx.security.service.AccountService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("account")
@Slf4j
public class AccountController {

    @Autowired
    AccountService accountService;

    @PostMapping("login")
    public AccountToken login(@RequestBody Account account){
        log.warn("登录参数:{}" , JSON.toJSONString(account));
        return accountService.login(account.getUsername() , account.getPassword());
    }
}
