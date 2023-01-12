package com.ifx.client;


import com.ifx.client.app.controller.LoginController;
import com.ifx.connect.connection.client.ClientLifeStyle;
import com.ifx.connect.connection.client.ClientToolkit;
import javafx.application.Application;
import javafx.stage.Stage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import reactor.core.publisher.Mono;


@SpringBootApplication(scanBasePackages = "com.ifx")
@Slf4j
public class ClientApplication extends Application{


    @Override
    public void start(Stage stage)   {
        //接管FXPlus属性的创建
       LoginController.show();
    }


    public static void main(String[] args){
        SpringApplication.run(ClientApplication.class);
        Mono.just(ClientToolkit.getDefaultClientLifeStyle())
                .subscribe(ClientLifeStyle::reTryConnect);
        Application.launch(ClientApplication.class);
    }
}
