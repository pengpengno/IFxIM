package com.ifx.client.netty;

import com.ifx.connect.netty.client.ClientAction;
import com.ifx.connect.proto.Protocol;
import io.netty.channel.ChannelFuture;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("netty")
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
    public void init() {

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
    public void isClosed() {

    }
}
