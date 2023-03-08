package com.ifx.connect.proto;

import com.ifx.common.res.Result;
import lombok.Data;

import java.io.Serializable;

/**
 * 通讯基础协议包
 */
@Data
@Deprecated
public class Protocol implements Serializable {

  private String protocol;   //协议头 标识协议体中类型
  /**
   * 请求体 不同实现
   * @see DubboApiMetaData
   */
  private String protocolBody; // 协议请求体

  /**
   * 请求类型
   * @see com.ifx.connect.proto.ifx.IFxMsgProtocol
   */
  private String type; // 请求操作类型

  private String clientMark;  // 客户端 唯一标识 用于再服务端建立与channel的绑定关系

  private String serverTrace;  // 服务端 追踪 trace

  private String clientTrace; // 客户端 追踪 trace

  private String clientSDKVersion;   // 客户端版本类类型

  private Result result; //返回结果



}