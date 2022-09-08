package com.ifx.client.service.helper;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.IdUtil;
import com.alibaba.fastjson.JSON;
import com.ifx.account.service.AccountService;
import com.ifx.account.vo.AccountBaseInfo;
import com.ifx.account.vo.AccountSearchVo;
import com.ifx.client.parse.DubboGenericParse;
import com.ifx.connect.proto.IProtocolParse;
import com.ifx.connect.proto.Protocol;
import com.ifx.connect.proto.ProtocolHeaderConst;
import com.ifx.connect.proto.ReqProtocol;
import com.ifx.connect.proto.dubbo.DubboApiConst;
import com.ifx.connect.proto.dubbo.DubboApiMetaData;
import com.ifx.connect.proto.dubbo.DubboProtocol;
import com.ifx.connect.proto.ifx.IFxMsgProtocol;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.lang.reflect.Method;

@Service
@Slf4j
public class AccountHelper {

    @SneakyThrows
    public Protocol applyLogins(AccountBaseInfo vo){
        DubboApiMetaData metaData = DubboGenericParse.applyMeta(AccountService.class, "loginAndGetCur", CollectionUtil.newArrayList(vo));
        Protocol protocol = new DubboProtocol();
        protocol.setBody(JSON.toJSONString(metaData));
        protocol.setType(IFxMsgProtocol.LOGIN_MSG_HEADER);
        return protocol;
    }

    /**
     * 废弃 请使用下方接口实现
     * {@link ProtocolHelper}
     * @see ProtocolHelper
     * @param vo
     * @return
     */
    @SneakyThrows
    @Deprecated
    public Protocol applySearch(AccountSearchVo vo){
        Method search = AccountService.class.getMethod("search", AccountSearchVo.class);
        Class<?> returnType = search.getReturnType();
        DubboApiMetaData metaData = DubboGenericParse.applyMeta(AccountService.class, "search", CollectionUtil.newArrayList(vo));
        Protocol protocol = new DubboProtocol();
        protocol.setBody(JSON.toJSONString(metaData));
        protocol.setType(IFxMsgProtocol.LOGIN_MSG_HEADER);
        return protocol;
    }



    public void sendLogin(){
//

    }


}
