package com.ifx.account.except;

import cn.hutool.core.exceptions.ExceptionUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.reactive.result.method.annotation.ResponseEntityExceptionHandler;
import reactor.core.publisher.Mono;

import java.util.Optional;

/**
 * @author pengpeng
 * @description
 * @date 2023/2/17
 */
@RestControllerAdvice
@Slf4j
public class AccountExceptionHandler extends ResponseEntityExceptionHandler {



    @ExceptionHandler
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Mono<ResponseEntity<String>> illegalFail(IllegalAccessException exception){
        return Mono.just(ResponseEntity.of(Optional.of("ill")));
    }



    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_GATEWAY)
    public Mono<ErrorResponse> exception(Exception exception){
        return Mono.just(ErrorResponse.builder(exception,HttpStatus.INTERNAL_SERVER_ERROR,ExceptionUtil.stacktraceToString(exception)).build());
    }
}
