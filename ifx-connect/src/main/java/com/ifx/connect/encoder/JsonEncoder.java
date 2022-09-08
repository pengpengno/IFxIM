package com.ifx.connect.encoder;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.ifx.connect.proto.Protocol;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class JsonEncoder implements Encoder{


    public <T> T encoder (String args,Class<T> tClass){
        try{
            return JSONObject.parseObject(args,tClass);
        }catch (Exception e){
            log.info("序列化异常  {} ",args);
            throw e;
        }
    }
    public String encoder (Object args){
        return JSON.toJSONString(args);
    }

    @Override
    public String encoder() {
        return null;
    }
}
