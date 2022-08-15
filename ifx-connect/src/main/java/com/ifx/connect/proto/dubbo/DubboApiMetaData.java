package com.ifx.connect.proto.dubbo;

import lombok.Data;

import java.io.Serializable;

@Data
public class DubboApiMetaData implements Serializable {

    private String apiInterFacePath;

    private String method;

    private String[] argsType;

    private Object[]  args;

}
