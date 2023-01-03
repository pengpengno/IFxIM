package com.ifx.server.s2c;

import com.ifx.connect.proto.Protocol;
import io.netty.channel.Channel;

public interface IServer2ClientAction {

    /**
     * 发送基本协议包
     * @param channel netty Channel 客户端连接管道
     * @param protocol 协议包
     */
    public void sendProtoCol(Channel channel,Protocol protocol);

    public void sendProtoCol(String account,Protocol protocol);


    public void releaseClient(Channel channel);

    public void releaseClient(String account);


}
