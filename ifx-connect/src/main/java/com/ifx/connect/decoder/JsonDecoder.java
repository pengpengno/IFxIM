package com.ifx.connect.decoder;

import com.alibaba.fastjson2.JSON;

public class JsonDecoder implements Decoder{


    protected String decode(Object object ){
        return JSON.toJSONString(object);
    }
}
