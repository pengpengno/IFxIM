package com.ifx.connect;

import com.ifx.connect.proto.Protocol;

public interface CglibInterface {
    default Protocol getProtocol() {
        return new Protocol();
    };
    default String getString() {
        return "dsadsa";
    };
}
