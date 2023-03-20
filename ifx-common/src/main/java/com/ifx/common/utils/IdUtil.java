package com.ifx.common.utils;

/**
 * @author pengpeng
 * @description
 * @date 2023/2/16
 */
public class IdUtil {

    /***
     * 获取id
     * @return
     */
    public static Long getId(){
        return cn.hutool.core.util.IdUtil.getSnowflake().nextId();
    }
}
