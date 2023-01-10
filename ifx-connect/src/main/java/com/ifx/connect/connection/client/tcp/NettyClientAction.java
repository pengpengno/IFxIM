package com.ifx.connect.connection.client.tcp;

import cn.hutool.core.exceptions.ExceptionUtil;
import com.ifx.connect.connection.client.ClientAction;
import com.ifx.connect.connection.client.ClientLifeStyle;
import com.ifx.connect.proto.Protocol;
import com.ifx.connect.task.handler.TaskHandler;
import com.ifx.connect.task.TaskManager;
import com.ifx.connect.task.handler.def.DefaultHandler;
import com.ifx.exec.ex.net.NetException;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ThreadPoolExecutor;

//@Component("netty")
@Slf4j
public class NettyClientAction implements ClientAction, ClientLifeStyle {


//    @Autowired
    private TcpNettyClient tcpNettyClient;
//    @Autowired
    private TaskManager taskManager;

    private Long connectTimeout;  // 连接时间延

    private Long reConnectDelay; // 重试时延

    public NettyClientAction(){
        tcpNettyClient = new TcpNettyClient();
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
    public void connect() {
        try{
            tcpNettyClient.doOpen();
            log.info("netty connect succ ！ host :  {}  posrt : {} ", tcpNettyClient.getAddress().getHostName(), tcpNettyClient.getAddress().getPort());
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
    public void sendJsonMsg(Protocol protocol, TaskHandler taskHandler) {
        sendJsonMsg(protocol);
    }

    public void sendJsonMsg(Protocol protocol, TaskHandler taskHandler, ThreadPoolExecutor executor) {
        sendJsonMsg(protocol);
    }



    @Override
    public void sendJsonMsg(Protocol protocol) throws NetException {
        if (protocol == null){
            log.warn("protocol is  null  no operate  to do ");
            return ;
        }

        if (!tcpNettyClient.getChannel().isActive()
                || tcpNettyClient.getChannel() == null) {
            log.error("netty Channel is close， loading ");
            reConnect();
            return ;
        }

        log.debug("sending msg to server ");
        ChannelFuture write = tcpNettyClient.write(protocol);
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
        tcpNettyClient.release();
    }

    public Boolean isAlive() {
        Channel channel = tcpNettyClient.getChannel();
        return channel != null && channel.isActive();
    }
}
