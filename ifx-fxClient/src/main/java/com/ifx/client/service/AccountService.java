package com.ifx.client.service;

import cn.hutool.core.exceptions.ExceptionUtil;
import com.alibaba.fastjson.JSON;
import com.ifx.account.vo.AccountSearchVo;
import com.ifx.client.parse.DubboGenericParse;
import com.ifx.client.service.helper.AccountHelper;
import com.ifx.connect.proto.Protocol;
import com.ifx.connect.proto.dubbo.DubboApiMetaData;
import com.ifx.connect.proto.dubbo.DubboProtocol;
import com.ifx.connect.proto.ifx.IFxMsgProtocol;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.lang.reflect.Method;

@Slf4j
@Service
public class AccountService {
    @Resource
    private AccountHelper accountHelper;

//    @Resource

    public Protocol query(AccountSearchVo searchVo){
        try{
            Method search = com.ifx.account.service.AccountService.class.getMethod("search", AccountSearchVo.class);
            DubboApiMetaData metaData = DubboGenericParse.applyMeta0(com.ifx.account.service.AccountService.class, search, searchVo);
            Protocol protocol = new DubboProtocol();
            protocol.setProtocolBody(JSON.toJSONString(metaData));
            protocol.setType(IFxMsgProtocol.CLIENT_TO_SERVER_MSG_HEADER);
            return protocol;
        }
        catch(Exception e){
            log.error(" {} ", ExceptionUtil.stacktraceToString(e));
        }
        return null;
    }

//    public void query0(Protocol protocol){
//
//    }

}
