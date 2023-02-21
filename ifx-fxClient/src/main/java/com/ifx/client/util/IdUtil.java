package com.ifx.client.util;

/**
 * @author pengpeng
 * @description
 * @date 2023/2/1
 */
public class IdUtil {

    /***
     * 获取traceid
     * @return 返回随机生成的traceId
     */
    public static  String traceId(){
        return cn.hutool.core.util.IdUtil.simpleUUID();
    }
}
