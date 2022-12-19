package com.ifx.connect.proto.ifx;

import com.ifx.connect.proto.ProtocolHeaderConst;

/**
 * ifx 系统通信信息
 */
public interface IFxMsgProtocol extends ProtocolHeaderConst {

    String SERVER_TO_CLIENT_MSG_HEADER =  "S2C://";  // 主要用于服务端主动通知客户端的消息


    String CLIENT_TO_CLIENT_MSG_HEADER =  "C2C://";      // 用于客户端和客户端的通信

    String CLIENT_TO_SERVER_MSG_HEADER =  "C2S://";      // 用于客户端与服务端通信的协议



    /***特殊业务 Header 头**/
    String LOGIN_MSG_HEADER =  "C2S://LOG";  // 主要用于客户端登录请求

    String MSG_SENT_HEADER =  "S2C://MSG";  // 主要用于服务段消息派送的

}
