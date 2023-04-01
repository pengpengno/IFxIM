package com.ifx.connect.connection.server.tcp;

import com.google.protobuf.Message;
import com.ifx.connect.connection.server.ReactiveServerAction;
import com.ifx.connect.connection.server.ServerToolkit;
import com.ifx.connect.connection.server.context.IConnectContextAction;
import com.ifx.connect.connection.server.context.IConnection;
import com.ifx.connect.enums.ConnectionStatus;
import com.ifx.exec.ex.connect.ConnectException;
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
    public Mono<Void> sendString(IConnection connection,String message) throws ConnectException {
        Connection clientConnection = connection.connection();
        if (ConnectionStatus.statusActive(connection.status())){
            return clientConnection.outbound().sendString(Mono.just("message")).then();
        }
        throw  new ConnectException("connection is ont active ");
    }



    @Override
    public Mono<Void> sendString(String account,String message) {
        IConnection iConnection = contextAction.applyConnection(account);
        return null;
    }

    @Override
    public Mono<Void> sendMessage(String account, Message message) {
        return null;
    }

    public Mono<Message> sendProtoMessage(String account, Message message){
//        IConnection iConnection = contextAction.applyConnection(account);
//
//        Chat.ChatMessage build = Chat.ChatMessage.newBuilder()
//                .setFromAccountInfo(account)
//                .build();
//        ByteBuf buffer = ByteBufAllocator.DEFAULT.buffer();
//        ByteBuf byteBuf = buffer.writeBytes(build.toByteArray());
//        NettyOutbound send = iConnection.connection().outbound().send(Mono.just(buffer));
//        return Mono.just(Chat.ChatMessage.newBuilder().build());
        return null;
    }


    public NettyOutbound sendProtoMsg(String account,Message message){
//        IConnection iConnection = contextAction.applyConnection(account);
//        MessageMapEnum messageMapEnum = MessageMapEnum.getByMessageClass(message.getClass());
//        ProtocolType.ProtocolMessageEnum typeEnum = messageMapEnum.getTypeEnum();
//        Chat.ChatMessage build = Chat
//                .ChatMessage
//                .newBuilder()
//                .setFromAccount(account)
//                .build();
//        ByteBuf buffer = ByteBufAllocator.DEFAULT.buffer();
//
//        ByteBuf byteBuf = buffer.writeBytes(build.toByteArray());
//        return iConnection.connection().outbound().send(Mono.just(buffer));

        return null;
    }







}
