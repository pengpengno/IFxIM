package com.ifx.connect.reactor.netty.tcp;

import com.ifx.common.base.AccountInfo;
import com.ifx.connect.handler.MessageParser;
import com.ifx.connect.handler.client.ClientInboundHandler;
import com.ifx.connect.proto.Account;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.handler.logging.LogLevel;
import io.netty.util.AttributeKey;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Mono;
import reactor.netty.Connection;
import reactor.netty.tcp.TcpClient;

/**
 * @author pengpeng
 * @description
 * @date 2023/3/7
 */
@Slf4j
public class ReactiveClientTest {
    public  static String  HOST = "127.0.0.1";
    public  static Integer  PORT = 8094;
    public static String TCPLogger = "TCPLogger";
    private final AttributeKey<AccountInfo> attribute = AttributeKey.valueOf(AccountInfo.class.getName());


    @Test
    public void testClient(){
        AccountInfo pengpeng = AccountInfo.builder().account("pengpeng")
                .build();
        Connection connect = TcpClient.create()
                .host(HOST)
                .port(8094)
                .wiretap("client",LogLevel.INFO)
                .doOnChannelInit((connectionObserver, channel, remoteAddress) -> {
                    channel.attr(attribute).set(pengpeng);
                })
                .handle((nettyInbound, nettyOutbound) -> Mono.never())
                .connectNow();
        log.info("{}", connect.isDisposed());
        connect.inbound().receive().asString().doOnNext(log::info).then().subscribe();
        connect.outbound().sendString(Mono.just("nice to meet you")).then().subscribe();
        connect.onDispose().block();
    }

    @Test
    public void createJwtClient(){
        String jwt = "eyJhbGciOiJIUzI1NiJ9.eyJpYXQiOjE2NzgyNzM1ODcsImV4cCI6MTY3ODM1OTk4NywiQUNDT1VOVCI6eyJ1c2VySWQiOm51bGwsImFjY291bnQiOiJ3YW5ncGVuZyIsInVzZXJOYW1lIjpudWxsLCJlbWFpbCI6bnVsbH19.KjmiH4PXvzKmMOFMtpwWQjHdm8bpr8-c4_-oHxzH1vA";
        Connection connect = TcpClient.create()
                .host(HOST)
                .port(8094)
                .wiretap("client",LogLevel.INFO)
                .doOnChannelInit((connectionObserver, channel, remoteAddress) -> {
                    channel.pipeline().addLast(new ClientInboundHandler(jwt));
                })
                .connectNow().bind();
        log.info("{}", connect.isDisposed());
        connect.outbound().sendString(Mono.just("nice to meet you")).then().subscribe();
        connect.outbound().sendString(Mono.just("send  twice ")).then().subscribe();
        connect.onDispose()
                .block()
        ;
    }

    @Test
    public void sentMessage(){
        Connection connect = TcpClient.create()
                .host(HOST)
                .port(PORT)
                .wiretap("client",LogLevel.INFO)
//                .doOnChannelInit((connectionObserver, channel, remoteAddress) -> {
//                })
                .connectNow().bind();
        log.info("{}", connect.isDisposed());
        Account.AccountInfo build = Account.AccountInfo.newBuilder()
                .setAccount("pengpeng")
                .setAccountName("王鹏")
                .setEMail("pengpeng_on@163.com")
                .build();
        ByteBuf buffer = PooledByteBufAllocator.DEFAULT.buffer();
        ByteBuf byteBuf = MessageParser.message2ByteBuf(build, buffer);
        connect.outbound().send(Mono.just(byteBuf)).then().subscribe();
        connect.inbound().receive().asString().then().subscribe();
//        connect.outbound().sendString(Mono.just("nice to meet you")).then().subscribe();
//        connect.outbound().sendString(Mono.just("send  twice ")).then().subscribe();
        connect.onDispose()
                .block()
        ;
    }



}
