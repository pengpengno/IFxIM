package com.ifx.connect.proto;

/** 协议序列化工厂 */
public interface IProtocolParse {
    /**
     *
     * @param protocolHeader  dubbo://interface::com.ifx.account.server.AccountService & method::login
     */
    public Protocol parseProtocol(String protocolHeader,Object data);


}
