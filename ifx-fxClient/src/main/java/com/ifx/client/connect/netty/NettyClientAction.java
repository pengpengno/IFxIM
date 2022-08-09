package com.ifx.client.connect.netty;

import cn.hutool.core.exceptions.ExceptionUtil;
import cn.hutool.core.util.IdUtil;
import com.alibaba.fastjson.JSON;
import com.ifx.connect.netty.client.ClientAction;
import com.ifx.connect.proto.Protocol;
import com.ifx.connect.task.Task;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Component("netty")
@Slf4j
public class NettyClientAction implements ClientAction {


    @Autowired
    private NettyClient nettyClient;


    private final ConcurrentHashMap<String,Task> nettyMsgMap = new ConcurrentHashMap<>();

    @Override
    public ChannelFuture sent(String msg) {
        return  nettyClient.write(msg);
    }

    @Override
    public void connect() {
        try{
//            NettyClient bean = SpringUtil.getBean(NettyClient.class);  // 启动netty
            nettyClient.doOpen();
            log.error("netty connect succ ！ host :  {}   posrt : {} ",nettyClient.getAddress().getHostName(), nettyClient.getAddress().getPort());
        }
        catch (Throwable e ){
           log.error("服务器链接失败！ {}",ExceptionUtil.stacktraceToString(e));
        }//启动Spring容器
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
    public Protocol doBioReq(Protocol protocol) {
        if (protocol == null){
            return null;
        }
//        if ()
        ChannelFuture write = nettyClient.write(JSON.toJSONString(protocol));
        write.addListener(future -> {
            if (future.isSuccess())
            log.info("client send succ ");
        });
        return null;
    }

    @Override
    public void init()  {
       connect();
    }

    @Override
    public void retry() {

    }

    @Override
    public void resetConnect() {

    }

    @Override
    public Protocol sendJsonMsg(Protocol protocol, Task task) {
        nettyMsgMap.put(Optional.ofNullable(protocol.getTaskCode()).orElse(IdUtil.fastSimpleUUID()), task);
        sendJsonMsg(protocol);
        return null;
    }

    @Override
    public Task getTask(Protocol protocol) {
        return getTask(protocol.getTaskCode());
    }

    public Task getTask(String protocolCode){
        return nettyMsgMap.computeIfAbsent(protocolCode,(key)-> (Task) protocol -> {
            log.info( "空Task操作 不存在taskcode {}", protocol.getTaskCode() );
        });
    }

    @Override
    public Protocol sendJsonMsg(Protocol protocol) {
        if (protocol == null){
            return null;
        }
        if (!nettyClient.getChannel().isActive()) {
            log.error("netty Channel is close");
            return null;
        }
        String taskCode = IdUtil.fastSimpleUUID();
        if (protocol.getTaskCode() == null )
            protocol.setTaskCode(taskCode);
        ChannelFuture write = nettyClient.write(JSON.toJSONString(protocol));
        write.addListener(future -> {
            if (future.isSuccess())
                log.info("client send success ");
        });
        return null;
    }

    @Override
    public void keepAlive() {

    }

    @Override
    public void releaseChannel() {

    }

    @Override
    public Boolean isActive() {
        Channel channel = nettyClient.getChannel();
        return channel != null && channel.isActive();
    }
}
