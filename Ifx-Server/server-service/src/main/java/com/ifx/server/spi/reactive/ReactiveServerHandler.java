package com.ifx.server.spi.reactive;

import com.google.protobuf.Message;
import com.ifx.common.base.AccountInfo;
import com.ifx.common.utils.AccountJwtUtil;
import com.ifx.connect.connection.ConnectionConsumer;
import com.ifx.connect.connection.server.ServerToolkit;
import com.ifx.connect.connection.server.context.IConnectContextAction;
import com.ifx.connect.connection.server.context.IConnection;
import com.ifx.connect.connection.server.context.ReactorConnection;
import com.ifx.connect.handler.MessageParser;
import com.ifx.connect.handler.server.ServerInboundHandler;
import com.ifx.connect.proto.Account;
import com.ifx.connect.proto.ProtocolType;
import com.ifx.server.service.MessageProcessService;
import io.netty.channel.Channel;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.netty.Connection;

import java.util.function.Consumer;

/**
 * 响应式 服务处理 Handler
 * @author pengpeng
 * @description
 * @date 2023/3/14
 */
@Slf4j
public class ReactiveServerHandler extends ConnectionConsumer {

    private static final IConnectContextAction contextAction = ServerToolkit.contextAction();

    public ReactiveServerHandler(){
        super((nettyInbound, nettyOutbound) -> {

            Flux<String> handle = nettyInbound.receive().handle((byteBuf, sink) -> {
                nettyInbound.withConnection(connection -> {
                    int i = byteBuf.readableBytes();

                    if (i > 0) {
                        try{

                            Message message = MessageParser.byteBuf2Message(byteBuf); //   parse the byteBuf to  Message

                            MessageProcessService.process(message);   // process service via message

                        }catch (Exception exception){

                            sink.next(" error ");

                        }

                    }
                });

            });

            return nettyOutbound.sendString(handle);
        });
        log.debug("The ReactorConnectionConsumer SPI FxReactiveClientHandler service provider is load ! ");
    }



    public static String auth(ProtocolType.ProtocolMessageEnum messageEnum, byte[] bytes, Connection connection){
        try{
            if (messageEnum == ProtocolType.ProtocolMessageEnum.AUTH){
                Account.Authenticate authenticate = Account.Authenticate.parseFrom(bytes);
                AccountInfo accountInfo = AccountJwtUtil.verifyAndGetClaim(authenticate.getJwt());
                Channel channel = connection.channel();
                channel.attr(ServerInboundHandler.AccAttr).set(accountInfo);
                IConnection build = ReactorConnection.builder()
                        .accountInfo(accountInfo)
                        .channel(channel)
                        .connection(connection)
                        .build();
                contextAction.putConnection(build);
                return "I have receive your auth";

            }else {
                return "I receive  your  message";
            }
        }catch (Exception exception){

            log.info(" occur  the error  ");
            return "occur error ";
        }
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
