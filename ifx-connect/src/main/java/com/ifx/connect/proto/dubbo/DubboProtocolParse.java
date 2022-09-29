package com.ifx.connect.proto.dubbo;

import com.ifx.connect.proto.IProtocolParse;
import com.ifx.connect.proto.Protocol;
import com.ifx.connect.proto.ProtocolHeaderConst;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class DubboProtocolParse implements IProtocolParse {

    @Override
    public Protocol parseProtocol(String protocolHeader, Object data) {
        if (!protocolHeader.startsWith(ProtocolHeaderConst.DUBBO_PROTOCOL_HEADER)){
//                1. 去除协议头
            String substring = protocolHeader.substring(protocolHeader.lastIndexOf(ProtocolHeaderConst.DUBBO_PROTOCOL_HEADER) + 1);
//            substring
//            2.获取接口全限定路径
//            3.获取方法名称
//            4.获取参数签名类型列表
//            5.组装参数参数数组
            log.error("ifx protocol parse protocol error and args is {}",protocolHeader);
        }

        return null;
    }




//    public Protocol parseProtocol()
}
