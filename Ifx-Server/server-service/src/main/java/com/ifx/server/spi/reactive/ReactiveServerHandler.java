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
import io.netty.channel.Channel;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.netty.Connection;

import java.util.function.Consumer;

/**
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

                            Message message = MessageParser.byteBuf2Message(byteBuf);

                            connection.outbound().sendString(Mono.just("trying")).then().subscribe();
//                            sink.next(auth(protocolMessageEnum, bytes,connection));

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

    public static void readMessage(){

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
