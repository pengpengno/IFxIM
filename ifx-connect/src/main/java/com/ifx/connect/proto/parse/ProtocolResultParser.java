package com.ifx.connect.proto.parse;

import com.alibaba.fastjson2.JSONException;
import com.ifx.common.res.Result;
import com.ifx.connect.proto.Protocol;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * 协议结果解析
 * @author pengpeng
 * @date 2023/1/9
 */
@Slf4j
public class ProtocolResultParser {

    /***
     * 获取返回结果 为集合
     * @param protocol
     * @param tClass
     * @return
     * @param <T>
     */
    public static <T> List<T> getDataAsList(Protocol protocol, Class<T> tClass){
        try{
            Result result = protocol.getResult();
            if (result == null){
                return null;
            }
            return result.getDataAsList(tClass);
        }catch (JSONException jsonException){
            log.error("序列化失败 !");
            return null;
        }
    }

    /***
     * 获取返回结果实体化为基本类
     * @param protocol
     * @param tClass
     * @return
     * @param <T>
     */
    public static <T> T getDataAsT(Protocol protocol, Class<T> tClass){
        try{
            Result result = protocol.getResult();
            if (result == null){
                return null;
            }
            return result.getDataAsTClass(tClass);
        }catch (JSONException jsonException){
            log.error("序列化失败 !");
            return null;
        }

    }
    /***
     * 获取返回结果为String
     * @param protocol
     * @return
     */
    public static String getDataAsString(Protocol protocol){
        Result result = protocol.getResult();
        if (result == null){
            return null;
        }
        return result.getDataAsString();
    }

}
