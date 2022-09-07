package com.ifx.server.invoke;

import com.ifx.connect.proto.Protocol;
import com.ifx.connect.proto.ProtocolHeaderConst;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
@Slf4j
public class ServerProtoReceive  {

    @Resource
    private GateInvoke gateInvoke;

    public void received(ChannelHandlerContext ctx, Protocol protocol){
//        1.接收协议 解析规则
        String header = protocol.getProtocol();
        if (header.startsWith(ProtocolHeaderConst.DUBBO_PROTOCOL_HEADER)){
            log.info("执行【dubbo】 方法");
            gateInvoke.doWork(ctx, protocol);
        }
    }
}
