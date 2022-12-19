package com.ifx.connect.client;

/**
 * 客户端 基础信息
 */
public interface ClientInfo {
    /**
     * 客户端类型
     * @return
     */
    public String getClientType();

    /**
     * 客户端版本信息
     * @return
     */
    public String getClientVersion();

    /**
     * 获取客户端身份标识
     * @return 客户端身份标识
     */
    default public String getIdentifyId(){
        return ClientConsts.DEFAULT_IDENTIFY;
    }
}
