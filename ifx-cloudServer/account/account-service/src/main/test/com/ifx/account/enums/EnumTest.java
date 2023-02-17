package com.ifx.account.enums;

import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author pengpeng
 * @description
 * @date 2023/2/13
 */
@Slf4j
public class EnumTest {


    @Test
    public void testLogOut(){
        String logs = "MyTestLog";
        LogEnum sLf4j  = LogEnum.SLf4j;

        sLf4j.outLog((logArgs)-> {
            log.info(logArgs);
            return LogEnum.SLf4j.name();
        },logs);

        String logEnumName = sLf4j.outLogFunction().apply(logs);
        Assert.assertEquals(logEnumName,LogEnum.SLf4j.name());

    }
}
