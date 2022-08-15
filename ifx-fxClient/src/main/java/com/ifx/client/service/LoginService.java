package com.ifx.client.service;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.IdUtil;
import com.alibaba.fastjson.JSON;
import com.ifx.account.service.AccountService;
import com.ifx.account.vo.AccountBaseInfo;
import com.ifx.client.parse.DubboGenericParse;
import com.ifx.connect.proto.IProtocolParse;
import com.ifx.connect.proto.Protocol;
import com.ifx.connect.proto.ProtocolHeaderConst;
import com.ifx.connect.proto.dubbo.DubboApiConst;
import com.ifx.connect.proto.dubbo.DubboApiMetaData;
import com.ifx.connect.proto.dubbo.DubboProtocol;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class LoginService {

    @SneakyThrows
    public Protocol applyLogins(AccountBaseInfo vo){
        DubboApiMetaData metaData = DubboGenericParse.applyMeta(AccountService.class, "login", CollectionUtil.newArrayList(vo));
        Protocol protocol = new DubboProtocol();
        protocol.setBody(JSON.toJSONString(metaData));
        return protocol;
    }

    public void sendLogin(){

    }


}
