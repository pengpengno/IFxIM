package com.ifx.client.connect.netty;

import cn.hutool.core.exceptions.ExceptionUtil;
import com.ifx.connect.netty.client.ClientAction;
import com.ifx.connect.proto.Protocol;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("netty")
@Slf4j
public class NettyClientAction implements ClientAction {

//    private NettyClient nettyClient = NettyClient.getInstance();
//    private static  NettyClientAction instance = null;
//    public  static NettyClientAction getInstance(){
//        if (instance ==null){
//            instance = new NettyClientAction();
//        }
//        return instance;
//    }
    @Autowired
    private NettyClient nettyClient;

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
    public Protocol sendJsonMsg(Protocol protocol) {
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
