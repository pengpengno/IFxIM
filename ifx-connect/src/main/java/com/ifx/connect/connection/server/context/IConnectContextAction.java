package com.ifx.connect.connection.server.context;

import com.ifx.exec.ex.connect.ConnectException;

/**
 *
 * connection 容器
 * @author pengpeng
 * @description
 * @date 2023/3/6
 */
public interface IConnectContextAction {



    /***
     * 获取指定 account 对应的connection
     * @param account
     * @return  返回指定账户的 connection
     * @throws  ConnectException 异常抛出指定IErrorMsg {@link com.ifx.exec.errorMsg.connect.ConnectErrorMsg#NOT_FOUND_CONNECTION}
     */
    public IConnection applyConnection(String account) throws ConnectException;



    public IConnection putConnection(IConnection connection);


    /***
     * 关闭指定账户的连接
     * @param account
     * @return
     */
    public Boolean closeAndRmConnection(String account) throws ConnectException;



}
