package com.ifx.server.service.impl;

import com.ifx.server.netty.NettyS2CAction;
import com.ifx.server.service.SeverActionService;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@DubboService
@Service
public class ServerActionServiceImpl implements SeverActionService {

    @Autowired
    private NettyS2CAction nettyS2CAction;

    @Override
    public void AccountChannelActive(String account, String clientUUId) {
        
    }

    @Override
    public void AccountChannelRelease(String account) {
        nettyS2CAction.releaseClient(account);
    }
}
