package com.ifx.gateway.exception;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.exceptions.ExceptionUtil;
import cn.hutool.core.util.StrUtil;
import com.ifx.exec.BaseException;
import com.ifx.exec.errorMsg.IErrorMsg;
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
public class GateWayExceptionHandler {




    @ExceptionHandler(WebExchangeBindException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Mono<ResponseEntity<ProblemDetail>> handleBindException(WebExchangeBindException bindException) {
        StringBuilder errorMsgBuilder = new StringBuilder();
        ProblemDetail problemDetail = ProblemDetail.forStatus(HttpStatus.BAD_REQUEST);
        List<ObjectError> allErrors = bindException.getAllErrors();
        if (CollectionUtil.isNotEmpty(allErrors)) {
            allErrors.forEach(e -> errorMsgBuilder.append(e.getDefaultMessage()));
            problemDetail.setDetail(errorMsgBuilder.toString());
        } else {
            problemDetail.setDetail(bindException.getMessage());
        }
        return Mono.just(ResponseEntity.of(problemDetail).build());
    }


    @ExceptionHandler({BindException.class})
    public  Mono<ResponseEntity<ProblemDetail>> validationException(BindException exception){
        List<ObjectError> errors =  exception.getAllErrors();
        ProblemDetail problemDetail = ProblemDetail.forStatus(HttpStatus.BAD_REQUEST);
        StringBuilder sb = new StringBuilder();
        if(!CollectionUtils.isEmpty(errors)){
            errors.forEach(e->sb.append(e.getDefaultMessage()).append(","));
            problemDetail.setDetail(sb.toString());
        }
        return Mono.just(ResponseEntity.of(problemDetail).build());
    }



    @ExceptionHandler({BaseException.class})
    public Mono<ResponseEntity<ProblemDetail>> handleBaseException(BaseException baseException){
        ProblemDetail problemDetail = ProblemDetail.forStatus(HttpStatus.BAD_REQUEST);
        IErrorMsg errorMsg = baseException.getErrorMsg();
        String message = baseException.getMessage();
        if (StrUtil.isNotBlank(baseException.getMessage())){
            problemDetail.setDetail(message);
        }
       else {
           if (null != errorMsg){
               Integer errorCode = errorMsg.getErrorCode();
               problemDetail.setDetail(errorMsg.getErrorMessage());
               problemDetail.setProperty("errorCode",errorCode);
           }
        }
        return Mono.just(ResponseEntity.of(problemDetail).build());
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
        }
        else {
            problemDetail.setDetail(exception.getMessage());
        }
        return Mono.just(ResponseEntity.of(problemDetail).build());
    }


    @ExceptionHandler
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Mono<ResponseEntity<String>> illegalFail(IllegalAccessException exception){
        log.error("出现异常 {}",ExceptionUtil.stacktraceToString(exception));
        return Mono.just(ResponseEntity.of(Optional.of("ill")));
    }


    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Mono<ResponseEntity<ProblemDetail>> IllegalArgumentException(IllegalArgumentException exception){
        log.error("出现异常 {}",ExceptionUtil.stacktraceToString(exception));
        ProblemDetail problemDetail = ProblemDetail.forStatus(HttpStatus.BAD_REQUEST);
        problemDetail.setDetail(exception.getMessage());
        return Mono.just(ResponseEntity.of(problemDetail).build());
    }



    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.BAD_GATEWAY)
    public Mono<ErrorResponse> exception(Exception exception){
        return Mono.just(ErrorResponse.builder(exception,HttpStatus.INTERNAL_SERVER_ERROR,ExceptionUtil.stacktraceToString(exception)).build());
    }




    @ExceptionHandler(Throwable.class)
    @ResponseStatus(HttpStatus.BAD_GATEWAY)
    public Mono<ErrorResponse> throwable(Throwable exception){
        return Mono.just(ErrorResponse.builder(exception,HttpStatus.INTERNAL_SERVER_ERROR,ExceptionUtil.stacktraceToString(exception)).build());
    }
}
