package com.ifx.client;



import cn.hutool.extra.spring.SpringUtil;
import com.ifx.client.util.SpringFxmlLoader;
import com.ifx.connect.netty.client.ClientLifeStyle;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


import javax.annotation.Resource;


@SpringBootApplication(scanBasePackages = "com.ifx")
@Slf4j
//@FXScan(base = "com.ifx.client")
public class ClientApplication extends Application{

    @Resource
    private SpringFxmlLoader springFxmlLoader;
    @Override
    public void start(Stage stage)   {
        //接管FXPlus属性的创建
        springFxmlLoader = SpringUtil.getBean(SpringFxmlLoader.class);
        Scene stage1 = SpringFxmlLoader.applySinScene("com/ifx/client/app/fxml/login.fxml");
        stage.setScene(stage1);
        stage.show();
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
        Application.launch(ClientApplication.class);
    }
}
