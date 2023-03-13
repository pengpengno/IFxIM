package com.ifx.connect.connection.client.tcp.reactive;

import lombok.extern.slf4j.Slf4j;
import org.reactivestreams.Publisher;
import reactor.core.publisher.Mono;
import reactor.netty.Connection;
import reactor.netty.NettyInbound;
import reactor.netty.NettyOutbound;

import java.util.function.BiFunction;
import java.util.function.Consumer;

import static reactor.netty.ReactorNetty.format;

/**
 * reactor connection consumer
 * @author pengpeng
 * @description
 * @date 2023/3/13
 */
@Slf4j
public class ReactorConnectionConsumer implements Consumer<Connection> {

    final BiFunction<? super NettyInbound, ? super NettyOutbound, ? extends Publisher<Void>> handler;

    ReactorConnectionConsumer(BiFunction<? super NettyInbound, ? super NettyOutbound, ? extends Publisher<Void>> handler) {
        this.handler = handler;
    }


    @Override
    public void accept(Connection c) {
        if (log.isDebugEnabled()) {
            log.debug(format(c.channel(), "Handler is being applied: {}"), handler);
        }
        Mono.fromDirect(handler.apply((NettyInbound) c, (NettyOutbound) c))
                .subscribe(c.disposeSubscriber());
    }
}
