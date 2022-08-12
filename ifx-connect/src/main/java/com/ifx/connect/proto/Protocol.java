package com.ifx.connect.proto;

import com.ifx.common.res.Result;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class Protocol<T> implements Serializable {

    /**
     * @see com.ifx.connect.connection.enums.ConnectCommandEnum
     * this design is deprecated {@see com.ifx.connect.proto.Protocol#protocol}
     */@Deprecated
    private String command;

    private String protocol;

    private Long serial;  //包syn序列号

    private String taskCode;  //taskCode

    private Long  length; // 数据包大小

    private String clientSDKVersion;   // 客户端版本类类型

    private Result<T> res;

    private String body;
}
