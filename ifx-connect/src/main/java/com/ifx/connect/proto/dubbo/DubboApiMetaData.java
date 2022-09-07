package com.ifx.connect.proto.dubbo;

import lombok.Data;

import java.io.Serializable;

@Data
public class DubboApiMetaData implements Serializable {

    private String apiInterFacePath;  // 接口全限定类名

    private String method;   // 方法名称

    private String[] argsType;  // 接口签名参数

    private Object[] args;
}
