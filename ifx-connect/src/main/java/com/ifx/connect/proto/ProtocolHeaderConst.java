package com.ifx.connect.proto;

public interface ProtocolHeaderConst {

    public static String  DUBBO_PROTOCOL_HEADER = "dubbo://";   // 业务型协议

    public static String  NETTY_PROTOCOL_HEADER = "netty://";   // netty协议

    public static String  HEART_BEAT_PROTOCOL_HEADER = "heart://";  //心跳包协议
}
