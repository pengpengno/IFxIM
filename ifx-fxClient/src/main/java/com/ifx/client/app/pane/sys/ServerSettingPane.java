package com.ifx.client.app.pane.sys;

import com.ifx.client.app.pane.SearchPane;
import com.ifx.connect.properties.ClientNettyConfigProperties;
import javafx.scene.layout.Pane;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ServerSettingPane extends Pane {

    private String hostName;
    private Integer port;
    private Integer connectTimeOut;
    private ClientNettyConfigProperties nettyConfigProperties;
    private enum INSTANCE{
        INSTANCE;
        public final ServerSettingPane instance ;
        INSTANCE(){
            instance = new ServerSettingPane();
        }
        public static ServerSettingPane getInstance(){
            return INSTANCE.instance;
        }
    }
    public static ServerSettingPane getInstance(){
        return ServerSettingPane.INSTANCE.getInstance();
    }

    public String getHostName() {
        return hostName;
    }

    public void setHostName(String hostName) {
        this.hostName = hostName;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public Integer getConnectTimeOut() {
        return connectTimeOut;
    }

    public void setConnectTimeOut(Integer connectTimeOut) {
        this.connectTimeOut = connectTimeOut;
    }
}
