//package com.ifx.account.except;
//
//import cn.hutool.core.exceptions.ExceptionUtil;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.ExceptionHandler;
//import org.springframework.web.bind.annotation.ResponseStatus;
//import org.springframework.web.bind.annotation.RestControllerAdvice;
//import reactor.core.publisher.Mono;
//
//import java.util.Optional;
//
///**
// * @author pengpeng
// * @description
// * @date 2023/2/13
// */
//@RestControllerAdvice
//@Slf4j
//public class ControllerException  {
//
//
//
//    @ExceptionHandler
//    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
//    public Mono<ResponseEntity<String>> illegalFail(IllegalAccessException exception){
////        ProblemDetail
////        ResponseEntity<String> illegalFail =
////                ResponseEntity
////                        .status(HttpStatus.INTERNAL_SERVER_ERROR)
////                        .body("illegalFail");
////        return Mono.deferContextual(contextView -> {
////            contextView.stream().mapMulti()
////        });
//        return Mono.just(ResponseEntity.of(Optional.of("ill")));
//    }
//
//
//
//    @ExceptionHandler
//    @ResponseStatus(HttpStatus.BAD_GATEWAY)
//    public String fail(Exception exception){
//        log.error("出现异常  异常堆栈 {}", ExceptionUtil.stacktraceToString(exception));
//        return "fail";
//    }
//}
