package com.ifx.connect.proto.dubbo;

import com.alibaba.fastjson.JSON;
import com.ifx.connect.proto.Protocol;
import com.ifx.connect.proto.ProtocolHeaderConst;

public class DubboProtocol extends Protocol {

    public DubboProtocol(){
        super.setProtocol(ProtocolHeaderConst.DUBBO_PROTOCOL_HEADER);
    }

    public DubboProtocol(DubboApiMetaData data){
        super.setProtocol(ProtocolHeaderConst.DUBBO_PROTOCOL_HEADER);
        super.setBody(JSON.toJSONString(data));
    }



}
