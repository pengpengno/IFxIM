package com.ifx.connect.proto;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class Protocol<T> implements Serializable {
//  TODO base tcp header
    private Integer command;

    private Long serial;  //包syn

    private Long  length; // 包大小

//    private String from;
//
//    private String to;

    private Integer status;  //状态

    private String msg;  // 回传信息

    private String dubboUrI;  // 调用dubbo服务？  未来做此实现

    private Integer retryCount;  // 重试机制？   下一版本进行设计

    private String  route;   // 路由下一版本设计

    private List<T> data;  //数据结构则自行序列化

}
