package com.ifx.connect.enums;

import cn.hutool.core.util.StrUtil;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@Getter@AllArgsConstructor
public enum CommandEnum {
    LOGIN("登录"),
    REGISTER("登录"),
    FORGET_PSD("登录"),
    USERINFO_UPDATE("登录");

    private final String desc;

    public  static  CommandEnum getByName(String name){
//        TODO 异常处理
        return Arrays.stream(CommandEnum.values()).filter(e-> StrUtil.equals(name, e.name())).findFirst().orElseThrow(() -> new RuntimeException("不存在的指令！"));
    }

}
