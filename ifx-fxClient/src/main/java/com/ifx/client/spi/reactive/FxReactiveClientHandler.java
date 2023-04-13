package com.ifx.client.spi.reactive;

import cn.hutool.core.exceptions.ExceptionUtil;
import com.ifx.connect.connection.ConnectionConsumer;
import com.ifx.connect.process.ByteBufProcessService;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
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

            Flux<byte[]> handle = nettyInbound.receive().handle((byteBuf, sink) -> nettyInbound.withConnection(connection -> {

                int i = byteBuf.readableBytes();

                if (i > 0) {
                    try{

                        ByteBufProcessService.getInstance().process(connection,byteBuf);

                    }catch (Exception exception){

                        log.error("occur error {} ", ExceptionUtil.stacktraceToString(exception));
                        sink.next("success".getBytes());

                    }

                }
            }));

            nettyInbound.receive().asString().doOnNext(log::info).then().subscribe();

//            return Mono.never();
            return nettyOutbound.sendByteArray(Flux.concat(handle)).neverComplete();

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
