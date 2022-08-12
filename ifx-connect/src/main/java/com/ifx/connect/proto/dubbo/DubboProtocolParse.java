package com.ifx.connect.proto.dubbo;

import com.ifx.connect.proto.IProtocolParse;
import com.ifx.connect.proto.Protocol;
import com.ifx.connect.proto.ProtocolConst;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class DubboProtocolParse implements IProtocolParse {
    @Override
    public Protocol parseProtocol(String protocolHeader, Object data) {
        if (!protocolHeader.startsWith(ProtocolConst.DubboProtocolHeader)){
            log.error("ifx protocol parse protocol error and args is {}",protocolHeader);
        }

        return null;
    }


//    public Protocol parseProtocol()
}
