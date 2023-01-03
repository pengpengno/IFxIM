package com.ifx.connect.proto;

import com.ifx.common.res.Result;
import com.ifx.connect.proto.dubbo.DubboApiMetaData;
import lombok.Data;

import java.io.Serializable;

/**
 * 通讯基础协议包
 */
@Data
public class Protocol implements Serializable {

  private String protocol;   //协议头
  /**
   * 请求体
   *
   * @see DubboApiMetaData
   */
  private String protocolBody; // 协议体

  private Long serial;  //包syn序列号
  /**
   * @see com.ifx.connect.proto.ifx.IFxMsgProtocol
   */
  private String type; // 请求类型

  private String clientMark;  // 客户端 唯一标识 用于再服务端建立与channel的绑定关系

  private String trace;  // trace

  private String clientSDKVersion;   // 客户端版本类类型

  private String content;

  private Result result;


}