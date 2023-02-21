//package com.ifx.account.config;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.http.codec.ServerCodecConfigurer;
//import org.springframework.validation.Validator;
//import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
//import org.springframework.web.reactive.config.ResourceHandlerRegistry;
//import org.springframework.web.reactive.config.WebFluxConfigurer;
//
///**
// * @author pengpeng
// * @description
// * @date 2023/2/17
// */
//@Configuration
//public class WebConfig implements WebFluxConfigurer {
//
//    @Override
//    public void configureHttpMessageCodecs(ServerCodecConfigurer configurer) {
//        configurer.defaultCodecs().maxInMemorySize(512*1024);
//    }
//
//
//    @Override
//    public void addResourceHandlers(ResourceHandlerRegistry registry) {
//        WebFluxConfigurer.super.addResourceHandlers(registry);
//    }
//
//
//
//    @Override
//    public Validator getValidator(@Autowired LocalValidatorFactoryBean factoryBean) {
//        return factoryBean.getValidator();
////        return WebFluxConfigurer.super.getValidator();
//    }
//}
