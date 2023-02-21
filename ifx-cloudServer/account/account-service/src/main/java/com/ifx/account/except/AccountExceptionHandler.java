package com.ifx.account.except;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.exceptions.ExceptionUtil;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.BindException;
import org.springframework.validation.ObjectError;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.bind.support.WebExchangeBindException;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * @author pengpeng
 * @description
 * @date 2023/2/17
 */
@RestControllerAdvice
@Slf4j
public class AccountExceptionHandler {
//public class AccountExceptionHandler extends ResponseEntityExceptionHandler {


    @ExceptionHandler(WebExchangeBindException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Mono<ResponseEntity<ProblemDetail>> handleBindException(WebExchangeBindException bindException) {
        StringBuilder errorMsgBuilder = new StringBuilder();
        ProblemDetail problemDetail = ProblemDetail.forStatus(HttpStatus.BAD_REQUEST);

        List<ObjectError> allErrors = bindException.getAllErrors();
        if (CollectionUtil.isNotEmpty(allErrors)) {
            allErrors.forEach(e -> errorMsgBuilder.append(e.getDefaultMessage()));
            problemDetail.setDetail(errorMsgBuilder.toString());
            problemDetail.setProperty("errorCode", 10000l);
        } else {
            problemDetail.setDetail(bindException.getLocalizedMessage());
            problemDetail.setProperty("errorCode", 10000l);
            problemDetail.setProperty("errorType ", 10000l);
        }
        return Mono.just(ResponseEntity.of(problemDetail).build());
    }

    @ExceptionHandler({BindException.class})
    public  Mono<ResponseEntity<String>> validationException(BindException exception){
        List<ObjectError> errors =  exception.getAllErrors();
        if(!CollectionUtils.isEmpty(errors)){
            StringBuilder sb = new StringBuilder();
            errors.forEach(e->sb.append(e.getDefaultMessage()).append(","));
            return Mono.just(ResponseEntity.badRequest().body(exception.getLocalizedMessage()));
        }
        return Mono.just(ResponseEntity.badRequest().body(exception.getLocalizedMessage()));
    }
    @ExceptionHandler({ConstraintViolationException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public  Mono<ResponseEntity<ProblemDetail>> constraintViolationException(ConstraintViolationException exception){
        Set<ConstraintViolation<?>> constraintViolations = exception.getConstraintViolations();
        ProblemDetail problemDetail = ProblemDetail.forStatus(HttpStatus.BAD_REQUEST);
        if(!CollectionUtils.isEmpty(constraintViolations)){
            StringBuilder sb = new StringBuilder();
            constraintViolations.forEach(e->sb.append(e.getMessage()).append(","));
            problemDetail.setDetail(sb.toString());
            problemDetail.setProperty("errorCode",10000l);
        }
        else {
            problemDetail.setDetail(exception.getLocalizedMessage());
            problemDetail.setProperty("errorCode",10000l);
        }
        return Mono.just(ResponseEntity.of(problemDetail).build());
    }
    @ExceptionHandler
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Mono<ResponseEntity<String>> illegalFail(IllegalAccessException exception){
        return Mono.just(ResponseEntity.of(Optional.of("ill")));
    }



    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.BAD_GATEWAY)
    public Mono<ErrorResponse> exception(Exception exception){
        log.info("ssssssss {} ",ExceptionUtil.stacktraceToString(exception));
        return Mono.just(ErrorResponse.builder(exception,HttpStatus.INTERNAL_SERVER_ERROR,ExceptionUtil.stacktraceToString(exception)).build());
    }




    @ExceptionHandler(Throwable.class)
    @ResponseStatus(HttpStatus.BAD_GATEWAY)
    public Mono<ErrorResponse> exception(Throwable exception){
        log.info("ssssssss");
        return Mono.just(ErrorResponse.builder(exception,HttpStatus.INTERNAL_SERVER_ERROR,ExceptionUtil.stacktraceToString(exception)).build());
    }
}
