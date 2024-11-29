package com.uwjx.security.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AccountToken {

    private String token;
    private Long expiresIn;
}
