package com.uwjx.security.service;

import com.uwjx.security.domain.AccountToken;

public interface AccountService {

    AccountToken login(String username, String password);
}
