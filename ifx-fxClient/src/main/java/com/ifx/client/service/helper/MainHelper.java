package com.ifx.client.service.helper;

import cn.hutool.core.collection.CollectionUtil;
import com.alibaba.fastjson.JSON;
import com.ifx.account.service.AccountService;
import com.ifx.client.parse.DubboGenericParse;
import com.ifx.connect.proto.Protocol;
import com.ifx.connect.proto.dubbo.DubboApiMetaData;
import com.ifx.connect.proto.dubbo.DubboProtocol;
import com.ifx.connect.proto.ifx.IFxMsgProtocol;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;

@Component
public class MainHelper {


    @SneakyThrows
    public Protocol applyChat( short vo){
        DubboApiMetaData metaData = DubboGenericParse.applyMeta(AccountService.class, "loginAndGetCur", CollectionUtil.newArrayList(vo));
        Protocol protocol = new DubboProtocol();
        protocol.setProtocolBody(JSON.toJSONString(metaData));
        protocol.setType(IFxMsgProtocol.LOGIN_MSG_HEADER);
        return protocol;
    }



}
