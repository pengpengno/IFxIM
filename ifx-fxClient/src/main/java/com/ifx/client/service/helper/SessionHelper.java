package com.ifx.client.service.helper;

import com.ifx.connect.proto.Protocol;
import com.ifx.connect.proto.parse.DubboGenericParse;
import com.ifx.session.service.SessionService;
import lombok.SneakyThrows;

import java.lang.reflect.Method;

/***
 * 会话接口协议组装层
 * @author pengpeng
 * @date 2023/1/9
 */
public class SessionHelper {
    private static Protocol SearchSessionProtocol = null;

    @SneakyThrows
    public static Protocol getSearchSessionProtocol(){
        if (SearchSessionProtocol !=null){
            return SearchSessionProtocol;
        }
        Method search = SessionService.class.getMethod("addorUpSession");
        SearchSessionProtocol = DubboGenericParse.applyMsgProtocol(search);
        return SearchSessionProtocol;
    }

}
