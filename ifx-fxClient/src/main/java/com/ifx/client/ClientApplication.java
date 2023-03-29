package com.ifx.client;


import com.ifx.client.app.controller.LoginController;
import javafx.application.Application;
import javafx.stage.Stage;
import lombok.extern.slf4j.Slf4j;

/***
 * 由于module-info 的可见性隔离 使用下方启动类启动
 * @see  APP
 */

@Slf4j
public class ClientApplication extends Application {

    @Override
    public void start(Stage stage)   {
        LoginController.show();
    }

    public static void main(String[] args){
        launch(ClientApplication.class);
    }
}
