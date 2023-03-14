package com.ifx.connect.handler.proto;

import com.google.protobuf.Message;
import com.ifx.connect.connection.server.ServerToolkit;
import com.ifx.connect.connection.server.context.IConnectContextAction;
import com.ifx.connect.connection.server.context.IConnection;
import com.ifx.connect.proto.Account;

/**
 * @author pengpeng
 * @description
 * @date 2023/3/14
 */
public class AccountInfoHandler {

    private IConnectContextAction contextAction = ServerToolkit.contextAction();
    public static void process(IConnection connection, Message message){
        if (message.getClass() == Account.AccountInfo.class){
            Account.AccountInfo accountInfo = (Account.AccountInfo) message;
            if (null == connection){

            }
//            contextAction.putConnection()

        }

    }
}
