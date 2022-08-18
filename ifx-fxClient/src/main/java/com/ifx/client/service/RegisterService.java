package com.ifx.client.service;

import cn.hutool.core.collection.CollectionUtil;
import com.alibaba.fastjson.JSON;
import com.ifx.account.service.AccountService;
import com.ifx.account.vo.AccountBaseInfo;
import com.ifx.client.parse.DubboGenericParse;
import com.ifx.connect.proto.Protocol;
import com.ifx.connect.proto.dubbo.DubboApiMetaData;
import com.ifx.connect.proto.dubbo.DubboProtocol;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class RegisterService {

    public Protocol applyRegister(AccountBaseInfo vo){
        DubboApiMetaData metaData = DubboGenericParse.applyMeta(AccountService.class, "register", CollectionUtil.newArrayList(vo));
        return new DubboProtocol(metaData);
    }
}
