package com.ifx.client;

import cn.hutool.core.exceptions.ExceptionUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.extra.spring.SpringUtil;
import com.ifx.client.netty.NettyClient;
import com.ifx.client.util.SpringFxmlLoader;
import com.ifx.connect.ConnectApplication;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.Resource;
import java.io.IOException;
import java.net.URL;

@SpringBootApplication(scanBasePackages = {"com.ifx"})
public class ClientApplication extends Application {
    @Override
    public void init() throws Exception {

    }

    @Override
    public void start(Stage stage) throws Exception {
//        Stage stage = new Stage();
        SpringFxmlLoader springFxmlLoader = new SpringFxmlLoader();
        URL resource = FileUtil.file("com\\ifx\\client\\app\\fxml\\login.fxml").toURI().toURL();
        springFxmlLoader.load()

        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(resource);
        Scene scene = new Scene(fxmlLoader.load());
//        scene.getStylesheets().add(BootstrapFX.bootstrapFXStylesheet());
        stage.setScene(scene);
        stage.show();
    }


    public static void main(String[] args){
        SpringApplication.run(ClientApplication.class);
        try{
            NettyClient bean = SpringUtil.getBean(NettyClient.class);  // 启动netty
            bean.doOpen(8976);
        }
        catch (Throwable e ){
            System.out.println(ExceptionUtil.stacktraceToString(e));
            System.out.println("服务器链接失败！");
        }//启动Spring容器

//        Application.launch(ClientApplication.class);

    }
}
