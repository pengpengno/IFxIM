package com.ifx.account.except;

import org.springframework.core.ReactiveAdapterRegistry;
import org.springframework.http.HttpStatus;
import org.springframework.http.codec.HttpMessageWriter;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.reactive.accept.RequestedContentTypeResolver;
import org.springframework.web.reactive.result.method.annotation.ResponseEntityResultHandler;

import java.util.List;

/**
 * @author pengpeng
 * @description
 * @date 2023/2/13
 */
@RestControllerAdvice
public class ControllerException extends ResponseEntityResultHandler {


    public ControllerException(List<HttpMessageWriter<?>> writers, RequestedContentTypeResolver resolver) {
        super(writers, resolver);
    }

    public ControllerException(List<HttpMessageWriter<?>> writers, RequestedContentTypeResolver resolver, ReactiveAdapterRegistry registry) {
        super(writers, resolver, registry);
    }

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
