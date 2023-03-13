package com.ifx.connect.connection.client.tcp.reactive;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;
import reactor.netty.Connection;

import java.util.Iterator;
import java.util.ServiceLoader;

/**
 * 执行 handler spi 注入
 * @author pengpeng
 * @description
 * @date 2023/3/13
 */
@Slf4j
public class ReactiveClientHandlerSPI {


    public static ReactorConnectionConsumer wiredSpiHandler()  {
        ServiceLoader<ReactorConnectionConsumer> load = ServiceLoader.load(ReactorConnectionConsumer.class);
        Iterator<ReactorConnectionConsumer> iterator = load.iterator();
        if (iterator.hasNext()){
            return iterator.next();
        }
        return new DefaultReactorConnectionConsumer();
//        throw new IllegalAccessException("The Client Connection Handler could not found!");
    }

    static final class DefaultReactorConnectionConsumer extends ReactorConnectionConsumer{



        DefaultReactorConnectionConsumer(){
            super((nettyInbound, nettyOutbound) -> Mono.never());
        }

        @Override
        public void accept(Connection c) {
            super.accept(c);
        }
    }

}
