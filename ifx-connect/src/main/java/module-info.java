module ifx.connect {
    exports com.ifx.connect.enums;
    exports com.ifx.connect.handler;
    exports com.ifx.connect.connection.server;
    exports com.ifx.connect.connection.server.tcp;
    exports com.ifx.connect.connection.server.context;
    exports com.ifx.connect.connection;
    exports com.ifx.connect.connection.client;

    requires static lombok;
    requires reactor.netty.core;
    requires org.reactivestreams;
    requires reactor.core;
    requires hutool.all;
    requires com.google.protobuf;
    requires org.slf4j;


    requires spring.boot.autoconfigure;
    requires spring.context;
    requires io.netty.common;
    requires io.netty.buffer;
    requires com.google.common;
    requires ifx.common;
    requires jakarta.validation;
    requires io.netty.transport;
    requires com.github.benmanes.caffeine;
    requires com.google.guice;
    requires io.netty.handler;
    requires org.mapstruct;
}