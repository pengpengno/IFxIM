package com.ifx.connect.proto;

import lombok.Data;

@Data
public class Protocol {
//TODO base tcp header
    private Integer command;

    private Long serial;

    private Long  length;

    private String from;

    private String to;

    private Integer status;

    private String msg;

    private String dubboUrI;

    private Integer retryCount;

    private String  route;

}
