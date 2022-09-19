package com.ifx.common.res;

import cn.hutool.core.collection.CollectionUtil;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Result<T> implements Serializable {
    private static final long serialVersionUID = 1L;
    private int code;
    private String msg;
    private String extra;
    private int total;
    private int totalpage;
    private List<T> data;
    private T resData;

    public Result() {
        this.data = new ArrayList();
    }

    public void addData(T element) {
        this.data.add(element);
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

    public String getExtra() {
        return this.extra;
    }

    public void setExtra(String extra) {
        this.extra = extra;
    }

    public int getTotal() {
        return this.total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<T> getData() {
        return this.data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }

    public int getTotalpage() {
        return this.totalpage;
    }

    public void setTotalpage(int totalpage) {
        this.totalpage = totalpage;
    }

    public Result(Result.Builder<T> tBuilder) {
        this.code = tBuilder._code;
        this.msg = tBuilder._msg;
        this.extra = tBuilder._extra;
        this.total = tBuilder._total;
        this.totalpage = tBuilder._totalpage;
        this.data = tBuilder._data;
    }

    public static <T> Result<T> ok() {
        Result<T> Result = new Result();
        Result.setCode(0);
        return Result;
    }
    public static <T> Result<T> ok(T data) {
        Result<T> Result = new Result();
        Result.setCode(0);
        Result.setData(CollectionUtil.newArrayList(data));
        return Result;
    }

    public static <T> Result<T> ok(List<T> data) {
        Result<T> Result = new Result();
        Result.setCode(0);
        Result.setData(data);
        return Result;
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

        public Result.Builder<T> setExtra(String extra) {
            this._extra = extra;
            return this;
        }

        public Result.Builder<T> setTotal(int total) {
            this._total = total;
            return this;
        }

        public Result.Builder<T> setTotalpage(int totalpage) {
            this._totalpage = totalpage;
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

        public Result<T> build() {
            return new Result(this);
        }
    }
}
