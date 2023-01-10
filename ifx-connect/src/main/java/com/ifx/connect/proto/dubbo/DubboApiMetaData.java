package com.ifx.connect.proto.dubbo;

import lombok.Data;

import java.io.Serializable;

/**
 * dubbo api 抽象元数据
 * 封装了 dubbo 使用泛化调用的 基本元数据
 */
@Data
public class DubboApiMetaData implements Serializable {

    private String apiInterFacePath;  // 接口全限定类名

    private String method;   // 方法名称

    private String[] argsType;  // 接口签名参数

    private Object[] args;
}
