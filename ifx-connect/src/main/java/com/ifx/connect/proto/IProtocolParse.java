package com.ifx.connect.proto;

/** 协议序列化工厂 */
public interface IProtocolParse {
    /**
     *
     * @param protocolHeader such as dubbo://interface::com.ifx.account.server.AccountService&method::login
     * @see
     * @return
     */
    public Protocol parseProtocol(String protocolHeader,Object data);


}
