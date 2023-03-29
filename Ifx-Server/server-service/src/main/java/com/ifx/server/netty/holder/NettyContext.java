package com.ifx.server.netty.holder;

import com.ifx.common.base.AccountInfo;
import io.netty.channel.Channel;
import io.netty.util.Attribute;
import io.netty.util.AttributeKey;

import java.util.concurrent.ConcurrentHashMap;

/**
 * netty 上下文处理
 * <p> 存储 netty Channel 通过 account 标识 获取 channel</p>
 */
public class NettyContext {

    private static ConcurrentHashMap<String, Channel> channelMap ; // <account ,channel>
    // 未来是否需要考虑多端  情况
    private static final AttributeKey<AccountInfo> ACCOUNT_CHANNEL_KEY = AttributeKey.valueOf("account");

    public void addAccount(Channel channel , AccountInfo account){
//        TODO 加入中间件做分布式缓存处理 避免单体下的负载过高带来的单点崩溃
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

    /**
     * 判断 指定账户 是否 处于连接状态
     * @param attrKey
     * @return
     */
    public Boolean hasKey(String attrKey){
        final String key = attrKey;
        return channelMap.containsKey(key);
    }
}
