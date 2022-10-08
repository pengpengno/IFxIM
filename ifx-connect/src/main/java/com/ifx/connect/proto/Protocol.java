package com.ifx.connect.proto;

import com.ifx.common.res.Result;
import com.ifx.connect.proto.dubbo.DubboApiMetaData;
import lombok.Data;

import java.io.Serializable;

@Data
public class Protocol implements Serializable {

    private String protocol;   //协议头
    /**
     * 请求体
     * @see DubboApiMetaData
     */
    private String protocolBody; // 协议体

    private Long serial;  //包syn序列号
    /**
     * @see com.ifx.connect.proto.ifx.IFxMsgProtocol
     */
    private String  type; // 请求类型

    private String clientMark;  // 客户端 唯一标识 用于再服务端建立与channel的绑定关系

    private String trace;  // trace
    @Deprecated   //现已在传输钱加入 length 长度的 header
    /**
     * @see com.ifx.connect.encoder.ProtocolEncoder
     */
    private Long  length; // 数据包大小

    private String clientSDKVersion;   // 客户端版本类类型

    private Object content;

    public <T> T  getRes(Class<T> tClass){
        return null;
    }


}
