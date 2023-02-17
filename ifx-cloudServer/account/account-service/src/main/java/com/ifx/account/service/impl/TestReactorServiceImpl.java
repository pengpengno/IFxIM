//package com.ifx.account.service.impl;
//
//import com.ifx.account.service.TestReactorService;
//import reactor.core.publisher.Mono;
//
//import java.util.Optional;
//
///**
// * @author pengpeng
// * @description
// * @date 2023/2/8
// */
//public class TestReactorServiceImpl implements TestReactorService {
//
//
//    @Override
//    public Mono<Long> getMonoLong(Long l) {
//        return Mono.justOrEmpty(Optional.ofNullable(l))
//                .switchIfEmpty(Mono.just(111l))
////                .flatMap(k-> Mono.just(k.))
//                ;
//    }
//}
