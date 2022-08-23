package com.ifx.server.netty.holder;

import io.netty.channel.Channel;
import org.springframework.stereotype.Component;

import java.util.concurrent.ConcurrentHashMap;

@Component
public class NettyContext {
    private ConcurrentHashMap<String, Channel> channelMap ;

    public void addAccount(Channel channel ,String account){
        if (channelMap == null ){
            channelMap = new ConcurrentHashMap<>();
        }
        channelMap.put(account,channel);
    }

    public Channel getChannelByAccount(String account){
        return channelMap.computeIfAbsent(account, (k)-> null);
    }
}
