package com.ifx.server.s2c;

import com.ifx.connect.proto.Protocol;

public interface IServer2ClientAction {

    public void sendProtoCol(Protocol protocol);

    public void closeChanel();


}
