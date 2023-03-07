package com.ifx.server.invoke;

import com.ifx.connect.proto.Protocol;
import com.ifx.connect.proto.ProtocolHeaderConst;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;
/**
 * 服务端协议接收层<br/>
 *
 */
//@Component
@Slf4j
public class ServerProtoReceive  {

//    @Resource
    private GateInvoke gateInvoke;

    /**
     * <p> 解析接收的 Protocol 交由网关路由命令解析层 {@link GateInvoke }处理 </p>
     * @param ctx
     * @param protocol
     */
    public void received(ChannelHandlerContext ctx, Protocol protocol){
//        1.接收协议 解析规则
        String header = protocol.getProtocol();
        if (header.startsWith(ProtocolHeaderConst.DUBBO_PROTOCOL_HEADER)){
            log.info("执行【dubbo】 方法");
            gateInvoke.doWork(ctx, protocol);
        }
    }



}
