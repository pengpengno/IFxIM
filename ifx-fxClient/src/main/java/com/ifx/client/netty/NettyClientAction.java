package com.ifx.client.netty;

import com.ifx.connect.netty.client.ClientAction;
import com.ifx.connect.proto.Protocol;

public class NettyClientAction implements ClientAction {
    private NettyClient nettyClient = NettyClient.getInstance();
    private static  NettyClientAction instance = null;
    public  static NettyClientAction getInstance(){
        if (instance ==null){
            instance = new NettyClientAction();
        }
        return instance;
    }


    @Override
    public void sent(String msg) {
        nettyClient.write(msg);
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
