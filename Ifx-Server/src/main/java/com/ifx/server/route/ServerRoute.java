package com.ifx.server.route;

import com.ifx.connect.proto.Protocol;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;


public interface ServerRoute {

    public void getMeteService();

    public void toService(Protocol protocol);


}
