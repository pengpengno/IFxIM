package com.ifx.server.spi.reactive;

import com.google.protobuf.Message;
import com.ifx.connect.connection.ConnectionConsumer;
import com.ifx.connect.handler.MessageParser;
import com.ifx.server.service.MessageProcessService;
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
public class ReactiveServerHandler extends ConnectionConsumer {


    public ReactiveServerHandler(){
        super((nettyInbound, nettyOutbound) -> {

            Flux<String> handle = nettyInbound.receive().handle((byteBuf, sink) -> nettyInbound.withConnection(connection -> {
                int i = byteBuf.readableBytes();

                if (i > 0) {
                    try{

                        Message message = MessageParser.byteBuf2Message(byteBuf); //   parse the byteBuf to  Message

                        MessageProcessService.process(connection,message);   // process service via message

                    }catch (Exception exception){

                        sink.next(" error ");

                    }

                }
            }));

            return nettyOutbound.sendString(handle);
        });
        log.debug("The ReactorConnectionConsumer SPI FxReactiveClientHandler service provider is load ! ");
    }



    @Override
    public void accept(Connection c) {
        super.accept(c);
    }
}
