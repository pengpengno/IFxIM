package com.ifx.connect.spi;

import com.ifx.connect.proto.Protocol;
import io.netty.channel.ChannelHandlerContext;

/**
 * 服务端业务处理扩展点
 * @author pengpeng
 * @description
 * @date 2023/2/3
 */
public interface ServerBusinessSPI {

    public Runnable doBusiness();
    public Runnable doBusiness(ChannelHandlerContext ctx , Protocol protocol);


}
