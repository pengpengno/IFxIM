package com.ifx.connect.connection.server.tcp;

import com.google.inject.Singleton;
import com.ifx.common.base.AccountInfo;
import com.ifx.common.utils.AccountJwtUtil;
import com.ifx.connect.connection.server.ReactiveServer;
import com.ifx.connect.connection.server.ServerToolkit;
import com.ifx.connect.connection.server.context.IConnectContextAction;
import com.ifx.connect.connection.server.context.IConnection;
import com.ifx.connect.connection.server.context.ReactorConnection;
import com.ifx.connect.handler.server.ServerInboundHandler;
import com.ifx.connect.proto.Account;
import com.ifx.connect.proto.ProtocolType;
import io.netty.channel.Channel;
import io.netty.handler.logging.LogLevel;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.netty.Connection;
import reactor.netty.DisposableServer;
import reactor.netty.tcp.TcpServer;

import java.net.InetSocketAddress;

/**
 * 响应式 tcp 链接
 * @author pengpeng
 * @description
 * @date 2023/3/3
 */
@Slf4j
@Singleton
public class ReactorTcpServer implements ReactiveServer {


    private InetSocketAddress address;
    private enum SingleInstance{
        INSTANCE;
        private final ReactiveServer instance;
        SingleInstance(){
            instance = new ReactorTcpServer();
        }
        private ReactiveServer getInstance(){
            return instance;
        }
    }
    public static ReactiveServer getInstance(){
        return ReactorTcpServer.SingleInstance.INSTANCE.getInstance();
    }


    private IConnectContextAction contextAction ;
    private TcpServer server;
    private DisposableServer disposableServer;

    private ReactorTcpServer(){
        contextAction = ServerToolkit.contextAction();
    }

    @Override
    public void start(InetSocketAddress address) {
        create(address);
        start();
    }

    @Override
    public void stop() {
        disposableServer.disposeNow();
    }

//    public void

    public  void create(InetSocketAddress address){
        this.address = address;
        server = TcpServer
                .create()
                .wiretap("tcp-server", LogLevel.INFO)
                .host(address.getHostName())
                .port(address.getPort())
                .handle((in, out) -> {
                    Flux<String> handle = in.receive().handle((byteBuf, sink) -> {
                        in.withConnection(connection -> {
                            int i = byteBuf.readableBytes();
                            if (i > 0) {

                                int length = byteBuf.readInt();

                                int type = byteBuf.readInt();

                                ProtocolType.ProtocolMessageEnum protocolMessageEnum = ProtocolType.ProtocolMessageEnum.forNumber(type);

                                byte[] bytes;

                                if (byteBuf.hasArray()) {  //  jvm  heap byteBuf 处理

                                    bytes = byteBuf.array();

                                } else {  //  memory  byteBuf 处理

                                    bytes = new byte[length];

                                    byteBuf.getBytes(byteBuf.readerIndex(),bytes);

                                }
                                sink.next(type(protocolMessageEnum, bytes,connection));

                            }
                        });

                    });

                    return out.sendString(handle);
                })
        ;
        log.info("startup netty  on port {}",address.getPort());

    }

    public void start(){
        disposableServer = server.bindNow();
        disposableServer.onDispose().block();
    }



    public  String  type(ProtocolType.ProtocolMessageEnum messageEnum, byte[] bytes, Connection connection){
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
}
