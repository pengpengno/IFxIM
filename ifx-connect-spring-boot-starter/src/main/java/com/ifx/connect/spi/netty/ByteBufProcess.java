
package com.ifx.connect.spi.netty;

import io.netty.buffer.ByteBuf;
import reactor.netty.Connection;

/***
 * 处理 网络传输中的字节
 */
@FunctionalInterface
public interface ByteBufProcess {
    public void process(Connection con , ByteBuf byteBuf) throws IllegalAccessException;

}
