package com.ifx.account.except;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.exceptions.ExceptionUtil;
import com.ifx.common.ex.valid.ValidationException;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.BindException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.bind.support.WebExchangeBindException;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Set;

/**
 * @author pengpeng
 * @description
 * @date 2023/2/17
 */
@RestControllerAdvice
@Slf4j
public class AccountExceptionHandler {


    @ExceptionHandler(WebExchangeBindException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Mono<ResponseEntity<ProblemDetail>> handleBindException(WebExchangeBindException bindException) {
        log.error("出现异常 {}",ExceptionUtil.stacktraceToString(bindException));

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
    public  Mono<ResponseEntity<String>> validationException(BindException exception){
        log.error("bind  error {}",ExceptionUtil.stacktraceToString(exception));

        List<ObjectError> errors =  exception.getAllErrors();
        if(!CollectionUtils.isEmpty(errors)){
            StringBuilder sb = new StringBuilder();
            errors.forEach(e->sb.append(e.getDefaultMessage()).append(","));
            return Mono.just(ResponseEntity.badRequest().body(sb.toString()));
        }
        return Mono.just(ResponseEntity.badRequest().body(exception.getLocalizedMessage()));
    }

    @ExceptionHandler({ValidationException.class})
    public  Mono<ResponseEntity<ProblemDetail>> validException(ValidationException exception){
        log.error("bind  error {}",ExceptionUtil.stacktraceToString(exception));
        ProblemDetail problemDetail = ProblemDetail.forStatus(HttpStatus.BAD_REQUEST);
        problemDetail.setDetail(exception.getMessage());
        return Mono.just(ResponseEntity.of(problemDetail).build());
    }


    @ExceptionHandler({ConstraintViolationException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public  Mono<ResponseEntity<ProblemDetail>> constraintViolationException(ConstraintViolationException exception){
        log.error("出现异常 {}",ExceptionUtil.stacktraceToString(exception));

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




    @ExceptionHandler(value = {IllegalAccessException.class,IllegalArgumentException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Mono<ResponseEntity<ProblemDetail>> IllegalArgumentException(Exception exception){
        log.error("出现异常 {}",ExceptionUtil.stacktraceToString(exception));
        ProblemDetail problemDetail = ProblemDetail.forStatus(HttpStatus.BAD_REQUEST);
        problemDetail.setDetail(exception.getMessage());
        return Mono.just(ResponseEntity.of(problemDetail).build());
    }



    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.BAD_GATEWAY)
    public Mono<ResponseEntity<ProblemDetail>> exception(Exception exception){
        log.error("出现异常 {}",ExceptionUtil.stacktraceToString(exception));
        ProblemDetail problemDetail = ProblemDetail.forStatus(HttpStatus.BAD_GATEWAY);
        problemDetail.setDetail("失败！");
        return Mono.just(ResponseEntity.of(problemDetail).build());
    }




    @ExceptionHandler(Throwable.class)
    @ResponseStatus(HttpStatus.BAD_GATEWAY)
    public Mono<ResponseEntity<ProblemDetail>> throwable(Throwable exception){
        log.error("出现异常 {}",ExceptionUtil.stacktraceToString(exception));
        ProblemDetail problemDetail = ProblemDetail.forStatus(HttpStatus.BAD_GATEWAY);
        problemDetail.setDetail("失败！");
        return Mono.just(ResponseEntity.of(problemDetail).build());
    }
}
