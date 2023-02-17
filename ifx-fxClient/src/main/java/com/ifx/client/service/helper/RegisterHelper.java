package com.ifx.client.service.helper;

import com.ifx.account.vo.AccountVo;
import com.ifx.connect.proto.Protocol;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * 注册使用
 */
@Component
@Slf4j
public class RegisterHelper {

    private static Protocol RegisterProtocol  = null;

    public Protocol applyRegister(AccountVo vo) throws NoSuchMethodException {
        if (RegisterProtocol == null ){
//            Method search = com.ifx.account.service.Re.class.getMethod("register", AccountVo.class);
//            RegisterProtocol = DubboGenericParse.applyMsgProtocol(search, CollectionUtil.newArrayList(vo));
            return RegisterProtocol;
        }
        return RegisterProtocol;
    }
}
