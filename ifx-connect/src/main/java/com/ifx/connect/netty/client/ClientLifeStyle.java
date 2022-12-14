package com.ifx.connect.netty.client;

public interface ClientLifeStyle {
    public void init ();  // 初始化连接

    /**
     * 开启channel 通道连接
     */
    public void connect();

    /**
     * 重置 channel 通道链接
     */
    public void reConnect();
    /**
     * 重置链接 channel
     */
    public void resetConnect();

    /**
     * 心跳包机制
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
