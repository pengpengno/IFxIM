package com.ifx.server.invoke;

import com.ifx.connect.proto.Protocol;
import com.ifx.connect.proto.ProtocolHeaderConst;
import com.ifx.server.invoke.dubbo.DubboInvoke;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import javax.annotation.Resource;

@Component
@Slf4j
public class ServerProtoReceive  {
    @Resource
    private DubboInvoke dubboInvoke;

    public void received(ChannelHandlerContext ctx, Protocol protocol){
        String header = protocol.getProtocol();
        if (header.startsWith(ProtocolHeaderConst.DUBBO_PROTOCOL_HEADER)){
            log.info("执行【dubbo】 方法");
            dubboInvoke.doWork(ctx, protocol);
        }
    }
}
