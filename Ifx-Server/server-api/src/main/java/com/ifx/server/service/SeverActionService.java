package com.ifx.server.service;

/**
 * 服务器操作
 */
public interface SeverActionService {
    /**
     * 长链接激活用户状态
     * @param account
     * @param requestId
     */
    public void AccountChannelActive(String account,String requestId);

    /**
     * 强制离线
     * 用户channel 释放
     * @param account
     */
    public void AccountChannelRelease(String account);
}
