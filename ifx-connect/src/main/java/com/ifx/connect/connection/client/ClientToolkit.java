package com.ifx.connect.connection.client;

import com.ifx.connect.connection.ConnectionDefaultValue;
import com.ifx.connect.connection.client.tcp.NettyClientAction;
import com.ifx.connect.enums.ConnectTypeEnums;

/**
 * IM系统连接获取客户端实现
 * @description IM系统工具箱
 * @author pengpeng
 * @date 2023/1/9
 */
public  class ClientToolkit {

    /**
     * 获取 {@link ClientAction} 的 Tcp 连接实现
     * @return 返回 ClientAction 的 Tcp 实现
     */
    public static ClientAction getTcpInstance(){
        return NettyClientAction.getInstance();
    }

    /***
     * 获取系统默认的客户端连接
     * @return 返回客户端连接实现
     */
    public static ClientAction getDefaultClientAction(){
        ConnectTypeEnums defaultValue = ConnectionDefaultValue.getDefaultConnectType();
        if (defaultValue == ConnectTypeEnums.TCP){
            return  NettyClientAction.getInstance();
        }
        return  NettyClientAction.getInstance();
    }

    public static  ClientLifeStyle getDefaultClientLifeStyle(){
        ConnectTypeEnums defaultValue = ConnectionDefaultValue.getDefaultConnectType();
        if (defaultValue == ConnectTypeEnums.TCP){
            return  NettyClientAction.getInstance();
        }
        return  NettyClientAction.getInstance();
    }
}
