package com.ifx.connect.connection;

import com.ifx.connect.enums.ConnectTypeEnums;

/**
 * 通信连接 部分模块的默认值
 * @author pengpeng
 * @description 包含了 系统默认连接的类型
 * @date 2023/1/10
 */
public class ConnectionDefaultValue {

    public  static  ConnectTypeEnums getDefaultValue(){
        return ConnectTypeEnums.TCP;
    }
}
