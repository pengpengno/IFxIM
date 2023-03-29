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




    public static ConnectionConsumer wiredSpiHandler()  {
        ServiceLoader<ConnectionConsumer> load = ServiceLoader.load(ConnectionConsumer.class);
        Iterator<ConnectionConsumer> iterator = load.iterator();
        if (iterator.hasNext()){
            return iterator.next();
        }
        return new ConnectionConsumer.DefaultConnectionConsumer();
    }





}
