package com.ifx.client;


import cn.edu.scau.biubiusuisui.annotation.FXScan;
import cn.edu.scau.biubiusuisui.config.FXPlusApplication;
import cn.edu.scau.biubiusuisui.factory.BeanBuilder;
import cn.hutool.extra.spring.SpringUtil;
import com.ifx.client.util.SpringFxmlLoader;
import com.ifx.connect.netty.client.ClientLifeStyle;
import com.ifx.connect.properties.ClientNettyConfigProperties;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;

import javax.annotation.Resource;


@SpringBootApplication
@Slf4j
@FXScan(base = "com.ifx.client")
public class ClientApplication extends Application{

    @Override
    public void start(Stage stage)   {
        //接管FXPlus属性的创建
        FXPlusApplication.start(ClientApplication.class, SpringUtil::getBean);
    }


    public static void main(String[] args){
        SpringApplication.run(ClientApplication.class);
        Runnable runnable = () -> {
            ClientLifeStyle clientAction = SpringUtil.getBean(ClientLifeStyle.class);  // 启动netty
            clientAction.connect();
            if (!clientAction.isAlive()) {
                clientAction.reConnect();
            }
        };
        runnable.run();
    }
}
