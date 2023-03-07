package com.ifx.connect.connection.server.tcp;

import com.google.protobuf.Message;
import com.ifx.connect.connection.server.ReactiveServerAction;
import com.ifx.connect.connection.server.ServerToolkit;
import com.ifx.connect.connection.server.context.IConnectContextAction;
import com.ifx.connect.connection.server.context.IConnection;
import com.ifx.connect.enums.ConnectionStatus;
import com.ifx.connect.proto.Chat;
import com.ifx.exec.ex.connect.ConnectException;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import reactor.core.publisher.Mono;
import reactor.netty.Connection;
import reactor.netty.NettyOutbound;

/**
 * @author pengpeng
 * @description
 * @date 2023/3/7
 */
public class ReactorTcpAction implements ReactiveServerAction {

    private final IConnectContextAction contextAction = ServerToolkit.contextAction();

    @Override
    public Mono<String> sendString(IConnection connection,String message) throws ConnectException {
        Connection clientConnection = connection.connection();
        if (ConnectionStatus.statusActive(connection.status())){
//            clientConnection.outbound().send()
        }
        throw  new ConnectException("connection is ont active ");
    }




    @Override
    public Mono<String> sendString(String account,String message) {
        IConnection iConnection = contextAction.applyConnection(account);
        return null;
    }


    public Mono<Message> sendProtoMessage(String account,Message message){
        IConnection iConnection = contextAction.applyConnection(account);

        Chat.ChatMessage build = Chat.ChatMessage.newBuilder()
                .setFromAccount(account)
                .build();
        ByteBuf buffer = ByteBufAllocator.DEFAULT.buffer();
        ByteBuf byteBuf = buffer.writeBytes(build.toByteArray());
        NettyOutbound send = iConnection.connection().outbound().send(Mono.just(buffer));
        return Mono.just(Chat.ChatMessage.newBuilder().build());
    }


    public NettyOutbound sendProtoMsg(String account,Message message){
        IConnection iConnection = contextAction.applyConnection(account);

        Chat.ChatMessage build = Chat
                .ChatMessage
                .newBuilder()
                .setFromAccount(account)
                .build();
        ByteBuf buffer = ByteBufAllocator.DEFAULT.buffer();

        ByteBuf byteBuf = buffer.writeBytes(build.toByteArray());
        return iConnection.connection().outbound().send(Mono.just(buffer));
    }







}
