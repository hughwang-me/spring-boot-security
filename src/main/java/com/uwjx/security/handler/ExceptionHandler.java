package com.uwjx.security.handler;

import com.uwjx.security.domain.ResultVO;
import com.uwjx.security.exception.TokenErrorException;
import com.uwjx.security.exception.UserNameErrorException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ResponseBody;


@ControllerAdvice
@Slf4j
public class ExceptionHandler {

    /**
     * 捕获 Exception 并处理响应返回
     * @param e 异常对象
     * @return 返回异常信息
     */
    @org.springframework.web.bind.annotation.ExceptionHandler(value = Exception.class)
    @ResponseBody
    public ResultVO handler(Exception e) {
        if (e instanceof UserNameErrorException) {
            UserNameErrorException hdvException = (UserNameErrorException) e;
            log.warn("捕获 [HdvException] 错误码 -> {} 错误信息-> {}", hdvException.getCode(), hdvException.getMessage());
            return new ResultVO(hdvException.getCode(), hdvException.getMsg());
        } else if (e instanceof TokenErrorException) {
            TokenErrorException hdvException = (TokenErrorException) e;
            log.warn("捕获 [HdvException] 错误码 -> {} 错误信息-> {}", hdvException.getCode(), hdvException.getMessage());
            return new ResultVO(hdvException.getCode(), hdvException.getMsg());
        } else{
            log.warn("捕获 [Exception] 错误信息-> {}", e.getMessage());
            return new ResultVO(4000, e.getMessage());
        }
    }

}
