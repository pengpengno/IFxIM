package com.ifx.connect.spi.netty;

import cn.hutool.core.exceptions.ExceptionUtil;
import com.ifx.connect.connection.ConnectionConsumer;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.netty.Connection;

/**
 * 响应式 服务处理 Handler
 * @author pengpeng
 * @description
 * @date 2023/3/14
 */
@Slf4j
public class ReactiveConnectionConsumer extends ConnectionConsumer {


    public ReactiveConnectionConsumer(){
        super((nettyInbound, nettyOutbound) -> {

            Flux<byte[]> handle = nettyInbound.receive().handle((byteBuf, sink) -> nettyInbound.withConnection(connection -> {

                int i = byteBuf.readableBytes();

                if (i > 0) {
                    try{

                        ByteBufProcessService.getInstance().process(connection,byteBuf);

                    }catch (Exception exception){


                        log.error("reactor netty occur error {} ", ExceptionUtil.stacktraceToString(exception));

                        sink.next("success".getBytes());

                    }

                }
            }));

            return nettyOutbound.sendByteArray(Flux.concat(handle));

        });
        log.debug("The ReactorConnectionConsumer SPI FxReactiveClientHandler service provider has load ! ");
    }



    @Override
    public void accept(Connection c) {
        super.accept(c);
    }
}
