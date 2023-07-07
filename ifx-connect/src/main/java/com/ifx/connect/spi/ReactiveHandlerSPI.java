package com.ifx.connect.spi;

import com.ifx.connect.connection.ConnectionConsumer;
import lombok.extern.slf4j.Slf4j;

import java.util.Iterator;
import java.util.ServiceLoader;

/**
 * 执行 handler spi 注入
 * @author pengpeng
 * @description
 * @date 2023/3/13
 */
@Slf4j
public class ReactiveHandlerSPI {

    /**
     * 注入 reactor 执行 handler spi
     * @return connection 处理 consumer
     */
    public static ConnectionConsumer wiredSpiHandler()  {
        ServiceLoader<ConnectionConsumer> load = ServiceLoader.load(ConnectionConsumer.class);
        Iterator<ConnectionConsumer> iterator = load.iterator();
        if (iterator.hasNext()){
            ConnectionConsumer next = iterator.next();
            log.info("ReactiveHandlerSPI wired class = {}", next.getClass().getName());
            return next;
        }
        log.info("ReactiveHandlerSPI could not found handlerSPI , it will use  default handler!");

        return new ConnectionConsumer.DefaultConnectionConsumer();
    }





}
