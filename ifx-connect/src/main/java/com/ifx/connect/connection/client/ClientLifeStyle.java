package com.ifx.connect.connection.client;

import com.ifx.common.ex.net.NetException;
import reactor.core.Exceptions;
import reactor.core.publisher.Flux;
import reactor.util.retry.Retry;

import java.net.ConnectException;
import java.net.InetSocketAddress;
import java.time.Duration;

/**
 * 客户端生命周期
 */

public interface ClientLifeStyle {



    public void config(InetSocketAddress address) ;
    /***
     * 连接远程节点
     * @param address
     * @return
     * @throws NetException
     */
    public Boolean connect (InetSocketAddress address) throws  NetException;

    /**
     * 开启channel 通道连接
     *
     * @return
     */
    public Boolean connect() throws ConnectException;


    /***
     * 重试连接 如果当前连接 处于正常状态则直接返回
     * @return connect state
     */
    public  default Boolean reTryConnect() throws ConnectException {
        if (isAlive()){
            return Boolean.TRUE;
        }

        Flux<Boolean> flux =
        Flux.just(connect())
            .retryWhen(
            Retry
            .backoff(3, Duration.ofSeconds(1)).jitter(0.3d)
//            .filter(throwable ->  throwable instanceof  NetException)
            .filter(throwable -> throwable instanceof Exceptions.SourceException)
            .onRetryExhaustedThrow((spec, rs) -> new ConnectException("remote server is invalid !")));
        flux.subscribe();
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
