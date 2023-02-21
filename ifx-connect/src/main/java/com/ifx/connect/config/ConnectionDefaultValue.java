package com.ifx.connect.config;

import com.ifx.connect.enums.ConnectTypeEnums;

/**
 * 通信连接 部分模块的默认值
 *
 * @author pengpeng
 * @description  connect 系统模块的默认值
 * @date 2023/1/10
 */
public class ConnectionDefaultValue {
    /**
     * 返回系统默认连接类型
     * @return 系统默认
     */
    public  static  ConnectTypeEnums getDefaultConnectType(){
        return ConnectTypeEnums.TCP;
    }



}
