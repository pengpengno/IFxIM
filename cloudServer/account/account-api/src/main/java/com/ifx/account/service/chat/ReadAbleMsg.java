package com.ifx.account.service.chat;

/**
 *
 * 可读信息
 * @author pengpeng
 * @description
 * @date 2023/3/29
 */
public interface ReadAbleMsg {

    public String read();

    public void write(byte[] bytes);


    public void write(String content);
}
