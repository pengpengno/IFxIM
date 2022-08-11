package com.ifx.client;


import cn.hutool.extra.spring.SpringUtil;
import com.ifx.client.util.SpringFxmlLoader;
import com.ifx.connect.netty.client.ClientAction;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import javax.annotation.Resource;


@SpringBootApplication(scanBasePackages = {"com.ifx"})
public class ClientApplication extends Application{

    @Resource
    private SpringFxmlLoader springFxmlLoader;
    @Override
    public void start(Stage stage) throws Exception {
        springFxmlLoader = SpringUtil.getBean(SpringFxmlLoader.class);
        String path = "com\\ifx\\client\\app\\fxml\\login.fxml";
        Scene scene = springFxmlLoader.applySinScene(path);
        stage.setScene(scene);
        stage.show();
    }


    public static void main(String[] args){
        SpringApplication.run(ClientApplication.class);
        Runnable runnable = () -> {
            ClientAction clientAction = SpringUtil.getBean(ClientAction.class);  // 启动netty
            clientAction.connect();
            if (!clientAction.isActive()) {
                clientAction.reConnect();
            }
        };
        runnable.run();
        Application.launch(ClientApplication.class);



    }
}
