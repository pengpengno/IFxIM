package com.ifx.server.netty.holder;

import com.ifx.common.base.AccountInfo;
import io.netty.channel.Channel;
import io.netty.util.Attribute;
import io.netty.util.AttributeKey;
import org.springframework.stereotype.Component;

import java.util.concurrent.ConcurrentHashMap;

@Component
public class NettyContext {

    private static ConcurrentHashMap<String, Channel> channelMap ; // <account ,channel>

    private static final AttributeKey<AccountInfo> ACCOUNT_CHANNEL_KEY = AttributeKey.valueOf("account");

    public void addAccount(Channel channel , AccountInfo account){
//        TODO 加入中间件做分布式缓存处理
        if (channelMap == null ){
            channelMap = new ConcurrentHashMap<>();
        }
        Attribute<AccountInfo> ke = channel.attr(ACCOUNT_CHANNEL_KEY);
        ke.set(account);
        channelMap.put(account.getAccount(),channel);
    }

    public AccountInfo getCurrentAcc(Channel channel){
        Attribute<AccountInfo> attr = channel.attr(ACCOUNT_CHANNEL_KEY);
        AccountInfo accountInfo = attr.get();
        return accountInfo;
    }

    public Channel getChannelByAccount(String account){
        return channelMap.computeIfAbsent(account, (k)-> null);
    }
}
