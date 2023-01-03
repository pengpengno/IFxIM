package com.ifx.connect.netty.client;

import cn.hutool.core.exceptions.ExceptionUtil;
import com.ifx.connect.proto.Protocol;
import com.ifx.connect.task.handler.TaskHandler;
import com.ifx.connect.task.TaskManager;
import com.ifx.connect.task.handler.def.DefaultHandler;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.ThreadPoolExecutor;

@Component("netty")
@Slf4j
public class NettyClientAction implements ClientAction, ClientLifeStyle {


    @Autowired
    private NettyClient nettyClient;
    @Autowired
    private TaskManager taskManager;


    private Long connectTimeout;  // 连接时间延

    private Long reConnectDelay; // 重试时延

    @Override
    public void connect() {
        try{
            nettyClient.doOpen();
            log.info("netty connect succ ！ host :  {}  posrt : {} ",nettyClient.getAddress().getHostName(), nettyClient.getAddress().getPort());
        }
        catch (Throwable e ){
           log.error("服务器链接失败！ {}",ExceptionUtil.stacktraceToString(e));
        }
    }

    @Override
    public void reConnect() {
        ((Runnable) () -> {
            reConnectTimeOut(5000L,3);
        }).run();
    }

    public void reConnectBlock() {
        reConnectTimeOut(5000L,3);
    }

    public void reConnectTimeOut(Long timeout,Integer retryTimes){
        try {
            log.error("retrying connect  timeout {}, retryTimes {}  ",timeout,retryTimes);
            Thread.sleep(timeout);
            connect();
            if (nettyClient.getChannel() == null ){
                if( retryTimes == 0){
                    log.info("retry times out  stop connect ");
                    return;
                }
                reConnectTimeOut(timeout,retryTimes-1);
            }
        }catch (Exception e){
            log.error("reconnect fail {}",ExceptionUtil.stacktraceToString(e));
        }
    }


    @Override
    public void init()  {
       connect();
    }



    @Override
    public void resetConnect() {

    }

    @Override
    public void sendJsonMsg(Protocol protocol, TaskHandler taskHandler) {
        sendJsonMsg(protocol);
    }

    public void sendJsonMsg(Protocol protocol, TaskHandler taskHandler, ThreadPoolExecutor executor) {
        sendJsonMsg(protocol);
    }



    @Override
    public void sendJsonMsg(Protocol protocol) {
        if (protocol == null){
            log.warn("protocol is  null  no operate  to do ");
            return ;
        }

        if (!nettyClient.getChannel().isActive()
                || nettyClient.getChannel() == null) {
            log.error("netty Channel is close， loading ");
            reConnect();
            return ;
        }

        log.debug("sending msg to server ");
        ChannelFuture write = nettyClient.write(protocol);
        write.addListener(future -> {
            if (future.isSuccess())
                log.debug("client send success ");
        });
    }

    @Override
    public void keepAlive() {
//        心跳包机制
        sendJsonMsg(new Protocol(), DefaultHandler.HEART_BEAT_HANDLER);
    }

    @Override
    public void releaseChannel() {
        nettyClient.release();
    }

    public Boolean isAlive() {
        Channel channel = nettyClient.getChannel();
        return channel != null && channel.isActive();
    }
}
