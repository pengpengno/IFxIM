package com.ifx.account.vaild;

import com.ifx.common.utils.ValidatorUtil;
import org.junit.Test;

import javax.validation.ConstraintViolation;
import java.util.Set;

/**
 * @author pengpeng
 * @description
 * @date 2023/2/20
 */
public class Validation {
    @Test
    public  void valid(){
        Acc build = new Acc();
        Set<ConstraintViolation<Acc>> constraintViolations = ValidatorUtil.validateOne(build);
        System.out.println("s");

    }
}
