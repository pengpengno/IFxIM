package com.ifx.server.service;

import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * 服务器 to 客户端 系统级操作
 */
public interface ServerActionService {
    /**
     * 长链接激活用户状态
     * @param account 用户账户标识
     * @param requestId
     */
    public void accountChannelActive(String account, String requestId);

    /**
     * 释放用户 channel 资源
     * 用户channel 释放
     * @param account
     */
    public Boolean accountChannelRelease(String account);

    /**
     * 获取客户端状态
     * <p> 返回结果为 {@link com.ifx.server.enums.ClientStateEnum} 的 name属性 </p>
     * 例如 用户在线 则如下返回数据
     *  <pre>{@code
     *     String clientState = ClientStateEnum.ONLINE.name();
     *  }</pre>
     *
     * 懒得写 code desc   麻烦死
     * @param account 账户标识
     * @return 返回枚举的name
     */
    public String clientState(String account);

    /**
     * 客户端连接状态
     * @param account
     * @return
     */
    public Boolean clientAliveState(String account);

    public Map<String,Boolean> clientBatchAliveState(Collection<String> accounts);
    public String clientState();



}
