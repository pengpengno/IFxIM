module ifx.fxClient {

    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires spring.amqp;
    requires spring.boot;
    requires spring.context;
    requires lombok;
    requires static spring.beans;
    requires spring.boot.autoconfigure;
    requires spring.webflux;
    requires reactor.netty.http;
    requires spring.web;
    requires ifx.connect.spring.boot.starter;
    requires ifx.connect;
    requires org.slf4j;
    requires com.alibaba.fastjson2;
    requires hutool.all;
    requires com.jfoenix;
    requires account.api;


//    exports com.ifx.client.api.controller.chat;
//    exports org.openjfx;
}