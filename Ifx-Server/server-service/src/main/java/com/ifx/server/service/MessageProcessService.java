package com.ifx.server.service;

import com.google.protobuf.Any;
import com.google.protobuf.Message;
import com.ifx.connect.connection.ConnectionConstants;
import com.ifx.connect.connection.server.ServerToolkit;
import com.ifx.connect.connection.server.context.IConnectContextAction;
import com.ifx.connect.connection.server.context.ReactorConnection;
import com.ifx.connect.enums.MessageMapEnum;
import com.ifx.connect.proto.Account;
import com.ifx.connect.proto.Chat;
import lombok.extern.slf4j.Slf4j;
import reactor.netty.Connection;

import java.util.HashMap;
import java.util.Map;

/**
 * message process
 * @author pengpeng
 * @description 根据网络传输过来的 Message 处理指定的业务
 * @date 2023/3/16
 */
@Slf4j
public class MessageProcessService {


    public static Map<Class<? extends Message> , MessageProcessService.ConnectMessageProcess> processMap = new HashMap<>(MessageMapEnum.values().length);

    static {

        processMap.put(Account.AccountInfo.class,accountProcess());

        processMap.put(Chat.ChatMessage.class,chatProcess());

    }



    /***
     * chat message 处理
     * @return
     */
    private static MessageProcessService.ConnectMessageProcess chatProcess(){
        return (con , message) -> {
//            Chat.ChatMessage.newBuilder()
//                    .setChat()
            Chat.ChatMessage chatMessage = (Chat.ChatMessage) message;
            Any chat = chatMessage.getChat();
            IConnectContextAction contextAction = ServerToolkit.contextAction();


        };
    }

    /**
     * Account.AccountInfo process
     *
     * @return
     * @throws IllegalArgumentException when the specified args is valid or null
     * @throws ClassCastException  the  message could not  cast to Account.AccountInfo
     */
    private static MessageProcessService.ConnectMessageProcess accountProcess() throws IllegalArgumentException,ClassCastException{
        return (con, message) -> {
            if (message == null ||  con == null ){
                throw  new IllegalArgumentException("The connection and message is invalid!");
            }
            Account.AccountInfo accountInfo = (Account.AccountInfo) message;

            con.channel().attr(ConnectionConstants.BING_ACCOUNT_KEY).set(accountInfo);

            ReactorConnection connection = ReactorConnection.builder()
                    .channel(con.channel())
                    .connection(con)
                    .accountInfo(accountInfo)
                    .build();

            ServerToolkit.contextAction().putConnection(connection);


            log.info(" {}  had bind  the channel !",accountInfo.getAccount());
        };
    }

    /***
     * process via the provided args message;
     * @param message 消息
     */
    public static void process (Connection connection,Message message){

        if (message == null){
            return;
        }

        ConnectMessageProcess messageProcess = processMap.get(message.getClass());
        if (messageProcess !=null){
            messageProcess.process(connection, message);
        }
    }




    /***
     * Message Process
     */
    @FunctionalInterface
    public interface MessageProcess{

        public void process( Message message) ;


    }

    /***
     * connect  message process
     */
    @FunctionalInterface
    public interface ConnectMessageProcess{

        public void process(Connection con , Message message);

    }




}
