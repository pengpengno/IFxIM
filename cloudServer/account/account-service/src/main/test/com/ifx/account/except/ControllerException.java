package com.ifx.account.except;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @author pengpeng
 * @description
 * @date 2023/2/13
 */
@RestControllerAdvice
public class ControllerException {



    @ExceptionHandler
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public String illegalFail(IllegalAccessException exception){
        return "illegalFail";
    }



    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_GATEWAY)
    public String fail(Exception exception){
        return "fail";
    }
}
