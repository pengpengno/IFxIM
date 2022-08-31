package com.ifx.connect.proto.dubbo;

/**
 * 基于串协议的实现
 * 此版本不做拓展
 */
public interface DubboApiConst {

    String signature = "interface";
    String method ="method";


    String LOGIN ="dubbo://interface::com.ifx.account.server.AccountService&method::login";

    String OFFINE ="dubbo://interface::com.ifx.account.server.AccountService&method::offline";

    String ALL_FRIEND_RELEATION ="dubbo://interface::com.ifx.account.server.AccountService&method::relation";

    String REGISTER = "dubbo://interface::com.ifx.account.server.AccountService&method::register";



}
