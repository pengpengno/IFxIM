package com.ifx.server.service;

import com.google.protobuf.Any;
import com.google.protobuf.Message;
import com.ifx.account.vo.AccountVo;
import com.ifx.connect.connection.server.ServerToolkit;
import com.ifx.connect.connection.server.context.IConnectContextAction;
import com.ifx.connect.connection.server.context.IConnection;
import com.ifx.connect.enums.MessageMapEnum;
import com.ifx.connect.proto.Account;
import com.ifx.connect.proto.Chat;

import java.util.HashMap;
import java.util.Map;

/**
 * message process
 * @author pengpeng
 * @description 根据网络传输过来的 Message 处理指定的业务
 * @date 2023/3/16
 */
public class MessageProcessService {


    public static Map<Class<? extends Message> , MessageProcessService.MessageProcess> processMap = new HashMap<>(MessageMapEnum.values().length);


    static {
        processMap.put(Account.AccountInfo.class,accountProcess());
        processMap.put( Chat.ChatMessage.class,chatProcess());
    }



    /***
     * chat message 处理
     * @return
     */
    private static MessageProcessService.MessageProcess chatProcess(){
        return (message) -> {
//            Chat.ChatMessage.newBuilder()
//                    .setChat()
            Chat.ChatMessage chatMessage = (Chat.ChatMessage) message;
            Any chat = chatMessage.getChat();
            IConnectContextAction contextAction = ServerToolkit.contextAction();


        };
    }

    private static MessageProcessService.MessageProcess accountProcess(){
        return (message) -> {
            try{
                Account.AccountInfo accountInfo = (Account.AccountInfo) message;

            }
            catch (ClassCastException s){
                throw new IllegalArgumentException("UnSupport message type,The message is not Account.AccountInfo");
            }

        };
    }

    /***
     * process via the provided args message;
     * @param message
     */
    public static void process (Message message){
        if (message == null){
            return;
        }
        processMap.get(message.getClass()).process(message);
    }




    /***
     * Message Process
     */
    @FunctionalInterface
    public interface MessageProcess{

        public void process( Message message) ;

        public default void authConnectionAttr(IConnection connection){


        }

    }

    public static void main(String[] args) {
        Object accountInfo = new AccountVo();
        Account.AccountInfo accountInfo2 = (Account.AccountInfo) accountInfo;
    }



}
