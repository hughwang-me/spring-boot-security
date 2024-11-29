package com.uwjx.security.exception;

import lombok.Data;

@Data
public class UserNameErrorException extends RuntimeException{

    /**
     * 异常错误码
     */
    private Integer code;
    private String msg;

    public UserNameErrorException( Integer code , String msg) {
        super(msg);
        this.code = code;
        this.msg = msg;
    }

}
