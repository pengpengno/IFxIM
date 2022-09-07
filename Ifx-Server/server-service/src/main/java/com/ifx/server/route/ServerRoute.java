package com.ifx.server.route;

import com.ifx.connect.proto.Protocol;


public interface ServerRoute {

    public void getMeteService();

    public void toService(Protocol protocol);


}
