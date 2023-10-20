package com.ifx.common.utils;

import cn.hutool.core.collection.CollectionUtil;
import com.ifx.common.ex.valid.ValidationException;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * javax 303 基础校验工具类
 * @author pengpeng
 * @description
 * @date 2023/1/12
 */
//@Slf4j
public class ValidatorUtil {

    static Validator validator = Validation.buildDefaultValidatorFactory().getValidator();



    /**
     * 验证单个实体
     *
     * @param t   参数
     * @param <T> 类型
     * @return 验证结果
     */
    public static <T>  Set<ConstraintViolation<T>> validateOne(T t , Class<?>... group) {
        return validator.validate(t,group);
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

    public static <T> List<String> validate(T t , Class<?>... group)  {
        ArrayList<String> messages = CollectionUtil.newArrayList();
        if (t == null){
            messages.add("The  specify is  valid !");
            return messages;
        }
        Set<ConstraintViolation<T>> validations = validator.validate(t,group);
        for (ConstraintViolation<T> validation : validations) {
            messages.add(validation.getMessage());
        }
        return messages;
    }

    /***
     * 校验参数 如果不合规 则以一条信息 作为 errorMessage 抛出
     * @param t 待验证的实体
     * @param group 验证组
     * @param <T> 实体类型
     * @throws ValidationException 错误异常 默认情况抛出第一条错误的 message
     */
    public static <T> void validateThrows(T t, Class<?>... group) throws ValidationException {
        List<String> validate = validate(t, group);
        if (CollectionUtil.isNotEmpty(validate)){
            throw new ValidationException(validate.get(0));
        }
    }


}
