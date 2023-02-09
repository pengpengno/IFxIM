package com.ifx.connect.connection.client.tcp;

import cn.hutool.core.exceptions.ExceptionUtil;
import com.ifx.connect.connection.client.ClientAction;
import com.ifx.connect.connection.client.ClientLifeStyle;
import com.ifx.connect.proto.Protocol;
import com.ifx.connect.task.handler.TaskHandler;
import com.ifx.connect.task.handler.def.DefaultHandler;
import com.ifx.exec.errorMsg.NetError;
import com.ifx.exec.ex.net.NetException;
import com.ifx.exec.ex.net.NettyException;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import lombok.extern.slf4j.Slf4j;

import java.net.InetSocketAddress;

@Slf4j
public class NettyClientAction implements ClientAction, ClientLifeStyle {

    private final TcpNettyClient tcpNettyClient;

    public NettyClientAction(){
        tcpNettyClient =  TcpNettyClient.getInstance();
    }

    private enum SingleInstance{
        INSTANCE;
        private final NettyClientAction instance;
        SingleInstance(){
            instance = new NettyClientAction();
        }
        private NettyClientAction getInstance(){
            return instance;
        }
    }
    public static NettyClientAction getInstance(){
        return NettyClientAction.SingleInstance.INSTANCE.getInstance();
    }

    @Override
    public Boolean connect() {
        try{
            tcpNettyClient.doOpen();
            log.debug("netty connect success ！ host :  {}  port : {} ", tcpNettyClient.getAddress().getHostName(), tcpNettyClient.getAddress().getPort());
            return Boolean.TRUE;
        }
        catch (Throwable e ){
//           log.error("服务器链接失败！ {}",ExceptionUtil.stacktraceToString(e));
           throw new NettyException(NetError.REMOTE_NET_CAN_NOT_CONNECT);
        }
    }

    @Override
    public Boolean connect(InetSocketAddress address) throws NetException {
        try {
            tcpNettyClient.doOpen(address);
            return Boolean.TRUE;
        }
        catch (Throwable e ){
//            log.error("服务器链接失败！ {}",ExceptionUtil.stacktraceToString(e));
            throw new NetException(NetError.REMOTE_NET_CAN_NOT_CONNECT);
        }
    }



    public void reConnectBlock() {
        reConnectTimeOut(5000L,3);
    }

    public void reConnectTimeOut(Long timeout,Integer retryTimes){
        try {
            log.error("retrying connect  timeout {}, retryTimes {}  ",timeout,retryTimes);
            Thread.sleep(timeout);
            connect();
            if (tcpNettyClient.getChannel() == null ){
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
            log.debug("reset channel");
    }

    @Override
    public Boolean sendJsonMsg(Protocol protocol, TaskHandler taskHandler) {
        return sendJsonMsg(protocol);
    }


    @Override
    public Boolean sendJsonMsg(Protocol protocol) throws NetException {
        if (protocol == null){
            log.warn("protocol is  null  no operate  to do ");
            return  Boolean.FALSE;
        }
        if (tcpNettyClient.getChannel() == null ||
                !tcpNettyClient.getChannel().isActive()
               ) {
            log.error("netty Channel is close， loading ");
            reTryConnect();
            return  Boolean.FALSE;
        }

        log.debug("sending msg to server ");
        ChannelFuture write = tcpNettyClient.write(protocol);
        write.addListener(future -> {
            if (future.isSuccess()){
                log.info("client send msg success ");
            }else {
                log.warn("client send msg fail");
            }
        });
        return Boolean.TRUE;
    }

    @Override
    public void keepAlive() {
//        心跳包机制  此处无需手动实现
        sendJsonMsg(new Protocol(), DefaultHandler.HEART_BEAT_HANDLER);
    }

    @Override
    public void releaseChannel() {
        tcpNettyClient.release();
    }

    public Boolean isAlive() {
        Channel channel = tcpNettyClient.getChannel();
        return channel != null && channel.isActive();
    }


}
