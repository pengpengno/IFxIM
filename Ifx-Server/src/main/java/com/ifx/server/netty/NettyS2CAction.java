package com.ifx.server.netty;

import com.alibaba.fastjson2.JSON;
import com.ifx.connect.proto.Protocol;
import com.ifx.server.netty.holder.NettyContext;
import com.ifx.server.s2c.IServer2ClientAction;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.util.CharsetUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
@Component
@Slf4j
public class NettyS2CAction implements IServer2ClientAction {
//    @Resource
    private NettyContext nettyContext;

    public void sendProtoCol(Channel channel ,Protocol protocol) {
        if (channel== null || !channel.isActive()){
            log.warn("channel is close can not send message");
          return;
        }
         channel.writeAndFlush(Unpooled.copiedBuffer(JSON.toJSONString(protocol), CharsetUtil.UTF_8));
    }


    public void sendProtoCol(String account , Protocol protocol){
        sendProtoCol(findChannel(account),protocol);
    }

    protected Channel findChannel(String account){
        return nettyContext.getChannelByAccount(account);
    }

    protected void sendMsg(Channel channel ,String msg){

    }

    @Override
    public void closeChanel(Channel channel) {
        log.info("正在关闭 channel {} ",channel.remoteAddress());
        channel.close();
    }
}
