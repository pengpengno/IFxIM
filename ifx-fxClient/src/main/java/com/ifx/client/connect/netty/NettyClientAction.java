package com.ifx.client.connect.netty;

import cn.hutool.core.exceptions.ExceptionUtil;
import com.alibaba.fastjson.JSON;
import com.ifx.connect.netty.client.ClientAction;
import com.ifx.connect.netty.client.ClientLifeStyle;
import com.ifx.connect.proto.Protocol;
import com.ifx.connect.task.TaskHandler;
import com.ifx.client.task.TaskManager;
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

//    private final ConcurrentHashMap<String,Task> nettyMsgMap = new ConcurrentHashMap<>();



    @Override
    public void connect() {
        try{
            nettyClient.doOpen();
            log.info("netty connect succ ！ host :  {}   posrt : {} ",nettyClient.getAddress().getHostName(), nettyClient.getAddress().getPort());
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
    public void receive(Protocol protocol) {

    }



    @Override
    public void sendJsonMsg(Protocol protocol) {
        if (protocol == null){
            return ;
        }
        if (!nettyClient.getChannel().isActive()) {
            log.error("netty Channel is close");
            return ;
        }
//        String taskCode = IdUtil.fastSimpleUUID();
//        if (protocol.getTaskCode() == null )
//            protocol.setTaskCode(taskCode);
        if (nettyClient.getChannel() == null){
            log.error("channel is close ,please start server ");
            return ;
        }
        ChannelFuture write = nettyClient.write(JSON.toJSONString(protocol));
        write.addListener(future -> {
            if (future.isSuccess())
                log.info("client send success ");
        });
        return ;
    }

    @Override
    public void keepAlive() {

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
