package com.ifx.connect.enums;

import cn.hutool.core.util.ObjectUtil;

/**
 * @author pengpeng
 * @description
 * @date 2023/3/7
 */
public enum ConnectionStatus {



    ACTIVE,

    OFFLINE,


    ;


    public static Boolean statusActive(ConnectionStatus status){
        return ObjectUtil.equals(status,ACTIVE);
    }
}
