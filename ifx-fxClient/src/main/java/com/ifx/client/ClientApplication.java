package com.ifx.client;

import cn.hutool.core.io.FileUtil;
import cn.hutool.extra.spring.SpringUtil;
//import com.ifx.client.util.SpringFxmlLoader;
//import com.ifx.client.view.LoginView;
//import de.felixroske.jfxsupport.AbstractJavaFxApplicationSupport;
import com.ifx.connect.netty.client.ClientAction;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.net.URL;


@SpringBootApplication(scanBasePackages = {"com.ifx"})
//public class ClientApplication extends AbstractJavaFxApplicationSupport {
public class ClientApplication extends Application{


    @Override
    public void start(Stage stage) throws Exception {
//        Stage stage = new Stage();
//        SpringFxmlLoader springFxmlLoader = new SpringFxmlLoader();
        URL resource = FileUtil.file("com\\ifx\\client\\app\\fxml\\login.fxml").toURI().toURL();
//        Object load = springFxmlLoader.load(resource);

        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(resource);
        fxmlLoader.setControllerFactory(bean -> SpringUtil.getBean(bean));
        Scene scene = new Scene(fxmlLoader.load());
//        Scene scene = new Scene(springFxmlLoader.applyLoad(resource).load());
//        scene.getStylesheets().add(BootstrapFX.bootstrapFXStylesheet());
        stage.setScene(scene);
        stage.show();
    }


    public static void main(String[] args){
        SpringApplication.run(ClientApplication.class);
//        launch(ClientApplication.class, LoginView.class,args);
        ClientAction clientAction = SpringUtil.getBean(ClientAction.class);  // 启动netty
        clientAction.connect();
//        if (!clientAction.isActive()){
//            clientAction.reConnect();
//        }

//        try{
//            NettyClient bean = SpringUtil.getBean(NettyClient.class);  // 启动netty
//            bean.doOpen(8976);
//        }
//        catch (Throwable e ){
//            System.out.println(ExceptionUtil.stacktraceToString(e));
//            System.out.println("服务器链接失败！");
//        }//启动Spring容器
        Application.launch(ClientApplication.class);

    }
}
