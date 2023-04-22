package com.ifx.connect.spi.netty;

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


    private enum SingleInstance{
        INSTANCE;
        private final ByteBufProcessService instance;
        SingleInstance(){
            instance = new ByteBufProcessService();
        }
        private ByteBufProcessService getInstance(){
            return instance;
        }
    }
    public static ByteBufProcessService getInstance(){
        return ByteBufProcessService.SingleInstance.INSTANCE.getInstance();
    }
    private ByteBufProcessService(){}




}
