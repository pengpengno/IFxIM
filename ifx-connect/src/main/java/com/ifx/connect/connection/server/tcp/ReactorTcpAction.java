package com.ifx.connect.connection.server.tcp;

import com.google.protobuf.Message;
import com.ifx.connect.connection.server.ReactiveServerAction;
import com.ifx.connect.connection.server.ServerToolkit;
import com.ifx.connect.connection.server.context.IConnectContextAction;
import com.ifx.connect.connection.server.context.IConnection;
import com.ifx.connect.enums.ConnectionStatus;
import com.ifx.connect.utils.ProtocolBufUtils;
import com.ifx.common.ex.connect.ConnectException;
import com.ifx.common.ex.net.NetException;
import io.netty.buffer.ByteBuf;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;
import reactor.netty.Connection;
import reactor.netty.NettyOutbound;

import java.util.Optional;

/**
 * Reactor Tcp Action
 * send message to  channel
 * @author pengpeng
 * @description
 * @date 2023/3/7
 */
@Slf4j
public class ReactorTcpAction implements ReactiveServerAction {

    private final IConnectContextAction contextAction ;


    private ReactorTcpAction(){
        contextAction = ServerToolkit.contextAction();
    }

    private enum SingleInstance{
        INSTANCE;
        private final ReactorTcpAction instance;
        SingleInstance(){
            instance = new ReactorTcpAction();
        }
        private ReactorTcpAction getInstance(){
            return instance;
        }
    }


    public static ReactorTcpAction getInstance(){
        return ReactorTcpAction.SingleInstance.INSTANCE.getInstance();
    }


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

        IConnection iConnection = contextAction.applyConnection(account);

        if (iConnection !=null ){

            Boolean online = iConnection.online();
            if (message == null ){
                log.warn("Message is Non-accessible Object ,return  empty !");
                return Mono.empty();
            }
            if (online){

                ByteBuf byteBuf = iConnection.channel().alloc().ioBuffer();

                ProtocolBufUtils.protoTrans2ByteBuf(byteBuf,message);

                return iConnection.connection().outbound().send(Mono.justOrEmpty(Optional.of(byteBuf))).then();
            }
        }


        throw new NetException("The specified account ["+account+"] connection is invalid ; ");

    }

    public Mono<Message> sendProtoMessage(String account, Message message){

        return null;
    }


    public NettyOutbound sendProtoMsg(String account,Message message){
        return null;
    }







}
