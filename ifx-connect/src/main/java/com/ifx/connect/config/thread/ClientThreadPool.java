package com.ifx.connect.config.thread;

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
public class ClientThreadPool {

    private static ExecutorService executorService;

    private enum SingleInstance{
        INSTANCE;
        private final ClientThreadPool instance;

        SingleInstance(){
            instance = new ClientThreadPool();

            executorService = new ThreadPoolExecutor(100, 300, 1,TimeUnit.SECONDS, new LinkedBlockingQueue<>(1024),
                    new ThreadFactoryBuilder().setNameFormat("socket-%d").build(), new ThreadPoolExecutor.CallerRunsPolicy());
        }
        private ClientThreadPool getInstance(){
            return instance;
        }
    }
    public static ClientThreadPool getInstance(){
        return SingleInstance.INSTANCE.getInstance();
    }

    public  ExecutorService threadPool(){
        return executorService;
    }
}
