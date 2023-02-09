package com.ifx.client;


import com.google.inject.Guice;
import com.google.inject.Injector;
import com.ifx.client.app.controller.LoginController;
import com.ifx.connect.connection.client.ClientToolkit;
import com.ifx.connect.properties.ClientNettyConfigProperties;
import javafx.application.Application;
import javafx.stage.Stage;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

import java.net.InetSocketAddress;


//@SpringBootApplication(scanBasePackages = "com.ifx")
@Slf4j
public class ClientApplication extends Application{


//    @Resource
    private ClientNettyConfigProperties configProperties;

    @Override
    public void start(Stage stage)   {
        Injector injector = Guice.createInjector();
        configProperties = injector.getInstance(ClientNettyConfigProperties.class);
        //接管FXPlus属性的创建
        Mono.just(ClientToolkit.getDefaultClientLifeStyle())
            .doOnNext(l-> {
                InetSocketAddress inetSocketAddress =
                    new InetSocketAddress(configProperties.getServerHost(), configProperties.getServerPort());
                l.reTryConnect(inetSocketAddress);
            })
            .subscribe()
        ;
       LoginController.show();
    }


    public static void main(String[] args){
//        SpringApplication.run(ClientApplication.class);
        Application.launch(ClientApplication.class);
    }
}
