package com.ifx.server.netty;

import com.ifx.connect.proto.Protocol;
import com.ifx.server.netty.holder.NettyContext;
import com.ifx.server.s2c.IServer2ClientAction;
import io.netty.channel.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
@Slf4j
public class NettyS2CAction implements IServer2ClientAction {
    @Resource
    private NettyContext nettyContext;

    public void sendProtoCol(Channel channel ,Protocol protocol) {
        if (channel== null || !channel.isActive()){
            log.warn("channel is close can not send message");
          return;
        }
         channel.writeAndFlush(protocol);
    }

    protected Boolean hasAccount( Channel channel) {
        return channel == null && nettyContext.getCurrentAcc(channel)!=null;
    }

    public void sendProtoCol(String account , Protocol protocol){
        sendProtoCol(findChannel(account),protocol);
    }

    protected Channel findChannel(String account){
        log.info("正在查询 账户{} 的channel",account);
        return nettyContext.getChannelByAccount(account);
    }

    /**
     * <h1>Channel 是否处理存活态</h1>
     * @param account
     * @return
     */
    public Boolean channelActive(String account){
        return nettyContext.hasKey(account);
    }


    @Override
    public void releaseClient(Channel channel) {
        log.info("正在关闭 channel {} ",channel.remoteAddress());
        channel.close();
    }

    @Override
    public void releaseClient(String account) {
        Channel channel = findChannel(account);
        log.info("正在关闭");
        releaseClient(channel);
    }
}
