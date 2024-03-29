package com.ifx.connect.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "ifx.connect.client")
@Data
public class  ClientNettyConfigProperties {

    private String serverHost ;   // default

    private Integer serverPort  ;

    private Integer connectTimeOut;

    private Integer retryTimes;

    private Boolean autoReConnect = Boolean.TRUE;

    public String getServerHost() {
        return serverHost;
    }

    public void setServerHost(String serverHost) {
        this.serverHost = serverHost;
    }

    public Integer getServerPort() {
        return serverPort;
    }

    public void setServerPort(Integer serverPort) {
        this.serverPort = serverPort;
    }

    public Integer getConnectTimeOut() {
        return connectTimeOut;
    }

    public void setConnectTimeOut(Integer connectTimeOut) {
        this.connectTimeOut = connectTimeOut;
    }

    public Integer getRetryTimes() {
        return retryTimes;
    }

    public void setRetryTimes(Integer retryTimes) {
        this.retryTimes = retryTimes;
    }

    public Boolean getAutoReConnect() {
        return autoReConnect;
    }

    public void setAutoReConnect(Boolean autoReConnect) {
        this.autoReConnect = autoReConnect;
    }
}
