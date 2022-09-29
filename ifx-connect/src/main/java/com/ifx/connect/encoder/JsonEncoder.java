package com.ifx.connect.encoder;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.ifx.connect.proto.Protocol;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import io.netty.util.CharsetUtil;
import lombok.extern.slf4j.Slf4j;

@Slf4j
//public class JsonEncoder extends MessageToByteEncoder implements Encoder{
public class JsonEncoder extends MessageToByteEncoder<Protocol> {


    public <T> T encoder (String args, Class<T> tClass){
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

    public String encoder() {
        return null;
    }

    @Override
    protected void encode(ChannelHandlerContext ctx, Protocol msg, ByteBuf out) throws Exception {
        String jsonString = JSON.toJSONString(msg);
        out.writeBytes(Unpooled.copiedBuffer(jsonString, CharsetUtil.UTF_8));
    }
}
