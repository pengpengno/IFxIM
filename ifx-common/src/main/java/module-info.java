module ifx.common {
    exports com.ifx.common.ex.net;
    exports com.ifx.common.ex.valid;
    exports com.ifx.common.ex.connect;
    exports com.ifx.common.utils;
    exports com.ifx.common.base;
    exports com.ifx.common.context;

    requires lombok;

    requires hutool.all;
    requires com.google.protobuf;

    requires com.google.common;
    requires transmittable.thread.local;
    requires java.validation;
    requires com.github.benmanes.caffeine;
    requires jjwt.api;
}