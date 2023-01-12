package com.ifx.connect.proto.dubbo;

import com.alibaba.fastjson2.JSON;
import com.ifx.connect.proto.Protocol;
import com.ifx.connect.proto.ProtocolHeaderConst;

/**
 *  Protocol 的 Dubbo 子类实现
 *  {@link com.ifx.connect.proto.Protocol}
 */
public class DubboProtocol extends Protocol {

    public DubboProtocol(){
        super.setProtocol(ProtocolHeaderConst.DUBBO_PROTOCOL_HEADER);
    }

    public DubboProtocol(DubboApiMetaData data){
        super.setProtocol(ProtocolHeaderConst.DUBBO_PROTOCOL_HEADER);
        super.setProtocolBody(JSON.toJSONString(data));
    }



}
