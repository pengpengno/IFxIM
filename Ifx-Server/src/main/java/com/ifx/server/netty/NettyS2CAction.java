package com.ifx.server.netty;

import com.ifx.connect.proto.Protocol;
import com.ifx.server.netty.holder.NettyContext;
import com.ifx.server.s2c.IServer2ClientAction;
import io.netty.channel.Channel;

import javax.annotation.Resource;
@Resource
public class NettyS2CAction implements IServer2ClientAction {
    @Resource
    private NettyContext nettyContext;

    @Override
    public void sendProtoCol(Protocol protocol) {

    }

    public void sendProtoCol(Channel ctx,Protocol protocol){
//        ctx.writeAndFlush()
    }

    protected void sendProtocol(String account , Protocol protocol){
        sendProtoCol(findChannel(account),protocol);
    }

    protected Channel findChannel(String account){
        return nettyContext.getChannelByAccount(account);
    }

    protected void sendMsg(Channel channel ,String msg){

    }

    @Override
    public void closeChanel() {

    }
}
