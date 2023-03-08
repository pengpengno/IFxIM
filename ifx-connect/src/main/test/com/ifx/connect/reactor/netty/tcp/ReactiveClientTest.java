package com.ifx.connect.reactor.netty.tcp;

import com.ifx.common.base.AccountInfo;
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
    public  static Integer  PORT = 9087;
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

}
