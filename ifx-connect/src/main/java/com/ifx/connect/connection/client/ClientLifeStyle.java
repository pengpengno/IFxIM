package com.ifx.connect.connection.client;

import com.ifx.exec.ex.net.NetException;
import reactor.core.publisher.Flux;
import reactor.util.retry.Retry;

import java.time.Duration;

/**
 * 客户端生命周期
 */
public interface ClientLifeStyle {
    public void init ();  // 初始化连接

    /**
     * 开启channel 通道连接
     *
     * @return
     */
    public Boolean connect() throws NetException;

    /**
     * 重置 channel 通道链接
     */
    public void reConnect();

    /***
     * 重试连接 如果当前连接 处于正常状态则直接返回
     * @return
     */
    public  default  Boolean reTryConnect(){
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
    /**
     * 重置链接 channel
     */
    public void resetConnect();

    /**
     * 保持链接存活 心跳包机制
     */
    public void keepAlive();

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
