package com.ifx.connect.connection.client;

import com.ifx.exec.errorMsg.NetError;
import com.ifx.exec.ex.net.NetException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.util.retry.Retry;

import java.net.InetSocketAddress;
import java.time.Duration;

/**
 * 客户端生命周期
 */
public interface ClientLifeStyle {



    public Boolean connect (InetSocketAddress address) throws NetException, IllegalAccessException;

    /**
     * 开启channel 通道连接
     *
     * @return
     */
    public Boolean connect() throws NetException;


    /***
     * 重试连接 如果当前连接 处于正常状态则直接返回
     * @return
     */
    public  default Boolean reTryConnect(){
        if (isAlive()){
            return Boolean.TRUE;
        }
        Flux<Boolean> flux =
        Flux.just(connect())
            .retryWhen(
            Retry
            .backoff(3, Duration.ofSeconds(1)).jitter(0.3d)
            .filter(throwable ->  throwable instanceof  NetException)
            .onRetryExhaustedThrow((spec, rs) -> rs.failure())
                    );
        flux.subscribe();
        return Boolean.TRUE;
    }


    /***
     * 使用指定的地址连接服务器 如果当前连接 处于正常状态则直接返回
     * @return
     */
    public  default  Boolean reTryConnect(InetSocketAddress address){
        if (isAlive()){
            return Boolean.TRUE;
        }
        Flux.just(address)
            .doOnNext(this::connect)
            .doOnError(k-> {
                System.out.println("准备重新连接服务器");
                Mono.error(new NetException(NetError.REMOTE_NET_CAN_NOT_CONNECT));
            })
            .doAfterTerminate(()->System.out.println("连接服务器"))
            .retryWhen(
                Retry
                .backoff(3, Duration.ofSeconds(1)).jitter(0.5d)
                .filter(throwable ->  throwable instanceof NetException)
                .onRetryExhaustedThrow((spec, rs) -> new NetException(NetError.REMOTE_NET_CAN_NOT_CONNECT))
                )
            .subscribe();
        return Boolean.TRUE;
    }

    /**
     * 释放 channel 断线资源
     */
    public void releaseChannel();

    /**
     * channel 是否存活
     * @return
     */
    public Boolean isAlive();
}
