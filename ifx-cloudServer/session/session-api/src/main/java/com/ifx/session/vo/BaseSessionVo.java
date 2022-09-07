package com.ifx.session.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.concurrent.ConcurrentSkipListSet;
@Data
public class BaseSessionVo  implements Serializable {


    private ConcurrentSkipListSet<String> account;

    private String sessionId;

    private String name;


}
