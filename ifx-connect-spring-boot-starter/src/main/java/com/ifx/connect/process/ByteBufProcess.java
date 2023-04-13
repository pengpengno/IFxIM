package com.ifx.connect.process;

import io.netty.buffer.ByteBuf;
import reactor.netty.Connection;
@FunctionalInterface
public interface ByteBufProcess {
    public void process(Connection con , ByteBuf byteBuf) throws IllegalAccessException;

}
