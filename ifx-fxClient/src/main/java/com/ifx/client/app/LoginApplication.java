//package com.ifx.client.app;
//
//import cn.edu.scau.biubiusuisui.annotation.FXScan;
//import cn.edu.scau.biubiusuisui.config.FXPlusApplication;
//import cn.hutool.core.exceptions.ExceptionUtil;
//import cn.hutool.core.io.FileUtil;
//import cn.hutool.extra.spring.SpringUtil;
//import com.ifx.client.ClientApplication;
//import com.ifx.client.connect.netty.NettyClient;
//import javafx.application.Application;
//import javafx.fxml.FXMLLoader;
//import javafx.scene.Scene;
//import javafx.stage.Stage;
//import lombok.SneakyThrows;
//
//import java.net.URL;
//@FXScan(base = "com.ifx.client.app.controller")
//public class LoginApplication extends Application {
//
////    @SneakyThrows
////    @Override
////    public void start(Stage primaryStage) throws Exception {
////        try{
////            NettyClient nettyClient = new NettyClient(8976);
//////            init.channel().writeAndFlush("standardised");
////            nettyClient.write("ewqeqweq");
////        }
////        catch (Throwable e ){
////            System.out.println(ExceptionUtil.stacktraceToString(e));
////            System.out.println("服务器链接失败！");
////        }
////        URL resource = FileUtil.file("com\\ifx\\client\\app\\fxml\\login.fxml").toURI().toURL();
////        FXMLLoader fxmlLoader = new FXMLLoader();
////        fxmlLoader.setLocation(resource);
////        Scene scene = new Scene(fxmlLoader.load());
//////        scene.getStylesheets().add(BootstrapFX.bootstrapFXStylesheet());
////        primaryStage.setScene(scene);
////        primaryStage.show();
////    }
//
//
//    @Override
//    public void start(Stage stage)   {
//        //接管FXPlus属性的创建
//        FXPlusApplication.start(ClientApplication.class, SpringUtil::getBean);
//    }
//}
