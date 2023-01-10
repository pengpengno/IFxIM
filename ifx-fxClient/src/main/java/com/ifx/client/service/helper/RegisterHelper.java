package com.ifx.client.service.helper;

import cn.hutool.core.collection.CollectionUtil;
import com.ifx.account.vo.AccountBaseInfo;
import com.ifx.connect.proto.Protocol;
import com.ifx.connect.proto.dubbo.DubboApiMetaData;
import com.ifx.connect.proto.dubbo.DubboProtocol;
import com.ifx.connect.proto.parse.DubboGenericParse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 *
 */
@Component
@Slf4j
public class RegisterHelper {

    public Protocol applyRegister(AccountBaseInfo vo) throws NoSuchMethodException {
        Method search = com.ifx.account.service.AccountService.class.getMethod("register", AccountBaseInfo.class);
        DubboApiMetaData metaData = DubboGenericParse.applyMeta(search, CollectionUtil.newArrayList(vo));
        return new DubboProtocol(metaData);
    }
}
