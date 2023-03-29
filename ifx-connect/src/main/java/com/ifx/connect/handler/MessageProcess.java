package com.ifx.connect.handler;

import com.google.protobuf.Message;

/**
 * @author pengpeng
 * @description
 * @date 2023/3/14
 */
@FunctionalInterface
public interface MessageProcess {


    public Message parse(byte[] bytes);


}
