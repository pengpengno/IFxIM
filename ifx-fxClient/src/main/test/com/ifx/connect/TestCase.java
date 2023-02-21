package com.ifx.connect;

import cn.hutool.core.collection.CollectionUtil;
import com.ifx.account.service.AccountService;
import com.ifx.account.vo.search.AccountSearchVo;
import com.ifx.connect.proto.dubbo.DubboApiMetaData;
import com.ifx.connect.proto.parse.DubboGenericParse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Method;

public class TestCase {
    @Test
    public void test() throws NoSuchMethodException {
        AccountSearchVo vo = new AccountSearchVo();
        Method search = AccountService.class.getMethod("test", Long.class);
        DubboApiMetaData metaData = DubboGenericParse.applyMeta(search,CollectionUtil.newArrayList(vo));
        System.out.println(search);
        Assertions.assertEquals(metaData.getApiInterFacePath(),AccountService.class.getName());
    }
}
