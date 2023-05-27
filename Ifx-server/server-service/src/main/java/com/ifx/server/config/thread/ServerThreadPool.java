package com.ifx.server.config.thread;

import com.google.common.util.concurrent.ThreadFactoryBuilder;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 客户端线程池
 * @author pengpeng
 * @description
 * @date 2023/2/3
 */
public class ServerThreadPool {


    private enum SingleInstance{
        INSTANCE;
        private final ServerThreadPool instance;
        SingleInstance(){
            instance = new ServerThreadPool();
        }
        private ServerThreadPool getInstance(){
            return instance;
        }
    }
    public static ServerThreadPool getInstance(){
        return SingleInstance.INSTANCE.getInstance();
    }

    public  ExecutorService threadPool(){
        return new ThreadPoolExecutor(100, 300, 1,TimeUnit.SECONDS, new LinkedBlockingQueue<>(1024),
                new ThreadFactoryBuilder().setNameFormat("server-%d").build(), new ThreadPoolExecutor.CallerRunsPolicy());
    }





}
