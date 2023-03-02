package com.ifx.server.service.impl;

import com.ifx.server.enums.ClientStateEnum;
import com.ifx.server.netty.NettyS2CAction;
import com.ifx.server.service.ServerActionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Map;

@Service
public class ServerActionServiceImpl implements ServerActionService {

    @Autowired
    private NettyS2CAction nettyS2CAction;

    @Override
    public void accountChannelActive(String account, String clientUUId) {
        
    }

    @Override
    public Boolean accountChannelRelease(String account) {
        nettyS2CAction.releaseClient(account);
        return true;
    }

    @Override
    public String clientState(String account) {
        return nettyS2CAction.channelActive(account) ?
                ClientStateEnum.ONLINE.name() : ClientStateEnum.OFFLINE.name();
    }


    @Override
    public Boolean clientAliveState(String account) {
        return null;
    }

    @Override
    public Map<String, Boolean> clientBatchAliveState(Collection<String> accounts) {
        return null;
    }

    @Override
    public String clientState() {
        return null;
    }
}
