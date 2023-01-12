package com.ifx.client.service.helper;

import cn.hutool.core.collection.CollectionUtil;
import com.ifx.account.vo.AccountBaseInfo;
import com.ifx.connect.proto.Protocol;
import com.ifx.connect.proto.parse.DubboGenericParse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 * 注册使用
 */
@Component
@Slf4j
public class RegisterHelper {

    private static Protocol RegisterProtocol  = null;

    public Protocol applyRegister(AccountBaseInfo vo) throws NoSuchMethodException {
        if (RegisterProtocol == null ){
            Method search = com.ifx.account.service.AccountService.class.getMethod("register", AccountBaseInfo.class);
            RegisterProtocol = DubboGenericParse.applyMsgProtocol(search, CollectionUtil.newArrayList(vo));
            return RegisterProtocol;
        }
        return RegisterProtocol;
    }
}
