package com.ifx.client;


import com.ifx.client.app.controller.LoginController;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ConfigurableApplicationContext;

/***
 * 由于module-info 的可见性隔离 使用下方启动类启动
 * @see  APP
 */

@Slf4j
public class ClientApplication extends Application {
    private ConfigurableApplicationContext applicationContext;

    @Override
    public void init() throws Exception {
        super.init();
        applicationContext = new SpringApplicationBuilder(APP.class).run();
        LoginController.show();
    }

    @Override
    public void stop() {
        applicationContext.close();
        Platform.exit();
    }

    @Override
    public void start(Stage stage)   {
        applicationContext.publishEvent(new StageReadyEvent(stage));

        LoginController.show();
    }
    static class StageReadyEvent extends ApplicationEvent {
        public StageReadyEvent(Stage stage) {
            super(stage);
        }
    }
    public static void main(String[] args){
        launch(ClientApplication.class);
    }
}
