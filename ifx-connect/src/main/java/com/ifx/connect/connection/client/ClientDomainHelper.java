package com.ifx.connect.connection.client;

import com.ifx.connect.connection.ConnectionDefaultValue;
import com.ifx.connect.connection.client.tcp.NettyClientAction;
import com.ifx.connect.enums.ConnectTypeEnums;

/**
 * IM系统连接获取客户端实现
 * @author pengpeng
 * @date 2023/1/9
 */
public  class ClientDomainHelper {

    /**
     * 获取 {@link ClientAction} 的 Tcp 连接实现
     * @return
     */
    public static ClientAction getTcpInstance(){
        return NettyClientAction.getInstance();
    }

    /***
     * 获取系统默认的客户端连接
     * @return 返回客户端连接实现
     */
    public static ClientAction getDefaultClientAction(){
        ConnectTypeEnums defaultValue = ConnectionDefaultValue.getDefaultValue();
        if (defaultValue == ConnectTypeEnums.TCP){
            return getTcpInstance();
        }
        return getTcpInstance();
    }
}
