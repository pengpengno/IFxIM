package com.ifx.client;

import cn.hutool.core.io.FileUtil;
import cn.hutool.extra.spring.SpringUtil;
//import com.ifx.client.util.SpringFxmlLoader;
//import com.ifx.client.view.LoginView;
//import de.felixroske.jfxsupport.AbstractJavaFxApplicationSupport;
import com.ifx.client.util.SpringFxmlLoader;
import com.ifx.connect.netty.client.ClientAction;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.Resource;
import java.net.URL;


@SpringBootApplication(scanBasePackages = {"com.ifx"})
//public class ClientApplication extends AbstractJavaFxApplicationSupport {
public class ClientApplication extends Application{

    @Resource
    private SpringFxmlLoader springFxmlLoader;
    @Override
    public void start(Stage stage) throws Exception {
        springFxmlLoader = SpringUtil.getBean(SpringFxmlLoader.class);
        URL resource = FileUtil.file("com\\ifx\\client\\app\\fxml\\login.fxml").toURI().toURL();
        Scene scene = springFxmlLoader.applyStage(resource);

//        FXMLLoader fxmlLoader = new FXMLLoader();
//        fxmlLoader.setLocation(resource);
//        fxmlLoader.setControllerFactory(SpringUtil::getBean);
//        Scene scene = new Scene(fxmlLoader.load());
        stage.setScene(scene);
        stage.show();
    }


    public static void main(String[] args){
        SpringApplication.run(ClientApplication.class);
        Application.launch(ClientApplication.class);

        ClientAction clientAction = SpringUtil.getBean(ClientAction.class);  // 启动netty
        clientAction.connect();
        if (!clientAction.isActive()){
            clientAction.reConnect();
        }


    }
}
