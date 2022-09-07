package com.ifx.client.service.helper;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.io.IoUtil;
import cn.hutool.core.util.ByteUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.ifx.account.service.AccountService;
import com.ifx.client.parse.DubboGenericParse;
import com.ifx.connect.proto.Protocol;
import com.ifx.connect.proto.dubbo.DubboApiMetaData;
import com.ifx.connect.proto.dubbo.DubboProtocol;
import com.ifx.connect.proto.ifx.IFxMsgProtocol;

import java.util.Arrays;

public class ProtocolHelper {


    public static Protocol apply(Class<?> service, String methodName, Class<?>... param){
        Object[] objects = Arrays.stream(param).toArray();
//       TODO 分包的大小 上传 包length的实现
        DubboApiMetaData metaData = DubboGenericParse.applyMeta(service, methodName, objects);
        Protocol protocol = new DubboProtocol();
        protocol.setBody(JSON.toJSONString(metaData));
        protocol.setType(IFxMsgProtocol.CLIENT_TO_SERVER_MSG_HEADER);
        return protocol;
    }
}
