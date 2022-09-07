package com.ifx.connect.proto;

import com.ifx.common.res.Result;
import lombok.Data;

import java.io.Serializable;

@Data
public class Protocol<T> implements Serializable {

    private String protocol;   //协议头

    private Long serial;  //包syn序列号
    /**
     * @see com.ifx.connect.proto.ifx.IFxMsgProtocol
     */
    private String  type; // 请求类型

    private String clientMark;  // 客户端 唯一标识 用于再服务端建立与channel的绑定关系

    private String trace;  // trace

    private Long  length; // 数据包大小

    private String clientSDKVersion;   // 客户端版本类类型

    private Result<T> res;

    private String body;
}
