package com.ifx.connect.spi;

import com.ifx.connect.proto.Protocol;

/**
 * 客户端业务处理拓展点
 * @author pengpeng
 * @description
 * @date 2023/2/3
 */
public interface ClientBusinessSPI {

    public Runnable doBusiness();

    /***
     * 业务处理
     * @param protocol 协议包裹
     * @return
     */
    public Runnable doBusiness(Protocol protocol);


}
