package com.uwjx.security.exception;

import lombok.Data;

@Data
public class TokenErrorException extends RuntimeException{

    /**
     * 异常错误码
     */
    private Integer code;
    private String msg;

    public TokenErrorException(Integer code , String msg) {
        super(msg);
        this.code = code;
        this.msg = msg;
    }

}