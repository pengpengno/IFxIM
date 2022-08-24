package com.ifx.server.s2c;

import com.ifx.connect.proto.Protocol;
import io.netty.channel.Channel;

public interface IServer2ClientAction {


    public void sendProtoCol(Channel channel,Protocol protocol);

    public void sendProtoCol(String account,Protocol protocol);

    public void closeChanel(Channel channel);


}
