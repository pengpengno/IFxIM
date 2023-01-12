package com.ifx.connect.connection.client;

import com.ifx.exec.ex.net.NetException;
import reactor.core.publisher.Flux;
import reactor.util.retry.Retry;

import java.time.Duration;
import java.time.LocalTime;
import java.util.concurrent.atomic.AtomicInteger;

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
    public  default  Boolean reTryConnect(){
        AtomicInteger errorCount = new AtomicInteger();
        Flux<String> flux =
                Flux.<String>error(new IllegalStateException("boom"))
                        .doOnError(e -> {
                            errorCount.incrementAndGet();
                            System.out.println(e + " at " + LocalTime.now());
                        })
                        .retryWhen(Retry
                                .backoff(3, Duration.ofMillis(100)).jitter(0d)
                                .doAfterRetry(rs -> System.out.println("retried at " + LocalTime.now() + ", attempt " + rs.totalRetries()))
                                .onRetryExhaustedThrow((spec, rs) -> rs.failure())
                        );
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
