package com.ifx.connect.valiator;

import cn.hutool.core.collection.CollectionUtil;
import com.ifx.common.base.AccountInfo;
import com.ifx.common.utils.ValidatorUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import javax.validation.ConstraintViolation;
import java.util.Set;

/**
 * @author pengpeng
 * @description
 * @date 2023/1/11
 */
@Slf4j
public class ValiatorTest {

    @Test
    public void vailator(){
        AccountInfo accountInfo = new AccountInfo();
        Set<ConstraintViolation<AccountInfo>> constraintViolations = ValidatorUtil.validateOne(accountInfo);
        Assertions.assertTrue(CollectionUtil.isNotEmpty(constraintViolations));
    }



}
