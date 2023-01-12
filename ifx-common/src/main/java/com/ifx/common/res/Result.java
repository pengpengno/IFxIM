package com.ifx.common.res;

import cn.hutool.core.util.ObjectUtil;
import com.alibaba.fastjson2.JSON;
import com.ifx.common.constant.CommonConstant;
import lombok.Data;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
@Data
public class Result implements Serializable {
    private static final long serialVersionUID = 1L;
    private int code;
    private String msg;
//    private Object res;
    private String res;

    /**
     * 将返回值 res  JSON序列化成指定的类型的对象
     * @param t
     * @return
     * @param <T>
     */
    public  <T>  T getDataAsTClass(Class<T> t) {
        if (res == null){
            return null;
        }
//        T objects = JSON.parseObject(res, t);
        return JSON.parseObject(res, t);
    }
    public String getDataAsString(){
        return res;
    }
    public  <T> Collection<T>  getCollection(Class<T> t) {
        return JSON.parseArray(res.toString(), t);
    }
    /**
     * 将返回值 res  JSON序列化成指定的类型的对象List
     * @param t
     * @return
     * @param <T>
     */
    public  <T> List<T>  getDataAsList(Class<T> t) {
        if (res == null){
            return null;
        }
        return JSON.parseArray(res.toString(), t);
    }

    public int getCode() {
        return this.code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return this.msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
    public void setRes(String res){
        if (ObjectUtil.isNull(code)){
            code = CommonConstant.SUCCESS_CODE;
        }
        if (msg == null){
            msg =   CommonConstant.SUCCESS_MSG;
        }
       this.res = res;
    }
    public static Result ok(String res){
        Result result = new Result();
        result.setCode( CommonConstant.SUCCESS_CODE);
        result.setMsg(CommonConstant.SUCCESS_MSG);
        result.setRes(res);
        return result;
    }

    public static <T> Result.Builder<T> builder() {
        return new Result.Builder();
    }

    public static class Builder<T> {
        private int _code = 0;
        private String _msg = "";
        private String _extra;
        private int _total;
        private int _totalpage;
        private List<T> _data;

        public Builder() {
        }

        public Builder(int code) {
            this._code = code;
        }

        public Result.Builder<T> setCode(int code) {
            this._code = code;
            return this;
        }

        public Result.Builder<T> setMsg(String msg) {
            this._msg = msg;
            return this;
        }


        public Result.Builder<T> setData(List<T> data) {
            this._data = data;
            return this;
        }

        public Result.Builder<T> addData(T one) {
            if (this._data == null) {
                this._data = new ArrayList();
            }

            this._data.add(one);
            return this;
        }

    }
}
