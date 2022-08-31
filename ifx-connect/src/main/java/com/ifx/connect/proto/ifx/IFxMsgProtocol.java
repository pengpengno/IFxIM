package com.ifx.connect.proto.ifx;

import com.ifx.connect.proto.ProtocolHeaderConst;

/**
 * ifx 系统通信信息
 */
public interface IFxMsgProtocol extends ProtocolHeaderConst {

    String SERVER_MSG_HEADER =  "ifxSMsg://";  // 主要用于服务端主动通知客户端的消息

    String CLIENT_MSG_HEADER =  "ifxCMsg://";

    public static String  HEART_BEAT_PROTOCOL_HEADER = "ifxHeart://";  //心跳包协议
}
