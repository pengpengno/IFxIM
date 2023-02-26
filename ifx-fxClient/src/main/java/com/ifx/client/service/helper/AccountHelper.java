package com.ifx.client.service.helper;

import com.ifx.account.vo.AccountVo;
import com.ifx.account.vo.search.AccountSearchVo;
import com.ifx.connect.proto.Protocol;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class AccountHelper {

    private  static Protocol LOGINPROTOCOL = null;  //登陆协议

    private  static Protocol SEARCHPROTOCOL = null;  //搜索用户协议

    @SneakyThrows
    public static Protocol applyLogins(AccountVo vo){
        if (LOGINPROTOCOL !=null){
            return LOGINPROTOCOL;
        }
//        Method search = com.ifx.account.service.AccountService.class.getMethod("search", AccountSearchVo.class);
//        DubboApiMetaData metaData = DubboGenericParse.applyMeta0(com.ifx.account.service.AccountService.class, search, vo);
//        Protocol protocol = new DubboProtocol();
//        protocol.setProtocolBody(JSON.toJSONString(metaData));
//        protocol.setType(IFxMsgProtocol.LOGIN_MSG_HEADER);
//        LOGINPROTOCOL = protocol;
        return LOGINPROTOCOL;
    }




    /**
     * 提供所有账户的 Protocol
     * @param searchVo
     * @return
     */
    public static Protocol applySearchAccount(AccountSearchVo searchVo){
        try{
            if (SEARCHPROTOCOL !=null){
                return SEARCHPROTOCOL;
            }
//            Method search = com.ifx.account.service.AccountService.class.getMethod("search", AccountSearchVo.class);
//            return DubboGenericParse.applyMsgProtocol(search,searchVo);
            return null;
        }catch (Exception noSuchMethodException){
            log.error("没有该方法");
            return null;
        }
    }





}
