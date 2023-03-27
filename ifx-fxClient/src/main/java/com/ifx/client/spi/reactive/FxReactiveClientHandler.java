package com.ifx.client.spi.reactive;

import com.ifx.connect.connection.ConnectionConsumer;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;
import reactor.netty.Connection;

import java.util.function.Consumer;

/**
 * @author pengpeng
 * @description
 * @date 2023/3/14
 */
@Slf4j
public class FxReactiveClientHandler extends ConnectionConsumer {

    public FxReactiveClientHandler(){
        super((nettyInbound, nettyOutbound) -> {

            nettyInbound.withConnection(connection -> {
                ByteBufAllocator alloc = connection.channel().alloc();
                ByteBuf buffer = alloc.buffer();
            });
            nettyInbound.receive().asString().doOnNext(log::info).then().subscribe();
            return Mono.never();
        });
        log.debug("The ReactorConnectionConsumer SPI FxReactiveClientHandler service provider is load ! ");
    }

    @Override
    public Consumer<Connection> andThen(Consumer<? super Connection> after) {
        return super.andThen(after);
    }

    @Override
    public void accept(Connection c) {
        super.accept(c);
    }
}
