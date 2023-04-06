package com.ifx.server.service;

import cn.hutool.core.collection.CollectionUtil;
import com.google.protobuf.Message;
import com.ifx.connect.handler.MessageParser;
import io.netty.buffer.ByteBuf;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import reactor.netty.Connection;

import java.util.Map;
import java.util.stream.Collectors;

/**
 * message process
 * @author pengpeng
 * @description 根据网络传输过来的 Message 处理指定的业务
 * @date 2023/3/16
 */
@Slf4j
public class ByteBufProcessService implements ApplicationContextAware ,ByteBufProcess {

    public Map<Class<? extends Message> , ProtoBufProcess> processMap;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {

        Map<String, ProtoBufProcess> protoBufProcessMap = applicationContext.getBeansOfType(ProtoBufProcess.class);
        if (CollectionUtil.isNotEmpty(protoBufProcessMap)){
            processMap =  protoBufProcessMap
                        .values()
                        .stream()
                        .filter(e->e.type()!=null)
                        .collect(Collectors.toMap(e -> e.type().getMessageClass(), e ->e, (e1, e2) -> e1));
        }

    }





    @Override
    public void process(Connection con, ByteBuf byteBuf) throws IllegalAccessException {

        Message message = MessageParser.byteBuf2Message(byteBuf); //   parse the byteBuf to  Message

        if (message != null){

            processMap.get(message.getClass()).process(con,message); // process service via message
            log.debug(" Program Handle the  message ");
        }
    }


//
//    /***
//     * chat message 处理
//     * @return
//     */
//    private static ProtoBufProcess chatProcess(){
//        return (con , message) -> {
////            Chat.ChatMessage.newBuilder()
////                    .setChat()
//            Chat.ChatMessage chatMessage = (Chat.ChatMessage) message;
//            Any chat = chatMessage.getChat();
//            IConnectContextAction contextAction = ServerToolkit.contextAction();
//
//        };
//    }
//
//    /**
//     * Account.AccountInfo process
//     *
//     * @return
//     * @throws IllegalArgumentException when the specified args is valid or null
//     * @throws ClassCastException  the  message could not  cast to Account.AccountInfo
//     */
//    private static ProtoBufProcess accountProcess() throws IllegalArgumentException,ClassCastException{
//        return (con, message) -> {
//            if (message == null ||  con == null ){
//                throw  new IllegalArgumentException("The connection and message is invalid!");
//            }
//            Account.AccountInfo accountInfo = (Account.AccountInfo) message;
//
//            con.channel().attr(ConnectionConstants.BING_ACCOUNT_KEY).set(accountInfo);
//
//            ReactorConnection connection = ReactorConnection.builder()
//                    .channel(con.channel())
//                    .connection(con)
//                    .accountInfo(accountInfo)
//                    .build();
//
//            ServerToolkit.contextAction().putConnection(connection);
//
//
//            log.info(" {}  had bind  the channel !",accountInfo.getAccount());
//        };
//
//    }
//    /***
//     * process via the provided args message;
//     * @param message 消息
//     */
//    public static void process (Connection connection,Message message){
//
//        if (message == null){
//            return;
//        }
//
//        ProtoBufProcess messageProcess = processMap.get(message.getClass());
//        if (messageProcess !=null){
//            messageProcess.process(connection, message);
//        }
//    }



    private enum SingleInstance{
        INSTANCE;
        private final com.ifx.server.service.ByteBufProcessService instance;
        SingleInstance(){
            instance = new com.ifx.server.service.ByteBufProcessService();
        }
        private com.ifx.server.service.ByteBufProcessService getInstance(){
            return instance;
        }
    }
    public static com.ifx.server.service.ByteBufProcessService getInstance(){
        return com.ifx.server.service.ByteBufProcessService.SingleInstance.INSTANCE.getInstance();
    }
    private ByteBufProcessService(){}




}
