package com.ifx.common.utils;

import cn.hutool.core.collection.CollectionUtil;
import lombok.extern.slf4j.Slf4j;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.Set;

/**
 * javax 303 基础校验工具类
 * @author pengpeng
 * @description
 * @date 2023/1/12
 */
@Slf4j
public class ValidatorUtil {

    static Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

//    /**
//     * 验证单个实体
//     *
//     * @param t   参数
//     * @param <T> 类型
//     * @return 验证结果
//     */
//    public static <T> List<String> validateOutString(T t , Class<?>... group) {
//        ArrayList<String> list = CollectionUtil.newArrayList();
//        Mono.just(Validation.buildDefaultValidatorFactory().getValidator())
//                .doOnError(Mono::error)
//                .flatMap(validator -> Mono.just(validator.validate(t,group)))
//                .onErrorResume(Exception.class,exception -> Mono.just(CollectionUtil.newHashSet()))
//                .subscribe(res-> res.forEach(va->list.add(va.getMessage())));
//        return list;
//    }
//
//    /**
//     * 验证单个实体
//     *
//     * @param t   参数
//     * @param <T> 类型
//     * @return 验证结果
//     */
//    public static <T> Flux<Set<ConstraintViolation<T>>> validateFlux(T t , Class<?>... group) {
//        Flux<Set<ConstraintViolation<T>>> setFlux =
//                Flux.just(Validation.buildDefaultValidatorFactory().getValidator())
////                .doOnError(Mono::error)
//                .flatMap(validator -> Mono.just(validator.validate(t, group)))
//                .onErrorResume(Exception.class, exception -> Mono.just(CollectionUtil.newHashSet()));
//        return setFlux;
//    }





    /**
     * 验证单个实体
     *
     * @param t   参数
     * @param <T> 类型
     * @return 验证结果
     */
    public static <T>  Set<ConstraintViolation<T>> validateOne(T t , Class<?>... group) {
        Set<ConstraintViolation<T>> validateResult = validator.validate(t,group);
        return validateResult;
    }


    /**
     * 验证是否存在异常
     *
     * @param t   参数
     * @param <T> 类型
     * @return 验证结果
     */
    public static <T>  Boolean validateOutBoolean(T t , Class<?>... group) {
        Set<ConstraintViolation<T>> constraintViolations = validateOne(t, group);
        return CollectionUtil.isNotEmpty(constraintViolations);
    }

//    /***
//     * 验证并抛出异常
//     * @param t
//     * @param group
//     * @param <T>
//     * @throws ValidationException
//     */
//    public static <T> void validateor(T t,Class<?>... group) throws ValidationException{
//        validateFlux(t,group)
//                .subscribe(set->{
//                    if (CollectionUtil.isNotEmpty(set)){
//                        throw new ValidationException(set.stream().findFirst().get().getMessage());
//                    }
//                });
//    }
}
