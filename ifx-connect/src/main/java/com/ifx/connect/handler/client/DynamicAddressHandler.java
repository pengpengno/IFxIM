package com.ifx.connect.handler.client;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPromise;
import io.netty.handler.address.DynamicAddressConnectHandler;

import java.net.InetSocketAddress;
import java.net.SocketAddress;

/**
 * @author pengpeng
 * @description
 * @date 2023/3/1
 */
public class DynamicAddressHandler extends DynamicAddressConnectHandler {

    private InetSocketAddress address;


//    public DynamicAddressHandler() {
//        DynamicAddressConnectHandler dynamicAddressConnectHandler = new DynamicAddressConnectHandler();
//    }


    @Override
    public void bind(ChannelHandlerContext ctx, SocketAddress localAddress, ChannelPromise promise) throws Exception {
        super.bind(ctx, localAddress, promise);
    }

    @Override
    public void disconnect(ChannelHandlerContext ctx, ChannelPromise promise) throws Exception {
        super.disconnect(ctx, promise);
    }

}
