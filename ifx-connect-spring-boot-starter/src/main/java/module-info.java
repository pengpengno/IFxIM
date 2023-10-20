module ifx.connect.spring.boot.starter {
    exports com.ifx.connect.properties;
    requires spring.boot.autoconfigure;
    requires spring.context;
    requires com.rabbitmq.client;
    requires spring.beans;
    requires spring.rabbit;
    requires spring.boot;
    requires static lombok;
    requires reactor.netty.core;
    requires io.netty.buffer;
    requires com.google.protobuf;
    requires spring.core;
    requires ifx.connect;
    requires reactor.rabbitmq;
    requires reactor.core;
    requires hutool.all;
    requires org.reactivestreams;

    requires org.slf4j;


}